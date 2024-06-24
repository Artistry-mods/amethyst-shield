package chaos.amyshield.updaterlib.updater.update_list;

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


public class UpdaterList {
    private static final Path UPDATER_LIST_FILE_LOCATION = FabricLoader.getInstance().getConfigDir().resolve("auto-updater").resolve("updater_list.json");
    private static final File UPDATER_LIST_FILE = UPDATER_LIST_FILE_LOCATION.toFile();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public List<String> filesToDelete;

    public UpdaterList() {
        this.filesToDelete = new ArrayList<>();
    }

    public UpdaterList(List<String> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    public void addToList(String fileName) {
        this.filesToDelete.add(fileName);
    }

    public UpdaterList createOrLoad() {
        if (UPDATER_LIST_FILE.exists()) {
            try (FileReader fileReader = new FileReader(UPDATER_LIST_FILE)) {
                return GSON.fromJson(fileReader, UpdaterList.class);
            } catch (IOException e) {
                ModUpdater.LOGGER.error("Failed to create or load deletion list to file");
            }
        }
        UpdaterList deletionList = new UpdaterList();
        deletionList.saveToFile();
        return deletionList;
    }

    public void saveToFile() {
        String json = GSON.toJson(this);
        if (!UPDATER_LIST_FILE.getParentFile().exists()) {
            if (UPDATER_LIST_FILE.getParentFile().mkdirs()) {
                ModUpdater.LOGGER.info("Directory created successfully on first time launch");
            } else {
                ModUpdater.LOGGER.error("Failed to create directory.");
                return; // Exit the method if directory creation fails
            }
        }
        try (FileWriter fileWriter = new FileWriter(UPDATER_LIST_FILE)) {
            fileWriter.write(json);
        } catch (IOException e) {
            ModUpdater.LOGGER.error("Failed to save deletion list to file");
        }
    }
}
