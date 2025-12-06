package chaos.amyshield.networking.C2Server;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.networking.playload.IgnoreFallDamagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class IgnorFallDamagePacketC2S {
    public static void ignoreFallDamage(IgnoreFallDamagePayload ignoreFallDamagePayload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : List.of(context.player().getMainHandStack(), context.player().getOffHandStack())) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    ServerPlayerEntity player = context.player();

                    if (player.currentExplosionImpactPos != null) {
                        if (player.currentExplosionImpactPos.y < ignoreFallDamagePayload.till().y) {
                            return;
                        }
                    }
                    player.currentExplosionImpactPos = ignoreFallDamagePayload.till();
                    player.setIgnoreFallDamageFromCurrentExplosion(true);
                }
            }
        });
    }
}