package chaos.amyshield.mixin.client;

import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.networking.C2Server.AmethystPushAbilityListener;
import chaos.amyshield.networking.C2Server.AmethystShieldAbilityListener;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class AmethystShieldAbilityClientMixin {
	@Shadow public abstract boolean isSneaking();
	//double jump mod I ripped :)
	@Unique
	private boolean jumpedLastTick = false;
	//My stuff
	@Unique
	public int isDoubleJumpingTimer = 0;
	@Unique
	private boolean hasSneakedLastTick = false;
	@Unique
	private int sneakCounter = 0;
	@Unique
	public boolean isSlashing = false;

	@Unique
	public Vec3d lastPos;

	//costs
	@Unique
	private final static float DOUBLE_JUMP_COST = 50f;
	@Unique
	private final static float SPARKLING_SLASH_COST = 25f;
	//timer timing (in game ticks)
	@Unique
	private final static int SLASH_TIMING = 10;
	@Unique
	private final static int AMETHYST_BUST_SNEAKING_TIMING = 10;
	//misc
	@Unique
	private final static float DOUBLE_JUMP_STRENGTH = 0.7f;
	@Unique
	private final static float SPARKLING_SLASH_STRENGTH = 2;
	@Inject(at = @At("HEAD"), method = "tickMovement")
	public void tickMovement(CallbackInfo ci) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

		//movement delta (I hate it and I will delete it)
		/*
        if (this.lastPos != null) {
            double movementDelta = player.squaredDistanceTo(this.lastPos);
            if (movementDelta > 0.001) {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeFloat((float) movementDelta);
                //sending the packed to remove charge
                ClientPlayNetworking.send(AmethystShieldAbilityListener.AMETHYST_ABILITY_C2S, passedData);
            }
        }
        this.lastPos = player.getPos();
		 */

		//returning from slashing state if player touches the ground
		if (player.isOnGround() || player.isClimbing() || player.getAbilities().flying) {
			this.isSlashing = false;
		}

		//this is just sneaking detection / ability activation
		if (this.sneakCounter >= 1) this.sneakCounter -= 1;

		if (player.isSneaking()) {
			this.hasSneakedLastTick = true;
		}
		if (!player.isSneaking() && this.hasSneakedLastTick) {
			this.hasSneakedLastTick = false;
			this.onSneakRelease();
		}

		if (!player.isOnGround() &&
				!player.isClimbing() &&
				!this.jumpedLastTick &&
				!player.getAbilities().flying) {

			if (this.isSlashing) {
				if (player.getRandom().nextInt(5) == 1) {
					player.getWorld().addParticle(ModParticles.AMETHYST_CRIT_PARTICLE,
							player.getX() + player.getRandom().nextFloat() - 0.5,
							player.getY() + player.getRandom().nextFloat() - 0.5,
							player.getZ() + player.getRandom().nextFloat() - 0.5,
							player.getRandom().nextFloat(),
							player.getRandom().nextFloat(),
							player.getRandom().nextFloat());
					player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 1);
				}
				///NOT WORKING
			}

			if (canJump(player)) {
				//dashing code
				if (this.isDoubleJumpingTimer >= 1 &&
						player.handSwinging &&
						AmethystShieldItem.getCharge(player.getOffHandStack()) >= SPARKLING_SLASH_COST &&
						player.getMainHandStack().getItem() instanceof SwordItem) {
					this.onSparklingSlash();
				}

				//double jumping code
				if (player.getVelocity().getY() < 0f) {
					if (this.isDoubleJumpingTimer >= 1) this.isDoubleJumpingTimer -= 1;
					if ((AmethystShieldItem.getCharge(player.getMainHandStack()) >= DOUBLE_JUMP_COST ||
							AmethystShieldItem.getCharge(player.getOffHandStack()) >= DOUBLE_JUMP_COST) &&
							player.isBlocking() &&
							player.input.jumping) {
						this.onDoubleJump();
					}
				}
			}
		}
		//idk why I need this, but it was there from the Mod I riped it from
		this.jumpedLastTick = player.input.jumping;
	}
	@Unique
	private boolean wearingUsableElytra(ClientPlayerEntity player) {
		ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
		return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
	}
	@Unique
	private boolean canJump(ClientPlayerEntity player) {
		return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
				&& !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
	}

	@Unique
	private void onAbilityUse(float cost) {
		//play a little sound
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		player.playSound(SoundEvents.BLOCK_BELL_USE, 0.3f, 0.8f);
		player.playSound(SoundEvents.BLOCK_BELL_RESONATE, 0.3f, 0.8f);
		player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.5f, 1);
		//spawn a nice particle
		player.getWorld().addParticle(ModParticles.AMETHYST_CHARGE_PARTICLE, player.getX(), player.getY(), player.getZ(), 0,0,0);

		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		passedData.writeFloat(-cost);
		//sending the packed to remove charge
		ClientPlayNetworking.send(ModPackets.AMETHYST_ABILITY_C2S, passedData);
	}
	@Unique
	private void onSparklingSlash() {
		this.isSlashing = true;
		flingPlayer(SPARKLING_SLASH_STRENGTH);
		this.isDoubleJumpingTimer = 0;

		this.onAbilityUse(SPARKLING_SLASH_COST);
	}
	@Unique
	private void onDoubleJump() {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

		//Setting velocity to make the player jump
		player.jump();
		player.setVelocity(player.getVelocity().getX(), DOUBLE_JUMP_STRENGTH, player.getVelocity().getZ());
		//setting the double jump timer to 10, so that we can use it later for the sword slash
		this.isDoubleJumpingTimer = SLASH_TIMING;

		this.onAbilityUse(DOUBLE_JUMP_COST);
	}

	@Unique
	private void onAmethystBurst() {
		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		//sending the packed to remove charge
		ClientPlayNetworking.send(ModPackets.AMETHYST_PUSH_ABILITY_C2S, passedData);
		System.out.println("bwoomm");
	}

	@Unique
	private void onSneakRelease() {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		if (player.isBlocking()) {
			if (this.sneakCounter >= 1) {
				this.sneakCounter = 0;
				this.onAmethystBurst();
				return;
			}
			this.sneakCounter = AMETHYST_BUST_SNEAKING_TIMING;
		}
	}

	/*
	public boolean isSlashing(PlayerEntity player) {
		PlayerEntity mixin_player = (PlayerEntity) (Object) this;
		if (mixin_player == player) {
			return this.isSlashing;
		}
		return false;
	}
	 */

	//chatGPTs code since IDK math like that
	@Unique
	private void flingPlayer(double speed) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		float yaw = player.getYaw();
		float pitch = player.getPitch();

		// Convert yaw and pitch to radians
		double yawRadians = Math.toRadians(yaw);
		double pitchRadians = Math.toRadians(pitch);

		// Calculate velocity components
		double vx = -Math.sin(yawRadians) * Math.cos(pitchRadians) * speed;
		double vy = -Math.sin(pitchRadians) * speed;
		double vz = Math.cos(yawRadians) * Math.cos(pitchRadians) * speed;

		// Apply velocity to the player
		player.setVelocity(player.getVelocity().getX() + vx,
				player.getVelocity().getY() + vy,
				player.getVelocity().getZ() + vz);
	}
}