package chaos.amyshield.abilities;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record SlashAmethystShieldAbility() implements AmethystShieldAbility {
    @Override
    public float getChargeCost() {
        return AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_COST();
    }

    @Override
    public boolean shouldDisplayParticle() {
        return true;
    }

    @Override
    public boolean shouldParticleBeFlat() {
        return false;
    }

    @Override
    public boolean shouldPlaySound() {
        return true;
    }

    @Override
    public void onTrigger(ServerPlayNetworking.Context context) {

    }

    public static String getId() {
        return "slash";
    }
}
