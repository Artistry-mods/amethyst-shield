package chaos.amyshield.enchantments;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.stream.Stream;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> SENSITIVITY = of("sensitivity");
    public static final ResourceKey<Enchantment> RELEASE = of("release");

    private static ResourceKey<Enchantment> of(String path) {
        Identifier id = Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, path);
        return ResourceKey.create(Registries.ENCHANTMENT, id);
    }

    public static int getReleaseEnchantmentLevel(Player player) {
        List<ItemStack> items = Stream.of(player.getOffhandItem(), player.getMainHandItem())
                .filter(stack -> stack.is(ModItems.AMETHYST_SHIELD))
                .toList();

        if (items.isEmpty() || player.level().registryAccess().get(ModEnchantments.RELEASE).isEmpty()) {
            return 1;
        }

        return EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().get(ModEnchantments.RELEASE).get(), items.getFirst()) + 1;
    }

    public static int getSensitivityEnchantmentLevel(Player player) {
        List<ItemStack> items = Stream.of(player.getOffhandItem(), player.getMainHandItem())
                .filter(stack -> stack.is(ModItems.AMETHYST_SHIELD))
                .toList();

        if (items.isEmpty() || player.level().registryAccess().get(ModEnchantments.SENSITIVITY).isEmpty()) {
            return 1;
        }

        return EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().get(ModEnchantments.SENSITIVITY).get(), items.getFirst()) + 1;
    }

    public static void init() {

    }
}
