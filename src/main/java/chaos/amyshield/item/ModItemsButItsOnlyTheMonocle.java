package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystMonocleItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItemsButItsOnlyTheMonocle {
    public static final Item AMETHYST_MONOCLE = registerItem("amethyst_monocle",
            new AmethystMonocleItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    private static void addItemsToToolTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_MONOCLE);
    }


    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AmethystShield.MOD_ID, name), item);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItemsButItsOnlyTheMonocle::addItemsToToolTabItemGroup);
    }
}
