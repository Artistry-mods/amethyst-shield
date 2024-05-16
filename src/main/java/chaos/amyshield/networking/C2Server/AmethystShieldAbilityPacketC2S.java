package chaos.amyshield.networking.C2Server;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import static chaos.amyshield.Item.custom.AmethystShieldItem.syncCharge;

public class AmethystShieldAbilityPacketC2S {
    public static void receiver(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        float amount = buf.readFloat();
        server.execute(() -> {
            for (ItemStack itemStack : player.getHandItems()) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    AmethystShieldItem.addCharge(((IEntityDataSaver) player), amount);
                    syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                }
            }
        });
    }
}
