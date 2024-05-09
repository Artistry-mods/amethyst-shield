package chaos.amyshield.util;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;

public class DoubleJumpListener {
    public static final Identifier C2S_DO_DOUBLEJUMP = new Identifier("amyshield", "do_a_double_jump");
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(C2S_DO_DOUBLEJUMP,
                (server, player, handler, buf, responseSender) -> {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeUuid(buf.readUuid());
                    //boolean isDoubleJumping = buf.readBoolean();

                    server.execute(() -> {
                        for (ItemStack itemStack : player.getHandItems()) {
                            Item shield = itemStack.getItem();
                            if (shield == ModItems.AMETHYST_SHIELD) {
                                AmethystShieldItem.setCharge(itemStack, AmethystShieldItem.getCharge(itemStack) - 50);
                                //AmethystShieldItem.setDoubleJumping(itemStack, isDoubleJumping);
                            }
                        }
                    });
                });
    }
}
