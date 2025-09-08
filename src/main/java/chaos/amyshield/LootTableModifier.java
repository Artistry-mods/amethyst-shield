package chaos.amyshield;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocle;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;

public class LootTableModifier {
    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(LootTables.TRIAL_CHAMBERS_REWARD_CHEST)) {
				LootPool.Builder pb = LootPool.builder()
					.with(ItemEntry.builder(ModItems.OXIWINE_BOLT).weight(50))
					.with(ItemEntry.builder(Items.AIR).weight(60));
				tableBuilder.pool(pb);
            }

            if (key.equals(LootTables.TRAIL_RUINS_RARE_ARCHAEOLOGY)) {
				LootPool.Builder pb = LootPool.builder()
					.with(ItemEntry.builder(ModItems.OXIWINE_BOLT).weight(10))
                    .with(ItemEntry.builder(Items.AIR).weight(100));
				tableBuilder.pool(pb);

                LootPool.Builder pbf = LootPool.builder()
                    .with(ItemEntry.builder(ModItemsButItsOnlyTheMonocle.AMETHYST_MONOCLE).weight(1))
				.with(ItemEntry.builder(Items.AIR).weight(100));
                tableBuilder.pool(pbf);
            }

            if (key.equals(LootTables.ANCIENT_CITY_CHEST) && !FabricLoader.getInstance().isModLoaded("sculk-latch")) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(ModItemsButItsOnlyTheSculkLatch.SCULK_LATCH).weight(30))
					.with(ItemEntry.builder(Items.AIR).weight(100));
                tableBuilder.pool(pb);
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
