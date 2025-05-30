package chaos.amyshield.mixin;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.custom.AmethystDispenserBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.client.sound.Sound;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
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

    public AmethystDispenserConverterMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "neighborUpdate", at = @At(value = "HEAD"))
    public void getStateForNeighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify, CallbackInfo ci) {
        convertDispenserToAmethyst(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Unique
    private static void convertDispenserToAmethyst(BlockState state, World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify) {
        BlockPos sourcePos = pos.up();
        if (world.getBlockState(sourcePos).getBlock() != Blocks.AMETHYST_CLUSTER || !world.getBlockState(sourcePos).get(FACING).equals(Direction.UP) || !pos.equals(sourcePos.down())) {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof DispenserBlockEntity) || blockEntity instanceof AmethystDispenserBlockEntity) {
            return;
        }

        Map<ItemStack, Integer> itemStackMap = new java.util.HashMap<>(Map.of());
        for (int i = 0; i < ((DispenserBlockEntity) blockEntity).size(); i++) {
            itemStackMap.put(((DispenserBlockEntity) blockEntity).getStack(i), i);
        }

        ((DispenserBlockEntity) blockEntity).clear();
        world.removeBlockEntity(pos);
        world.setBlockState(pos, ModBlocks.AMETHYST_DISPENSER.getDefaultState().with(FACING, state.get(FACING)), 3);
        BlockEntity amethystblockEntity = world.getBlockEntity(pos);
        if (amethystblockEntity instanceof AmethystDispenserBlockEntity) {
            amethystblockEntity.markDirty();
        }

        if (amethystblockEntity instanceof AmethystDispenserBlockEntity) {
            itemStackMap.forEach((stack, slot) -> ((AmethystDispenserBlockEntity) amethystblockEntity).setStack(slot, stack));
        }
    }
}
