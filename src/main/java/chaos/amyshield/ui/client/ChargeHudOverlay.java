package chaos.amyshield.ui.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.util.IEntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profilers;

import java.util.function.Function;

public class ChargeHudOverlay implements HudLayerRegistrationCallback {

    private static final Identifier CHARGE_UI_ATLAS = Identifier.of(AmethystShield.MOD_ID, "hud/amethyst_shield_ui");

    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options.hudHidden) {
            return;
        }

        if (client.player == null ||client.player.isSpectator()) {
            return;
        }
        ClientPlayerEntity player = client.player;

        if (!player.getMainHandStack().getItem().equals(ModItems.AMETHYST_SHIELD) &&
                !player.getOffHandStack().getItem().equals(ModItems.AMETHYST_SHIELD)) {
            return;
        }

        Profilers.get().swap("charge");

        int width = drawContext.getScaledWindowWidth();
        int height = drawContext.getScaledWindowHeight();
        int x = width / 2;
        int y = height;

        int yshift = 53 + AmethystShield.CONFIG.amethystShieldNested.CHARGE_BAR_OFFSET();
        int maxAir = player.getMaxAir();
        int playerAir = Math.min(player.getAir(), maxAir);
        if (player.getAbilities().creativeMode) yshift -= 17;
        if ((player.isSubmergedIn(FluidTags.WATER) || playerAir < maxAir) && !player.getAbilities().creativeMode) yshift += 10;
        LivingEntity livingEntity = this.getRiddenEntity();

        if (livingEntity != null) {
            int i = this.getHeartCount(livingEntity);
            if (i > 10) {
                yshift += 10;
            }
            if (player.getAbilities().creativeMode) yshift += 17;
        }

        drawContext.drawGuiTexture(RenderLayer::getGuiTextured, CHARGE_UI_ATLAS,
                81,
                18,
                1,
                15,
                x + 11,
                y - yshift + 5,
                (int) (79f * ((((IEntityDataSaver) player).amethyst_shield$getPersistentData().getFloat("charge"))
                / AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE())),
                3);

        drawContext.drawGuiTexture(RenderLayer::getGuiTextured, CHARGE_UI_ATLAS,
                81,
                18,
                0,
                0,
                x + 10,
                y - yshift,
                81,
                13);


        Profilers.get().pop();
    }

    private LivingEntity getRiddenEntity() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return null;
        }

        Entity entity = playerEntity.getVehicle();
        if (entity == null) {
            return null;
        }

        if (entity instanceof LivingEntity) {
            return (LivingEntity) entity;
        }

        return null;
    }

    private int getHeartCount(LivingEntity entity) {
        if (entity == null || !entity.isLiving()) {
            return 0;
        }

        float f = entity.getMaxHealth();
        int i = (int) (f + 0.5f) / 2;
        if (i > 30) {
            i = 30;
        }
        return i;
    }

    private PlayerEntity getCameraPlayer() {
        if (!(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity)) {
            return null;
        }
        return (PlayerEntity) MinecraftClient.getInstance().getCameraEntity();
    }

    @Override
    public void register(LayeredDrawerWrapper layeredDrawer) {

        layeredDrawer.addLayer(IdentifiedLayer.of(Identifier.of(AmethystShield.MOD_ID, "charge_hud"), this::onHudRender));
    }
}
