package chaos.amyshield.networking;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.networking.C2Server.AmethystPushAbilityListener;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityListener;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier AMETHYST_PUSH_ABILITY_C2S = new Identifier(AmethystShield.MOD_ID, "do_a_push");
    public static final Identifier AMETHYST_ABILITY_C2S = new Identifier(AmethystShield.MOD_ID, "remove_charge");

    public static void registerGlobalReceiversC2S() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.AMETHYST_PUSH_ABILITY_C2S, AmethystPushAbilityListener::receiver);
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.AMETHYST_ABILITY_C2S, AmethystShieldAbilityListener::receiver);
    }

    public static void registerGlobalReceiverS2C() {

    }
}
