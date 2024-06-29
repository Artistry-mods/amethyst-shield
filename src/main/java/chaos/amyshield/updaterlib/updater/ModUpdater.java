package chaos.amyshield.updaterlib.updater;

import chaos.amyshield.updaterlib.modrinth.quarry.ModrinthProjectVersionQuarry;
import chaos.amyshield.updaterlib.modrinth.version.ModrinthVersion;
import chaos.amyshield.updaterlib.modrinth.version.dependency.ModrinthDependency;
import chaos.amyshield.updaterlib.modrinth.version.file.ModrinthFile;
import chaos.amyshield.updaterlib.toast.UpdaterToast;
import chaos.amyshield.updaterlib.updater.deletion_list.DeletionList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.ToastManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModUpdater {
    public static final Logger LOGGER = LoggerFactory.getLogger("Updater Lib");
    public static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final Path MOD_DIRECTORY = FabricLoader.getInstance().getGameDir().resolve("mods");
    public static String minecraft_version = SharedConstants.getGameVersion().getName();
    public static ModUpdater INSTANCE = null;
    //public static final Path MOD_DIRECTORY = Path.of("C:/Users/User/AppData/Roaming/.minecraft", "mods");
    public static DeletionList DELETION_LIST_INSTANCE = null;

    private ModUpdater() {

    }

    public static ModUpdater getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModUpdater();
        }
        System.out.println(MOD_DIRECTORY);
        deletionInstance();
        return INSTANCE;
    }

    private static DeletionList deletionInstance() {
        if (DELETION_LIST_INSTANCE == null) {
            DELETION_LIST_INSTANCE = new DeletionList().createOrLoad();
            DELETION_LIST_INSTANCE.deleteAllFilesMarkedForDeletion();
            DELETION_LIST_INSTANCE.saveToFile();
        }
        return DELETION_LIST_INSTANCE;
    }

    private static void downloadFile(String fileUrl, Path destination) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .build();
        try {
            HttpResponse<Path> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(destination));

            if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
                ModUpdater.LOGGER.error("Failed to download file to: {}", destination);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonObject getModMetadata(Path jarFilePath) throws IOException {
        try (ZipFile zipFile = new ZipFile(jarFilePath.toFile())) {
            ZipEntry entry = zipFile.getEntry("fabric.mod.json");
            if (entry != null) {
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    Gson gson = new Gson();
                    return gson.fromJson(new String(inputStream.readAllBytes()), JsonObject.class);
                }
            }
        }
        return null;
    }

    private static String getFilesModId(Path jarFilePath) {
        try {
            JsonObject modMetadata = getModMetadata(jarFilePath);
            if (modMetadata != null) {
                return modMetadata.get("id").getAsString();
            } else {
                ModUpdater.LOGGER.warn("No mod modId metadata found.");
            }
        } catch (IOException e) {
            ModUpdater.LOGGER.error("Error reading mod metadata for modId: {}", e.getMessage());
        }
        return "";
    }

    private static String getFilesModName(Path jarFilePath) {
        try {
            JsonObject modMetadata = getModMetadata(jarFilePath);
            if (modMetadata != null) {
                return modMetadata.get("name").getAsString();
            } else {
                ModUpdater.LOGGER.warn("No mod name metadata found.");
            }
        } catch (IOException e) {
            ModUpdater.LOGGER.error("Error reading mod metadata for name: {}", e.getMessage());
        }
        return "";
    }

    private static String removeBasePath(String fullPath) {
        // Convert both paths to Path objects
        int lastSlashIndex = fullPath.lastIndexOf('\\');

        // Extract the substring from the last '/' to the end

        return fullPath.substring(lastSlashIndex + 1);
    }

    private static boolean doesModFileExist(String fileName) {
        try (Stream<Path> paths = Files.list(MOD_DIRECTORY)) {
            return paths.filter(Files::isRegularFile)
                    .anyMatch(path -> path.getFileName().toString().equals(fileName));
        } catch (IOException e) {
            System.err.println("Error while searching for files: " + e.getMessage());
        }
        return false;
    }

    private String getLoadedFileNameByModID(String modID) {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modID);
        if (modContainer.isPresent()) {
            String modPath = modContainer.get().getOrigin().toString();
            return removeBasePath(modPath);
        } else {
            ModUpdater.LOGGER.error("Mod with ID {} not found.", modID);
        }
        return "";
    }

    public void downloadModAndDependencies(String id) {
        if (!MinecraftClient.getInstance().getSession().getUsername().equals("Player")) {
            ModrinthProjectVersionQuarry quarry = new ModrinthProjectVersionQuarry(id);
            ModrinthVersion newestVersion = quarry.getFittingVersion();
            if (newestVersion != null) {
                ModrinthFile newestVersionFile = newestVersion.getPrimary();
                if (!doesModFileExist(newestVersionFile.filename)) {
                    ModUpdater.LOGGER.info("Detected a newer version for {}. Downloading {} now", id, newestVersionFile.filename);
                    for (ModrinthDependency dependency : newestVersion.dependencies) {
                        INSTANCE.downloadModAndDependencies(dependency.project_id);
                    }
                    try {
                        Path fileLocation = MOD_DIRECTORY.resolve(newestVersionFile.filename);
                        downloadFile(newestVersionFile.url, fileLocation);
                        String modId = getFilesModId(fileLocation);
                        String fileName = getLoadedFileNameByModID(modId);

                        ToastManager toastcomponent = MinecraftClient.getInstance().getToastManager();
                        UpdaterToast.show(toastcomponent, newestVersion, getFilesModName(fileLocation));

                        if (doesModFileExist(fileName)) {
                            if (!DELETION_LIST_INSTANCE.filesToDelete.contains(fileName)) {
                                DELETION_LIST_INSTANCE.addToList(fileName);
                                DELETION_LIST_INSTANCE.saveToFile();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    ModUpdater.LOGGER.info("The newest version for {} is installed. File named {}", id, newestVersionFile.filename);
                }
            }
        } else {
            ModUpdater.LOGGER.warn("can not check for updates since the client is in offline mode.");
        }
    }
}
