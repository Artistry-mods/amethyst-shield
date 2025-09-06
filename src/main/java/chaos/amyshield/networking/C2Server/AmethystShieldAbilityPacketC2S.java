package chaos.amyshield.networking.C2Server;

import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.List;

import static chaos.amyshield.item.custom.AmethystShieldItem.syncCharge;

public class AmethystShieldAbilityPacketC2S {
    public static void setChargeAndSpawnParticle(AmethystAbilityPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        for (ItemStack itemStack : List.of(player.getMainHandStack(), player.getOffHandStack())) {
            Item shield = itemStack.getItem();
            if (shield == ModItems.AMETHYST_SHIELD) {
                if (payload.sound()) {
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.5f, 1.0F);
                }
                if (payload.flatParticles() && !payload.notFlatParticles()) {
                    player.getWorld().spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
                }
                if (payload.flatParticles() && !payload.notFlatParticles()) {
                    player.getWorld().spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
                }

                AmethystShieldItem.addCharge((player), payload.chargeAmount());
                syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                return;
            }
        }
    }
}
