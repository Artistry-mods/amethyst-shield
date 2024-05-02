package chaos.amyshield.Item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item AMETHYST_SHIELD = registerItem("amethyst_shield",
            new AmethystShieldItem(new FabricItemSettings().maxCount(1).maxDamage(200), 100, 14, Items.AMETHYST_SHARD));

    public static final Item SKULK_LATCH = registerItem("skulk_latch",
            new Item(new FabricItemSettings()));

    public static final Item OXALINE_BOLT = registerItem("oxaline_bolt",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AmethystShield.MOD_ID, name), item);
    }

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_SHIELD);
    }

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
    }
    private static void addItemsToFoodTabItemGroup(FabricItemGroupEntries entries) {
    }
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
        AmethystShield.LOGGER.info("Registering Items for Mod " + AmethystShield.MOD_ID);
    }
}