package chaos.amyshield;

import chaos.amyshield.Item.client.model.ModEntityModels;
import chaos.amyshield.Item.client.renderer.ModTrinketRenderers;
import chaos.amyshield.Item.custom.predicate.ModModelPredicates;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.client.ModClientParticles;
import chaos.amyshield.ui.client.ChargeHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;

public class AmethystShieldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerGlobalReceiversS2C();
        ModEntityModels.registerModEntityModels();
        HudRenderCallback.EVENT.register(new ChargeHudOverlay());
        ModModelPredicates.registerModModelPredicates();
        ModClientParticles.registerModParticlesClient();

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            ModTrinketRenderers.init();
        }
    }
}