package chaos.amyshield.renderer.custom;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AmethystShieldRenderer extends GeoItemRenderer<AmethystShieldItem> {
    public AmethystShieldRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier("amyshield", "amethyst_shield")));
    }
}
