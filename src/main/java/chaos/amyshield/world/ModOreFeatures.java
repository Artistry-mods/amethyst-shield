package chaos.amyshield.world;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.HeightMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModOreFeatures {
    public static final ResourceKey<Feature> DIAMOND_DEPOSIT = createFeatureKey("diamond_deposit");

    public static final ResourceKey<PlacedFeature> PLACED_DIAMOND_DEPOSIT = createPlacementKey("diamond_deposit");


    public static ResourceKey<Feature> createFeatureKey(final String name) {
        return ResourceKey.create(Registries.FEATURE, AmethystShield.id(name));
    }

    public static ResourceKey<PlacedFeature> createPlacementKey(final String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AmethystShield.id(name));
    }

    public static void bootstrapFeature(final BootstrapContext<Feature> context) {
        RuleTest deepslateOreReplaceables = RuleTest.either(
                new TagMatchTest(BlockTags.HEIGHT_SPECIFIC_ORE_REPLACEABLES), HeightMatchTest.max(8), new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
        );

        List<BlockReplacement> diamondDepositeTargetList = List.of(
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DIAMOND_DEPOSIT.defaultBlockState())
        );

        context.register(DIAMOND_DEPOSIT, new OreFeature(diamondDepositeTargetList, 9));
    }


    public static void bootstrapPlacedFeature(final BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);
        Holder<Feature> diamondDeposit = configuredFeatures.getOrThrow(DIAMOND_DEPOSIT);

        PlacementUtils.register(
                context, PLACED_DIAMOND_DEPOSIT, diamondDeposit, List.of(CountPlacement.of(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256)), BiomeFilter.biome())
        );
    }
}
