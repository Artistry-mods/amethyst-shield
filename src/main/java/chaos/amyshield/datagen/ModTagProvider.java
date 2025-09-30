package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            valueLookupBuilder(ModTags.AMETHYST_SHIELD_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD);

            valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.AMETHYST_SHIELD);

            valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS).add(ModItems.AMETHYST_SHIELD);
        }
    }

    public static class ModEntityProvider extends FabricTagProvider.EntityTypeTagProvider {
        public ModEntityProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            valueLookupBuilder(ModTags.SLASH_IMMUNE).add(EntityType.HAPPY_GHAST);
        }
    }

    public static class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
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
