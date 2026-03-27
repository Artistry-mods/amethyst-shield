package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

public class ModItemsButItsOnlyTheSculkLatch {
    public static final Item SCULK_LATCH = Items.registerItem(
            keyOf("sculk_latch"),
            Item::new,
            new Item.Properties().stacksTo(16));

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.accept(SCULK_LATCH);
    }

    private static ResourceKey<Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    public static void registerModItemsButItsOnlyTheSculkLatch() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(ModItemsButItsOnlyTheSculkLatch::addItemsToIngredientsTabItemGroup);
    }
}
