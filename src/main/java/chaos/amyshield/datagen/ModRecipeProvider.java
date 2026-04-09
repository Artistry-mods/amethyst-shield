package chaos.amyshield.datagen;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.COMBAT, ModItems.AMETHYST_SHIELD)
                        .define('s', Items.SHIELD)
                        .define('c', Items.COPPER_INGOT)
                        .define('a', Items.AMETHYST_SHARD)
                        .define('o', ModItems.OXIWINE_BOLT)
                        .define('l', ModItemsButItsOnlyTheSculkLatch.SCULK_LATCH)
                        .pattern("aaa")
                        .pattern("oso")
                        .pattern("clc")
                        .showNotification(true)
                        .unlockedBy("has_item", has(Items.SHIELD))
                        .save(output);
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return "ModRecipeProvider";
    }
}