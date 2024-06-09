package chaos.amyshield.Item.client.model;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.client.model.custom.AmethystShieldEntityModel;
import chaos.amyshield.Item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class ModEntityModels {

    public static void registerModEntityModels() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.AMETHYST_SHIELD, new AmethystShieldEntityRenderer(AmethystShieldEntityModel.getTexturedModelData().createModel()));
    }
}
