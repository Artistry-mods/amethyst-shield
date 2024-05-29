package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(DispenserBlock.class)
public class AmethystDispenserConverterMixin {
    @Shadow @Final public static DirectionProperty FACING;

    @Inject(method = "neighborUpdate", at = @At("HEAD"))
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (world.getBlockState(sourcePos).getBlock() == Blocks.AMETHYST_CLUSTER && world.getBlockState(sourcePos).get(FACING).equals(Direction.UP) && pos.equals(sourcePos.down())) {
            world.setBlockState(pos, ModBlocks.AMETHYST_DISPENSER.getDefaultState().with(FACING, state.get(FACING)));
        }
    }
}
