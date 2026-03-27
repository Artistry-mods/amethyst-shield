// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package chaos.amyshield.item.client.model.custom;

import chaos.amyshield.AmethystShield;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class AmethystShieldEntityModel extends Model<AmethystShield> {
    public static final ModelLayerLocation AMETHYST_SHIELD = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AmethystShield.MOD_ID, "amethyst_shield_model"), "main");

    public AmethystShieldEntityModel(ModelPart root) {
        super(root, identifier -> RenderTypes.entityCutout((Identifier) identifier));
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition amethist_shield = modelPartData.addOrReplaceChild("amethist_shield", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));

        amethist_shield.addOrReplaceChild("mainshield", CubeListBuilder.create()
                .texOffs(0, 23).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -11.0F, -4.0F, 12.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition skulk = amethist_shield.addOrReplaceChild("skulk", CubeListBuilder.create()
                .texOffs(10, 24).addBox(-10.0F, -12.0F, 5.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 11.0F, -7.0F));

        skulk.addOrReplaceChild("glow", CubeListBuilder.create()
                .texOffs(0, 35).addBox(-10.0F, -12.0F, 5.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition amethist = amethist_shield.addOrReplaceChild("amethist", CubeListBuilder.create(), PartPose.offset(16.0F, 11.0F, -15.0F));

        PartDefinition budinfront = amethist.addOrReplaceChild("budinfront", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.0F, -3.5F, 12.25F, 0.2618F, 0.0F, 0.0F));

        budinfront.addOrReplaceChild("right_font", CubeListBuilder.create()
                .texOffs(42, 14).addBox(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        budinfront.addOrReplaceChild("left_font", CubeListBuilder.create()
                .texOffs(26, 14).addBox(-8.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition budinback = amethist.addOrReplaceChild("budinback", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.0F, -8.0F, 11.75F, 0.1745F, 0.0F, 0.0F));

        budinback.addOrReplaceChild("left_back", CubeListBuilder.create()
                .texOffs(26, 0).addBox(-8.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        budinback.addOrReplaceChild("right_back", CubeListBuilder.create()
                .texOffs(42, 0).addBox(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}