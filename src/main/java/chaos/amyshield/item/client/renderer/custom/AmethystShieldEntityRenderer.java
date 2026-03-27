package chaos.amyshield.item.client.renderer.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer.BakingContext;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class AmethystShieldEntityRenderer implements SpecialModelRenderer<DataComponentMap> {
    private final AmethystShieldEntityModel modelShield;
    private static final Identifier AMETHYST_SHIELD_TEXTURE = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "textures/item/amethyst_shield.png");

    public AmethystShieldEntityRenderer(AmethystShieldEntityModel model) {
        this.modelShield = model;
    }

    @Nullable
    public DataComponentMap extractArgument(ItemStack itemStack) {
        return itemStack.immutableComponents();
    }

    @Override
    public void submit(@Nullable DataComponentMap data, ItemDisplayContext displayContext, PoseStack matrices, SubmitNodeCollector queue, int light, int overlay, boolean glint, int i) {
        matrices.pushPose();
        matrices.scale(1.0f, -1.0f, -1.0f);

        queue.submitModelPart(this.modelShield.root(), matrices, this.modelShield.renderType(AMETHYST_SHIELD_TEXTURE), light, overlay, (TextureAtlasSprite)null, false, glint, -1, (ModelFeatureRenderer.CrumblingOverlay)null, i);

        matrices.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {

        PoseStack matrixStack = new PoseStack();
        this.modelShield.root().getExtentsForGui(matrixStack, consumer);
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked {
        public static final AmethystShieldEntityRenderer.Unbaked INSTANCE = new AmethystShieldEntityRenderer.Unbaked();
        public static final MapCodec<AmethystShieldEntityRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable SpecialModelRenderer<?> bake(BakingContext context) {
            return new AmethystShieldEntityRenderer(new AmethystShieldEntityModel(context.entityModelSet().bakeLayer(AmethystShieldEntityModel.AMETHYST_SHIELD)));
        }

        public MapCodec<AmethystShieldEntityRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
