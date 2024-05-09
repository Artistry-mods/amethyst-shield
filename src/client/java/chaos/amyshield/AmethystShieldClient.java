package chaos.amyshield;

import chaos.amyshield.renderer.ModRenderers;
import net.fabricmc.api.ClientModInitializer;

public class AmethystShieldClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModRenderers.registerRenderers();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}