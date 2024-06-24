package chaos.amyshield.networking;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.networking.C2Server.AmethystPushAbilityPacketC2S;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityPacketC2S;
import chaos.amyshield.networking.C2Server.AmethystSlashAbilitySyncPacketC2S;
import chaos.amyshield.networking.S2Client.SyncChargePacketS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final CustomPayload.Id<CustomPayload> AMETHYST_PUSH_ABILITY_C2S = new CustomPayload.Id<>(Identifier.of(AmethystShield.MOD_ID, "do_a_push"));
    public static final CustomPayload.Id<CustomPayload> AMETHYST_ABILITY_C2S = new CustomPayload.Id<>(Identifier.of(AmethystShield.MOD_ID, "remove_charge"));
    public static final CustomPayload.Id<CustomPayload> SYNC_CHARGE_S2C = new CustomPayload.Id<>(Identifier.of(AmethystShield.MOD_ID, "sync_charge"));
    public static final CustomPayload.Id<CustomPayload> SYNC_SLASHING_S2C = new CustomPayload.Id<>(Identifier.of(AmethystShield.MOD_ID, "sync_slashing"));
    public static void registerGlobalReceiversC2S() {
        /*
        ServerPlayNetworking.registerGlobalReceiver(AMETHYST_PUSH_ABILITY_C2S, AmethystPushAbilityPacketC2S::push);
        ServerPlayNetworking.registerGlobalReceiver(AMETHYST_ABILITY_C2S, AmethystShieldAbilityPacketC2S::setChargeAndSpawnParticle);
        ServerPlayNetworking.registerGlobalReceiver(SYNC_SLASHING_S2C, AmethystSlashAbilitySyncPacketC2S::syncSlash);

         */
        //ServerPlayNetworking.registerGlobalReceiver(new CustomPayload.Id(AMETHYST_PUSH_ABILITY_C2S), AmethystPushAbilityPacketC2S::receiver);
        //ServerPlayNetworking.registerGlobalReceiver(AMETHYST_ABILITY_C2S, AmethystShieldAbilityPacketC2S::receiver);
        //ServerPlayNetworking.registerGlobalReceiver(SYNC_SLASHING_S2C, AmethystSlashAbilitySyncPacketC2S::receiver);
    }

    //ServerPlayNetworking.registerReceiver(BoomPayload.ID, (payload, context) -> {     ModPacketHandler. createExplosion(context. player(), payload. fire()); });

    public static void registerGlobalReceiversS2C() {
        //ClientPlayNetworking.registerGlobalReceiver(SYNC_CHARGE_S2C, SyncChargePacketS2C::syncCharge);
    }
}
