package chaos.amyshield.Item.custom;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.renderer.custom.*;
import net.minecraft.client.render.item.*;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AmethystShieldItem extends FabricShieldItem implements GeoItem {
    public final int maxCharge = 100;
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

    public static void setCharge(ItemStack itemStack, int amount) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putInt("charge", amount);
            if (nbt.getInt("charge") > 100) {
                nbt.putInt("charge", 100);
            }
        }
    }

    public static int getCharge(ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getNbt();
            if (nbt != null && nbt.contains("charge")) {
                return nbt.getInt("charge");
            }
        }
        return 0;
    }

    public static void setDoubleJumping(ItemStack itemStack, boolean state) {
        if (itemStack.getItem() == ModItems.AMETHYST_SHIELD) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            if (!state) {
                nbt.remove("double_jumping");
            }
            nbt.putBoolean("double_jumping", state);
        }
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
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
