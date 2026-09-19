package chaos.amyshield;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.ModBlockEntities;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocle;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.sounds.ModSounds;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.util.IEntityDataSaver;
import chaos.amyshield.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Amethyst Shield");

    public static final String MOD_ID = "amyshield";

    //public static final AmethystShieldConfig CONFIG = AmethystShieldConfig.createAndLoad();

    //Amethyst monocle
    public static final int AMETHYST_MONOCLE_TIMER = 3 * 20; //How many ticks should pass before the amethyst monocle pings again. (sec * ticksPerSec)
    public static final int AMETHYST_MONOCLE_RANGE = 4; //How many blocks get checked around the player
    //Amethyst dispenser
    public static final float AMETHYST_DISPENSER_STRENGTH = 2.0f; //How strong the amethyst dispenser shoots
    public static final float AMETHYST_DISPENSER_SPREAD = 0f; //How much spread the amethyst dispenser has
    //Abilities
    public final static float MAX_CHARGE = 100f; //The maximum amethyst shield charge
    public final static float MIN_CHARGE = 0f; //The minimum amethyst shield charge
    //Slash
    public final static float SPARKLING_SLASH_COST = -25f; //How much charge the sparkling slash costs
    public final static int SLASH_TIMING = 10; //how many ticks the player has to hit and activate the ability after starting to fall again
    public final static float SPARKLING_SLASH_STRENGTH = 2f; //How far the sparkling slash propels the use
    public final static float SPARKLING_SLASH_DAMAGE = 17f; //How much a sparkling slash hit does (hit points)
    public final static float SPARKLING_SLASH_CHARGE_RETURN = 20f; //How much charge the player get returned for landing a successful sparkling slash
    public final static float SPARKLING_SLASH_RADIUS = 0.5f; //The radius around the player in which entities get damaged and flung
    //Double jump
    public final static float DOUBLE_JUMP_COST = -50f; //How much charge the double jump costs
    public final static float DOUBLE_JUMP_STRENGTH = 0.7f; //How high the double jump propels the user
    //Amethyst push
    public final static float AMETHYST_PUSH_COST = -50f; //How much charge the amethyst push costs
    public final static int AMETHYST_PUSH_SNEAKING_TIMING = 10; //How quickly sneak has to be pressed twice for the ability to activate (in ticks)
    public final static float AMETHYST_PUSH_RADIUS = 6f; //The radius around the player in which entities get damaged and flung
    public final static float AMETHYST_PUSH_STRENGTH_X = 0.5f; //how strong they get pushed away on x and z plane
    public final static float AMETHYST_PUSH_STRENGTH_Y = 0.6f; //how strong they get knocked up
    public final static float AMETHYST_PUSH_DAMAGE = 16f; //How much an amethyst push hit does (hit points)
    //Amethyst Slide
    public static final int AMETHYST_SLIDE_TIMING = 5; //How quickly the double block has to be executed for the ability to activate
    public static final float AMETHYST_SLIDE_COST = -25f; //How much charge the amethyst slide costs
    //Movement charge gain
    public final static int MOVEMENT_CHARGE_TIMING = 20; //After how many tick the packet for movement charge gain gets send
    public final static float MIN_MOVEMENT_DELTA = 0.001f; //The minimum movement distance required for charge contribution
    //Block charge gain
    public static final float BLOCK_GAIN_MULTIPLIER = 0.4F;
    public static final float CHARGE_GAIN_INCREASE_PER_LEVEL = 1.5f;

    public static final float HYPER_SLASH_TICK_TIMING = 10;
    public static final float MOVEMENT_CHARGE_MULTIPLIER = 1;
    public static final float RELEASE_DOUBLE_JUMP_MULTIPLIER = .2f;
    public static final float RELEASE_SPARKLING_SLASH_MULTIPLIER = .4f;
    public static final float RELEASE_SLIDE_MULTIPLIER = .5f;
    public static final float RELEASE_PUSH_MULTIPLIER = .6f;
    public static final boolean KILL_ONLY_HOSTILE = true;
    public static final float MACE_HIT_MULTIPLIER = .5f;
    public static final int AMETHYST_SHIELD_DURABILITY = 512; //The durability of the amethyst shield.

    @Override
    public void onInitialize() {
        ModTags.registerModKeys();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.init();
        ModPackets.registerGlobalReceiversC2S();
        ModParticles.registerModParticles();
        ModEnchantments.init();
        ModBlockEntities.registerModBlockEntities();
        LootTableModifier.init();
        ModWorldGeneration.generateModWorldGen();

        if (!FabricLoader.getInstance().isModLoaded("sculk-latch")) {
            ModItemsButItsOnlyTheSculkLatch.registerModItemsButItsOnlyTheSculkLatch();
        }

        ModItemsButItsOnlyTheMonocle.init();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            float charge = AmethystShieldItem.getCharge((IEntityDataSaver) handler.player);
            AmethystShieldItem.syncCharge(charge, handler.player);
        });

        LOGGER.info("Hello, Blockixel :)");
    }
}