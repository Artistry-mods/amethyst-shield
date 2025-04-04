package chaos.amyshield.mixin;

import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
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
            World world = pointer.world();
            Direction direction = pointer.state().get(DispenserBlock.FACING);
            Position position = this.projectileSettings.positionFunction().getDispensePosition(pointer, direction);
            ProjectileEntity projectileEntity = this.projectile.createEntity(world, position, stack, direction);
            this.projectile.initializeProjectile(projectileEntity, direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ(), this.projectileSettings.power() * 2, 0);
            world.spawnEntity(projectileEntity);
            stack.decrement(1);
            cir.setReturnValue(stack);
        }
    }
}
