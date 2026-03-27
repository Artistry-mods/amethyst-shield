package chaos.amyshield.datagen;

import chaos.amyshield.enchantments.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagModifier extends EnchantmentTagsProvider {
    public ModEnchantmentTagModifier(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(EnchantmentTags.NON_TREASURE)
                .addElement(ModEnchantments.SENSITIVITY.identifier());
    }
}
