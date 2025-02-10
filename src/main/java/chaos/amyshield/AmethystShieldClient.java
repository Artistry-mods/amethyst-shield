package chaos.amyshield;

import chaos.amyshield.item.client.model.ModEntityModels;
import chaos.amyshield.item.client.renderer.ModTrinketRenderers;
import chaos.amyshield.item.custom.predicate.ModModelPredicates;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.client.ModClientParticles;
import chaos.amyshield.ui.client.ChargeHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AmethystShieldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerGlobalReceiversS2C();
        ModEntityModels.registerModEntityModels();
        HudRenderCallback.EVENT.register(new ChargeHudOverlay());
        ModModelPredicates.registerModModelPredicates();
        ModClientParticles.registerModParticlesClient();

        ResourceManagerHelper.registerBuiltinResourcePack(
            Identifier.of(AmethystShield.MOD_ID, "lower_amethyst_shield"),
            FabricLoader.getInstance().getModContainer(AmethystShield.MOD_ID).orElseThrow(),
            Text.of("Lower Amethyst Shield"),
            ResourcePackActivationType.NORMAL
        );

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            ModTrinketRenderers.init();
        }
    }
}