package chaos.amyshield.block.blockEntities.custom;

import chaos.amyshield.block.blockEntities.ModBlockEntities;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.text.Text;

public class AmethystDispenserBlockEntity extends DispenserBlockEntity {
    public AmethystDispenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.dropper");
    }
}
