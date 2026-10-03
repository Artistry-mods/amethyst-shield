package chaos.amyshield.datagen;

import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider {
    public static class Block extends FabricBlockLootSubProvider {
        public Block(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {

            this.add(ModBlocks.AMETHYST_DISPENSER, LootTable.lootTable().withPool(this.applyExplosionCondition(Items.DISPENSER, LootPool.lootPool().add(LootItem.lootTableItem(Items.DISPENSER)))));

            this.dropWhenSilkTouch(ModBlocks.DIAMOND_DEPOSIT);

            this.add(ModBlocks.DIAMOND_DEPOSIT, LootTable.lootTable().withPool(
                            LootPool.lootPool().when(this.hasSilkTouch())
                                    .setRolls(ContextIntProviders.exactly(1))
                                    .add(LootItem.lootTableItem(ModBlocks.DIAMOND_DEPOSIT.asItem()))
                    ).withPool(
                    this.applyExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.lootPool()
                            .when(this.doesNotHaveSilkTouch())
                            .setRolls(ContextIntProviders.between(3, 9)))
                            .add(LootItem.lootTableItem(Items.DIAMOND))
                            .setBonusRolls(ContextFloatProviders.exactly(2))
                    ).withPool(
                    this.applyExplosionCondition(ModBlocks.DIAMOND_DEPOSIT.asItem(), LootPool.lootPool()
                            .when(this.doesNotHaveSilkTouch())
                            .setRolls(ContextIntProviders.between(1, 3)))
                            .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK))
                            .setBonusRolls(ContextFloatProviders.exactly(1))
                    )
            );
        }
    }
}
