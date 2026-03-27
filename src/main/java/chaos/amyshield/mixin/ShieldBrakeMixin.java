package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static chaos.amyshield.item.custom.AmethystShieldItem.syncCharge;

@Mixin(BlocksAttacks.class)
public class ShieldBrakeMixin {

    @Inject(method = "hurtBlockingItem", at = @At("HEAD"))
    public void damageShield(Level world, ItemStack stack, LivingEntity entity, InteractionHand hand, float amount, CallbackInfo ci) {
        if (entity instanceof Player player) {
            if (!(player.getUseItem().getItem() instanceof ShieldItem)) {
                return;
            }
            if (!player.level().isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(player.getUseItem().getItem()));
            }

            if (player.getUseItem().getItem() instanceof AmethystShieldItem) {
                double addedCharge = amount * AmethystShield.CONFIG.amethystShieldNested.chargeNested.BLOCK_GAIN_MULTIPLIER();
                AmethystShieldItem.addCharge((player), (float) addedCharge);
                syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), (ServerPlayer) player);
            }
        }

//        if (amount <= 3.0f) {
//            return;
//        }
//
//        if (!player.getActiveItem().isEmpty()) {
//            return;
//        }
//
//        int i = 1 + MathHelper.floor(amount);
//        player.getActiveItem().damage(i, player.getActiveItem().getItem(), player, EquipmentSlot.OFFHAND);
//
//        if (hand == Hand.MAIN_HAND) {
//            player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
//        } else {
//            player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
//        }
//        player.clearActiveItem();
//        player.playSound(SoundEvents.ITEM_SHIELD_BREAK.value(), 0.8f, 0.8f + player.getWorld().random.nextFloat() * 0.4f);
    }
}
