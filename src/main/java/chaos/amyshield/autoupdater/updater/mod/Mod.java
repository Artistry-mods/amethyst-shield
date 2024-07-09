package chaos.amyshield.autoupdater.updater.mod;

import chaos.amyshield.autoupdater.updater.Updater;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Mod {
    public String modId;
    public String modrinthId;
    public Path modPath;

    public Mod(String modId, String modrinthId) {
        this.modId = modId;
        this.modrinthId = modrinthId;
        this.modPath = getModPath(modId);
        //D:\Minecraft Mods\autoupdaterrework\run\mods\sculk-latch-1.0.5.jar
        //sculk-latch-1.0.5.jar
        //D:\Minecraft Mods\autoupdaterrework\run\mods
        //System.out.println(this.modPath);
        //System.out.println(this.modPath.getFileName());
        //System.out.println(this.modPath.getParent());
    }

    private static Path getModPath(String modId) {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modId);
        if (modContainer.isPresent()) {
            List<Path> modPaths = modContainer.get().getOrigin().getPaths();
            if (!modPaths.isEmpty()) {
                return modPaths.getFirst();
            } else {
                Updater.LOGGER.error("No paths found for mod with ID {}", modId);
            }
        } else {
            Updater.LOGGER.error("Mod with ID {} not found.", modId);
        }
        return null;
    }
}
