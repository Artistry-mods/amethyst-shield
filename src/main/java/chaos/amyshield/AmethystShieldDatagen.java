package chaos.amyshield;

import chaos.amyshield.datagen.DynamicRegistryProvider;
import chaos.amyshield.datagen.ModEnchantmentTagModifier;
import chaos.amyshield.datagen.ModTagProvider;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.world.ModConfiguredFeatures;
import chaos.amyshield.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.*;

public class AmethystShieldDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DynamicRegistryProvider::new);

        pack.addProvider(ModEnchantmentTagModifier::new);

        pack.addProvider(ModTagProvider.ModBlockTagProvider::new);
        pack.addProvider(ModTagProvider.ModItemTagProvider::new);
        pack.addProvider(ModTagProvider.ModEntityProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::boostrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::boostrap);

        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, (context) -> {

            final var items = context.getRegistryLookup(RegistryKeys.ITEM);
            context.register(ModEnchantments.SENSITIVITY, Enchantment.builder(
                            Enchantment.definition(
                                    items.getOrThrow(ModTags.AMETHYST_SHIELD_ENCHANTABLE),
                                    // this is the "weight" or probability of our enchantment showing up in the table
                                    10,
                                    // the maximum level of the enchantment
                                    3,
                                    // base cost for level 1 of the enchantment, and min levels required for something higher
                                    Enchantment.leveledCost(1, 10),
                                    // same fields as above but for max cost
                                    Enchantment.leveledCost(1, 15),
                                    // anvil cost
                                    5,
                                    // valid slots
                                    AttributeModifierSlot.HAND
                            )
                    ).build(ModEnchantments.SENSITIVITY.getValue())
            );


            context.register(ModEnchantments.RELEASE, Enchantment.builder(
                Enchantment.definition(
                    items.getOrThrow(ModTags.AMETHYST_SHIELD_ENCHANTABLE),
                    // this is the "weight" or probability of our enchantment showing up in the table
                    10,
                    // the maximum level of the enchantment
                    3,
                    // base cost for level 1 of the enchantment, and min levels required for something higher
                    Enchantment.leveledCost(1, 10),
                    // same fields as above but for max cost
                    Enchantment.leveledCost(1, 15),
                    // anvil cost
                    5,
                    // valid slots
                    AttributeModifierSlot.HAND
                ))
                .build(ModEnchantments.RELEASE.getValue())
            );
        });
    }
}
