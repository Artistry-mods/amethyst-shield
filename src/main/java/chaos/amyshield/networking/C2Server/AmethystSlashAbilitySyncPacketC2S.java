package chaos.amyshield.networking.C2Server;

import chaos.amyshield.networking.playload.SyncSlashPayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class AmethystSlashAbilitySyncPacketC2S {
    public static void syncSlash(SyncSlashPayload customPayload, ServerPlayNetworking.Context context) {
        ((IEntityDataSaver) context.player()).amethyst_shield$getPersistentData().putBoolean("slashing", customPayload.isSlashing());
    }
}