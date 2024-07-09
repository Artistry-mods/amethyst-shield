package chaos.amyshield.mixin.client;

import chaos.amyshield.autoupdater.gui.screen.AutoUpdaterScreen;
import chaos.amyshield.autoupdater.updater.Updater;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class ModUpdaterScreenButtonMixin extends Screen {
    protected ModUpdaterScreenButtonMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgetsNormal(II)V")
    private void initWidgetsNormal(int y, int spacingY, CallbackInfo info) {

        if (!Updater.CONFIG.SHOW_UPDATER_SCREEN_BUTTON()) {
            return;
        }

        TextIconButtonWidget modupdaterButton = this.addDrawableChild(TextIconButtonWidget.builder(Text.translatable("gui.updater.updater_screen.name"), (button -> {
            if (this.client != null) {
                this.client.setScreen(new AutoUpdaterScreen(this, Updater.getInstance().updaterList));
            }
        }), true).width(20).texture(Updater.UPDATER_ICON, 16, 16).build());

        modupdaterButton.setPosition(this.width / 2 - 124, y + spacingY * 2);
    }
}