package chaos.amyshield.datagen;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(
        HolderLookup.@NonNull Provider registries,
        @NonNull BootstrapContext<Recipe<?>> recipes,
        @NonNull BootstrapContext<Advancement> advancements
    ) {
        return new VanillaRecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                this.shaped(RecipeCategory.COMBAT, ModItems.AMETHYST_SHIELD)
                        .define('s', Items.SHIELD)
                        .define('c', Items.COPPER_INGOT)
                        .define('a', Items.AMETHYST_SHARD)
                        .define('o', ModItems.OXIWINE_BOLT)
                        .define('l', ModItemsButItsOnlyTheSculkLatch.SCULK_LATCH)
                        .pattern("aaa")
                        .pattern("oso")
                        .pattern("clc")
                        .showNotification(true)
                        .unlockedBy("has_item", this.has(Items.SHIELD))
                        .save(this.output);
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return "ModRecipeProvider";
    }
}