package chaos.amyshield.networking.C2Server;

import chaos.amyshield.abilities.*;
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

import java.util.HashMap;
import java.util.List;

import static chaos.amyshield.item.custom.AmethystShieldItem.syncCharge;

public class AmethystShieldAbilityPacketC2S {
    public static HashMap<String, AmethystShieldAbility> abilities = new HashMap<>() {{
        put(SlashAmethystShieldAbility.getId(), new SlashAmethystShieldAbility());
        put(PushAmethystShieldAbility.getId(), new PushAmethystShieldAbility());
        put(SlideAmethystShieldAbility.getId(), new SlideAmethystShieldAbility());
        put(DoubleJumpAmethystShieldAbility.getId(), new DoubleJumpAmethystShieldAbility());
        put(HyperSlashAmethystShieldAbility.getId(), new HyperSlashAmethystShieldAbility());
    }};


    public static void setChargeAndSpawnParticle(AmethystAbilityPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        for (ItemStack itemStack : List.of(player.getMainHandStack(), player.getOffHandStack())) {
            Item shield = itemStack.getItem();
            if (shield == ModItems.AMETHYST_SHIELD) {
                if (isChargeGainPacket(payload.abilityIdentifier())) {
                    AmethystShieldItem.addCharge((player), calculateChargeGain(payload.abilityIdentifier()));
                    syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                    return;
                }

                AmethystShieldAbility ability = abilities.get(payload.abilityIdentifier());

                if (ability.shouldPlaySound()) {
                    player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 0.2F, 1.0F);
                    player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.5f, 1.0F);
                }

                if (ability.shouldDisplayParticle()) {
                    if (ability.shouldParticleBeFlat()) {
                        player.getEntityWorld().spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
                    } else {
                        player.getEntityWorld().spawnParticles(ModParticles.AMETHYST_CHARGE_PARTICLE, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
                    }
                }

                ability.onTrigger(context);
                
                AmethystShieldItem.addCharge((player), ability.getChargeCost());
                syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                return;
            }
        }
    }

    private static float calculateChargeGain(String idAndCharge) {
        return Float.parseFloat(idAndCharge.replace(ChargeGain.getId(), ""));
    }

    private static boolean isChargeGainPacket(String id) {
        return id.contains(ChargeGain.getId());
    }
}
