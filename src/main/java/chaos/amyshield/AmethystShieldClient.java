package chaos.amyshield;

import chaos.amyshield.debug_renderer.ChargeF3DebugRenderer;
import chaos.amyshield.item.client.model.ModEntityModels;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.client.ModClientParticles;
import chaos.amyshield.ui.client.ChargeHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.hud.debug.DebugHudEntries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AmethystShieldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerGlobalReceiversS2C();
        ModEntityModels.registerModEntityModels();
        HudElementRegistry.addFirst(Identifier.of(AmethystShield.MOD_ID, "charge_hud_overlay"), new ChargeHudOverlay());
        ModClientParticles.registerModParticlesClient();
        DebugHudEntries.register(Identifier.of(AmethystShield.MOD_ID, "charge"), new ChargeF3DebugRenderer());

        ResourceManagerHelper.registerBuiltinResourcePack(
            Identifier.of(AmethystShield.MOD_ID, "lower_amethyst_shield"),
            FabricLoader.getInstance().getModContainer(AmethystShield.MOD_ID).orElseThrow(),
            Text.of("Lower Amethyst Shield"),
            ResourcePackActivationType.NORMAL
        );
    }
}