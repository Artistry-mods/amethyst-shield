package chaos.amyshield.block.blockEntities.custom;

import chaos.amyshield.block.blockEntities.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class AmethystDispenserBlockEntity extends DispenserBlockEntity {
    public int cooldown;

    public AmethystDispenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY, pos, state);
        this.cooldown = 0;
    }

    public List<ItemStack> getInventory() {
        return this.getHeldStacks();
    }
}
