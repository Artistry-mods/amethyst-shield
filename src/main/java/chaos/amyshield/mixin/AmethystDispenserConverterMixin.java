package chaos.amyshield.mixin;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;


@Mixin(DispenserBlock.class)
public class AmethystDispenserConverterMixin extends Block {
    @Shadow
    @Final
    public static EnumProperty<Direction> FACING;

    public AmethystDispenserConverterMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "neighborChanged", at = @At(value = "HEAD"))
    public void getStateForNeighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, Orientation wireOrientation, boolean notify, CallbackInfo ci) {
        convertDispenserToAmethyst(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Unique
    private static void convertDispenserToAmethyst(BlockState state, Level world, BlockPos pos, Block sourceBlock, Orientation wireOrientation, boolean notify) {
        BlockPos sourcePos = pos.above();
        if (world.getBlockState(sourcePos).getBlock() != Blocks.AMETHYST_CLUSTER || !world.getBlockState(sourcePos).getValue(FACING).equals(Direction.UP) || !pos.equals(sourcePos.below())) {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof DispenserBlockEntity) || blockEntity instanceof AmethystDispenserBlockEntity) {
            return;
        }

        Map<ItemStack, Integer> itemStackMap = new java.util.HashMap<>(Map.of());
        for (int i = 0; i < ((DispenserBlockEntity) blockEntity).getContainerSize(); i++) {
            itemStackMap.put(((DispenserBlockEntity) blockEntity).getItem(i), i);
        }

        ((DispenserBlockEntity) blockEntity).clearContent();
        world.removeBlockEntity(pos);
        world.setBlock(pos, ModBlocks.AMETHYST_DISPENSER.defaultBlockState().setValue(FACING, state.getValue(FACING)), 3);
        BlockEntity amethystblockEntity = world.getBlockEntity(pos);
        if (amethystblockEntity instanceof AmethystDispenserBlockEntity) {
            amethystblockEntity.setChanged();
        }

        if (amethystblockEntity instanceof AmethystDispenserBlockEntity) {
            itemStackMap.forEach((stack, slot) -> ((AmethystDispenserBlockEntity) amethystblockEntity).setItem(slot, stack));
        }
    }
}
