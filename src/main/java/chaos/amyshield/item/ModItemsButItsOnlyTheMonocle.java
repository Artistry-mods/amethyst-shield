package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystMonocleItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModItemsButItsOnlyTheMonocle {
    public static final Item AMETHYST_MONOCLE = registerItem(keyOf("amethyst_monocle"),
            AmethystMonocleItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).equippableUnswappable(EquipmentSlot.HEAD));

    private static ResourceKey<Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    private static Item registerItem(final ResourceKey<@NotNull Item> key, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static void addItemsToToolTabItemGroup(FabricCreativeModeTabOutput entries) {
        entries.accept(AMETHYST_MONOCLE);
    }

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(ModItemsButItsOnlyTheMonocle::addItemsToToolTabItemGroup);
    }
}
