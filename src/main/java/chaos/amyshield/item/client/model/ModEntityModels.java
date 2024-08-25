package chaos.amyshield.item.client.model;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import chaos.amyshield.item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class ModEntityModels {

    public static void registerModEntityModels() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.AMETHYST_SHIELD, new AmethystShieldEntityRenderer(AmethystShieldEntityModel.getTexturedModelData().createModel()));
    }
}
