package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystMonocleItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Rarity;

public class ModItemsButItsOnlyTheMonocle {
    public static final Item AMETHYST_MONOCLE = Items.registerItem(keyOf("amethyst_monocle"),
            AmethystMonocleItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).equippableUnswappable(EquipmentSlot.HEAD));

    private static ResourceKey<Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    private static void addItemsToToolTabItemGroup(FabricItemGroupEntries entries) {
        entries.accept(AMETHYST_MONOCLE);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(ModItemsButItsOnlyTheMonocle::addItemsToToolTabItemGroup);
    }
}
