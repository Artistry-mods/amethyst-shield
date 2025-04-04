package chaos.amyshield.item.client.model;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.client.renderer.custom.AmethystShieldEntityRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;

public class ModEntityModels {

    public static void registerModEntityModels() {
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(AmethystShield.MOD_ID, "amethyst_shield"), AmethystShieldEntityRenderer.Unbaked.CODEC);
    }
}
