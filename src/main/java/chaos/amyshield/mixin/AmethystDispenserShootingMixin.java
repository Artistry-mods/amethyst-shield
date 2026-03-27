package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.block.custom.AmethystDispenserBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileDispenseBehavior.class)
public class AmethystDispenserShootingMixin {
    @Shadow @Final private ProjectileItem projectileItem;

    @Shadow @Final private ProjectileItem.DispenseConfig dispenseConfig;

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    public void dispenseSilently(BlockSource pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (pointer.state().getBlock() instanceof AmethystDispenserBlock) {
            ServerLevel serverWorld = pointer.level();
            Direction direction = pointer.state().getValue(DispenserBlock.FACING);
            Position position = this.dispenseConfig.positionFunction().getDispensePosition(pointer, direction);
            Projectile.spawnProjectileUsingShoot(this.projectileItem.asProjectile(serverWorld, position, stack, direction), serverWorld, stack, direction.getStepX(), direction.getStepY(), direction.getStepZ(), this.dispenseConfig.power() * AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_STRENGTH(), this.dispenseConfig.uncertainty() * AmethystShield.CONFIG.dispenserNested.AMETHYST_DISPENSER_SPREAD());
            stack.shrink(1);
            cir.setReturnValue(stack);
        }
    }
}
