package chaos.amyshield.updaterlib.toast;

import chaos.amyshield.updaterlib.modrinth.version.ModrinthVersion;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.util.Identifier;

public class UpdaterToast implements Toast {
    private static final Identifier TEXTURE = Identifier.ofVanilla("toast/recipe");
    private static final Identifier ICON = Identifier.of("updaterlib","textures/gui/auto-updater-logo-big.png");
    private static final long DEFAULT_DURATION_MS = 5000L;
    private final ModrinthVersion version;
    private final String modName;

    public UpdaterToast(ModrinthVersion version, String modName) {
        this.version = version;
        this.modName = modName;
    }

    @Override
    public Visibility draw(DrawContext context, ToastManager manager, long startTime) {
        context.drawTexture(TEXTURE, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        context.drawTexture(ICON,5,5,0,0,22, 22);
        context.drawText(manager.getClient().textRenderer, this.modName, 30, 7, -11534256, false);
        context.drawText(manager.getClient().textRenderer, this.version.name, 30, 18, -16777216, false);
        return (double)startTime >= 5000.0 * manager.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
    }

    public static void show(ToastManager manager, ModrinthVersion version, String modName) {
        manager.add(new UpdaterToast(version, modName));
    }
}
