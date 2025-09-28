package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public class GrantChargeOnMaceHitMixin {
    @Inject(method = "getBonusAttackDamage", at = @At("RETURN"))
    public void onGetBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        if (damageSource.getAttacker() instanceof PlayerEntity player) {
            AmethystShieldItem.addCharge(player, cir.getReturnValue() * AmethystShield.CONFIG.amethystShieldNested.chargeNested.MACE_HIT_MULTIPLIER());
        }
    }
}
