package chaos.amyshield.Item.client.renderer.custom;

import chaos.amyshield.Item.client.model.custom.AmethystShieldModel;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AmethystShieldRenderer extends GeoItemRenderer<AmethystShieldItem> {
    public AmethystShieldRenderer() {
        super(new AmethystShieldModel());
        //new AutoGlowingGeoLayer<>(this);
    }
}
