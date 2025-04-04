package chaos.amyshield;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.ModItemsButItsOnlyTheMonocle;
import chaos.amyshield.item.ModItemsButItsOnlyTheSculkLatch;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.ModBlockEntities;
import chaos.amyshield.config.AmethystShieldConfig;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.util.IEntityDataSaver;
import chaos.amyshield.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Amethyst Shield");

    public static final String MOD_ID = "amyshield";

    public static final AmethystShieldConfig CONFIG = AmethystShieldConfig.createAndLoad();

    /*
    //Amethyst monocle
    public static final int AMETHYST_MONOCLE_TIMER = CONFIG.monocleNested.AMETHYST_MONOCLE_TIMER(); //How many ticks should pass before the amethyst monocle pings again. (sec * ticksPerSec)
    public static final int AMETHYST_MONOCLE_RANGE = CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(); //How many blocks get checked around the player
    //Amethyst dispenser
    public static final float AMETHYST_DISPENSER_STRENGTH = CONFIG.dispenserNested.AMETHYST_DISPENSER_STRENGTH(); //How strong the amethyst dispenser shoots
    public static final float AMETHYST_DISPENSER_SPREAD = CONFIG.dispenserNested.AMETHYST_DISPENSER_SPREAD(); //How much spread the amethyst dispenser has
    //Amethyst shield
    public static final int AMETHYST_SHIELD_DURABILITY = CONFIG.amethystShieldNested.AMETHYST_SHIELD_DURABILITY();
    //Abilities
    public static float MAX_CHARGE = CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE(); //The maximum amethyst shield charge
    public static final float MIN_CHARGE = CONFIG.amethystShieldNested.chargeNested.MIN_CHARGE(); //The minimum amethyst shield charge
    //Slash
    public static final float SPARKLING_SLASH_COST = CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_COST(); //How much charge the sparkling slash costs
    public static final int SLASH_TIMING = CONFIG.amethystShieldNested.slashNested.SLASH_TIMING(); //how many ticks the player has to hit and activate the ability after starting to fall again
    public static final float SPARKLING_SLASH_STRENGTH = CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_STRENGTH(); //How far the sparkling slash propels the use
    public static final float SPARKLING_SLASH_DAMAGE = CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_DAMAGE(); //How much a sparkling slash hit does (hit points)
    public static final float SPARKLING_SLASH_CHARGE_RETURN = CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_CHARGE_RETURN(); //How much charge the player get returned for landing a successful sparkling slash
    public static final float SPARKLING_SLASH_RADIUS = CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_RADIUS(); //The radius around the player in which entities get damaged and flung
    //Double jump
    public static float DOUBLE_JUMP_COST = CONFIG.amethystShieldNested.doubleJumpNested.DOUBLE_JUMP_COST(); //How much charge the double jump costs
    public static float DOUBLE_JUMP_STRENGTH = CONFIG.amethystShieldNested.doubleJumpNested.DOUBLE_JUMP_STRENGTH(); //How high the double jump propels the user
    //Amethyst push
    public static final float AMETHYST_PUSH_COST = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_COST(); //How much charge the amethyst push costs
    public static final int AMETHYST_PUSH_SNEAKING_TIMING = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_SNEAKING_TIMING(); //How quickly sneak has to be pressed twice for the ability to activate (in ticks)
    public static final float AMETHYST_PUSH_RADIUS = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_RADIUS(); //The radius around the player in which entities get damaged and flung
    public static final float AMETHYST_PUSH_STRENGTH_X = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_X(); //how strong they get pushed away on x and z plane
    public static final float AMETHYST_PUSH_STRENGTH_Y = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_Y(); //how strong they get knocked up
    public static final float AMETHYST_PUSH_DAMAGE = CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_DAMAGE(); //How much an amethyst push hit does (hit points)
    //Amethyst Slide
    public static final int AMETHYST_SLIDE_TIMING = CONFIG.amethystShieldNested.slideNested.AMETHYST_SLIDE_TIMING(); //How quickly the double block has to be executed for the ability to activate
    public static final float AMETHYST_SLIDE_COST = CONFIG.amethystShieldNested.slideNested.AMETHYST_SLIDE_COST(); //How much charge the amethyst slide costs
    //Movement charge gain
    public static final int MOVEMENT_CHARGE_TIMING = CONFIG.amethystShieldNested.chargeNested.MOVEMENT_CHARGE_TIMING(); //After how many tick the packet for movement charge gain gets send
    public static final float MIN_MOVEMENT_DELTA = CONFIG.amethystShieldNested.chargeNested.MIN_MOVEMENT_DELTA(); //The minimum movement distance required for charge contribution
    //Block charge gain
    public static final float BLOCK_GAIN_MULTIPLIER = CONFIG.amethystShieldNested.chargeNested.BLOCK_GAIN_MULTIPLIER();
     */

    @Override
    public void onInitialize() {
        ModTags.registerModKeys();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModPackets.registerGlobalReceiversC2S();
        ModParticles.registerModParticles();
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