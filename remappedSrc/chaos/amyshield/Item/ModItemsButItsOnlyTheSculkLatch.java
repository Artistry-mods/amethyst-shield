package chaos.amyshield.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static chaos.amyshield.item.ModItems.registerItem;

public class ModItemsButItsOnlyTheSculkLatch {
    public static final Item SCULK_LATCH = registerItem("sculk_latch",
            new Item(new FabricItemSettings().maxCount(16)));

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(SCULK_LATCH);
    }
    public static void registerModItemsButItsOnlyTheSculkLatch() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItemsButItsOnlyTheSculkLatch::addItemsToIngredientsTabItemGroup);
    }
}
