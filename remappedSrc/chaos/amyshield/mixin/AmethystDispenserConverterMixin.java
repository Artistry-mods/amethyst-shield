package chaos.amyshield.mixin;

import chaos.amyshield.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;


@Mixin(DispenserBlock.class)
public class AmethystDispenserConverterMixin {
    @Shadow @Final public static DirectionProperty FACING;

    @Inject(method = "neighborUpdate", at = @At("HEAD"))
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (world.getBlockState(sourcePos).getBlock() == Blocks.AMETHYST_CLUSTER && world.getBlockState(sourcePos).get(FACING).equals(Direction.UP) && pos.equals(sourcePos.down())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DispenserBlockEntity) {
                Map<ItemStack, Integer> itemStackMap = new java.util.HashMap<>(Map.of());
                for (int i = 0; i < ((DispenserBlockEntity) blockEntity).size(); i++) {
                    itemStackMap.put(((DispenserBlockEntity) blockEntity).getStack(i), i);
                }

                ((DispenserBlockEntity) blockEntity).clear();
                world.setBlockState(pos, ModBlocks.AMETHYST_DISPENSER.getDefaultState().with(FACING, state.get(FACING)));
                BlockEntity amethystblockEntity = world.getBlockEntity(pos);

                if (amethystblockEntity instanceof DispenserBlockEntity) {
                    itemStackMap.forEach((stack, slot) -> ((DispenserBlockEntity) amethystblockEntity).setStack(slot, stack));
                }
            }
        }
    }
}
