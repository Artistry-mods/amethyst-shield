package chaos.amyshield.networking.C2Server;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.networking.playload.IgnoreFallDamagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class IgnorFallDamagePacketC2S {
    public static void ignoreFallDamage(IgnoreFallDamagePayload ignoreFallDamagePayload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : List.of(context.player().getMainHandItem(), context.player().getOffhandItem())) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    ServerPlayer player = context.player();

                    if (player.currentImpulseImpactPos != null) {
                        if (player.currentImpulseImpactPos.y < ignoreFallDamagePayload.till().y) {
                            return;
                        }
                    }
                    player.currentImpulseImpactPos = ignoreFallDamagePayload.till();
                    player.setIgnoreFallDamageFromCurrentImpulse(true);
                }
            }
        });
    }
}