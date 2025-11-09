package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public class SlashingHitMixin {
    /*
    @Unique
    public int doomTimer = -1;
    @Unique
    public Entity otherDoomedPlayer;

     */

    @Inject(method = "tick", at = @At("HEAD"))
    protected void tickInject(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        /*
        if (doomTimer > -1) {
            doomTimer--;

            player.setPos(player.lastX, player.lastY, player.lastZ);
            player.fallDistance = 0;
            player.setVelocity(Vec3d.ZERO);
            player.velocityDirty = true;
        }

        if (doomTimer == 0 && otherDoomedPlayer != null) {
            player.setVelocity(player.getPos().subtract(otherDoomedPlayer.getPos()).normalize().multiply(10));
            player.velocityDirty = true;
        }

         */

        if (player != null && player.isAlive() && !player.isRemoved() && !player.isDisconnected() && AmethystShieldItem.getSlashing(((IEntityDataSaver) player))) {
            if (player.getRandom().nextInt(5) == 1) {
                player.getEntityWorld().spawnParticles(ModParticles.AMETHYST_CRIT_PARTICLE,
                        player.getX() + player.getRandom().nextFloat() - 0.5,
                        player.getY() + player.getRandom().nextFloat() - 0.5,
                        player.getZ() + player.getRandom().nextFloat() - 0.5,
                        1,
                        player.getRandom().nextFloat(),
                        player.getRandom().nextFloat(),
                        player.getRandom().nextFloat(),
                        0);
                player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1f, 1);
            }

            List<Entity> entityList = new ArrayList<>(player.getEntityWorld().getOtherEntities(player, player.getBoundingBox().expand(AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_RADIUS())));
            Objects.requireNonNull(player.getEntityWorld().getServer()).execute(() -> {
                for (Entity entity : entityList) {
                    if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved() && !entity.getType().isIn(ModTags.SLASH_IMMUNE)) {
                        /*
                        if (entity instanceof ServerPlayerEntity otherPlayer) {
                            if (otherPlayer != null && otherPlayer.isAlive() && !otherPlayer.isRemoved() && AmethystShieldItem.getSlashing(((IEntityDataSaver) otherPlayer))) {
                                doomTimer = 9;
                                player.getWorld().playSound(player, player.getPos().x, player.getPos().y, player.getPos().z, ModSounds.HYPER_EXPLOSION, SoundCategory.PLAYERS, 1.1f, 1f);
                                //AmethystShieldItem.setSlashing(((IEntityDataSaver) otherPlayer), false);
                                AmethystShieldItem.setSlashing(((IEntityDataSaver) player), false);
                                this.otherDoomedPlayer = otherPlayer;
                            }
                        }
                        */

                        if (entity.damage(player.getEntityWorld(), player.getDamageSources().indirectMagic(player, player), (float) getSlashMultiplier(player))) {
                            AmethystShieldItem.addCharge((player), AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_CHARGE_RETURN());
                            AmethystShieldItem.syncCharge(AmethystShieldItem.getCharge(((IEntityDataSaver) player)), player);
                        }
                    }
                }
            });
        }
    }

    @Unique
    private static double getSlashMultiplier(PlayerEntity player) {
        return AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_DAMAGE() * (ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_SLIDE_MULTIPLIER());
    }
}
