package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static chaos.amyshield.item.custom.AmethystShieldItem.syncCharge;

@Mixin(BlocksAttacksComponent.class)
public class ShieldBrakeMixin {

    @Inject(method = "onShieldHit", at = @At("HEAD"))
    public void damageShield(World world, ItemStack stack, LivingEntity entity, Hand hand, float amount, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            if (!(player.getActiveItem().getItem() instanceof ShieldItem)) {
                return;
            }
            if (!player.getWorld().isClient) {
                player.incrementStat(Stats.USED.getOrCreateStat(player.getActiveItem().getItem()));
            }

            if (player.getActiveItem().getItem() instanceof AmethystShieldItem) {
                double addedCharge = amount * AmethystShield.CONFIG.amethystShieldNested.chargeNested.BLOCK_GAIN_MULTIPLIER();
                AmethystShieldItem.addCharge((player), (float) addedCharge);
                syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), (ServerPlayerEntity) player);
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
