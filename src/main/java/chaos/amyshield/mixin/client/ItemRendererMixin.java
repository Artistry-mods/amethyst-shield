package chaos.amyshield.mixin.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocle;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel renderItem(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!FabricLoader.getInstance().isModLoaded("trinkets")) {
            if (stack.isOf(ModItemsButItsOnlyTheMonocle.AMETHYST_MONOCLE) && renderMode == ModelTransformationMode.HEAD) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(AmethystShield.MOD_ID, "amethyst_monocle_3d")));
            }
        } else {
            if (stack.isOf(ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled.AMETHYST_MONOCLE) && renderMode == ModelTransformationMode.FIXED) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(AmethystShield.MOD_ID, "amethyst_monocle_3d")));
            }
        }
        return value;
    }
}