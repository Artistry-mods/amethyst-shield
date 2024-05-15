package chaos.amyshield.util;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ServerSaveListener {
    public static void init() {
        ServerWorldEvents.UNLOAD.register(ServerSaveListener::listener);

    }

    private static void listener(MinecraftServer minecraftServer, ServerWorld serverWorld) {
        serverWorld.getPlayers().forEach(serverPlayerEntity -> {
            Iterable<ItemStack> inventory = serverPlayerEntity.getInventory().main;

            // Update NBT data of your item in the player's inventory
            for (ItemStack stack : inventory) {
                if (stack.getItem() == ModItems.AMETHYST_SHIELD) {

                }
            }
        });
    }
}
