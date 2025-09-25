package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider {
    public static class Block extends FabricBlockLootTableProvider {

        public Block(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.AMETHYST_DISPENSER, LootTable.builder().pool(addSurvivesExplosionCondition(Items.DISPENSER, LootPool.builder().with(ItemEntry.builder(Items.DISPENSER)))));

            addDropWithSilkTouch(ModBlocks.DIAMOND_DEPOSIT);

            addDrop(ModBlocks.DIAMOND_DEPOSIT, LootTable.builder().pool(
                            LootPool.builder().conditionally(this.createSilkTouchCondition())
                                    .rolls(ConstantLootNumberProvider.create(1.0F))
                                    .with(ItemEntry.builder(ModBlocks.DIAMOND_DEPOSIT.asItem()))
                    ).pool(
                        addSurvivesExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.builder()
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().components(ComponentsPredicate.Builder.create().partial(ComponentPredicateTypes.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1))))).build())).invert())
                            .rolls(new UniformLootNumberProvider(new ConstantLootNumberProvider(3), new ConstantLootNumberProvider(9)))
                            .with(ItemEntry.builder(Items.DIAMOND))
                            .bonusRolls(new ConstantLootNumberProvider(2)))
                    ).pool(
                        addSurvivesExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.builder()
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().components(ComponentsPredicate.Builder.create().partial(ComponentPredicateTypes.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1))))).build())).invert())
                            .rolls(new UniformLootNumberProvider(new ConstantLootNumberProvider(1), new ConstantLootNumberProvider(3)))
                            .with(ItemEntry.builder(Items.DIAMOND_BLOCK))
                            .bonusRolls(new ConstantLootNumberProvider(1)))
                    )
            );
        }
    }
}
