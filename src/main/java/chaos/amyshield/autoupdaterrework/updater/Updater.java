package chaos.amyshield.autoupdaterrework.updater;

import chaos.amyshield.autoupdaterrework.modrinth.quarry.ModrinthProjectVersionQuarry;
import chaos.amyshield.autoupdaterrework.modrinth.version.ModrinthVersion;
import chaos.amyshield.autoupdaterrework.modrinth.version.dependency.ModrinthDependency;
import chaos.amyshield.autoupdaterrework.modrinth.version.file.ModrinthFile;
import chaos.amyshield.autoupdaterrework.updater.deletion_list.DeletionList;
import chaos.amyshield.autoupdaterrework.updater.mod.Mod;
import chaos.amyshield.autoupdaterrework.updater.update_list.UpdatePacket;
import chaos.amyshield.autoupdaterrework.updater.update_list.UpdaterList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Updater {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Logger LOGGER = LoggerFactory.getLogger("Updater Lib");
    public static final HttpClient OK_HTTP_CLIENT = HttpClient.newHttpClient();
    public static final String minecraft_version = SharedConstants.getGameVersion().getName();
    public static Updater INSTANCE = null;

    public List<UpdatePacket.AdvancedUpdatePacket> advancedUpdaterList = new ArrayList<>();
    public UpdaterList updaterList;
    public DeletionList deletionList;

    private Updater() {
        this.deletionList = new DeletionList().createOrLoad();
        this.deletionList.deleteAllFilesMarkedForDeletion();
        this.updaterList = new UpdaterList().createOrLoad();
    }

    public static Updater getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Updater();
        }
        return INSTANCE;
    }

    public void markForDownloadWithDependencies(@NotNull String modrinthId) {
        //return when you are in a dev env because it would have some issues with the FAPI and other deps
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Updater.LOGGER.info("Did not check for updates since the mod was launched in a dev environment");
            return;
        }

        //quarry the mod on Modrinth
        ModrinthProjectVersionQuarry quarry = new ModrinthProjectVersionQuarry(modrinthId);
        String modId = fetchModId(quarry.getFittingVersion().getPrimary().url);
        Mod oldMod = new Mod(modId, modrinthId);
        //return if the old version of the mod is not existent
        if (oldMod.modPath == null) {
            return;
        }

        //return if the quarry did not find a fitting version
        ModrinthVersion newestVersion = quarry.getFittingVersion();
        if (newestVersion == null) {
            return;
        }

        ModrinthFile newestVersionFile = newestVersion.getPrimary();
        //return if the newest version is already installed
        if (doesModFileExist(oldMod.modPath.getParent(), newestVersionFile.filename)) {
            Updater.LOGGER.info("The newest version for {} is installed. File named {}", modId, newestVersionFile.filename);
            return;
        }

        for (ModrinthDependency dependency : newestVersion.dependencies) {
            INSTANCE.markForDownloadWithDependencies(dependency.project_id);
        }

        UpdatePacket modToUpdate = new UpdatePacket(oldMod.modPath.getParent().toString(), newestVersionFile.url, oldMod.modPath.toString());
        if (!this.updaterList.contains(modToUpdate)) {
            this.updaterList.addToList(modToUpdate);
            this.advancedUpdaterList.add(new UpdatePacket.AdvancedUpdatePacket(newestVersion, modToUpdate));
            this.updaterList.saveToFile();
        }
        Updater.LOGGER.info("Detected a newer version for {}. Marking {} for download now", modId, newestVersionFile.filename);
    }

    private static boolean doesModFileExist(Path filepath, String fileName) {
        try (Stream<Path> paths = Files.list(filepath)) {
            return paths.filter(Files::isRegularFile)
                    .anyMatch(path -> path.getFileName().toString().equals(fileName));
        } catch (IOException e) {
            System.err.println("Error while searching for files: " + e.getMessage());
        }
        return false;
    }

    public static String fetchModId(String urlString) {
        String modId = null;
        try {
            InputStream inputStream = downloadFile(urlString);
            modId = processJarFile(inputStream);
        } catch (IOException e) {
            Updater.LOGGER.error("Failed to fetch modId from: {}", urlString);
        }
        return modId;
    }

    private static InputStream downloadFile(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        return connection.getInputStream();
    }

    private static String processJarFile(InputStream inputStream) throws IOException {
        String modId = null;
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("fabric.mod.json")) {
                    modId = extractModIdFromJson(zipInputStream);
                    break;
                }
                zipInputStream.closeEntry();
            }
        }
        return modId;
    }

    private static String extractModIdFromJson(InputStream inputStream) {
        String modId = null;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            modId = jsonObject.get("id").getAsString();
        } catch (Exception e) {
            Updater.LOGGER.error("Failed to fetch modId");
        }
        return modId;
    }
}
