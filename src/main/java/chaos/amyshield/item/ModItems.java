package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ModItems {
    public static final Item AMETHYST_SHIELD = Items.register(
			keyOf("amethyst_shield"),
            AmethystShieldItem::new,
            new Item.Settings().enchantable(10).maxDamage(AmethystShield.CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY()).repairable(ItemTags.WOODEN_TOOL_MATERIALS).equippableUnswappable(EquipmentSlot.OFFHAND).component(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.25F, 1.0F, List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)), new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK))).component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK));

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