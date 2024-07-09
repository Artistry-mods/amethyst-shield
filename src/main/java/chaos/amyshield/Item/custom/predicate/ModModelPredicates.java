package chaos.amyshield.Item.custom.predicate;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.AMETHYST_SHIELD, Identifier.of(AmethystShield.MOD_ID, "amethyst_blocking"), AmethystShieldItem::getBlocking);
    }
}
