package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModItemsButItsOnlyTheSculkLatch {
    public static final Item SCULK_LATCH = registerItem(
            keyOf("sculk_latch"),
            Item::new,
            new Item.Properties().stacksTo(16));

    private static void addItemsToIngredientsTabItemGroup(FabricCreativeModeTabOutput entries) {
        entries.accept(SCULK_LATCH);
    }

    private static Item registerItem(final ResourceKey<@NotNull Item> key, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static ResourceKey<@NotNull Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    public static void registerModItemsButItsOnlyTheSculkLatch() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(ModItemsButItsOnlyTheSculkLatch::addItemsToIngredientsTabItemGroup);
    }
}
