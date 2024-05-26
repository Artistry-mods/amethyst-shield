package chaos.amyshield.ui.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.util.IEntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;

public class ChargeHudOverlay implements HudRenderCallback {

    private static final Identifier CHARGE_UI_ATLAS = new Identifier(AmethystShield.MOD_ID, "textures/ui/amethyst_shield_ui.png");
    //assets/amyshield/textures/ui/charge_ui_frame.png
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        RenderSystem.enableBlend();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int x = width / 2;
            int y = height;
            if (client.player != null && !client.player.isSpectator()) {
                ClientPlayerEntity player = client.player;
                if (player.getMainHandStack().getItem().equals(ModItems.AMETHYST_SHIELD) ||
                    player.getOffHandStack().getItem().equals(ModItems.AMETHYST_SHIELD)) {

                    int yshift = 53;
                    if (player.getAbilities().creativeMode) yshift -= 17;
                    int maxAir = player.getMaxAir();
                    int playerAir = Math.min(player.getAir(), maxAir);
                    if (player.isSubmergedIn(FluidTags.WATER) || playerAir < maxAir) yshift += 10;
                    LivingEntity livingEntity = this.getRiddenEntity();
                    if (livingEntity != null) {
                        int i = this.getHeartCount(livingEntity);
                        if (i > 10) {
                            yshift += 10;
                        }
                    }
                    drawContext.drawTexture(CHARGE_UI_ATLAS, x + 10, y - yshift, 0, 0, 81, 13);
                    drawContext.drawTexture(CHARGE_UI_ATLAS, x + 10, y - yshift + 5, 0, 15, (int) (81f * ((((IEntityDataSaver) player).getPersistentData().getFloat("charge")) / 100)), 5);
                }
            }
        }
    }
    private LivingEntity getRiddenEntity() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            Entity entity = playerEntity.getVehicle();
            if (entity == null) {
                return null;
            }
            if (entity instanceof LivingEntity) {
                return (LivingEntity)entity;
            }
        }
        return null;
    }
    private int getHeartCount(LivingEntity entity) {
        if (entity == null || !entity.isLiving()) {
            return 0;
        }
        float f = entity.getMaxHealth();
        int i = (int)(f + 0.5f) / 2;
        if (i > 30) {
            i = 30;
        }
        return i;
    }
    private PlayerEntity getCameraPlayer() {
        if (!(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity)) {
            return null;
        }
        return (PlayerEntity)MinecraftClient.getInstance().getCameraEntity();
    }
}
