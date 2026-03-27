package chaos.amyshield.particles.client;

import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.particles.custom.AmethystChargeParticle;
import chaos.amyshield.particles.custom.AmethystCritParticle;
import chaos.amyshield.particles.custom.AmethystMonoclePing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class ModClientParticles {
    public static void registerModParticlesClient() {
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_CHARGE_PARTICLE, spriteProvider -> new AmethystChargeParticle.Factory(spriteProvider, false));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, spriteProvider -> new AmethystChargeParticle.Factory(spriteProvider, true));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_CRIT_PARTICLE, AmethystCritParticle.Factory::new);

        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_DOWN, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.DOWN));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_UP, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.UP));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_NORTH, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.NORTH));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_SOUTH, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.SOUTH));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_WEST, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.WEST));
        ParticleProviderRegistry.getInstance().register(ModParticles.AMETHYST_MONOCLE_PING_EAST, spriteProvider -> new AmethystMonoclePing.Factory(spriteProvider, Direction.EAST));
    }
}
