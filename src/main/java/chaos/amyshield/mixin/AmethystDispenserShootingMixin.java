package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileDispenserBehavior.class)
public class AmethystDispenserShootingMixin {
    @Shadow @Final private ProjectileItem projectile;

    @Shadow @Final private ProjectileItem.Settings projectileSettings;

    @Inject(method = "dispenseSilently", at = @At("HEAD"), cancellable = true)
    public void dispenseSilently(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (pointer.state().getBlock() instanceof AmethystDispenserBlock) {
            ServerWorld serverWorld = pointer.world();
            Direction direction = pointer.state().get(DispenserBlock.FACING);
            Position position = this.projectileSettings.positionFunction().getDispensePosition(pointer, direction);
            ProjectileEntity.spawnWithVelocity(this.projectile.createEntity(serverWorld, position, stack, direction), serverWorld, stack, direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ(), this.projectileSettings.power() * AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_STRENGTH(), this.projectileSettings.uncertainty() * AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_SPREAD());
            stack.decrement(1);
            cir.setReturnValue(stack);
        }
    }
}
