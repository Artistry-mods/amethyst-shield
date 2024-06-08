package chaos.amyshield.Item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AmethystShieldItem extends ShieldItem {
    private final Item repairItem;

    public AmethystShieldItem(Settings settings, Item repairItem) {
        super(settings);
        this.repairItem = repairItem;
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ModelPredicateProviderRegistry.register(new Identifier("blocking"), AmethystShieldItem::getBlocking);
        }
    }

    private static float getBlocking(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, int i) {
        if (livingEntity != null) {
            if (livingEntity.getActiveItem().getItem() == ModItems.AMETHYST_SHIELD) {
                return 1;
            }
        }
        return 0;
    }


    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == repairItem;
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
}
