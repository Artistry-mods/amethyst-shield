package chaos.amyshield;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocle;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class LootTableModifier {
    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(BuiltInLootTables.TRIAL_CHAMBERS_REWARD)) {
				LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(ModItems.OXIWINE_BOLT).setWeight(50))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(60));
				tableBuilder.withPool(pb);
            }

            if (key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)) {
				LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(ModItems.OXIWINE_BOLT).setWeight(10))
                    .add(LootItem.lootTableItem(Items.AIR).setWeight(100));
				tableBuilder.withPool(pb);

                LootPool.Builder pbf = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItemsButItsOnlyTheMonocle.AMETHYST_MONOCLE).setWeight(1))
				.add(LootItem.lootTableItem(Items.AIR).setWeight(100));
                tableBuilder.withPool(pbf);
            }

            if (key.equals(BuiltInLootTables.ANCIENT_CITY) && !FabricLoader.getInstance().isModLoaded("sculk-latch")) {
                LootPool.Builder pb = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItemsButItsOnlyTheSculkLatch.SCULK_LATCH).setWeight(30))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(100));
                tableBuilder.withPool(pb);
            }

            /*
            if (key.equals(LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST)) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                        .apply(new SetEnchantmentsLootFunction.Builder()
                            .enchantment(
                                RegistryEntry.of(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.RELEASE).value()),
                                EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.linear(1, 2))
                            )
                        )
                        .weight(400)
                            .build()
                    );

                tableBuilder.pool(pb);
            }
            */
        });
    }
}
