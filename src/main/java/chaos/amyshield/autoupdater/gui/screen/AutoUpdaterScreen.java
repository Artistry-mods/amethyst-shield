package chaos.amyshield.autoupdater.gui.screen;

import chaos.amyshield.autoupdater.updater.Updater;
import chaos.amyshield.autoupdater.updater.update_list.UpdatePacket;
import chaos.amyshield.autoupdater.updater.update_list.UpdaterList;
import io.wispforest.owo.config.ui.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class AutoUpdaterScreen extends Screen {
    public final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    protected final Screen parent;
    private final UpdaterList updaterList;
    public TextIconButtonWidget settings_button;
    protected OptionListWidget body;
    private ModSelectionListWidget languageSelectionList;

    public AutoUpdaterScreen(Screen parent, UpdaterList updaterList) {
        super(Text.translatable("gui.updater.updater_screen.name"));
        this.parent = parent;
        this.updaterList = updaterList;
        this.layout.setFooterHeight(53);
    }

    @Override
    protected void init() {
        this.initHeader();
        this.initBody();
        this.initFooter();
        this.layout.forEachChild(this::addDrawableChild);
        this.initTabNavigation();
    }

    /*
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        //context.drawTextWithShadow(this.textRenderer,"refresh", (int) this.client.mouse.getX() / 2 + 5, (int) this.client.mouse.getY() / 2 - 10, Colors.LIGHT_GRAY);
    }
     */

    protected void initHeader() {
        this.layout.addHeader(this.title, this.textRenderer);
    }

    protected void initBody() {
        this.languageSelectionList = this.layout.addBody(new ModSelectionListWidget(this.client));
    }

    protected void initFooter() {
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.vertical()).spacing(8);
        directionalLayoutWidget.getMainPositioner().alignHorizontalCenter();
        DirectionalLayoutWidget directionalLayoutWidget2 = directionalLayoutWidget.add(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.translatable("gui.updater.updater_screen.update_button"), (button) -> this.onUpdate()).build());
        directionalLayoutWidget2.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.onDone()).build());
        this.settings_button = this.addDrawableChild(TextIconButtonWidget.builder(Text.literal("Settings"), (button -> {
                    if (this.client == null) return;
                    this.client.setScreen(ConfigScreen.create(Updater.CONFIG, this));
                }), true)
                .width(20)
                .texture(Updater.SETTINGS_ICON, 16, 16)
                .build());
        //375, 204
    }

    @Override
    protected void initTabNavigation() {
        this.layout.refreshPositions();
        if (this.body != null) {
            this.body.position(this.width, this.layout);
        }
        this.settings_button.setPosition(this.width - 36, this.height - 36);
        this.languageSelectionList.position(this.width, this.layout);
    }

    @Override
    public void removed() {
        if (this.client == null) return;
        this.client.options.write();
    }

    @Override
    public void close() {
        if (this.body != null) {
            this.body.applyAllPendingValues();
        }
        if (this.client == null) return;
        this.client.setScreen(this.parent);
    }

    public void onDone() {
        if (this.client == null) return;
        this.client.setScreen(this.parent);
    }

    public void onUpdate() {
        if (this.languageSelectionList.getSelectedOrNull() == null) {
            return;
        }
        this.languageSelectionList.getSelectedOrNull().updatePacket.download();
        this.languageSelectionList.removeModEntry(this.languageSelectionList.getSelectedOrNull());
    }

    @Environment(EnvType.CLIENT)
    private class ModSelectionListWidget extends AlwaysSelectedEntryListWidget<ModSelectionListWidget.ModEntry> {
        public ModSelectionListWidget(MinecraftClient client) {
            super(client, AutoUpdaterScreen.this.width, AutoUpdaterScreen.this.height, 32, 18);

            AutoUpdaterScreen.this.updaterList.filesToUpdate.forEach(updatePacket -> {
                ModEntry languageEntry = new ModEntry(updatePacket);
                this.addEntry(languageEntry);
            });
            if (this.getSelectedOrNull() != null) {
                this.centerScrollOn(this.getSelectedOrNull());
            }
        }

        public void removeModEntry(ModEntry updatePacket) {
            this.removeEntry(updatePacket);
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