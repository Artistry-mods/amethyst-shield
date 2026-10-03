package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityTypeIds;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
        public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
            this.builder(ModTags.AMETHYST_SHIELD_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD_ID);

            this.builder(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD_ID);

            this.builder(ConventionalItemTags.SHIELD_TOOLS).add(ModItems.AMETHYST_SHIELD_ID);
        }
    }

    public static class ModEntityProvider extends FabricTagsProvider.EntityTypeTagsProvider {
        public ModEntityProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
            this.builder(ModTags.SLASH_IMMUNE).add(EntityTypeIds.HAPPY_GHAST);
        }
    }

    public static class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
        public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
            this.builder(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.DIAMOND_DEPOSIT_ID)
                    .add(ModBlocks.AMETHYST_DISPENSER_ID);

            this.builder(ModTags.SHINY_ORES)
                    .add(BlockItemIds.DIAMOND_ORE)
                    .add(BlockItemIds.DEEPSLATE_DIAMOND_ORE)

                    .add(BlockItemIds.EMERALD_ORE)
                    .add(BlockItemIds.DEEPSLATE_EMERALD_ORE)

                    .add(BlockItemIds.GOLD_ORE)
                    .add(BlockItemIds.DEEPSLATE_GOLD_ORE)

                    .add(BlockItemIds.IRON_ORE)
                    .add(BlockItemIds.DEEPSLATE_IRON_ORE)

                    .add(BlockItemIds.NETHER_QUARTZ_ORE)
                    .add(BlockItemIds.GILDED_BLACKSTONE)
                    .add(BlockItemIds.ANCIENT_DEBRIS)
                    .add(BlockItemIds.NETHER_GOLD_ORE)

                    .add(ModBlocks.DIAMOND_DEPOSIT_ID);

        }
    }
}
