package chaos.amyshield.item.client.model;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import chaos.amyshield.item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;

public class ModEntityModels {

    public static void registerModEntityModels() {
        EntityModelLayerRegistry.registerModelLayer(AmethystShieldEntityModel.AMETHYST_SHIELD, AmethystShieldEntityModel::getTexturedModelData);

        SpecialModelTypes.ID_MAPPER.put(Identifier.of(AmethystShield.MOD_ID, "amethyst_shield_model"), AmethystShieldEntityRenderer.Unbaked.CODEC);
    }
}
