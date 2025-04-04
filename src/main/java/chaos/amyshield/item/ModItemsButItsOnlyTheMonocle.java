package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystMonocleItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItemsButItsOnlyTheMonocle {
    public static final Item AMETHYST_MONOCLE = Items.register(keyOf("amethyst_monocle"),
            AmethystMonocleItem::new,
            new Item.Settings().maxCount(1).rarity(Rarity.RARE).equippableUnswappable(EquipmentSlot.HEAD));

    private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AmethystShield.MOD_ID, id));
	}

    private static void addItemsToToolTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_MONOCLE);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItemsButItsOnlyTheMonocle::addItemsToToolTabItemGroup);
    }
}
