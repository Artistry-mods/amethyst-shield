package chaos.amyshield.autoupdater.mixin;

import chaos.amyshield.autoupdater.gui.screen.AutoUpdaterScreen;
import chaos.amyshield.autoupdater.updater.Updater;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class ModUpdaterScreenButtonMixin extends Screen {
    protected ModUpdaterScreenButtonMixin(Text title) {
        super(title);
    }

    @Shadow
    @Nullable
    protected abstract Text getMultiplayerDisabledText();

    @Inject(at = @At("HEAD"), method = "initWidgetsNormal(II)V", cancellable = true)
    private void initWidgetsNormal(int y, int spacingY, CallbackInfo info) {

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) -> {
            if (this.client != null) {
                this.client.setScreen(new SelectWorldScreen(this));
            }
        }).dimensions(this.width / 2 - 100, y, 200, 20).build());

        Text text = this.getMultiplayerDisabledText();
        boolean bl = text == null;
        Tooltip tooltip = text != null ? Tooltip.of(text) : null;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.multiplayer"), (button) -> {
            if (this.client != null) {
                Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
                this.client.setScreen(screen);
            }
        }).dimensions(this.width / 2 - 100, y + spacingY, 200, 20).tooltip(tooltip).build()).active = bl;

        if (Updater.CONFIG.SHOW_UPDATER_SCREEN_BUTTON()) {
            TextIconButtonWidget modupdaterButton = this.addDrawableChild(TextIconButtonWidget.builder(Text.translatable("gui.updater.updater_screen.name"), (button -> {
                if (this.client != null) {
                    this.client.setScreen(new AutoUpdaterScreen(this, Updater.getInstance().updaterList));
                }
            }), true).width(20).texture(Updater.UPDATER_ICON, 16, 16).build());
            modupdaterButton.setPosition(this.width / 2 - 124, y + spacingY * 2);
        }
        /*
		this.addDrawableChild(new TextIconButtonWidget(this.width / 2 - 124, y + spacingY * 2, 20, 20, 0, 0, 20, ICON, 20, 40, (button) -> {
			this.client.setScreen(new AutoUpdaterScreen(this, Updater.getInstance().updaterList));
		}, Text.translatable("gui.updater.updater_screen.name")));
         */


        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.online"), (buttonWidget) -> {
            if (this.client != null) {
                this.client.setScreen(new RealmsMainScreen(this));
            }
        }).dimensions(this.width / 2 - 100, y + spacingY * 2, 200, 20).tooltip(tooltip).build()).active = bl;
        info.cancel();
    }
}