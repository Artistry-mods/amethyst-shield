package chaos.amyshield.block;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block AMETHYST_DISPENSER = register("amethyst_dispenser",
            AmethystDispenserBlock::new, AbstractBlock.Settings.copy(Blocks.DISPENSER));

    public static final Block DIAMOND_DEPOSIT = register("diamond_deposit",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE));

	public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		Block block = (Block)factory.apply(settings.registryKey(key));
		return (Block)Registry.register(Registries.BLOCK, (RegistryKey)key, block);
	}

	public static Block register(RegistryKey<Block> key, AbstractBlock.Settings settings) {
		return register(key, Block::new, settings);
	}

	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AmethystShield.MOD_ID, id));
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return register(keyOf(id), factory, settings);
	}

	private static Block register(String id, AbstractBlock.Settings settings) {
		return register(id, Block::new, settings);
	}


    public static void registerModBlocks() {
    }
}
