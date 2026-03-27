package chaos.amyshield.abilities;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public interface AmethystShieldAbility {
    float getChargeCost();

    boolean shouldDisplayParticle();

    boolean shouldParticleBeFlat();

    boolean shouldPlaySound();

    default SoundEvent getSoundEvent() {
        return SoundEvents.BELL_RESONATE;
    }

    void onTrigger(ServerPlayNetworking.Context context);
}
