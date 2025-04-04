package chaos.amyshield.block.custom;

import chaos.amyshield.block.blockEntities.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

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
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        boolean bl = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean bl2 = state.get(TRIGGERED);
        if (bl && !bl2 && this.cooldown == 0) {
            tickView.scheduleBlockTick(pos, this, 1);
            return state.with(TRIGGERED, true);
        } else if (!bl && bl2) {
            this.cooldown = 5;
            tickView.scheduleBlockTick(pos, this, 1);
            return state.with(TRIGGERED, false);
        }

        return state;
    }


    @Override
    public Item asItem() {
        return Items.DISPENSER;
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
