package chaos.amyshield.Item.custom;

import chaos.amyshield.Item.ModItems;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;

import java.util.Collection;

public class AmethystShieldItem extends FabricShieldItem {
    public final int maxCharge = 100;
    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings, coolDownTicks, enchantability, repairItems);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, ToolMaterial material) {
        super(settings, coolDownTicks, material);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, coolDownTicks, enchantability, repairItemTag);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, Collection<TagKey<Item>> repairItemTags) {
        super(settings, coolDownTicks, enchantability, repairItemTags);
    }

    public static void setCharge(ItemStack itemStack, int amount) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putInt("charge", amount);
            if (nbt.getInt("charge") > 100) {
                System.out.println("charge was to high: " + nbt.getInt("charge"));
                nbt.putInt("charge", 100);
            }
        }
    }

    public static int getCharge(ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getNbt();
            if (nbt != null && nbt.contains("charge")) {
                return nbt.getInt("charge");
            }
        }
        return 0;
    }
}
