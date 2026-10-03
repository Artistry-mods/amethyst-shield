package chaos.amyshield.config;

public class TempOverrideConfig {

    //Amethyst monocle
    
    public TempOverrideConfig.MonocleNested monocleNested = new TempOverrideConfig.MonocleNested();
    //Amethyst dispenser
    
    public TempOverrideConfig.DispenserNested dispenserNested = new TempOverrideConfig.DispenserNested();
    //Amethyst shield
    
    public TempOverrideConfig.AmethystShieldNested amethystShieldNested = new TempOverrideConfig.AmethystShieldNested();

    public static class AmethystShieldNested {
        public int AMETHYST_SHIELD_DURABILITY() {
            return 512;
        } //The durability of the amethyst shield.

        public int CHARGE_BAR_OFFSET() { return 0; } //The durability of the amethyst shield.
        
        public TempOverrideConfig.ChargeNested chargeNested = new TempOverrideConfig.ChargeNested();
        //Abilities
        //Slash
        
        public TempOverrideConfig.SlashNested slashNested = new TempOverrideConfig.SlashNested();
        //Double jump
        
        public TempOverrideConfig.DoubleJumpNested doubleJumpNested = new TempOverrideConfig.DoubleJumpNested();
        //Amethyst push
        
        public TempOverrideConfig.PushNested pushNested = new TempOverrideConfig.PushNested();
        //Amethyst Slide
        
        public TempOverrideConfig.SlideNested slideNested = new TempOverrideConfig.SlideNested();
        //Enchantments
        
        public TempOverrideConfig.EnchantmentNested enchantmentNested = new TempOverrideConfig.EnchantmentNested();
    }

    public static class MonocleNested {
        
        public int AMETHYST_MONOCLE_TIMER() { return 3 * 20; } //How many ticks should pass before the amethyst monocle pings again.(sec * ticksPerSec)
        
        public int AMETHYST_MONOCLE_RANGE() { return 4; } //How many blocks get checked around the player
    }

    public static class DispenserNested {
        
        public float AMETHYST_DISPENSER_STRENGTH() { return 2.0f; } //How strong the amethyst dispenser shoots
        
        public float AMETHYST_DISPENSER_SPREAD() { return 0f; } //How much spread the amethyst dispenser has
        
        public int AMETHYST_DISPENSER_COOLDOWN() { return 10; } //How much cooldown the amethyst dispenser has
    }

    public static class EnchantmentNested {
        
        public float CHARGE_GAIN_INCREASE_PER_LEVEL() { return 1.5f; }
        
        public float RELEASE_DOUBLE_JUMP_MULTIPLIER() { return .2f; }
        
        public float RELEASE_SPARKLING_SLASH_MULTIPLIER() { return .4f; }
        
        public float RELEASE_SLIDE_MULTIPLIER() { return .5f; }
        
        public float RELEASE_PUSH_MULTIPLIER() { return .6f; }
    }

    public static class ChargeNested {
        
        public float MAX_CHARGE() { return 100f; } //The maximum amethyst shield charge
        
        
        public float MIN_CHARGE() { return 0f; } //The minimum amethyst shield charge
        //Movement charge gain
        
        public int MOVEMENT_CHARGE_TIMING() { return 2; } //After how many tick the packet for movement charge gain gets send
        
        public float MOVEMENT_CHARGE_MULTIPLIER() { return 1; }
        
        
        public float MIN_MOVEMENT_DELTA() { return 0.002f; } //The minimum movement distance required for charge contribution
        //Block charge gain
        
        public float BLOCK_GAIN_MULTIPLIER() { return 0.4F; }
        
        public float MACE_HIT_MULTIPLIER() { return .5f; }
    }

    public static class SlashNested {
        
        public float SPARKLING_SLASH_COST() { return -25f; } //How much charge the sparkling slash costs
        
        public int SLASH_TIMING() { return 10; } //how many ticks the player has to hit and activate the ability after starting to fall again
        
        public float SPARKLING_SLASH_STRENGTH() { return 2f; } //How far the sparkling slash propels the use
        
        public float SPARKLING_SLASH_DAMAGE() { return 17f; } //How much a sparkling slash hit does(hit points)
        
        public float SPARKLING_SLASH_CHARGE_RETURN() { return 20f; } //How much charge the player get returned for landing a successful sparkling slash
        
        public float SPARKLING_SLASH_RADIUS() { return 0.5f; } //The radius around the player in which entities get damaged and flung
        
        public float HYPER_SLASH_TICK_TIMING() { return 10; } //The radius around the player in which entities get damaged and flung
    }

    public static class DoubleJumpNested {
        
        public float DOUBLE_JUMP_COST() { return -50f; } //How much charge the double jump costs
        
        public float DOUBLE_JUMP_STRENGTH() { return 0.7f; } //How high the double jump propels the user
    }

    public static class PushNested {
        
        public float AMETHYST_PUSH_COST() { return -75f; } //How much charge the amethyst push costs
        
        public int AMETHYST_PUSH_SNEAKING_TIMING() { return 10; } //How quickly sneak has to be pressed twice for the ability to activate(in ticks)
        
        public float AMETHYST_PUSH_RADIUS() { return 6f; } //The radius around the player in which entities get damaged and flung
        
        public float AMETHYST_PUSH_STRENGTH_X() { return 0.5f; } //how strong they get pushed away on x and z plane
        
        public float AMETHYST_PUSH_STRENGTH_Y() { return 0.6f; } //how strong they get knocked up
        
        public float AMETHYST_PUSH_DAMAGE() { return 16f; } //How much an amethyst push hit does(hit points)
        
        public boolean KILL_ONLY_HOSTILE() { return true; } //How much an amethyst push hit does(hit points)
    }

    public static class SlideNested {
        
        public int AMETHYST_SLIDE_TIMING() { return 5; } //How quickly the double block has to be executed for the ability to activate
        
        public float AMETHYST_SLIDE_COST() { return -25f; } //How much charge the amethyst slide costs
    }
}
