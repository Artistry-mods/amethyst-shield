package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AmethystMonocleItem extends Item {
    private int activationTimer = 0;

    public AmethystMonocleItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
        return false;
    }

    /*
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user.getInventory().getArmorStack(3).getItem() == Items.AIR) {
            //user.getInventory().armor.set(3, user.getStackInHand(hand));
            user.equipStack(EquipmentSlot.HEAD, user.getStackInHand(hand));
            user.setStackInHand(hand, ItemStack.EMPTY);
            if (world.isClient()) {
                world.playSoundAtBlockCenter(user.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value(), SoundCategory.PLAYERS, 1.0f, 1.0F, true);
            }
            return ActionResult.SUCCESS;
        }

        return super.use(world, user, hand);
    }

     */

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            if (this.activationTimer >= 1) {
                this.activationTimer--;
            } else {
                this.onPing(world, entity);
                this.activationTimer = AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_TIMER();
            }
        }

        super.inventoryTick(stack, world, entity, slot);
    }

    private void onPing(Level world, Entity entity) {
        if (!(world instanceof ServerLevel serverWorld) || !(entity instanceof ServerPlayer player)) {
            return;
        }

        for (BlockPos blockPos : BlockPos.betweenClosed(
                entity.blockPosition().offset(AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE()),
                entity.blockPosition().offset(-AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE())))
        {
            BlockState state = world.getBlockState(blockPos);

            if (!state.is(ModTags.SHINY_ORES)) {
                continue;
            }

            ClipContext context = new ClipContext(
                    entity.position().add(0, 1, 0),
                    blockPos.getCenter(),
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    entity
            );
            BlockHitResult result = world.clip(context);
            Vec3 particlePos = result.getBlockPos().getCenter().relative(result.getDirection(), 0.51);
            Direction facing = result.getDirection();

            if (facing == Direction.WEST) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_WEST, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.EAST) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_EAST, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.NORTH) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_NORTH, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.SOUTH) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_SOUTH, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.UP) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_UP, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.DOWN) {
                serverWorld.sendParticles(player, ModParticles.AMETHYST_MONOCLE_PING_DOWN, true, true, particlePos.x(),  particlePos.y(), particlePos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
