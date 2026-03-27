package chaos.amyshield.block.blockEntities.custom;

import chaos.amyshield.block.blockEntities.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import java.util.List;

public class AmethystDispenserBlockEntity extends DispenserBlockEntity {
    public int cooldown;

    public AmethystDispenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY, pos, state);
        this.cooldown = 0;
    }

    public List<ItemStack> getInventory() {
        return this.getItems();
    }
}
