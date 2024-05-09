package chaos.amyshield.mixin.client;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.util.DoubleJumpListener;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ExampleClientMixin {
	private boolean jumpedLastTick = false;
	@Inject(at = @At("HEAD"), method = "tickMovement")
	public void tickMovement(CallbackInfo ci) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		if (!player.isOnGround() &&
				!player.isClimbing() &&
				!this.jumpedLastTick &&
				!player.getAbilities().flying) {

			PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
			passedData.writeUuid(player.getUuid());
			//passedData.writeBoolean(player.getVelocity().getY() > 0f);

			if (canJump(player)) {
				if (player.getVelocity().getY() < 0f &&
						(AmethystShieldItem.getCharge(player.getMainHandStack()) >= 50 ||
						AmethystShieldItem.getCharge(player.getOffHandStack()) >= 50) &&
						player.isBlocking() &&
						player.input.jumping) {

					player.setVelocity(player.getVelocity().getX(), 0.6, player.getVelocity().getZ());

					ClientPlayNetworking.send(DoubleJumpListener.C2S_DO_DOUBLEJUMP, passedData);
				}
			}
		}
		this.jumpedLastTick = player.input.jumping;
	}
	private boolean wearingUsableElytra(ClientPlayerEntity player) {
		ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
		return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
	}
	private boolean canJump(ClientPlayerEntity player) {
		return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
				&& !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
	}
}