package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.Optional;

public class ModItems {
    public static final Item AMETHYST_SHIELD = Items.registerItem(
			keyOf("amethyst_shield"),
            AmethystShieldItem::new,
            new Item.Properties().rarity(Rarity.EPIC).enchantable(10).durability(AmethystShield.CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY()).repairable(ItemTags.WOODEN_TOOL_MATERIALS).equippableUnswappable(EquipmentSlot.OFFHAND).component(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)), new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK))).component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK));

    public static final Item OXIWINE_BOLT = Items.registerItem(
			keyOf("oxiwine_bolt"),
			Item::new,
            new Item.Properties());

    public static final Item DIAMOND_DEPOSIT = Items.registerBlock(ModBlocks.DIAMOND_DEPOSIT);

    private static ResourceKey<Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.accept(AMETHYST_SHIELD);
    }

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.accept(OXIWINE_BOLT);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::addItemsToIngredientsTabItemGroup);
    }
}