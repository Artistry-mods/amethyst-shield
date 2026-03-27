package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public class GrantChargeOnMaceHitMixin {
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"))
    public void onGetBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        if (damageSource.getEntity() instanceof Player player) {
            AmethystShieldItem.addCharge(player, cir.getReturnValue() * AmethystShield.CONFIG.amethystShieldNested.chargeNested.MACE_HIT_MULTIPLIER());
        }
    }
}
