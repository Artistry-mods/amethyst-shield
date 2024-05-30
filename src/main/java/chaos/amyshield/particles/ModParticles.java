package chaos.amyshield.particles;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static DefaultParticleType AMETHYST_CHARGE_PARTICLE = register(FabricParticleTypes.simple(true),
            "amethyst_charge_particle");
    public static DefaultParticleType AMETHYST_CRIT_PARTICLE = register(FabricParticleTypes.simple(true),
            "amethyst_crit_particle");
    private static DefaultParticleType register(DefaultParticleType particle, String name) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(AmethystShield.MOD_ID, name), particle);
    }

    public static void registerModParticles() {
        AmethystShield.LOGGER.info("Registering Particles for " + AmethystShield.MOD_ID);
    }
}
