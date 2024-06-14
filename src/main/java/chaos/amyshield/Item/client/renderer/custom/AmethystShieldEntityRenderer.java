package chaos.amyshield.Item.client.renderer.custom;

import chaos.amyshield.Item.client.model.custom.AmethystShieldEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AmethystShieldEntityRenderer extends BuiltinModelItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final AmethystShieldEntityModel modelShield;

    public AmethystShieldEntityRenderer(ModelPart model, BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher,loader);
        this.modelShield = new AmethystShieldEntityModel(model);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);

        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, RenderLayer.getItemEntityTranslucentCull(new Identifier("amyshield","textures/item/amethyst_shield.png")), false, stack.hasGlint());
        this.modelShield.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
    }
}
