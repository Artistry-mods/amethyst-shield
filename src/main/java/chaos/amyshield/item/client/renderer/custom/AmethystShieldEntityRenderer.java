package chaos.amyshield.item.client.renderer.custom;

import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class AmethystShieldEntityRenderer implements SpecialModelRenderer<ComponentMap> {
    private final AmethystShieldEntityModel modelShield;

    public AmethystShieldEntityRenderer(ModelPart model) {
        this.modelShield = new AmethystShieldEntityModel(model);
    }


    @Override
    public void render(@Nullable ComponentMap data, net.minecraft.item.ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);
        if (data == null) {
            matrices.pop();
            return;
        }
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, RenderLayer.getItemEntityTranslucentCull(Identifier.of("amyshield", "textures/item/amethyst_shield.png")), false, Boolean.TRUE.equals(data.get(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE)));

        this.modelShield.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }

    @Override
    public @Nullable ComponentMap getData(ItemStack stack) {
        return stack.getImmutableComponents();
    }



	@Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final AmethystShieldEntityRenderer.Unbaked INSTANCE = new AmethystShieldEntityRenderer.Unbaked();
		public static final MapCodec<AmethystShieldEntityRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

		public MapCodec<AmethystShieldEntityRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new AmethystShieldEntityRenderer(AmethystShieldEntityModel.getTexturedModelData().createModel());
		}
	}
}
