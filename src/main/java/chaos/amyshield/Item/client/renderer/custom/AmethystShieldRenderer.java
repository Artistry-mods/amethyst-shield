package chaos.amyshield.Item.client.renderer.custom;

import chaos.amyshield.Item.client.model.custom.AmethystShieldModel;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AmethystShieldRenderer extends GeoItemRenderer<AmethystShieldItem> {
    public AmethystShieldRenderer() {
        super(new AmethystShieldModel());
        //new AutoGlowingGeoLayer<>(this);
    }
}
