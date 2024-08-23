package chaos.amyshield.Item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item AMETHYST_SHIELD = registerItem("amethyst_shield",
            new AmethystShieldItem(new Item.Settings().maxDamage(AmethystShield.CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY()), Items.AMETHYST_SHARD));

    public static final Item OXIWINE_BOLT = registerItem("oxiwine_bolt",
            new Item(new Item.Settings()));

    public static final Item DIAMOND_DEPOSIT = registerItem("diamond_deposit",
            new BlockItem(ModBlocks.DIAMOND_DEPOSIT, new Item.Settings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AmethystShield.MOD_ID, name), item);
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_SHIELD);
    }

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(OXIWINE_BOLT);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsTabItemGroup);
        AmethystShield.LOGGER.info("Registering Items for " + AmethystShield.MOD_ID);
    }
}