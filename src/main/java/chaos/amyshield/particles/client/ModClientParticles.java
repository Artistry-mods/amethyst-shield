package chaos.amyshield.particles.client;

import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.particles.custom.AmethystChargeParticle;
import chaos.amyshield.particles.custom.AmethystCritParticle;
import chaos.amyshield.particles.custom.AmethystMonoclePing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class ModClientParticles {
    public static void registerModParticlesClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_CHARGE_PARTICLE, spriteProvider -> new AmethystChargeParticle.Factory(spriteProvider, false));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, spriteProvider -> new AmethystChargeParticle.Factory(spriteProvider, true));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_CRIT_PARTICLE, AmethystCritParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_DOWN, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.DOWN));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_UP, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.UP));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_NORTH, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.NORTH));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_SOUTH, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.SOUTH));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_WEST, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.WEST));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_EAST, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.EAST));
    }
}
