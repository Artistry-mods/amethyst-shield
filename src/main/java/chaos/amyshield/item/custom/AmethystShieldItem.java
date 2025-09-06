package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.networking.playload.SyncSlashPayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.stream.Stream;

public class AmethystShieldItem extends ShieldItem {

    public AmethystShieldItem(Item.Settings settings) {
        super(settings);
    }

    public static float setCharge(IEntityDataSaver player, float amount) {
        IEntityDataSaver.AmethystShieldData nbt = player.amethyst_shield$getPersistentData();
        if (amount >= AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE()) {
            amount = AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE();
        }
        nbt.setCharge(amount);
        return amount;
    }

    public static void setSlashing(IEntityDataSaver player, boolean value) {
        IEntityDataSaver.AmethystShieldData nbt = player.amethyst_shield$getPersistentData();
        nbt.setSlashing(value);
    }

    public static boolean getSlashing(IEntityDataSaver player) {
        return player.amethyst_shield$getPersistentData().isSlashing();
    }

    public static void syncSlashing(boolean isSlashing) {
        ClientPlayNetworking.send(new SyncSlashPayload(isSlashing));
    }

    public static float addCharge(PlayerEntity player, float amount) {
        if (amount > 0) {
            int level = EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().getOptionalEntry(ModEnchantments.SENSITIVITY).get(),
                    Stream.of(player.getOffHandStack(), player.getMainHandStack())
                            .filter(stack -> stack.isOf(ModItems.AMETHYST_SHIELD))
                            .toList().getFirst());

            if (level > 0) {
                amount = amount * (level * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.CHARGE_GAIN_INCREASE_PER_LEVEL());
            }
        }

        IEntityDataSaver.AmethystShieldData nbt = ((IEntityDataSaver) player).amethyst_shield$getPersistentData();
        float charge = nbt.getCharge();
        if (charge + amount >= AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE()) {
            charge = AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE();
        } else if (charge + amount <= AmethystShield.CONFIG.amethystShieldNested.chargeNested.MIN_CHARGE()) {
            charge = AmethystShield.CONFIG.amethystShieldNested.chargeNested.MIN_CHARGE();
        } else {
            charge += amount;
        }
        nbt.setCharge(charge);
        return charge;
    }

    public static float getCharge(IEntityDataSaver player) {
        return player.amethyst_shield$getPersistentData().getCharge();
    }

    public static void syncCharge(float charge, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new SyncChargePayload(charge));
    }
}
