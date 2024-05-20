package chaos.amyshield.Item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.client.renderer.custom.AmethystShieldRenderer;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.util.IEntityDataSaver;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AmethystShieldItem extends FabricShieldItem implements GeoItem, GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings, coolDownTicks, enchantability, repairItems);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, ToolMaterial material) {
        super(settings, coolDownTicks, material);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, coolDownTicks, enchantability, repairItemTag);
    }

    public AmethystShieldItem(Settings settings, int coolDownTicks, int enchantability, Collection<TagKey<Item>> repairItemTags) {
        super(settings, coolDownTicks, enchantability, repairItemTags);
    }

    public static float setCharge(IEntityDataSaver player, float amount) {
        NbtCompound nbt = player.getPersistentData();
        if(amount >= AmethystShield.MAX_CHARGE) {
            amount = AmethystShield.MAX_CHARGE;
        }
        nbt.putFloat("charge", amount);
        return amount;
    }

    public static boolean setSlashing(IEntityDataSaver player, boolean value) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("slashing", value);
        return value;
    }
    public static boolean getSlashing(IEntityDataSaver player) {
        return player.getPersistentData().getBoolean("slashing");
    }

    public static void syncSlashing(boolean value) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(value);
        ClientPlayNetworking.send(ModPackets.SYNC_SLASHING_S2C, buffer);
    }

    public static float addCharge(IEntityDataSaver player, float amount) {
        NbtCompound nbt = player.getPersistentData();
        float charge = nbt.getFloat("charge");
        if(charge + amount >= AmethystShield.MAX_CHARGE) {
            charge = AmethystShield.MAX_CHARGE;
        } else if (charge + amount <= AmethystShield.MIN_CHARGE) {
            charge = AmethystShield.MIN_CHARGE;
        } else {
            charge += amount;
        }
        nbt.putFloat("charge", charge);
        return charge;
    }

    public static float getCharge(IEntityDataSaver player) {
        return player.getPersistentData().getFloat("charge");
    }

    public static void syncCharge(float charge, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(charge);
        ServerPlayNetworking.send(player, ModPackets.SYNC_CHARGE_S2C, buffer);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final AmethystShieldRenderer renderer = new AmethystShieldRenderer();

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
