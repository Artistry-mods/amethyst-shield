package chaos.amyshield.Item.client.model;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.client.model.custom.AmethystShieldEntityModel;
import chaos.amyshield.Item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;

public class ModEntityModels {

    public static void registerModEntityModels() {
        BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
        EntityModelLoader loader = MinecraftClient.getInstance().getEntityModelLoader();
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.AMETHYST_SHIELD, new AmethystShieldEntityRenderer(AmethystShieldEntityModel.getTexturedModelData().createModel(), dispatcher, loader));
    }
}
