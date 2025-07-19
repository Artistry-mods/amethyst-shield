package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItemsButItsOnlyTheSculkLatch {
    public static final Item SCULK_LATCH = Items.register(
            keyOf("sculk_latch"),
            Item::new,
            new Item.Settings().maxCount(16));

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(SCULK_LATCH);
    }

    private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AmethystShield.MOD_ID, id));
	}

    public static void registerModItemsButItsOnlyTheSculkLatch() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItemsButItsOnlyTheSculkLatch::addItemsToIngredientsTabItemGroup);
    }
}
