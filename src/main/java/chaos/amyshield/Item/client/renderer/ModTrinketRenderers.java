package chaos.amyshield.Item.client.renderer;

import chaos.amyshield.Item.ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled;
import chaos.amyshield.Item.client.renderer.custom.MonocleItemTrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class ModTrinketRenderers {
    public static void init() {
        TrinketRendererRegistry.registerRenderer(ModItemsButItsOnlyTheMonocleWhenTrinketIsEnabled.AMETHYST_MONOCLE, new MonocleItemTrinketRenderer());
    }
}
