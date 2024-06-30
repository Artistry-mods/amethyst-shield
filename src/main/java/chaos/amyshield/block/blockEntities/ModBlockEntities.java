package chaos.amyshield.block.blockEntities;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    @SuppressWarnings("unused")
    public static final BlockEntityType<AmethystDispenserBlockEntity> AMETHYST_DISPENSER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of("amyshield", "amethyst_dispenser_block_entity"),
            BlockEntityType.Builder.create(AmethystDispenserBlockEntity::new, ModBlocks.AMETHYST_DISPENSER).build()
    );

    public static void registerModBlockEntities() {
        AmethystShield.LOGGER.info("Registering Block Entities for " + AmethystShield.MOD_ID);
    }
}
