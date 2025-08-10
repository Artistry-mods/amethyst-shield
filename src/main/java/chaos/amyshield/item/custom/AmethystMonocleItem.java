package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AmethystMonocleItem extends Item {
    private int activationTimer = 0;

    public AmethystMonocleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
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
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
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

    private void onPing(World world, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld) || !(entity instanceof ServerPlayerEntity player)) {
            return;
        }

        for (BlockPos blockPos : BlockPos.iterate(
                entity.getBlockPos().add(AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE()),
                entity.getBlockPos().add(-AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE())))
        {
            BlockState state = world.getBlockState(blockPos);

            if (!state.isIn(ModTags.SHINY_ORES)) {
                continue;
            }

            RaycastContext context = new RaycastContext(
                    entity.getPos().add(0, 1, 0),
                    blockPos.toCenterPos(),
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    entity
            );
            BlockHitResult result = world.raycast(context);
            Vec3d particlePos = result.getBlockPos().toCenterPos().offset(result.getSide(), 0.51);
            Direction facing = result.getSide();

            if (facing == Direction.WEST) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_WEST, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.EAST) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_EAST, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.NORTH) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_NORTH, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.SOUTH) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_SOUTH, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.UP) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_UP, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            } else if (facing == Direction.DOWN) {
                serverWorld.spawnParticles(player, ModParticles.AMETHYST_MONOCLE_PING_DOWN, true, true, particlePos.getX(),  particlePos.getY(), particlePos.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }
}
