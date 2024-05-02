package chaos.amyshield.Item.custom;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

import java.util.Collection;

public class AmethystShieldItem extends FabricShieldItem {
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


}
