package chaos.amyshield;

import chaos.amyshield.datagen.*;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.world.ModConfiguredFeatures;
import chaos.amyshield.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class AmethystShieldDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DynamicRegistryProvider::new);

        pack.addProvider(ModEnchantmentTagModifier::new);

        pack.addProvider(ModLootTableProvider.Block::new);

        pack.addProvider(ModRecipeProvider::new);

        pack.addProvider(ModTagProvider.ModBlockTagProvider::new);
        pack.addProvider(ModTagProvider.ModItemTagProvider::new);
        pack.addProvider(ModTagProvider.ModEntityProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::boostrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::boostrap);

        registryBuilder.add(Registries.ENCHANTMENT, (context) -> {

            final var items = context.lookup(Registries.ITEM);
            context.register(ModEnchantments.SENSITIVITY, Enchantment.enchantment(
                            Enchantment.definition(
                                    items.getOrThrow(ModTags.AMETHYST_SHIELD_ENCHANTABLE),
                                    // this is the "weight" or probability of our enchantment showing up in the table
                                    10,
                                    // the maximum level of the enchantment
                                    3,
                                    // base cost for level 1 of the enchantment, and min levels required for something higher
                                    Enchantment.dynamicCost(1, 10),
                                    // same fields as above but for max cost
                                    Enchantment.dynamicCost(1, 15),
                                    // anvil cost
                                    5,
                                    // valid slots
                                    EquipmentSlotGroup.HAND
                            )
                    ).build(ModEnchantments.SENSITIVITY.identifier())
            );


            context.register(ModEnchantments.RELEASE, Enchantment.enchantment(
                Enchantment.definition(
                    items.getOrThrow(ModTags.AMETHYST_SHIELD_ENCHANTABLE),
                    // this is the "weight" or probability of our enchantment showing up in the table
                    10,
                    // the maximum level of the enchantment
                    3,
                    // base cost for level 1 of the enchantment, and min levels required for something higher
                    Enchantment.dynamicCost(1, 10),
                    // same fields as above but for max cost
                    Enchantment.dynamicCost(1, 15),
                    // anvil cost
                    5,
                    // valid slots
                    EquipmentSlotGroup.HAND
                ))
                .build(ModEnchantments.RELEASE.identifier())
            );
        });
    }
}
