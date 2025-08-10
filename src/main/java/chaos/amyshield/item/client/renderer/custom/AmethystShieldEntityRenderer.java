package chaos.amyshield.item.client.renderer.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.model.custom.AmethystShieldEntityModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
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

    public void render(@Nullable ComponentMap componentMap, ItemDisplayContext displayContext, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, boolean bl) {
        /*BannerPatternsComponent bannerPatternsComponent = componentMap != null ? componentMap.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT) : BannerPatternsComponent.DEFAULT;
        DyeColor dyeColor = componentMap != null ? componentMap.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl2 ? ModelBaker.SHIELD_BASE : ModelBaker.SHIELD_BASE_NO_PATTERN;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(spriteIdentifier.getAtlasId()), itemDisplayContext == ItemDisplayContext.GUI, bl));
        this.model.getHandle().render(matrixStack, vertexConsumer, i, j);
        if (bl2) {
            BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.model.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, bl, false);
        } else {
            this.model.getPlate().render(matrixStack, vertexConsumer, i, j);
        }*/

        //vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(OnGuardClient.CRYSTAL.getAtlasId()), itemDisplayContext == ItemDisplayContext.GUI, bl));
        //this.model.getLeftCrystal().render(matrixStack, vertexConsumer, i, j);
        //this.model.getRightCrystal().render(matrixStack, vertexConsumer, i, j);

        // above: slightly modified vanilla shield code. below: extraordinarily simple amy shield renderer

        matrixStack.push();
        matrixStack.scale(1.0f, -1.0f, -1.0f);

        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, RenderLayer.getItemEntityTranslucentCull(AMETHYST_SHIELD_TEXTURE), false, bl);
        this.modelShield.render(matrixStack, vertexConsumer, i, j);

        matrixStack.pop();
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

        public MapCodec<AmethystShieldEntityRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new AmethystShieldEntityRenderer(new AmethystShieldEntityModel(entityModels.getModelPart(AmethystShieldEntityModel.AMETHYST_SHIELD)));
        }
    }
}
