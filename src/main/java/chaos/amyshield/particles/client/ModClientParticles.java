package chaos.amyshield.particles.client;

import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.particles.custom.AmethystChargeParticle;
import chaos.amyshield.particles.custom.AmethystCritParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class ModClientParticles {
    public static void registerModParticlesClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_CHARGE_PARTICLE, AmethystChargeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_CRIT_PARTICLE, AmethystCritParticle.Factory::new);
    }
}
