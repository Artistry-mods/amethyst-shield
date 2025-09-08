package chaos.amyshield.mixin;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import chaos.amyshield.util.IEntityDataSaver;
import net.minecraft.enchantment.EnchantmentHelper;
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
import java.util.stream.Stream;

@Mixin(ServerPlayerEntity.class)
public class SlashingHitMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    protected void tickInject(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player != null && AmethystShieldItem.getSlashing(((IEntityDataSaver) player))) {
            //double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed
            if (player.getRandom().nextInt(5) == 1) {
                player.getWorld().spawnParticles(ModParticles.AMETHYST_CRIT_PARTICLE,
                        player.getX() + player.getRandom().nextFloat() - 0.5,
                        player.getY() + player.getRandom().nextFloat() - 0.5,
                        player.getZ() + player.getRandom().nextFloat() - 0.5,
                        1,
                        player.getRandom().nextFloat(),
                        player.getRandom().nextFloat(),
                        player.getRandom().nextFloat(),
                        0);
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1f, 1);
            }

            List<Entity> entityList = new ArrayList<>(player.getWorld().getOtherEntities(player, player.getBoundingBox().expand(AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_RADIUS())));
            Objects.requireNonNull(player.getServer()).execute(() -> {
                for (Entity entity : entityList) {
                    if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved() && !entity.getType().isIn(ModTags.SLASH_IMMUNE)) {
                        if (entity.damage(player.getWorld(), player.getDamageSources().indirectMagic(player, player), (float) getSlashMultiplier(player))) {
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
        return AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_DAMAGE() * (getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_SLIDE_MULTIPLIER());
    }

    @Unique
    private static double getReleaseEnchantmentLevel(PlayerEntity player) {
        return EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().getOptionalEntry(ModEnchantments.RELEASE).get(),
                Stream.of(player.getOffHandStack(), player.getMainHandStack())
                        .filter(stack -> stack.isOf(ModItems.AMETHYST_SHIELD))
                        .toList().getFirst());
    }
}
