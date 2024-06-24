package chaos.amyshield.particles;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static SimpleParticleType AMETHYST_CHARGE_PARTICLE = register(FabricParticleTypes.simple(true),
            "amethyst_charge_particle");
    public static SimpleParticleType AMETHYST_CHARGE_PARTICLE_FLAT = register(FabricParticleTypes.simple(true),
            "amethyst_charge_flat_particle");
    public static SimpleParticleType AMETHYST_CRIT_PARTICLE = register(FabricParticleTypes.simple(true),
            "amethyst_crit_particle");


    public static SimpleParticleType AMETHYST_MONOCLE_PING_UP = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_up");
    public static SimpleParticleType AMETHYST_MONOCLE_PING_DOWN = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_down");
    public static SimpleParticleType AMETHYST_MONOCLE_PING_WEST = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_west");
    public static SimpleParticleType AMETHYST_MONOCLE_PING_EAST = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_east");
    public static SimpleParticleType AMETHYST_MONOCLE_PING_NORTH = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_north");
    public static SimpleParticleType AMETHYST_MONOCLE_PING_SOUTH = register(FabricParticleTypes.simple(true),
            "amethyst_monocle_ping_south");

    private static SimpleParticleType register(SimpleParticleType particle, String name) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(AmethystShield.MOD_ID, name), particle);
    }

    public static void registerModParticles() {
        AmethystShield.LOGGER.info("Registering Particles for " + AmethystShield.MOD_ID);
    }
}
