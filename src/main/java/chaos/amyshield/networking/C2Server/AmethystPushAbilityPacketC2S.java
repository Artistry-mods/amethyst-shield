package chaos.amyshield.networking.C2Server;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.networking.playload.AmethystPushPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AmethystPushAbilityPacketC2S {
    public static void push(AmethystPushPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            for (ItemStack itemStack : context.player().getHandItems()) {
                Item shield = itemStack.getItem();
                if (shield == ModItems.AMETHYST_SHIELD) {
                    List<Entity> entityList = new ArrayList<>(getEntitiesAroundPlayer(AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_RADIUS(), context.player()));
                    for (Entity entity : entityList) {
                        if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved()) {
                            pushEntityAwayFromPlayer(entity, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_X(), context.player());
                            entity.damage(context.player().getDamageSources().indirectMagic(context.player(), context.player()), AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_DAMAGE());
                        }
                    }
                    return;
                }
            }
        });
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

        entity.addVelocity(new Vec3d(velocity.x, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_STRENGTH_Y(), velocity.y));
    }
}