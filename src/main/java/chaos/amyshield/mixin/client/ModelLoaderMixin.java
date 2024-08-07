package chaos.amyshield.mixin.client;

import chaos.amyshield.AmethystShield;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    @Final
    private Map<ModelIdentifier, UnbakedModel> modelsToBake;

    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Shadow
    abstract UnbakedModel getOrLoadModel(Identifier id);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addAmethystMonocleModel(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        profiler.push("special");
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(AmethystShield.MOD_ID, "amethyst_monocle_3d")));
        this.modelsToBake.values().forEach((model) -> model.setParents(this::getOrLoadModel));
        profiler.pop();
    }
}