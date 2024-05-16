package chaos.amyshield;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.BlockingListener;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("amyshield");

	public static String MOD_ID = "amyshield";

	//Abilities
	public final static float MAX_CHARGE = 100f; //The maximum amethyst shield charge
	public final static float MIN_CHARGE = 0f; //The minimum amethyst shield charge
	//SLASH
	public final static float SPARKLING_SLASH_COST = 25f; //How much charge the sparkling slash costs
	public final static int SLASH_TIMING = 10; //how many ticks the player has to hit and activate the ability after starting to fall again
	public final static float SPARKLING_SLASH_STRENGTH = 2f; //How far the sparkling slash propels the use
	public final static float SPARKLING_SLASH_DAMAGE = 6f; //How much a sparkling slash hit does (hit points)
	public final static float SPARKLING_SLASH_CHARGE_RETURN = 20f; //How much charge the player get returned for landing a successful sparkling slash
	public final static float SPARKLING_SLASH_RADIUS = 0.5f; //The radius around the player in which entities get damaged and flung
	//DOUBLE JUMP
	public final static float DOUBLE_JUMP_COST = 50f; //How much charge the double jump costs
	public final static float DOUBLE_JUMP_STRENGTH = 0.7f; //How high the double jump propels the user
	//AMETHYST PUSH
	public final static float AMETHYST_PUSH_COST = 50f; //How much charge the amethyst push costs
	public final static int AMETHYST_PUSH_SNEAKING_TIMING = 10; //How quickly sneak has to be pressed twice for the ability to activate (in ticks)
	public final static float AMETHYST_PUSH_RADIUS = 6f; //The radius around the player in which entities get damaged and flung
	public final static float AMETHYST_PUSH_STRENGTH_X = 0.5f; //how strong they get pushed away on x and z plane
	public final static float AMETHYST_PUSH_STRENGTH_Y = 0.6f; //how strong they get knocked up
	public final static float AMETHYST_PUSH_DAMAGE = 19f; //How much a amethyst push hit does (hit points)
	//MOVEMENT CHARGE GAIN
	public final static int MOVEMENT_CHARGE_TIMING = 10; //After how many tick the packet for movement charge gain gets send
	public final static float MIN_MOVEMENT_DELTA = 0.001f; //The minimum movement distance required for charge contribution

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModPackets.registerGlobalReceiversC2S();
		ModParticles.registerModParticles();
		BlockingListener.init();
		LOGGER.info("Hello, Blockixel :)");
	}
}