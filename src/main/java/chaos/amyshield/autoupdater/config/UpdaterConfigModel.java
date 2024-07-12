package chaos.amyshield.autoupdater.config;

import chaos.amyshield.autoupdater.updater.Updater;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Nest;
import io.wispforest.owo.config.annotation.RestartRequired;

@Modmenu(modId = Updater.MOD_ID)
@Config(name = "updater-lib-config", wrapperName = "UpdaterConfig")
public class UpdaterConfigModel {
    @RestartRequired
    public boolean SHOW_UPDATER_SCREEN_BUTTON = true;
    public boolean DISABLE_UPDATER = false;

    @Nest
    public UpdateNested updateNested = new UpdateNested();

    public static class UpdateNested {
        public boolean AUTO_UPDATE = false;
        public boolean DOWNLOAD_OPTIONAL_DEPENDENCIES = true;
        public boolean UPDATE_TO_BETA = true;
        public boolean UPDATE_TO_ALPHA = false;
    }
}
