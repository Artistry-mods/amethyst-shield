package chaos.amyshield.networking.C2Server;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.networking.playload.AmethystPushPayload;
import chaos.amyshield.networking.playload.IgnoreFallDamagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

public class AmethystPushAbilityPacketC2S {
    public static void push(AmethystPushPayload _payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : List.of(context.player().getMainHandStack(), context.player().getOffHandStack())) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    SoundEvent soundEvent = context.player().fallDistance > 5.0 ? SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY : SoundEvents.ITEM_MACE_SMASH_GROUND;
                    context.player().getWorld().playSound(null, context.player().getX(), context.player().getY(), context.player().getZ(), soundEvent, context.player().getSoundCategory(), 1.0F, 1.0F);

                    Vec3d vec3d = context.player().getPos();
                    int i = (int) MathHelper.clamp(50.0, 0.0, 200.0);
                    context.player().getWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, context.player().getSteppingBlockState()), vec3d.x, vec3d.y, vec3d.z, i, 0.30000001192092896, 0.30000001192092896, 0.30000001192092896, 0.15000000596046448);
                    context.player().getWorld().syncWorldEvent(WorldEvents.SMASH_ATTACK, context.player().getSteppingPos(), 750);

                    List<Entity> entityList = new ArrayList<>(getEntitiesAroundPlayer(AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_RADIUS() * getBurstMultiplier(context.player()), context.player()));
                    for (Entity entity : entityList) {
                        if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved()) {
                            pushEntityAwayFromPlayer(entity, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_X() * getBurstMultiplier(context.player()), context.player());
                            entity.damage(context.player().getWorld(), context.player().getDamageSources().indirectMagic(context.player(), context.player()), (float) (AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_DAMAGE() * getBurstMultiplier(context.player())));
                        }
                    }

                    return;
                }
            }
        });
    }

    @Unique
    public static double getBurstMultiplier(PlayerEntity player) {
        return (ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_PUSH_MULTIPLIER());
    }

    private static List<Entity> getEntitiesAroundPlayer(double radius, PlayerEntity player) {
        World world = player.getWorld();
        Box box = new Box(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return world.getOtherEntities(player, box);
    }

    private static void pushEntityAwayFromPlayer(Entity entity, double speed, PlayerEntity player) {
        Vec2f playerPos = new Vec2f((float) -player.getPos().getX(), (float) -player.getPos().getZ());
        Vec2f entityPos = new Vec2f((float) entity.getPos().getX(), (float) entity.getPos().getZ());
        Vec2f direction = entityPos.add(playerPos);

        Vec2f normalizedDirection = direction.normalize();

        Vec2f velocity = normalizedDirection.multiply((float) (speed + (player.distanceTo(entity) * 0.8)));

        entity.addVelocity(new Vec3d(velocity.x, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_Y() * getBurstMultiplier(player), velocity.y));
    }

    public static void ignoreFallDamage(IgnoreFallDamagePayload ignoreFallDamagePayload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : List.of(context.player().getMainHandStack(), context.player().getOffHandStack())) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    ServerPlayerEntity player = context.player();

                    player.currentExplosionImpactPos = ignoreFallDamagePayload.till();
                    player.setIgnoreFallDamageFromCurrentExplosion(true);
                }
            }
        });
    }
}