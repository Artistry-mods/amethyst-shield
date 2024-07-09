package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileDispenserBehavior.class)
public class AmethystDispenserShootingMixin {
    @Inject(method = "dispenseSilently", at = @At("HEAD"), cancellable = true)
    public void dispenseSilently(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (pointer.state().getBlock() instanceof AmethystDispenserBlock) {
            ServerWorld world = pointer.world();
            Position position = DispenserBlock.getOutputLocation(pointer);
            Direction direction = pointer.state().get(DispenserBlock.FACING);
            ProjectileEntity projectileEntity = createProjectile(world, position, stack);
            projectileEntity.setVelocity(direction.getOffsetX(), (float) direction.getOffsetY() + 0.1f, direction.getOffsetZ(), AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_STRENGTH(), AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_SPREAD());
            world.spawnEntity(projectileEntity);
            stack.decrement(1);
            cir.setReturnValue(stack);
        }
    }

    @Unique
    protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
        ArrowEntity arrowEntity = new ArrowEntity(world, position.getX(), position.getY(), position.getZ(), stack, null);
        arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return arrowEntity;
    }
}
