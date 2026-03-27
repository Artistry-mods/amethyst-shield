package chaos.amyshield.tag;

import chaos.amyshield.AmethystShield;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ModTags {
    public static final TagKey<@NotNull Block> SHINY_ORES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "shiny_ores"));
    public static final TagKey<@NotNull Item> AMETHYST_SHIELD_ENCHANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "amethyst_shield_enchantable"));
    public static final TagKey<@NotNull EntityType<?>> SLASH_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "slash_immune"));

    public static void registerModKeys() {

    }
}
