package chaos.amyshield.abilities;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record ChargeGain() implements AmethystShieldAbility {
    @Override
    public float getChargeCost() {
        throw new UnsupportedOperationException("Charge Gain should be calculated separately");
    }

    @Override
    public boolean shouldDisplayParticle() {
        return false;
    }

    @Override
    public boolean shouldParticleBeFlat() {
        return false;
    }

    @Override
    public boolean shouldPlaySound() {
        return false;
    }

    @Override
    public void onTrigger(ServerPlayNetworking.Context context) {

    }

    public static String getId() {
        return "charge_gain";
    }
}
