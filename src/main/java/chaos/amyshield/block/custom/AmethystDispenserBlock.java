package chaos.amyshield.block.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.blockEntities.ModBlockEntities;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.TickPriority;

public class AmethystDispenserBlock extends DispenserBlock implements BlockEntityProvider {
    public AmethystDispenserBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        ItemStack stack = Items.DISPENSER.asItem().getDefaultStack();
        if (includeData && world.getBlockEntity(pos) instanceof AmethystDispenserBlockEntity amethystDispenserBlockEntity) {
            ComponentMap map = ComponentMap.builder().add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(amethystDispenserBlockEntity.getInventory())).build();

            stack.applyComponentsFrom(map);
        }
        return stack;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AmethystDispenserBlockEntity(pos, state);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!(world.getBlockEntity(pos) instanceof AmethystDispenserBlockEntity amethystDispenserBlockEntity)) {
            return;
        }

        if (amethystDispenserBlockEntity.cooldown == 0 && state.get(TRIGGERED)) {
            super.scheduledTick(state, world, pos, random);
            amethystDispenserBlockEntity.cooldown = AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_COOLDOWN();
            world.scheduleBlockTick(pos, state.getBlock(), 1, TickPriority.NORMAL);
        } else {
            amethystDispenserBlockEntity.cooldown = Math.max(0, amethystDispenserBlockEntity.cooldown - 1);
            if (amethystDispenserBlockEntity.cooldown != 0) {
                world.scheduleBlockTick(pos, state.getBlock(), 1, TickPriority.NORMAL);
            }
        }
    }

    @Override
    protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
        AmethystDispenserBlockEntity dispenserBlockEntity = world.getBlockEntity(pos, ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY).orElse(null);
        if (dispenserBlockEntity == null) {
            AmethystShield.LOGGER.warn("Ignoring dispensing attempt for Dispenser without matching block entity at {}", pos);
        } else {
            BlockPointer blockPointer = new BlockPointer(world, pos, state, dispenserBlockEntity);
            int i = dispenserBlockEntity.chooseNonEmptySlot(world.random);
            if (i < 0) {
                world.syncWorldEvent(1001, pos, 0);
                world.emitGameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Emitter.of(dispenserBlockEntity.getCachedState()));
            } else {
                ItemStack itemStack = dispenserBlockEntity.getStack(i);
                DispenserBehavior dispenserBehavior = this.getBehaviorForItem(world, itemStack);
                if (dispenserBehavior != DispenserBehavior.NOOP) {
                    dispenserBlockEntity.setStack(i, dispenserBehavior.dispense(blockPointer, itemStack));
                }
            }
        }
    }
}
