package chaos.amyshield;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.AmethystPushAbilityListener;
import chaos.amyshield.util.BlockingListener;
import chaos.amyshield.util.AmethystShieldAbilityListener;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("amyshield");

	public static String MOD_ID = "amyshield";

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		BlockingListener.init();
		AmethystShieldAbilityListener.init();
		AmethystPushAbilityListener.init();
		ModParticles.registerModParticles();
		LOGGER.info("Hello, Blockixel :)");
	}
}