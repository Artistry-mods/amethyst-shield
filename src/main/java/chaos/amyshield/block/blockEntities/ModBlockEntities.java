package chaos.amyshield.block.blockEntities;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<AmethystDispenserBlockEntity> AMETHYST_DISPENSER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("amyshield", "amethyst_dispenser_block_entity"),
            FabricBlockEntityTypeBuilder.create(AmethystDispenserBlockEntity::new, ModBlocks.AMETHYST_DISPENSER).build()
    );

    public static void registerModBlockEntities() {
        AmethystShield.LOGGER.info("registering block entities for " + AmethystShield.MOD_ID);
    }
}
