package chaos.amyshield.autoupdater.updater.update_list;

import chaos.amyshield.autoupdater.modrinth.version.ModrinthVersion;
import chaos.amyshield.autoupdater.updater.Updater;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdatePacket {
    public String download_url;
    public String updatePath;
    public String oldModPath;

    public UpdatePacket(String updatePath, String download_url, String oldModPath) {
        this.download_url = download_url;
        this.updatePath = updatePath;
        this.oldModPath = oldModPath;
    }

    public void download() {
        Path downloadPath = Path.of(this.updatePath + "\\" + this.getName());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.download_url))
                .build();
        try {
            if (!Files.exists(downloadPath)) {
                Updater.LOGGER.info("Downloading file to: {} form: {}", downloadPath, this.download_url);

                HttpResponse<Path> response = Updater.OK_HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(downloadPath));

                if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
                    Updater.LOGGER.error("Failed to download file to: {}", downloadPath);
                }

                Updater.getInstance().deletionList.addToList(this.oldModPath);
                Updater.getInstance().updaterList.removeFromList(this);
            }

        } catch (InterruptedException | IOException e) {
            Updater.LOGGER.error("Failed to download from: {}", this.download_url);
        }
    }

    public String getName() {
        int lastSlashIndex = this.download_url.lastIndexOf('/');

        // Extract the substring from the last '/' to the end

        return this.download_url.substring(lastSlashIndex + 1);
    }

    public static class AdvancedUpdatePacket {
        public ModrinthVersion mod;
        public UpdatePacket updatePacket;

        public AdvancedUpdatePacket(ModrinthVersion mod, UpdatePacket updatePacket) {
            this.mod = mod;
            this.updatePacket = updatePacket;
        }
    }
}
