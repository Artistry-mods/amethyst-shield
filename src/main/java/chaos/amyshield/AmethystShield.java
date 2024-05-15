package chaos.amyshield;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.networking.C2Server.AmethystPushAbilityListener;
import chaos.amyshield.util.BlockingListener;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityListener;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("amyshield");

	public static String MOD_ID = "amyshield";

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModPackets.registerGlobalReceiversC2S();
		ModParticles.registerModParticles();
		BlockingListener.init();
		LOGGER.info("Hello, Blockixel :)");
	}
}