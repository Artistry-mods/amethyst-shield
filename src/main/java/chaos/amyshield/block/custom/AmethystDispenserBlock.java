package chaos.amyshield.block.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.blockEntities.ModBlockEntities;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.ticks.TickPriority;

public class AmethystDispenserBlock extends DispenserBlock implements EntityBlock {
    public AmethystDispenserBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
        ItemStack stack = Items.DISPENSER.asItem().getDefaultInstance();
        if (includeData && world.getBlockEntity(pos) instanceof AmethystDispenserBlockEntity amethystDispenserBlockEntity) {
            DataComponentMap map = DataComponentMap.builder().set(DataComponents.CONTAINER, ItemContainerContents.fromItems(amethystDispenserBlockEntity.getInventory())).build();

            stack.applyComponents(map);
        }
        return stack;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AmethystDispenserBlockEntity(pos, state);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!(world.getBlockEntity(pos) instanceof AmethystDispenserBlockEntity amethystDispenserBlockEntity)) {
            return;
        }

        if (amethystDispenserBlockEntity.cooldown == 0 && state.getValue(TRIGGERED)) {
            super.tick(state, world, pos, random);
            amethystDispenserBlockEntity.cooldown = AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_COOLDOWN();
            world.scheduleTick(pos, state.getBlock(), 1, TickPriority.NORMAL);
        } else {
            amethystDispenserBlockEntity.cooldown = Math.max(0, amethystDispenserBlockEntity.cooldown - 1);
            if (amethystDispenserBlockEntity.cooldown != 0) {
                world.scheduleTick(pos, state.getBlock(), 1, TickPriority.NORMAL);
            }
        }
    }

    @Override
    protected void dispenseFrom(ServerLevel world, BlockState state, BlockPos pos) {
        AmethystDispenserBlockEntity dispenserBlockEntity = world.getBlockEntity(pos, ModBlockEntities.AMETHYST_DISPENSER_BLOCK_ENTITY).orElse(null);
        if (dispenserBlockEntity == null) {
            AmethystShield.LOGGER.warn("Ignoring dispensing attempt for Dispenser without matching block entity at {}", pos);
        } else {
            BlockSource blockPointer = new BlockSource(world, pos, state, dispenserBlockEntity);
            int i = dispenserBlockEntity.getRandomSlot(world.random);
            if (i < 0) {
                world.levelEvent(1001, pos, 0);
                world.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(dispenserBlockEntity.getBlockState()));
            } else {
                ItemStack itemStack = dispenserBlockEntity.getItem(i);
                DispenseItemBehavior dispenserBehavior = this.getDispenseMethod(world, itemStack);
                if (dispenserBehavior != DispenseItemBehavior.NOOP) {
                    dispenserBlockEntity.setItem(i, dispenserBehavior.dispense(blockPointer, itemStack));
                }
            }
        }
    }
}
