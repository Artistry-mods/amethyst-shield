package chaos.amyshield.autoupdaterrework.gui.screen;

import chaos.amyshield.autoupdaterrework.updater.update_list.UpdatePacket;
import chaos.amyshield.autoupdaterrework.updater.update_list.UpdaterList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;


public class AutoUpdaterScreen extends Screen {
    protected final Screen parent;
    private ModSelectionListWidget modSelectionList;
    private final UpdaterList updaterList;

    public AutoUpdaterScreen(Screen parent, UpdaterList updaterList) {
        super(Text.translatable("gui.updater.updater_screen.name"));
        this.parent = parent;
        this.updaterList = updaterList;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        this.modSelectionList = new ModSelectionListWidget(this.client);
        this.addSelectableChild(this.modSelectionList);

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            this.onDone();
        }).dimensions(this.width / 2 - 155, this.height - 38, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.updater.updater_screen.update_button"), (button) -> {
            this.onUpdate();
        }).dimensions(this.width / 2, this.height - 38, 150, 20).build());
    }

    public void onDone() {
        if (this.client == null) return;
        this.client.setScreen(this.parent);
    }

    public void onUpdate() {
        if (this.modSelectionList.getSelectedOrNull() == null) {
            return;
        }
        this.modSelectionList.getSelectedOrNull().updatePacket.download();
        this.modSelectionList.removeEntry(this.modSelectionList.getSelectedOrNull(), true);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.modSelectionList.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 16, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    @Environment(EnvType.CLIENT)
    private class ModSelectionListWidget extends AlwaysSelectedEntryListWidget<ModSelectionListWidget.ModEntry> {
        public ModSelectionListWidget(MinecraftClient client) {
            super(client, AutoUpdaterScreen.this.width, AutoUpdaterScreen.this.height, 32, AutoUpdaterScreen.this.height - 65 + 4, 18);

            AutoUpdaterScreen.this.updaterList.filesToUpdate.forEach(updatePacket -> {
                ModEntry languageEntry = new ModEntry(updatePacket);
                this.addEntry(languageEntry);
            });
            if (this.getSelectedOrNull() != null) {
                this.centerScrollOn(this.getSelectedOrNull());
            }
        }

        public void removeEntry(ModEntry updatePacket, boolean lol) {
            this.removeEntry(updatePacket);
        }

        protected int getScrollbarPositionX() {
            return super.getScrollbarPositionX() + 20;
        }

        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        @Environment(EnvType.CLIENT)
        public class ModEntry extends Entry<ModEntry> {
            public UpdatePacket updatePacket;
            private long clickTime;

            public ModEntry(UpdatePacket packet) {
                this.updatePacket = packet;
                // int x, int y, int width, int height, int u, int v, Identifier texture, ButtonWidget.PressAction pressAction
                /*
                AutoUpdaterScreen.this.addDrawableChild(new TexturedButtonWidget(0, 0, 20, 20, 0, 0, ClickableWidget.WIDGETS_TEXTURE, button -> {
                    this.updatePacket.download();
                    AutoUpdaterScreen.this.languageManager = Updater.getInstance().updaterList.createOrLoad();
                }));

                 */
            }

            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                context.drawCenteredTextWithShadow(AutoUpdaterScreen.this.textRenderer, this.updatePacket.getName(), ModSelectionListWidget.this.width / 2, y + 1, 16777215);
            }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    this.onPressed();
                    if (Util.getMeasuringTimeMs() - this.clickTime < 250L) {
                        AutoUpdaterScreen.this.onUpdate();
                    }

                    this.clickTime = Util.getMeasuringTimeMs();
                    return true;
                } else {
                    this.clickTime = Util.getMeasuringTimeMs();
                    return false;
                }
            }

            void onPressed() {
                ModSelectionListWidget.this.setSelected(this);
            }

            @Override
            public Text getNarration() {
                return Text.of(this.updatePacket.getName());
            }
        }
    }
}
