package chaos.amyshield.tag;

import chaos.amyshield.AmethystShield;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.Identifier;

public class ModTags {
    public static final TagKey<Block> SHINY_ORES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "shiny_ores"));
    public static final TagKey<Item> AMETHYST_SHIELD_ENCHANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "amethyst_shield_enchantable"));
    public static final TagKey<EntityType<?>> SLASH_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "slash_immune"));

    public static void registerModKeys() {

    }
}
