package chaos.amyshield.tag;

import chaos.amyshield.AmethystShield;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> SHINY_ORES = TagKey.of(RegistryKeys.BLOCK,Identifier.of(AmethystShield.MOD_ID, "shiny_ores"));

    public static void registerModKeys() {

    }
}
