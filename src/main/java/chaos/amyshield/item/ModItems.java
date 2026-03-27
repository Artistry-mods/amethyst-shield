package chaos.amyshield.item;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModItems {
    public static final Item AMETHYST_SHIELD = registerItem(
			keyOf("amethyst_shield"),
            AmethystShieldItem::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .enchantable(10)
                    .durability(AmethystShield.CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY())
                    .repairable(ItemTags.WOODEN_TOOL_MATERIALS)
                    .equippableUnswappable(EquipmentSlot.OFFHAND)
                    .delayedComponent(
                            DataComponents.BLOCKS_ATTACKS,
                           context -> new BlocksAttacks(
                                    0.25F,
                                    1.0F,
                                    List.of(
                                            new BlocksAttacks.DamageReduction(
                                                    90.0F,
                                                    Optional.empty(),
                                                    0.0F,
                                                    1.0F)
                                    ),
                                    new BlocksAttacks.ItemDamageFunction(
                                            3.0F,
                                            1.0F,
                                            1.0F
                                    ),
                                   Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                                    Optional.of(SoundEvents.SHIELD_BLOCK),
                                    Optional.of(SoundEvents.SHIELD_BREAK)
                            )
                    ).component(
                            DataComponents.BREAK_SOUND,
                            SoundEvents.SHIELD_BREAK)
    );

    public static final Item OXIWINE_BOLT = registerItem(
			keyOf("oxiwine_bolt"),
			Item::new,
            new Item.Properties());

    public static final Item DIAMOND_DEPOSIT = registerBlock(ModBlocks.DIAMOND_DEPOSIT);

    private static Item registerBlock(final Block block) {
        return registerBlock(block, BlockItem::new, new Item.Properties());
    }

    private static Item registerBlock(final Block block, final BiFunction<Block, Item.Properties, Item> itemFactory, final Item.Properties properties) {
        return registerItem(
                blockIdToItemId(block.builtInRegistryHolder().key()),
                p -> (Item)itemFactory.apply(block, p),
                properties.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures())
        );
    }

    private static ResourceKey<Item> blockIdToItemId(final ResourceKey<Block> blockName) {
        return ResourceKey.create(Registries.ITEM, blockName.identifier());
    }

    private static Item registerItem(final ResourceKey<@NotNull Item> key, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static ResourceKey<Item> keyOf(String id) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    private static void addItemsToCombatTabItemGroup(FabricCreativeModeTabOutput entries) {
        entries.accept(AMETHYST_SHIELD);
    }

    private static void addItemsToIngredientsTabItemGroup(FabricCreativeModeTabOutput entries) {
        entries.accept(OXIWINE_BOLT);
    }

    public static void registerModItems() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::addItemsToIngredientsTabItemGroup);
    }
}