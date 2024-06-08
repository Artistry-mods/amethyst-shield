package chaos.amyshield.Item.client.model;

import chaos.amyshield.AmethystShield;
import com.google.common.collect.Sets;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ModEntityModelLayers {
    public static final EntityModelLayer AMETHYST_SHIELD = new EntityModelLayer(new Identifier(AmethystShield.MOD_ID, "amethyst_shield"), "main");
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
}
