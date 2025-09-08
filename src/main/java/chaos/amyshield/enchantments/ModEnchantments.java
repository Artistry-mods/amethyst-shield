package chaos.amyshield.enchantments;

import chaos.amyshield.AmethystShield;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> SENSITIVITY = of("sensitivity");
    public static final RegistryKey<Enchantment> RELEASE = of("release");

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(AmethystShield.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(AmethystShield.MOD_ID, id), codec);
    }

    public static void init() {

    }
}
