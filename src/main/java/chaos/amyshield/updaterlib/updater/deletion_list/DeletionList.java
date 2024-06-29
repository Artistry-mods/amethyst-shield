package chaos.amyshield.updaterlib.updater.deletion_list;

import chaos.amyshield.updaterlib.updater.ModUpdater;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class DeletionList {
    private static final Path DELETION_LIST_FILE_LOCATION = FabricLoader.getInstance().getConfigDir().resolve("auto-updater").resolve("deletion_list.json");
    private static final File DELETION_LIST_FILE = DELETION_LIST_FILE_LOCATION.toFile();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public List<String> filesToDelete;

    public DeletionList() {
        this.filesToDelete = new ArrayList<>();
    }

    public DeletionList(List<String> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    public void addToList(String fileName) {
        this.filesToDelete.add(fileName);
    }

    public void deleteAllFilesMarkedForDeletion() {
        List<String> filesToDeleteCopy = this.filesToDelete;
        for (String fileName : filesToDeleteCopy) {
            File file = new File(ModUpdater.MOD_DIRECTORY.resolve(fileName).toUri());
            if (file.exists()) {
                if (file.delete()) {
                    ModUpdater.LOGGER.info("Deleted file successfully at {}", file);
                } else {
                    ModUpdater.LOGGER.error("Failed to delete file");
                }
            } else {
                ModUpdater.LOGGER.error("File marked for deletion not found");
            }
        }
        this.filesToDelete.clear();
        this.saveToFile();
    }

    public DeletionList createOrLoad() {
        if (DELETION_LIST_FILE.exists()) {
            try (FileReader fileReader = new FileReader(DELETION_LIST_FILE)) {
                return GSON.fromJson(fileReader, DeletionList.class);
            } catch (IOException e) {
                ModUpdater.LOGGER.error("Failed to create or load deletion list to file");
            }
        }
        DeletionList deletionList = new DeletionList();
        deletionList.saveToFile();
        return deletionList;
    }

    public void saveToFile() {
        String json = GSON.toJson(this);
        if (!DELETION_LIST_FILE.getParentFile().exists()) {
            if (DELETION_LIST_FILE.getParentFile().mkdirs()) {
                ModUpdater.LOGGER.info("Directory created successfully on first time launch");
            } else {
                ModUpdater.LOGGER.error("Failed to create directory.");
                return; // Exit the method if directory creation fails
            }
        }

        try (FileWriter fileWriter = new FileWriter(DELETION_LIST_FILE)) {
            fileWriter.write(json);
        } catch (IOException e) {
            ModUpdater.LOGGER.error("Failed to save deletion list to file");
        }
    }
}
