package chaos.amyshield.datagen;

import chaos.amyshield.enchantments.ModEnchantments;
import net.minecraft.data.DataOutput;
import net.minecraft.data.tag.EnchantmentTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagModifier extends EnchantmentTagProvider {
    public ModEnchantmentTagModifier(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getTagBuilder(EnchantmentTags.NON_TREASURE).add(ModEnchantments.SENSITIVITY.getValue());
    }
}
