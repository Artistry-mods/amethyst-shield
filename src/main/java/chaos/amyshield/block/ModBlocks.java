package chaos.amyshield.block;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
	public static final ResourceKey<Block> AMETHYST_DISPENSER_ID = keyOf("amethyst_dispenser");
    public static final Block AMETHYST_DISPENSER = Blocks.register(AMETHYST_DISPENSER_ID,
            AmethystDispenserBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DISPENSER));

	public static final ResourceKey<Block> DIAMOND_DEPOSIT_ID = keyOf("diamond_deposit");
    public static final Block DIAMOND_DEPOSIT = Blocks.register(DIAMOND_DEPOSIT_ID,
            RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));

	private static ResourceKey<Block> keyOf(String id) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, id));
	}

    public static void registerModBlocks() {
    }
}
