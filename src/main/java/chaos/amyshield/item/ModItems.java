package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ModItems {

    public static final Item AMETHYST_SHIELD = Items.register(
			keyOf("amethyst_shield"),
            AmethystShieldItem::new,
            new Item.Settings().maxDamage(AmethystShield.CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY()).repairable(ItemTags.WOODEN_TOOL_MATERIALS).equippableUnswappable(EquipmentSlot.OFFHAND));

    public static final Item OXIWINE_BOLT = Items.register(
			keyOf("oxiwine_bolt"),
			Item::new,
            new Item.Settings());

    public static final Item DIAMOND_DEPOSIT = Items.register(ModBlocks.DIAMOND_DEPOSIT);

    private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AmethystShield.MOD_ID, id));
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
    }
}