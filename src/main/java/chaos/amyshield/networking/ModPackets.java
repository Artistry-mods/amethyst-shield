package chaos.amyshield.networking;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityPacketC2S;
import chaos.amyshield.networking.C2Server.AmethystSlashAbilitySyncPacketC2S;
import chaos.amyshield.networking.C2Server.IgnorFallDamagePacketC2S;
import chaos.amyshield.networking.S2Client.SyncChargePacketS2C;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.networking.playload.IgnoreFallDamagePayload;
import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.networking.playload.SyncSlashPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;

public class ModPackets {
    public static final Identifier SYNC_SLASHING_S2C = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "sync_slashing");

    public static final Identifier AMETHYST_ABILITY_C2S = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "remove_charge");
    public static final Identifier SYNC_CHARGE_S2C = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "sync_charge");
    public static final Identifier IGNORE_FALL_DAMAGE_ABILITY_C2S = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "ignore_fall_damage");

    public static void registerGlobalReceiversC2S() {
        PayloadTypeRegistry.serverboundPlay().register(AmethystAbilityPayload.ID, AmethystAbilityPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SyncSlashPayload.ID, SyncSlashPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(IgnoreFallDamagePayload.ID, IgnoreFallDamagePayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncChargePayload.ID, SyncChargePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(AmethystAbilityPayload.ID, AmethystShieldAbilityPacketC2S::setChargeAndSpawnParticle);
        ServerPlayNetworking.registerGlobalReceiver(SyncSlashPayload.ID, AmethystSlashAbilitySyncPacketC2S::syncSlash);
        ServerPlayNetworking.registerGlobalReceiver(IgnoreFallDamagePayload.ID, IgnorFallDamagePacketC2S::ignoreFallDamage);
    }

    public static void registerGlobalReceiversS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SyncChargePayload.ID, SyncChargePacketS2C::syncCharge);
    }
}
