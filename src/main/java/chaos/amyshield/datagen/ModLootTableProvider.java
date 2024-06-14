package chaos.amyshield.datagen;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.DIAMOND_DEPOSIT, createDiamondDepositLootTable());
    }

    private static LootTable.Builder createDiamondDepositLootTable() {
        /*
        return LootTable.builder()
                .pool(
                    LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                            .with(AlternativeEntry.builder(
                        ItemEntry.builder(Items.DIAMOND)
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                                .apply(ExplosionDecayLootFunction.builder()),
                        ItemEntry.builder(Items.DIAMOND_BLOCK)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                            .conditionally(RandomChanceLootCondition.builder(0.1f)),
                        ItemEntry.builder(ModItems.DIAMOND_DEPOSIT)
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))))
                        .conditionally(SurvivesExplosionLootCondition.builder()))
                );

         */

        return LootTable.builder()
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(AlternativeEntry.builder(
                    ItemEntry.builder(ModItems.DIAMOND_DEPOSIT)
                         .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                             .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))),
                    ItemEntry.builder(Items.DIAMOND_BLOCK)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .apply(ExplosionDecayLootFunction.builder()),
                    ItemEntry.builder(Items.DIAMOND)
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4, 9), true))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(RandomChanceLootCondition.builder(0.8f))
                ))
                .conditionally(SurvivesExplosionLootCondition.builder())
            );
    }
}
