package chaos.amyshield.item.custom.predicate;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.custom.AmethystShieldItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.AMETHYST_SHIELD, Identifier.of(AmethystShield.MOD_ID, "amethyst_blocking"), AmethystShieldItem::getBlocking);
    }
}
