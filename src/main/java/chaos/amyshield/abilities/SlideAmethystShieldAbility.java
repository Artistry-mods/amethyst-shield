package chaos.amyshield.abilities;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record SlideAmethystShieldAbility() implements AmethystShieldAbility {
    @Override
    public float getChargeCost() {
        return AmethystShield.CONFIG.amethystShieldNested.slideNested.AMETHYST_SLIDE_COST();
    }

    @Override
    public boolean shouldDisplayParticle() {
        return true;
    }

    @Override
    public boolean shouldParticleBeFlat() {
        return true;
    }

    @Override
    public boolean shouldPlaySound() {
        return true;
    }

    @Override
    public void onTrigger(ServerPlayNetworking.Context context) {

    }

    public static String getId() {
        return "slide";
    }
}
