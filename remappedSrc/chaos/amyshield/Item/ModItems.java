package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystMonocleItem;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item AMETHYST_SHIELD = registerItem("amethyst_shield",
            new AmethystShieldItem(new FabricItemSettings().maxCount(1).maxDamage(512), Items.AMETHYST_SHARD));

    public static final Item OXIWINE_BOLT = registerItem("oxiwine_bolt",
            new Item(new FabricItemSettings()));

    public static final Item AMETHYST_MONOCLE = registerItem("amethyst_monocle",
            new AmethystMonocleItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));


    public static final Item DIAMOND_DEPOSIT = registerItem("diamond_deposit",
            new BlockItem(ModBlocks.DIAMOND_DEPOSIT, new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AmethystShield.MOD_ID, name), item);
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_SHIELD);
    }

    private static void addItemsToToolTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(AMETHYST_MONOCLE);
    }

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(OXIWINE_BOLT);
    }
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsTabItemGroup);
        AmethystShield.LOGGER.info("Registering Items for " + AmethystShield.MOD_ID);
    }
}