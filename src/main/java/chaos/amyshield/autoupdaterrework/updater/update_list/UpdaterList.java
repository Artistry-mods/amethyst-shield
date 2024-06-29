package chaos.amyshield.autoupdaterrework.updater.update_list;

import chaos.amyshield.autoupdaterrework.updater.Updater;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UpdaterList {
    private static final Path UPDATER_LIST_FILE_LOCATION = FabricLoader.getInstance().getConfigDir().resolve("auto-updater").resolve("updater_list.json");
    private static final File UPDATER_LIST_FILE = UPDATER_LIST_FILE_LOCATION.toFile();

    public List<UpdatePacket> filesToUpdate;

    public UpdaterList() {
        this.filesToUpdate = new ArrayList<>();
    }

    public void addToList(UpdatePacket fileName) {
        this.filesToUpdate.add(fileName);
    }

    public UpdaterList createOrLoad() {
        if (UPDATER_LIST_FILE.exists()) {
            try (FileReader fileReader = new FileReader(UPDATER_LIST_FILE)) {
                return Updater.GSON.fromJson(fileReader, UpdaterList.class);
            } catch (IOException e) {
                Updater.LOGGER.error("Failed to create or load deletion list to file");
            }
        }
        UpdaterList deletionList = new UpdaterList();
        deletionList.saveToFile();
        return deletionList;
    }

    public void saveToFile() {
        String json = Updater.GSON.toJson(this);
        if (!UPDATER_LIST_FILE.getParentFile().exists()) {
            if (UPDATER_LIST_FILE.getParentFile().mkdirs()) {
                Updater.LOGGER.info("Directory created successfully on first time launch");
            } else {
                Updater.LOGGER.error("Failed to create directory.");
                return; // Exit the method if directory creation fails
            }
        }
        try (FileWriter fileWriter = new FileWriter(UPDATER_LIST_FILE)) {
            fileWriter.write(json);
        } catch (IOException e) {
            Updater.LOGGER.error("Failed to save deletion list to file");
        }
    }

    public boolean contains(UpdatePacket updatePacket) {
        boolean doesContain = false;
        for (UpdatePacket presentUpdatePacket: this.filesToUpdate) {
            if (Objects.equals(presentUpdatePacket.oldModPath, updatePacket.oldModPath) && Objects.equals(presentUpdatePacket.download_url, updatePacket.download_url)) {
                doesContain = true;
                break;
            }
        }
        return doesContain;
    }

    public void downloadAll() {
        List<UpdatePacket> tmpFilesToUpdate = new ArrayList<>(this.filesToUpdate);
        for (UpdatePacket updatePacket: tmpFilesToUpdate) {
            updatePacket.download();
        }
    }

    public void removeFromList(UpdatePacket updatePacket) {
        this.filesToUpdate.remove(updatePacket);
        this.saveToFile();
    }
}
