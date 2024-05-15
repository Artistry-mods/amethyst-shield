package chaos.amyshield.util;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class BlockingListener {
    public static void init() {
        ShieldBlockCallback.EVENT.register(BlockingListener::listener);

    }

    private static ActionResult listener(LivingEntity defender, DamageSource damageSource, float amount, Hand hand, ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            float currantCharge = AmethystShieldItem.getCharge(itemStack);
            System.out.println("Charge is: " + (currantCharge + amount) + " | Damage is: " + amount);
            AmethystShieldItem.setCharge(itemStack, currantCharge + amount);
            return ActionResult.PASS;
        }
        return ActionResult.PASS;
    }
}
