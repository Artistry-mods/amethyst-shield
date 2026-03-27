package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
        public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            valueLookupBuilder(ModTags.AMETHYST_SHIELD_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD);

            valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD);

            valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS).add(ModItems.AMETHYST_SHIELD);
        }
    }

    public static class ModEntityProvider extends FabricTagsProvider.EntityTypeTagsProvider {
        public ModEntityProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            valueLookupBuilder(ModTags.SLASH_IMMUNE).add(EntityType.HAPPY_GHAST);
        }
    }

    public static class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
        public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.DIAMOND_DEPOSIT)
                    .add(ModBlocks.AMETHYST_DISPENSER);

            valueLookupBuilder(ModTags.SHINY_ORES)
                    .add(Blocks.DIAMOND_ORE)
                    .add(Blocks.DEEPSLATE_DIAMOND_ORE)

                    .add(Blocks.EMERALD_ORE)
                    .add(Blocks.DEEPSLATE_EMERALD_ORE)

                    .add(Blocks.GOLD_ORE)
                    .add(Blocks.DEEPSLATE_GOLD_ORE)

                    .add(Blocks.IRON_ORE)
                    .add(Blocks.DEEPSLATE_IRON_ORE)

                    .add(Blocks.NETHER_QUARTZ_ORE)
                    .add(Blocks.GILDED_BLACKSTONE)
                    .add(Blocks.ANCIENT_DEBRIS)
                    .add(Blocks.NETHER_GOLD_ORE)

                    .add(ModBlocks.DIAMOND_DEPOSIT);

        }
    }
}
