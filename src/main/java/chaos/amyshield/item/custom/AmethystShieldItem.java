package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.networking.playload.SyncChargePayload;
import chaos.amyshield.networking.playload.SyncSlashPayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

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

    public static float addCharge(IEntityDataSaver player, float amount) {
        IEntityDataSaver.AmethystShieldData nbt = player.amethyst_shield$getPersistentData();
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

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        return enchantment.matchesKey(Enchantments.MENDING) || enchantment.matchesKey(Enchantments.UNBREAKING);
    }
}
