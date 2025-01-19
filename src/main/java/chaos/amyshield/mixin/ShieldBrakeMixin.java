package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static chaos.amyshield.Item.custom.AmethystShieldItem.syncCharge;

@Mixin(PlayerEntity.class)
public class ShieldBrakeMixin {

    @Inject(method = "disableShield", at = @At("HEAD"))
    public void disableShield(boolean sprinting, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        float f = 0.25f + (float) EnchantmentHelper.getEfficiency(player) * 0.05f;
        if (sprinting) {
            f += 0.75f;
        }
        if (player.getRandom().nextFloat() < f) {
            player.getItemCooldownManager().set(ModItems.AMETHYST_SHIELD, 100);
            player.clearActiveItem();
            player.getWorld().sendEntityStatus(player, EntityStatuses.BREAK_SHIELD);
        }
    }


    @Inject(method = "damageShield", at = @At("HEAD"))
    public void damageShield(float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!(player.getActiveItem().getItem() instanceof ShieldItem)) {
            return;
        }
        if (!player.getWorld().isClient) {
            player.incrementStat(Stats.USED.getOrCreateStat(player.getActiveItem().getItem()));
        }
        if (player.getActiveItem().getItem() instanceof AmethystShieldItem) {
            double addedCharge = amount * AmethystShield.CONFIG.amethystShieldNested.chargeNested.BLOCK_GAIN_MULTIPLIER();
            AmethystShieldItem.addCharge(((IEntityDataSaver) player), (float) addedCharge);
            syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), (ServerPlayerEntity) player);
        }
        if (amount >= 3.0f) {
            int i = 1 + MathHelper.floor(amount);
            Hand hand = player.getActiveHand();
            player.getActiveItem().damage(i, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            if (player.getActiveItem().isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                player.clearActiveItem();
                player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + player.getWorld().random.nextFloat() * 0.4f);
            }
        }
    }
}
