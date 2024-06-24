package chaos.amyshield.networking.C2Server;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import static chaos.amyshield.Item.custom.AmethystShieldItem.syncCharge;

public class AmethystShieldAbilityPacketC2S {
    public static void receiver(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        float amount = buf.readFloat();
        int fuckedUpTrieState = buf.readInt();

        boolean playSoundAndParticles;
        boolean playSoundAndFlatParticles = false;

        if (fuckedUpTrieState == 1) {
            playSoundAndParticles = true;
        } else if (fuckedUpTrieState == 2){
            playSoundAndParticles = false;
        } else {
            playSoundAndParticles = false;
            playSoundAndFlatParticles = true;
        }

        for (ItemStack itemStack : player.getHandItems()) {
            Item shield = itemStack.getItem();
            if (shield == ModItems.AMETHYST_SHIELD) {
                if (playSoundAndParticles) {
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.5f, 1.0F);
                    ((ServerWorld) player.getWorld()).spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE, player.getX(), player.getY() + 1, player.getZ(),1,0, 0, 0,0);
                }
                if (playSoundAndFlatParticles) {
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.5f, 1.0F);
                    ((ServerWorld) player.getWorld()).spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, player.getX(), player.getY() + 1, player.getZ(),1,0, 0, 0,0);
                }

                AmethystShieldItem.addCharge(((IEntityDataSaver) player), amount);
                syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                return;
            }
        }
    }

    public static void setChargeAndSpawnParticle(CustomPayload payload, ServerPlayNetworking.Context context) {
        System.out.println("ability packed");
    }
}
