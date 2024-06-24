package chaos.amyshield.networking.S2Client;

import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;

public class SyncChargePacketS2C {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            ((IEntityDataSaver) client.player).getPersistentData().putFloat("charge", buf.readFloat());
        }
    }

    public static void syncCharge(CustomPayload customPayload, ClientPlayNetworking.Context context) {
        System.out.println("sync charge");
    }
}
