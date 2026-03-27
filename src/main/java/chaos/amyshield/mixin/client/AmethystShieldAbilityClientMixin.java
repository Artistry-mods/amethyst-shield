package chaos.amyshield.mixin.client;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.abilities.*;
import chaos.amyshield.enchantments.ModEnchantments;
import chaos.amyshield.item.ModItems;
import chaos.amyshield.item.custom.AmethystShieldItem;
import chaos.amyshield.networking.playload.AmethystAbilityPayload;
import chaos.amyshield.networking.playload.IgnoreFallDamagePayload;
import chaos.amyshield.util.IEntityDataSaver;
import chaos.amyshield.util.IMinecraftClientDatasaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LocalPlayer.class)
public abstract class AmethystShieldAbilityClientMixin {
    //My stuff
    @Unique
    public int isDoubleJumpingTimer = 0;
    //for movement charge stuff
    @Unique
    public Vec3 lastPos;
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
    private int lastSlashTimer = 0;

    @Unique
    private static boolean canUseAbility(LocalPlayer player, float cost) {
        return AmethystShieldItem.getCharge(((IEntityDataSaver) player)) >= cost * -(1);
    }

    @Inject(at = @At("HEAD"), method = "aiStep")
    public void tickMovement(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        this.gainMovementDeltaCharge();

        if ((player.onGround() || player.onClimbable() || player.getAbilities().flying || player.isSwimming())) {
            if (this.isSliding) {
                this.isSliding = false;
            }

            if (AmethystShieldItem.getSlashing(((IEntityDataSaver) player))) {
                AmethystShieldItem.setSlashing(((IEntityDataSaver) player), false);
                AmethystShieldItem.syncSlashing(false);
            }
        }

        this.checkForSneaking();

        this.checkForBlocking();

        if (!player.onGround() &&
                !player.onClimbable() &&
                !player.getAbilities().flying) {

            if (canJump(player)) {
                //slashing code
                if (this.canUseSparklingSlash()) {
                    this.onSparklingSlash();
                }

                //double jumping code
                if (!this.jumpedLastTick && player.getDeltaMovement().y() < 0.2f) {
                    if (this.isDoubleJumpingTimer >= 1) this.isDoubleJumpingTimer -= 1;
                    if (this.canDoubleJump()) {
                        this.onDoubleJump();
                    }
                }
            }
        }

        if (canHyperSlash()) {
            this.onHyperSlash();
        }

        if (lastSlashTimer < 40) {
            this.lastSlashTimer++;
        }

        this.lastPos = player.position();
        //IDK why I need this, but it was there from the Mod I riped it from
        this.jumpedLastTick = player.input.keyPresses.jump();
    }

    @Unique
    private void onHyperSlash() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        double originalAmount = player.getDeltaMovement().length();

//        Vec3d rotation = player.getRotationVecClient().subtract(0, player.getRotationVecClient().y, 0).normalize();
//        Vec3d newRotation = rotation.add(0, 0.1, 0).normalize();

        double multiplier = 3;

        this.lastSlashTimer = 40;

        if (getMovementDirectionOfKeypresses(player) == Vec3.ZERO) {
            onAbilityUse(PushAmethystShieldAbility.getId());

            player.setDeltaMovement(new Vec3(0, 0.3, 0));
        } else {
            player.setDeltaMovement(getMovementDirectionOfKeypresses(player).add(0, 0.1, 0).normalize().scale(originalAmount * multiplier));
        }

        ignoreAllFallDamageTill(player, player.position().subtract(0, 1000, 0));

        AmethystShieldItem.setSlashing(((IEntityDataSaver) player), true);
        AmethystShieldItem.syncSlashing(true);

        onAbilityUse(HyperSlashAmethystShieldAbility.getId());
    }

    @Unique
    private boolean canHyperSlash() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        return player.onGround() && player.input.keyPresses.jump() && lastSlashTimer < AmethystShield.CONFIG.amethystShieldNested.slashNested.HYPER_SLASH_TICK_TIMING();
    }

    @Unique
    private boolean canDoubleJump() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        return canUseAbility(player, AmethystShield.CONFIG.amethystShieldNested.doubleJumpNested.DOUBLE_JUMP_COST()) &&
                player.isBlocking() &&
                player.input.keyPresses.jump();
    }

    @Unique
    private boolean canUseSparklingSlash() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        return this.isDoubleJumpingTimer >= 1 &&
               ((IMinecraftClientDatasaver) Minecraft.getInstance()).amethyst_shield$getLastAttackTick() != 0 &&
               canUseAbility(player, AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_COST()) &&
               player.getMainHandItem().is(ItemTags.WEAPON_ENCHANTABLE);
    }

    @Unique
    public void checkForSneaking() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (this.sneakTimer >= 1) this.sneakTimer -= 1;

        if (player.isShiftKeyDown()) {
            this.hasSneakedLastTick = true;
        }
        if (!player.isShiftKeyDown() && this.hasSneakedLastTick) {
            this.hasSneakedLastTick = false;
            this.onSneakRelease();
        }
    }
    @Unique
    public void checkForBlocking() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (this.blockTimer >= 1) this.blockTimer -= 1;

        if (player.isUsingItem() && player.getUseItem().getItem().equals(ModItems.AMETHYST_SHIELD)) {
            this.hasBlockedLastTick = true;
        }

        if (!player.isUsingItem() && this.hasBlockedLastTick) {
            this.hasBlockedLastTick = false;
            this.onBlockRelease();
        }
    }

    @Unique
    private void gainMovementDeltaCharge() {
        this.tickMovementTimer();

        LocalPlayer player = (LocalPlayer) (Object) this;

        if (this.lastPos == null || AmethystShieldItem.getSlashing(((IEntityDataSaver) player)) || this.isSliding || player.isFallFlying()) {
            return;
        }
        //movement delta
        double movementDelta = new Vec2(((float) player.position().x()), ((float) player.position().z()))
                .distanceToSqr(new Vec2(((float) lastPos.x()), ((float) lastPos.z()))) * AmethystShield.CONFIG.amethystShieldNested.chargeNested.MOVEMENT_CHARGE_MULTIPLIER();

        if (movementDelta > AmethystShield.CONFIG.amethystShieldNested.chargeNested.MIN_MOVEMENT_DELTA()) {
            this.movementCharge += movementDelta;
        }
    }

    @Unique
    private void tickMovementTimer() {
        if (this.movementChargeTimer >= 1 && this.movementCharge < AmethystShield.CONFIG.amethystShieldNested.chargeNested.MAX_CHARGE() / 4) {
            this.movementChargeTimer    --;
        } else {
            if (this.movementCharge > 0) {
                this.onAbilityUse(ChargeGain.getId() + " " + this.movementCharge);
            }
            this.movementChargeTimer = AmethystShield.CONFIG.amethystShieldNested.chargeNested.MOVEMENT_CHARGE_TIMING();
            this.movementCharge = 0;
        }
    }

    @Unique
    private boolean canJump(LocalPlayer player) {
        return !player.isFallFlying() && !player.isPassenger()
                && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION);
    }

    @Unique
    private void onAbilityUse(String abilityId) {
        ClientPlayNetworking.send(new AmethystAbilityPayload(abilityId));
    }

    @Unique
    public double getSparklingSlashMultiplier(Player player) {
        double multiplier = AmethystShield.CONFIG.amethystShieldNested.slashNested.SPARKLING_SLASH_STRENGTH();

        return multiplier + (ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_SPARKLING_SLASH_MULTIPLIER());
    }

    @Unique
    private void onSparklingSlash() {
        LocalPlayer player = (LocalPlayer) (Object) this;
        AmethystShieldItem.setSlashing(((IEntityDataSaver) player), true);
        AmethystShieldItem.syncSlashing(true);

        flingPlayer(getSparklingSlashMultiplier(player));
        this.isDoubleJumpingTimer = 0;
        this.lastSlashTimer = 0;

        ignoreAllFallDamageTill(player, player.position().subtract(0, 1000, 0));

        this.onAbilityUse(SlashAmethystShieldAbility.getId());
    }

    @Unique
    private void ignoreAllFallDamageTill(LocalPlayer player, Vec3 player1) {
        player.fallDistance = 0;
        player.currentImpulseImpactPos = player.position();
        player.setIgnoreFallDamageFromCurrentImpulse(true);
        ClientPlayNetworking.send(new IgnoreFallDamagePayload(player1));
    }

    @Unique
    public double getDoubleJumpMultiplier(Player player) {
        double multiplier = AmethystShield.CONFIG.amethystShieldNested.doubleJumpNested.DOUBLE_JUMP_STRENGTH();

        return multiplier + (ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_DOUBLE_JUMP_MULTIPLIER());
    }

    @Unique
    private void onDoubleJump() {
        LocalPlayer player = (LocalPlayer) (Object) this;

        for (ItemStack itemStack : List.of(player.getOffhandItem(), player.getMainHandItem())) {
            Item shield = itemStack.getItem();

            if (shield != ModItems.AMETHYST_SHIELD) {
                continue;
            }

            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().x(), getDoubleJumpMultiplier(player) , player.getDeltaMovement().z());

            this.isDoubleJumpingTimer = AmethystShield.CONFIG.amethystShieldNested.slashNested.SLASH_TIMING();

            ignoreAllFallDamageTill(player, player.position());

            this.onAbilityUse(DoubleJumpAmethystShieldAbility.getId());
            return;
        }
    }

    @Unique
    private void onAmethystBurst() {
        this.onAbilityUse(PushAmethystShieldAbility.getId());
    }

    @Unique
    private void onSneakRelease() {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!player.isBlocking()) {
            return;
        }

        if (this.sneakTimer >= 1) {
            this.sneakTimer = 0;
            if (canUseAbility(player, AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_COST())) {
                this.onAmethystBurst();
            }
            return;
        }
        this.sneakTimer = AmethystShield.CONFIG.amethystShieldNested.pushNested.AMETHYST_PUSH_SNEAKING_TIMING();
    }

    @Unique
    private void onBlockRelease() {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (this.blockTimer >= 1) {
            this.blockTimer = 0;
            if (canUseAbility(player, AmethystShield.CONFIG.amethystShieldNested.slideNested.AMETHYST_SLIDE_COST())) {
                this.onAmethystSlide();
            }
            return;
        }
        this.blockTimer = AmethystShield.CONFIG.amethystShieldNested.slideNested.AMETHYST_SLIDE_TIMING();
    }

    @Unique
    private void onAmethystSlide() {
        this.applyMovementVelocity();
        this.isSliding = true;
        this.onAbilityUse(SlideAmethystShieldAbility.getId());
    }

    @Unique
    public double getSlideMultiplier(Player player) {
        return ModEnchantments.getReleaseEnchantmentLevel(player) * AmethystShield.CONFIG.amethystShieldNested.enchantmentNested.RELEASE_SLIDE_MULTIPLIER();
    }

    @Unique
    public void applyMovementVelocity() {
        LocalPlayer player = (LocalPlayer) (Object) this;
        // Get the player's current direction vector

        // Initialize movement vector
        Vec3 movement = getMovementDirectionOfKeypresses(player);
        movement = movement.scale(2 + getSlideMultiplier(player));

        // Apply velocity to the player
        player.getRootVehicle().setDeltaMovement(movement.x + player.getDeltaMovement().x, player.getDeltaMovement().y() * 0.5, movement.z + player.getDeltaMovement().z);
    }

    @Unique
    private Vec3 getMovementDirectionOfKeypresses(LocalPlayer player) {
        Vec3 facing = player.calculateViewVector(0, player.getYRot());

        Vec3 movement = Vec3.ZERO;

        // Get movement inputs
        boolean forward = player.input.keyPresses.forward();
        boolean back = player.input.keyPresses.backward();
        boolean left = player.input.keyPresses.left();
        boolean right = player.input.keyPresses.right();

        if (!back && !left && !forward && !right) {
            forward = true;
        }
        // Calculate movement direction
        if (forward && !back) {
            movement = movement.add(facing);
        }
        if (back && !forward) {
            movement = movement.add(facing.reverse());
        }
        if (left && !right) {
            movement = movement.add(facing.yRot((float) Math.toRadians(90)));
        }
        if (right && !left) {
            movement = movement.add(facing.yRot((float) Math.toRadians(-90)));
        }

        // Normalize movement vector
        if (movement.lengthSqr() > 0) {
            movement = movement.normalize();
        }
        return movement;
    }

    //chatGPTs code since IDK math like that
    @Unique
    private void flingPlayer(double speed) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        float yaw = player.getYRot();
        float pitch = player.getXRot();

        // Convert yaw and pitch to radians
        double yawRadians = Math.toRadians(yaw);
        double pitchRadians = Math.toRadians(pitch);

        // Calculate velocity components
        double vx = -Math.sin(yawRadians) * Math.cos(pitchRadians) * speed;
        double vy = -Math.sin(pitchRadians) * speed;
        double vz = Math.cos(yawRadians) * Math.cos(pitchRadians) * speed;

        // Apply velocity to the player
        player.setDeltaMovement(player.getDeltaMovement().x() + vx,
                player.getDeltaMovement().y() + vy,
                player.getDeltaMovement().z() + vz);
    }
}