package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider {
    public static class Block extends FabricBlockLootSubProvider {

        public Block(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            add(ModBlocks.AMETHYST_DISPENSER, LootTable.lootTable().withPool(applyExplosionCondition(Items.DISPENSER, LootPool.lootPool().add(LootItem.lootTableItem(Items.DISPENSER)))));

            dropWhenSilkTouch(ModBlocks.DIAMOND_DEPOSIT);

            add(ModBlocks.DIAMOND_DEPOSIT, LootTable.lootTable().withPool(
                            LootPool.lootPool().when(this.hasSilkTouch())
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(ModBlocks.DIAMOND_DEPOSIT.asItem()))
                    ).withPool(
                        applyExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.lootPool()
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().withComponents(net.minecraft.advancements.criterion.DataComponentMatchers.Builder.components().partial(DataComponentPredicates.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))).build())).invert())
                            .setRolls(new UniformGenerator(new ConstantValue(3), new ConstantValue(9)))
                            .add(LootItem.lootTableItem(Items.DIAMOND))
                            .setBonusRolls(new ConstantValue(2)))
                    ).withPool(
                        applyExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.lootPool()
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().withComponents(net.minecraft.advancements.criterion.DataComponentMatchers.Builder.components().partial(DataComponentPredicates.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))).build())).invert())
                            .setRolls(new UniformGenerator(new ConstantValue(1), new ConstantValue(3)))
                            .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK))
                            .setBonusRolls(new ConstantValue(1)))
                    )
            );
        }
    }
}
