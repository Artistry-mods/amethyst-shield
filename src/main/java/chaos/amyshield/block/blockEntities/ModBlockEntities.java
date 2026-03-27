package chaos.amyshield.block.blockEntities;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<AmethystDispenserBlockEntity> AMETHYST_DISPENSER_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath("amyshield", "amethyst_dispenser_block_entity"),
            FabricBlockEntityTypeBuilder.create(AmethystDispenserBlockEntity::new, ModBlocks.AMETHYST_DISPENSER).build()
    );

    public static void registerModBlockEntities() {
    }
}
