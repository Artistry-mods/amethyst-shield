package chaos.amyshield.ui.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ChargeHudOverlay implements HudElement {
    private static final Identifier CHARGE_UI_ATLAS = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "hud/amethyst_shield_ui");

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        if (client.options.hideGui) {
            return;
        }

        if (client.player == null ||client.player.isSpectator()) {
            return;
        }
        LocalPlayer player = client.player;

        if (!player.getMainHandItem().getItem().equals(ModItems.AMETHYST_SHIELD) &&
                !player.getOffhandItem().getItem().equals(ModItems.AMETHYST_SHIELD)) {
            return;
        }

        Profiler.get().popPush("charge");

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        int x = width / 2;
        int y = height;

        int yshift = 53 + AmethystShield.CONFIG.amethystShieldNested.CHARGE_BAR_OFFSET();
        int maxAir = player.getMaxAirSupply();
        int playerAir = Math.min(player.getAirSupply(), maxAir);
        if (player.getAbilities().instabuild) yshift -= 17;
        if ((player.isEyeInFluid(FluidTags.WATER) || playerAir < maxAir) && !player.getAbilities().instabuild) yshift += 10;
        LivingEntity livingEntity = this.getRiddenEntity();

        if (livingEntity != null) {
            int i = this.getHeartCount(livingEntity);
            if (i > 10) {
                yshift += 10;
            }
            if (player.getAbilities().instabuild) yshift += 17;
        }

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CHARGE_UI_ATLAS,
                81,
                18,
                1,
                15,
                x + 11,
                y - yshift + 5,
                (int) (79f * ((((IEntityDataSaver) player).amethyst_shield$getPersistentData().getCharge())
                / AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE())),
                3);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CHARGE_UI_ATLAS,
                81,
                18,
                0,
                0,
                x + 10,
                y - yshift,
                81,
                13);


        Profiler.get().pop();
    }

    private LivingEntity getRiddenEntity() {
        Player playerEntity = this.getCameraPlayer();
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
        if (entity == null || !entity.showVehicleHealth()) {
            return 0;
        }

        float f = entity.getMaxHealth();
        int i = (int) (f + 0.5f) / 2;
        if (i > 30) {
            i = 30;
        }
        return i;
    }

    private Player getCameraPlayer() {
        if (!(Minecraft.getInstance().getCameraEntity() instanceof Player)) {
            return null;
        }
        return (Player) Minecraft.getInstance().getCameraEntity();
    }
}
