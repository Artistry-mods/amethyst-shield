package chaos.amyshield.sounds;

import chaos.amyshield.AmethystShield;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent HYPER_EXPLOSION = registerSound("hyper-explosion");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(AmethystShield.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init() {
    }
}
