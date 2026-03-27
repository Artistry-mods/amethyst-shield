package chaos.amyshield.networking.C2Server;

import chaos.amyshield.abilities.*;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

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
        ServerPlayer player = context.player();
        for (ItemStack itemStack : List.of(player.getMainHandItem(), player.getOffhandItem())) {
            Item shield = itemStack.getItem();
            if (shield == ModItems.AMETHYST_SHIELD) {
                if (isChargeGainPacket(payload.abilityIdentifier())) {
                    AmethystShieldItem.addCharge((player), calculateChargeGain(payload.abilityIdentifier()));
                    syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                    return;
                }

                AmethystShieldAbility ability = abilities.get(payload.abilityIdentifier());

                if (ability.shouldPlaySound()) {
                    player.level().playSound(null, player.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 0.2F, 1.0F);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.PLAYERS, 0.2F, 1.0F);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.5f, 1.0F);
                }

                if (ability.shouldDisplayParticle()) {
                    if (ability.shouldParticleBeFlat()) {
                        player.level().sendParticles(ModParticles.AMETHYST_CHARGE_PARTICLE_FLAT, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
                    } else {
                        player.level().sendParticles(ModParticles.AMETHYST_CHARGE_PARTICLE, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
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
