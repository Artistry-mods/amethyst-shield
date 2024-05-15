package chaos.amyshield.Item.client.model.custom;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AmethystShieldModel extends GeoModel<AmethystShieldItem> {
    @Override
    public Identifier getModelResource(AmethystShieldItem animatable) {
        return new Identifier("amyshield", "geo/item/amethyst_shield.geo.json");
    }

    @Override
    public Identifier getTextureResource(AmethystShieldItem animatable) {
        return new Identifier("amyshield", "textures/item/amethyst_shield.png");
    }

    @Override
    public Identifier getAnimationResource(AmethystShieldItem animatable) {
        return new Identifier("amyshield", "animations/item/amethyst_shield.animation.json");
    }
}
