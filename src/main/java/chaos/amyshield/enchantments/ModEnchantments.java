package chaos.amyshield.enchantments;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Stream;

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

    public static int getReleaseEnchantmentLevel(PlayerEntity player) {
        List<ItemStack> items = Stream.of(player.getOffHandStack(), player.getMainHandStack())
                .filter(stack -> stack.isOf(ModItems.AMETHYST_SHIELD))
                .toList();

        if (items.isEmpty() || player.getWorld().getRegistryManager().getOptionalEntry(ModEnchantments.RELEASE).isEmpty()) {
            return 1;
        }

        return EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().getOptionalEntry(ModEnchantments.RELEASE).get(), items.getFirst()) + 1;
    }

    public static void init() {

    }
}
