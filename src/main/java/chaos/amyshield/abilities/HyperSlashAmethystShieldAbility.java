package chaos.amyshield.abilities;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sound.SoundEvent;

public record HyperSlashAmethystShieldAbility() implements AmethystShieldAbility {
    @Override
    public float getChargeCost() {
        return 0;
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
    public SoundEvent getSoundEvent() {
        return AmethystShieldAbility.super.getSoundEvent();
    }

    @Override
    public void onTrigger(ServerPlayNetworking.Context context) {

    }

    public static String getId() {
        return "hyper_shlash";
    }
}
