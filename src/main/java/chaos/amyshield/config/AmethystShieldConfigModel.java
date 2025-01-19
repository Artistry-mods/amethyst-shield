package chaos.amyshield.config;

import chaos.amyshield.AmethystShield;
import io.wispforest.owo.config.annotation.*;

import static io.wispforest.owo.config.Option.SyncMode.OVERRIDE_CLIENT;

@Modmenu(modId = AmethystShield.MOD_ID)
@Config(name = "amethyst-shield-config", wrapperName = "AmethystShieldConfig")
public class AmethystShieldConfigModel {
    //Amethyst monocle
    @Nest
    public MonocleNested monocleNested = new MonocleNested();
    //Amethyst dispenser
    @Nest
    public DispenserNested dispenserNested = new DispenserNested();
    //Amethyst shield
    @Nest
    public AmethystShieldNested amethystShieldNested = new AmethystShieldNested();

    public static class AmethystShieldNested {
        @RestartRequired
        public int AMETHYST_SHIELD_DURABILITY = 512; //The durability of the amethyst shield.
        public int CHARGE_BAR_OFFSET = 0; //The durability of the amethyst shield.
        @Nest
        public ChargeNested chargeNested = new ChargeNested();
        //Abilities
        //Slash
        @Nest
        public SlashNested slashNested = new SlashNested();
        //Double jump
        @Nest
        public DoubleJumpNested doubleJumpNested = new DoubleJumpNested();
        //Amethyst push
        @Nest
        public PushNested pushNested = new PushNested();
        //Amethyst Slide
        @Nest
        public SlideNested slideNested = new SlideNested();
    }

    public static class MonocleNested {
        public int AMETHYST_MONOCLE_TIMER = 3 * 20; //How many ticks should pass before the amethyst monocle pings again. (sec * ticksPerSec)
        public int AMETHYST_MONOCLE_RANGE = 4; //How many blocks get checked around the player
    }

    public static class DispenserNested {
        public float AMETHYST_DISPENSER_STRENGTH = 2.0f; //How strong the amethyst dispenser shoots
        public float AMETHYST_DISPENSER_SPREAD = 0f; //How much spread the amethyst dispenser has
    }

    public static class ChargeNested {
        @Sync(OVERRIDE_CLIENT)
        public float MAX_CHARGE = 100f; //The maximum amethyst shield charge
        @Sync(OVERRIDE_CLIENT)
        @ExcludeFromScreen
        public float MIN_CHARGE = 0f; //The minimum amethyst shield charge
        //Movement charge gain
        public int MOVEMENT_CHARGE_TIMING = 20; //After how many tick the packet for movement charge gain gets send
        public float MOVEMENT_CHARGE_MULTIPLIER = 1;
        @ExcludeFromScreen
        public float MIN_MOVEMENT_DELTA = 0.002f; //The minimum movement distance required for charge contribution
        //Block charge gain
        public float BLOCK_GAIN_MULTIPLIER = 0.4F;
    }

    public static class SlashNested {
        public float SPARKLING_SLASH_COST = -25f; //How much charge the sparkling slash costs
        public int SLASH_TIMING = 10; //how many ticks the player has to hit and activate the ability after starting to fall again
        public float SPARKLING_SLASH_STRENGTH = 2f; //How far the sparkling slash propels the use
        public float SPARKLING_SLASH_DAMAGE = 17f; //How much a sparkling slash hit does (hit points)
        public float SPARKLING_SLASH_CHARGE_RETURN = 20f; //How much charge the player get returned for landing a successful sparkling slash
        public float SPARKLING_SLASH_RADIUS = 0.5f; //The radius around the player in which entities get damaged and flung
    }

    public static class DoubleJumpNested {
        public float DOUBLE_JUMP_COST = -50f; //How much charge the double jump costs
        public float DOUBLE_JUMP_STRENGTH = 0.7f; //How high the double jump propels the user
    }

    public static class PushNested {
        public float AMETHYST_PUSH_COST = -50f; //How much charge the amethyst push costs
        public int AMETHYST_PUSH_SNEAKING_TIMING = 10; //How quickly sneak has to be pressed twice for the ability to activate (in ticks)
        public float AMETHYST_PUSH_RADIUS = 6f; //The radius around the player in which entities get damaged and flung
        public float AMETHYST_PUSH_STRENGTH_X = 0.5f; //how strong they get pushed away on x and z plane
        public float AMETHYST_PUSH_STRENGTH_Y = 0.6f; //how strong they get knocked up
        public float AMETHYST_PUSH_DAMAGE = 16f; //How much an amethyst push hit does (hit points)
    }

    public static class SlideNested {
        public int AMETHYST_SLIDE_TIMING = 5; //How quickly the double block has to be executed for the ability to activate
        public float AMETHYST_SLIDE_COST = -25f; //How much charge the amethyst slide costs
    }
}