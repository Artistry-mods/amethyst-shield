package chaos.amyshield.mixin.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.Item.ModItems;
import chaos.amyshield.Item.custom.AmethystShieldItem;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.networking.playload.AmethystPushPayload;
import chaos.amyshield.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class AmethystShieldAbilityClientMixin {
    //My stuff
    @Unique
    public int isDoubleJumpingTimer = 0;
    //for movement charge stuff
    @Unique
    public Vec3d lastPos;
    @Unique
    public int movementChargeTimer = 0;
    @Unique
    public double movementCharge = 0;
    //double jump mod I ripped :)
    @Unique
    private boolean jumpedLastTick = false;
    @Unique
    private boolean isSliding = false;
    @Unique
    private boolean hasSneakedLastTick = false;
    @Unique
    private int sneakTimer = 0;
    @Unique
    private boolean hasBlockedLastTick = false;
    @Unique
    private int blockTimer = 0;

    @Unique
    private static boolean canUseAbility(ClientPlayerEntity player, float cost) {
        return AmethystShieldItem.getCharge(((IEntityDataSaver) player)) >= cost * -(1);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        //movement delta (I hate it and I will delete it)
        if (this.lastPos != null && !AmethystShieldItem.getSlashing(((IEntityDataSaver) player)) && !this.isSliding && !player.isFallFlying()) {
            double movementDelta = new Vec2f(((float) player.getPos().getX()), ((float) player.getPos().getZ()))
                    .distanceSquared(new Vec2f(((float) lastPos.getX()), ((float) lastPos.getZ())));
            if (movementDelta > AmethystShield.MIN_MOVEMENT_DELTA) {
                this.movementCharge += movementDelta;
                //sending the packed to remove charge
            }
        }
        this.lastPos = player.getPos();

        if (this.movementChargeTimer >= 1) this.movementChargeTimer -= 1;
        if (this.movementChargeTimer == 0) {
            if (this.movementCharge > 0) {
                this.onAbilityUse((float) this.movementCharge, false, false, false);
            }
            this.movementChargeTimer = AmethystShield.MOVEMENT_CHARGE_TIMING;
            this.movementCharge = 0;
        }

        //returning from slashing state if a player touches the ground
        if (((player.isOnGround() || player.isClimbing() || player.getAbilities().flying) || player.isSwimming()) && AmethystShieldItem.getSlashing(((IEntityDataSaver) player))) {
            AmethystShieldItem.setSlashing(((IEntityDataSaver) player), false);
            AmethystShieldItem.syncSlashing(false);
        }

        if ((player.isOnGround() || player.isClimbing() || player.getAbilities().flying) && this.isSliding) {
            this.isSliding = false;
        }

        //this is just sneaking detection / ability activation
        if (this.sneakTimer >= 1) this.sneakTimer -= 1;

        if (player.isSneaking()) {
            this.hasSneakedLastTick = true;
        }
        if (!player.isSneaking() && this.hasSneakedLastTick) {
            this.hasSneakedLastTick = false;
            this.onSneakRelease();
        }

        if (this.blockTimer >= 1) this.blockTimer -= 1;

        if (player.isUsingItem() && player.getActiveItem().getItem().equals(ModItems.AMETHYST_SHIELD)) {
            this.hasBlockedLastTick = true;
        }
        if (!player.isUsingItem() && this.hasBlockedLastTick) {
            this.hasBlockedLastTick = false;
            this.onBlockRelease();
        }

        if (!player.isOnGround() &&
                !player.isClimbing() &&
                !this.jumpedLastTick &&
                !player.getAbilities().flying) {

            if (canJump(player)) {
                //dashing code
                if (this.isDoubleJumpingTimer >= 1 &&
                        player.handSwinging &&
                        canUseAbility(player, AmethystShield.SPARKLING_SLASH_COST) &&
                        player.getMainHandStack().getItem() instanceof SwordItem) {
                    this.onSparklingSlash();
                }

                //double jumping code
                if (player.getVelocity().getY() < 0f) {
                    if (this.isDoubleJumpingTimer >= 1) this.isDoubleJumpingTimer -= 1;
                    if (canUseAbility(player, AmethystShield.DOUBLE_JUMP_COST) &&
                            player.isBlocking() &&
                            player.input.jumping) {
                        this.onDoubleJump();
                    }
                }
            }
        }
        //IDK why I need this, but it was there from the Mod I riped it from
        this.jumpedLastTick = player.input.jumping;
    }

    @Unique
    private boolean canJump(ClientPlayerEntity player) {
        return !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
    }

    @Unique
    private void onAbilityUse(float cost, boolean flatParticles, boolean notFlatParticles, boolean sound) {
        ClientPlayNetworking.send(new AmethystAbilityPayload(cost, flatParticles, notFlatParticles, sound));
    }

    @Unique
    private void onSparklingSlash() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        AmethystShieldItem.setSlashing(((IEntityDataSaver) player), true);
        AmethystShieldItem.syncSlashing(true);

        flingPlayer(AmethystShield.SPARKLING_SLASH_STRENGTH);
        this.isDoubleJumpingTimer = 0;

        this.onAbilityUse(AmethystShield.SPARKLING_SLASH_COST, false, true, true);
    }

    @Unique
    private void onDoubleJump() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        for (ItemStack itemStack : player.getHandItems()) {
            Item shield = itemStack.getItem();
            if (shield == ModItems.AMETHYST_SHIELD) {
                //Setting velocity to make the player jump
                player.jump();
                player.setVelocity(player.getVelocity().getX(), AmethystShield.DOUBLE_JUMP_STRENGTH, player.getVelocity().getZ());
                //setting the double jump timer to 10, so that we can use it later for the sword slash
                this.isDoubleJumpingTimer = AmethystShield.SLASH_TIMING;

                this.onAbilityUse(AmethystShield.DOUBLE_JUMP_COST, true, false, true);
                return;
            }
        }
    }

    @Unique
    private void onAmethystBurst() {
        ClientPlayNetworking.send(new AmethystPushPayload(true));
        this.onAbilityUse(AmethystShield.AMETHYST_PUSH_COST, true, false, true);
    }

    @Unique
    private void onSneakRelease() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (player.isBlocking()) {
            if (this.sneakTimer >= 1) {
                this.sneakTimer = 0;
                if (canUseAbility(player, AmethystShield.AMETHYST_PUSH_COST)) {
                    this.onAmethystBurst();
                }
                return;
            }
            this.sneakTimer = AmethystShield.AMETHYST_PUSH_SNEAKING_TIMING;
        }
    }

    @Unique
    private void onBlockRelease() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (this.blockTimer >= 1) {
            this.blockTimer = 0;
            if (canUseAbility(player, AmethystShield.AMETHYST_SLIDE_COST)) {
                this.onAmethystSlide();
            }
            return;
        }
        this.blockTimer = AmethystShield.AMETHYST_SLIDE_TIMING;
    }

    @Unique
    private void onAmethystSlide() {
        this.applyMovementVelocity();
        this.isSliding = true;
        this.onAbilityUse(AmethystShield.AMETHYST_SLIDE_COST, true, false, true);
    }

    @Unique
    public void applyMovementVelocity() {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        // Get the player's current direction vector
        Vec3d facing = player.getRotationVec(1.0F);

        // Initialize movement vector
        Vec3d movement = Vec3d.ZERO;

        // Get movement inputs
        boolean forward = player.input.pressingForward;
        boolean back = player.input.pressingBack;
        boolean left = player.input.pressingLeft;
        boolean right = player.input.pressingRight;

        if (!back && !left && !forward && !right) {
            forward = true;
        }
        // Calculate movement direction
        if (forward) {
            movement = movement.add(facing);
        }
        if (back) {
            movement = movement.add(facing.negate());
        }
        if (left) {
            movement = movement.add(facing.rotateY((float) Math.toRadians(90)));
        }
        if (right) {
            movement = movement.add(facing.rotateY((float) Math.toRadians(-90)));
        }

        // Normalize movement vector
        if (movement.lengthSquared() > 0) {
            movement = movement.normalize();
        }
        movement = movement.multiply(2);

        // Apply velocity to the player
        player.setVelocity(movement.x + player.getVelocity().x, player.getVelocity().getY() * 0.5, movement.z + player.getVelocity().z);
    }

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