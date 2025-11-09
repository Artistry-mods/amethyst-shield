// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package chaos.amyshield.item.client.model.custom;

import chaos.amyshield.AmethystShield;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AmethystShieldEntityModel extends Model {
    public static final EntityModelLayer AMETHYST_SHIELD = new EntityModelLayer(Identifier.of(AmethystShield.MOD_ID, "amethyst_shield_model"), "main");

    public AmethystShieldEntityModel(ModelPart root) {
        super(root, identifier -> RenderLayer.getEntityCutout((Identifier) identifier));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData amethist_shield = modelPartData.addChild("amethist_shield", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 13.0F, 0.0F));

        amethist_shield.addChild("mainshield", ModelPartBuilder.create()
                .uv(0, 23).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.0F, -11.0F, -4.0F, 12.0F, 22.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 3.0F));

        ModelPartData skulk = amethist_shield.addChild("skulk", ModelPartBuilder.create()
                .uv(10, 24).cuboid(-10.0F, -12.0F, 5.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 11.0F, -7.0F));

        skulk.addChild("glow", ModelPartBuilder.create()
                .uv(0, 35).cuboid(-10.0F, -12.0F, 5.0F, 4.0F, 4.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData amethist = amethist_shield.addChild("amethist", ModelPartBuilder.create(), ModelTransform.origin(16.0F, 11.0F, -15.0F));

        ModelPartData budinfront = amethist.addChild("budinfront", ModelPartBuilder.create(), ModelTransform.of(-16.0F, -3.5F, 12.25F, 0.2618F, 0.0F, 0.0F));

        budinfront.addChild("right_font", ModelPartBuilder.create()
                .uv(42, 14).cuboid(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        budinfront.addChild("left_font", ModelPartBuilder.create()
                .uv(26, 14).cuboid(-8.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        ModelPartData budinback = amethist.addChild("budinback", ModelPartBuilder.create(), ModelTransform.of(-16.0F, -8.0F, 11.75F, 0.1745F, 0.0F, 0.0F));

        budinback.addChild("left_back", ModelPartBuilder.create()
                .uv(26, 0).cuboid(-8.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        budinback.addChild("right_back", ModelPartBuilder.create()
                .uv(42, 0).cuboid(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}