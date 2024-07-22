package chaos.amyshield.networking.S2Client;

import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SyncChargePacketS2C {
    public static void syncCharge(SyncChargePayload customPayload, ClientPlayNetworking.Context context) {
        ((IEntityDataSaver) context.player()).amethyst_shield$getPersistentData().putFloat("charge", customPayload.chargeAmount());
    }
}
