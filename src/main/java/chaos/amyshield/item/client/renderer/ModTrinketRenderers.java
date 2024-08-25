package chaos.amyshield.item.client.renderer;

import chaos.amyshield.item.ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled;
import chaos.amyshield.item.client.renderer.custom.MonocleItemTrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class ModTrinketRenderers {
    public static void init() {
        TrinketRendererRegistry.registerRenderer(ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled.AMETHYST_MONOCLE, new MonocleItemTrinketRenderer());
    }
}
