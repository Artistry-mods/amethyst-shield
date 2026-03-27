package chaos.amyshield.abilities;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;

import java.util.ArrayList;
import java.util.List;

public record PushAmethystShieldAbility() implements AmethystShieldAbility {
    @Override
    public float getChargeCost() {
        return AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_COST();
    }

    @Override
    public boolean shouldDisplayParticle() {
        return true;
    }

    @Override
    public boolean shouldParticleBeFlat() {
        return true;
    }

    @Override
    public boolean shouldPlaySound() {
        return true;
    }

    @Override
    public void onTrigger(ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : List.of(context.player().getMainHandItem(), context.player().getOffhandItem())) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    SoundEvent soundEvent = context.player().fallDistance > 5.0 ? SoundEvents.MACE_SMASH_GROUND_HEAVY : SoundEvents.MACE_SMASH_GROUND;
                    context.player().level().playSound(null, context.player().getX(), context.player().getY(), context.player().getZ(), soundEvent, context.player().getSoundSource(), 1.0F, 1.0F);

                    Vec3 vec3d = context.player().position();
                    int i = (int) Mth.clamp(50.0, 0.0, 200.0);
                    context.player().level().sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, context.player().getBlockStateOn()), vec3d.x, vec3d.y, vec3d.z, i, 0.30000001192092896, 0.30000001192092896, 0.30000001192092896, 0.15000000596046448);
                    context.player().level().levelEvent(LevelEvent.PARTICLES_SMASH_ATTACK, context.player().getOnPos(), 750);

                    List<Entity> entityList = new ArrayList<>(getEntitiesAroundPlayer(AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_RADIUS(), context.player()));
                    if (AmethystShield.CONFIG.amethystShieldNested.pushNested.KILL_ONLY_HOSTILE()) {
                        entityList = entityList.stream().filter((entity) -> entity instanceof Enemy || entity instanceof Player).toList();
                    }

                    for (Entity entity : entityList) {
                        if (entity instanceof LivingEntity && !((LivingEntity) entity).isDeadOrDying() && !entity.isRemoved()) {
                            pushEntityAwayFromPlayer(entity, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_X() * getBurstMultiplier(context.player()), context.player());
                            entity.hurtServer(context.player().level(), context.player().damageSources().indirectMagic(context.player(), context.player()), (float) (AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_DAMAGE() * getBurstMultiplier(context.player())));
                        }
                    }

                    return;
                }
            }
        });
    }

    public static double getBurstMultiplier(Player player) {
        return (ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_PUSH_MULTIPLIER());
    }

    private static List<Entity> getEntitiesAroundPlayer(double radius, Player player) {
        Level world = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return world.getEntities(player, box);
    }

    private static void pushEntityAwayFromPlayer(Entity entity, double speed, Player player) {
        Vec2 playerPos = new Vec2((float) -player.position().x(), (float) -player.position().z());
        Vec2 entityPos = new Vec2((float) entity.position().x(), (float) entity.position().z());
        Vec2 direction = entityPos.add(playerPos);

        Vec2 normalizedDirection = direction.normalized();

        Vec2 velocity = normalizedDirection.scale((float) (speed + (player.distanceTo(entity) * 0.8)));

        entity.push(new Vec3(velocity.x, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_Y() * getBurstMultiplier(player), velocity.y));
    }

    public static String getId() {
        return "push";
    }
}
