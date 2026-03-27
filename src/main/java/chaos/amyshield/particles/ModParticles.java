package chaos.amyshield.particles;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

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
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, name), particle);
    }

    public static void registerModParticles() {

    }
}
