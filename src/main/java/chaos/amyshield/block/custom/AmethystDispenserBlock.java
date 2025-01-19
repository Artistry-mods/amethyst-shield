package chaos.amyshield.block.custom;

import chaos.amyshield.block.blockEntities.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class AmethystDispenserBlock extends DispenserBlock implements BlockEntityProvider {
    public int cooldown;
    public static final MapCodec<AmethystDispenserBlock> CODEC = createCodec(AmethystDispenserBlock::new);

    public AmethystDispenserBlock(Settings settings) {
        super(settings);
        this.cooldown = 0;
    }

    public MapCodec<AmethystDispenserBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean bl2 = state.get(TRIGGERED);
        if (bl && !bl2 && this.cooldown == 0) {
            world.scheduleBlockTick(pos, this, 1);
            world.setBlockState(pos, state.with(TRIGGERED, true), Block.NO_REDRAW);
        } else if (!bl && bl2) {
            this.cooldown = 5;
            world.scheduleBlockTick(pos, this, 1);
            world.setBlockState(pos, state.with(TRIGGERED, false), Block.NO_REDRAW);
        }
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return Items.DISPENSER.getDefaultStack();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(TRIGGERED)) {
            this.dispense(world, state, pos);
        } else {
            if (this.cooldown > 0) {
                this.cooldown--;
                world.scheduleBlockTick(pos, this, 2);
            }
        }
    }
}
