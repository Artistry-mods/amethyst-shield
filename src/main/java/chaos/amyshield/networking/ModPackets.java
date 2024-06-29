package chaos.amyshield.networking;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.networking.C2Server.AmethystPushAbilityPacketC2S;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityPacketC2S;
import chaos.amyshield.networking.C2Server.AmethystSlashAbilitySyncPacketC2S;
import chaos.amyshield.networking.S2Client.SyncChargePacketS2C;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.networking.playload.AmethystPushPayload;
import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.networking.playload.SyncSlashPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier AMETHYST_PUSH_ABILITY_C2S = Identifier.of(AmethystShield.MOD_ID, "do_a_push");
    public static final Identifier SYNC_SLASHING_S2C = Identifier.of(AmethystShield.MOD_ID, "sync_slashing");

    public static final Identifier AMETHYST_ABILITY_C2S = Identifier.of(AmethystShield.MOD_ID, "remove_charge");
    public static final Identifier SYNC_CHARGE_S2C = Identifier.of(AmethystShield.MOD_ID, "sync_charge");

    public static void registerGlobalReceiversC2S() {
        PayloadTypeRegistry.playC2S().register(AmethystAbilityPayload.ID, AmethystAbilityPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SyncSlashPayload.ID, SyncSlashPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(AmethystPushPayload.ID, AmethystPushPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(AmethystAbilityPayload.ID, AmethystShieldAbilityPacketC2S::setChargeAndSpawnParticle);
        ServerPlayNetworking.registerGlobalReceiver(SyncSlashPayload.ID, AmethystSlashAbilitySyncPacketC2S::syncSlash);
        ServerPlayNetworking.registerGlobalReceiver(AmethystPushPayload.ID, AmethystPushAbilityPacketC2S::push);
    }

    //ServerPlayNetworking.registerReceiver(BoomPayload.ID, (payload, context) -> {     ModPacketHandler. createExplosion(context. player(), payload. fire()); });

    public static void registerGlobalReceiversS2C() {
        PayloadTypeRegistry.playS2C().register(SyncChargePayload.ID, SyncChargePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SyncChargePayload.ID, SyncChargePacketS2C::syncCharge);
    }
}
