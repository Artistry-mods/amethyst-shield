package chaos.amyshield.enchantments;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

    public static int getReleaseEnchantmentLevel(PlayerEntity player) {
        List<ItemStack> items = Stream.of(player.getOffHandStack(), player.getMainHandStack())
                .filter(stack -> stack.isOf(ModItems.AMETHYST_SHIELD))
                .toList();

        if (items.isEmpty() || player.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantments.RELEASE).isEmpty()) {
            return 1;
        }

        return EnchantmentHelper.getLevel(player.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantments.RELEASE).get(), items.getFirst()) + 1;
    }

    public static int getSensitivityEnchantmentLevel(PlayerEntity player) {
        List<ItemStack> items = Stream.of(player.getOffHandStack(), player.getMainHandStack())
                .filter(stack -> stack.isOf(ModItems.AMETHYST_SHIELD))
                .toList();

        if (items.isEmpty() || player.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantments.SENSITIVITY).isEmpty()) {
            return 1;
        }

        return EnchantmentHelper.getLevel(player.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantments.SENSITIVITY).get(), items.getFirst()) + 1;
    }

    public static void init() {

    }
}
