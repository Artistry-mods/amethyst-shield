package chaos.amyshield.util;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AmethystShieldAbilityListener {
    public static final Identifier AMETHYST_ABILITY_C2S = new Identifier("amyshield", "remove_charge");
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(AMETHYST_ABILITY_C2S,
                (server, player, handler, buf, responseSender) -> {
                    float amount = buf.readFloat();

                    System.out.println(amount);

                    server.execute(() -> {
                        for (ItemStack itemStack : player.getHandItems()) {
                            Item shield = itemStack.getItem();
                            if (shield == ModItems.AMETHYST_SHIELD) {
                                AmethystShieldItem.setCharge(itemStack, AmethystShieldItem.getCharge(itemStack) + amount);
                            }
                        }
                    });
                });
    }
}
