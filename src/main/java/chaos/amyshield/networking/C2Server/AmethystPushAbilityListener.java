package chaos.amyshield.networking.C2Server;

import chaos.amyshield.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;

public class AmethystPushAbilityListener {
    public static void receiver(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        List<Entity> entityList = getEntitiesAroundPlayer(3, player);
        entityList.forEach(entity -> {
            pushEntityAwayFromPlayer(entity, 0.5, player);
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
        // Calculate the direction vector from player to entity
        Vec3d playerPos = player.getPos().add(0,-1,0);
        Vec3d entityPos = entity.getPos();
        Vec3d direction = entityPos.subtract(playerPos);

        // Normalize the direction vector
        Vec3d normalizedDirection = direction.normalize();

        // Scale the normalized direction vector by the desired speed
        Vec3d velocity = normalizedDirection.multiply(speed + (player.distanceTo(entity) * 0.5));

        // Set the entity's velocity
        entity.addVelocity(velocity);
    }

}
