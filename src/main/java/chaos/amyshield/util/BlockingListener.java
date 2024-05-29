package chaos.amyshield.util;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import static chaos.amyshield.Item.custom.AmethystShieldItem.syncCharge;

public class BlockingListener {
    public static void init() {
        ShieldBlockCallback.EVENT.register(BlockingListener::listener);
    }
    private static ActionResult listener(LivingEntity defender, DamageSource damageSource, float amount, Hand hand, ItemStack itemStack) {
        if (!defender.isInvulnerableTo(damageSource) && defender.canTakeDamage() && itemStack.getItem() == ModItems.AMETHYST_SHIELD && defender instanceof ServerPlayerEntity && !damageSource.isOf(DamageTypes.INDIRECT_MAGIC) && !damageSource.isOf(DamageTypes.MAGIC)) {
            double addedCharge = amount * AmethystShield.BLOCK_GAIN_MULTIPLIER;
            AmethystShieldItem.addCharge(((IEntityDataSaver) defender), (float) addedCharge);
            syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) defender)), (ServerPlayerEntity) defender);
        }
        return ActionResult.PASS;
    }
}
