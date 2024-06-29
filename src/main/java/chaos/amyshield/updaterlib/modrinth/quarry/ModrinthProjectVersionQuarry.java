package chaos.amyshield.updaterlib.modrinth.quarry;

import chaos.amyshield.updaterlib.modrinth.version.ModrinthVersion;
import chaos.amyshield.updaterlib.updater.ModUpdater;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static chaos.amyshield.updaterlib.updater.ModUpdater.HTTP_CLIENT;


public class ModrinthProjectVersionQuarry {
    public ModrinthVersion[] versions = null;
    public String QuarryUrl = "";

    //https://api.modrinth.com/v2/project/sxqnyZvD/version?game_versions=[%221.20.1%22]&loaders=[%22fabric%22]

    public ModrinthProjectVersionQuarry() {

    }

    public ModrinthProjectVersionQuarry(String id) {
        this.QuarryUrl = "https://api.modrinth.com/v2/project/" + id + "/version?game_versions=[%22" + ModUpdater.minecraft_version + "%22]&loaders=[%22fabric%22]";
        //System.out.println(this.QuarryUrl);
        this.versions = quarry(this.QuarryUrl).versions;
    }

    private static ModrinthProjectVersionQuarry quarry(String url) {
        ModrinthProjectVersionQuarry quarry = new ModrinthProjectVersionQuarry();
        quarry.QuarryUrl = url;

        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).build();
            try {
                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
                    ModUpdater.LOGGER.error("Failed to contact the modrinth api");
                }

                if (response.body() != null) {
                    String responseData = response.body();
                    Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
                    quarry.versions = gson.fromJson(responseData, ModrinthVersion[].class);
                }
            } catch (IOException | InterruptedException e) {
                ModUpdater.LOGGER.error("Failed to contact the modrinth api or the internet in general");
            }
        } catch (URISyntaxException e) {
            ModUpdater.LOGGER.error("Failed to build the HttpRequest");
        }
        return quarry;
    }

    public ModrinthVersion getFittingVersion() {
        if (this.versions != null) {
            for (ModrinthVersion version : this.versions) {
                if (Arrays.asList(version.game_versions).contains(ModUpdater.minecraft_version) &&
                        Arrays.asList(version.loaders).contains("fabric")) {
                    return version;
                }
            }
        }
        return null;
    }
}
