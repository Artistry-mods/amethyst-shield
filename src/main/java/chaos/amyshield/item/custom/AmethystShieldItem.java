package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.networking.playload.SyncSlashPayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;

public class AmethystShieldItem extends ShieldItem {

    public AmethystShieldItem(Item.Properties settings) {
        super(settings);
    }

    public static float setCharge(IEntityDataSaver player, float amount) {
        IEntityDataSaver.AmethystShieldData nbt = player.amethyst_shield$getPersistentData();
        if (amount >= AmethystShield.MAX_CHARGE) {
            amount = AmethystShield.MAX_CHARGE;
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

    public static float addCharge(Player player, float amount) {
        if (amount > 0) {

            int level = ModEnchantments.getSensitivityEnchantmentLevel(player);

            if (level > 0) {
                amount = amount * (level * AmethystShield.CHARGE_GAIN_INCREASE_PER_LEVEL);
            }
        }

        IEntityDataSaver.AmethystShieldData nbt = ((IEntityDataSaver) player).amethyst_shield$getPersistentData();
        float charge = nbt.getCharge();
        if (charge + amount >= AmethystShield.MAX_CHARGE) {
            charge = AmethystShield.MAX_CHARGE;
        } else if (charge + amount <= AmethystShield.MIN_CHARGE) {
            charge = AmethystShield.MIN_CHARGE;
        } else {
            charge += amount;
        }
        nbt.setCharge(charge);
        return charge;
    }

    public static float getCharge(IEntityDataSaver player) {
        return player.amethyst_shield$getPersistentData().getCharge();
    }

    public static void syncCharge(float charge, ServerPlayer player) {
        ServerPlayNetworking.send(player, new SyncChargePayload(charge));
    }
}
