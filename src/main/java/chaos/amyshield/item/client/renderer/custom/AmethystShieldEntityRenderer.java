package chaos.amyshield.item.client.renderer.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;

public class AmethystShieldEntityRenderer implements SpecialModelRenderer<ComponentMap> {
    private final AmethystShieldEntityModel modelShield;
    private static final Identifier AMETHYST_SHIELD_TEXTURE = Identifier.of(AmethystShield.MOD_ID, "textures/item/amethyst_shield.png");

    public AmethystShieldEntityRenderer(AmethystShieldEntityModel model) {
        this.modelShield = model;
    }

    @Nullable
    public ComponentMap getData(ItemStack itemStack) {
        return itemStack.getImmutableComponents();
    }

    @Override
    public void render(@Nullable ComponentMap data, ItemDisplayContext displayContext, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, boolean glint, int i) {
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);

        queue.submitModelPart(this.modelShield.getRootPart(), matrices, this.modelShield.getLayer(AMETHYST_SHIELD_TEXTURE), light, overlay, (Sprite)null, false, glint, -1, (ModelCommandRenderer.CrumblingOverlayCommand)null, i);

        matrices.pop();
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        this.modelShield.getRootPart().collectVertices(matrixStack, vertices);
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final AmethystShieldEntityRenderer.Unbaked INSTANCE = new AmethystShieldEntityRenderer.Unbaked();
        public static final MapCodec<AmethystShieldEntityRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable SpecialModelRenderer<?> bake(BakeContext context) {
            return new AmethystShieldEntityRenderer(new AmethystShieldEntityModel(context.entityModelSet().getModelPart(AmethystShieldEntityModel.AMETHYST_SHIELD)));
        }

        public MapCodec<AmethystShieldEntityRenderer.Unbaked> getCodec() {
            return CODEC;
        }
    }
}
