package chaos.amyshield.Item.custom;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.client.renderer.custom.AmethystShieldRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AmethystShieldItem extends FabricShieldItem implements GeoItem, GeoAnimatable {
    public final static float maxCharge = 100;
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

    public static void setCharge(ItemStack itemStack, float amount) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putFloat("charge", amount);
            if (nbt.getFloat("charge") > maxCharge) {
                nbt.putFloat("charge", maxCharge);
            }
        }
    }

    public static float getCharge(ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getNbt();
            if (nbt != null && nbt.contains("charge")) {
                return nbt.getFloat("charge");
            }
        }
        return 0;
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
