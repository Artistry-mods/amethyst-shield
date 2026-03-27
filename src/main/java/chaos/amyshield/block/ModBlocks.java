package chaos.amyshield.block;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block AMETHYST_DISPENSER = register("amethyst_dispenser",
            AmethystDispenserBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DISPENSER));

    public static final Block DIAMOND_DEPOSIT = register("diamond_deposit",
            RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));

	public static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
		Block block = (Block)factory.apply(settings.setId(key));
		return (Block) Registry.register(BuiltInRegistries.BLOCK, (ResourceKey)key, block);
	}

	public static Block register(ResourceKey<Block> key, BlockBehaviour.Properties settings) {
		return register(key, Block::new, settings);
	}

	private static ResourceKey<Block> keyOf(String id) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

	private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
		return register(keyOf(id), factory, settings);
	}

	private static Block register(String id, BlockBehaviour.Properties settings) {
		return register(id, Block::new, settings);
	}


    public static void registerModBlocks() {
    }
}
