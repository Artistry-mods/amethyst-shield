package chaos.amyshield;

import chaos.amyshield.block.ModBlocks;
import chaos.amyshield.block.blockEntities.ModBlockEntities;
import chaos.amyshield.config.TempOverrideConfig;
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
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Amethyst Shield");

    public static final String MOD_ID = "amyshield";

    // If owo lib ever updates
    // public static final AmethystShieldConfig CONFIG = AmethystShieldConfig.createAndLoad();
    public static final TempOverrideConfig CONFIG = new TempOverrideConfig();

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

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

        ServerPlayConnectionEvents.JOIN.register((handler, _, _) -> {
            float charge = AmethystShieldItem.getCharge((IEntityDataSaver) handler.player);
            AmethystShieldItem.syncCharge(charge, handler.player);
        });

        LOGGER.info("Hello, Blockixel :)");
    }
}