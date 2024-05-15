package chaos.amyshield;

import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.particles.client.ModClientParticles;
import net.fabricmc.api.ClientModInitializer;

public class AmethystShieldClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModClientParticles.registerModParticlesClient();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}