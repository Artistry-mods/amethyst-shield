package chaos.amyshield.abilities;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public interface AmethystShieldAbility {
    float getChargeCost();

    boolean shouldDisplayParticle();

    boolean shouldParticleBeFlat();

    boolean shouldPlaySound();

    default SoundEvent getSoundEvent() {
        return SoundEvents.BLOCK_BELL_RESONATE;
    }

    void onTrigger(ServerPlayNetworking.Context context);
}
