package chaos.amyshield.Item.client.renderer.custom;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class MonocleItemTrinketRenderer implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!(entity instanceof AbstractClientPlayerEntity) || !(contextModel instanceof PlayerEntityModel)) {
            return;
        }

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        TrinketRenderer.translateToFace(matrices, (PlayerEntityModel) contextModel, ((AbstractClientPlayerEntity) entity), headYaw - 180, -headPitch - 180);

        matrices.translate(-0.03, 0.49, 0.29);
        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, 0, matrices, vertexConsumers, entity.getWorld(), 1);
    }
}
