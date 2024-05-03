package chaos.amyshield.mixin;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class DoubleJumpAbilityMixin {


    /*
    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovements(CallbackInfo ci) {
    }
     */
}
