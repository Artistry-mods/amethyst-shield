package chaos.amyshield.item.client.model;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import chaos.amyshield.item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;

public class ModEntityModels {

    public static void registerModEntityModels() {
        EntityModelLayerRegistry.registerModelLayer(AmethystShieldEntityModel.AMETHYST_SHIELD, AmethystShieldEntityModel::getTexturedModelData);

        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "amethyst_shield_model"), AmethystShieldEntityRenderer.Unbaked.CODEC);
    }
}
