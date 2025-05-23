package chaos.amyshield.item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (slot == 3) {
            if (this.activationTimer >= 1) {
                this.activationTimer--;
            } else {
                this.onPing(world, entity);
                this.activationTimer = AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_TIMER();
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void onPing(World world, Entity entity) {
        if (!world.isClient()) {
            return;
        }

        if (!(entity instanceof PlayerEntity)) {
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
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_WEST, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            } else if (facing == Direction.EAST) {
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_EAST, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            } else if (facing == Direction.NORTH) {
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_NORTH, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            } else if (facing == Direction.SOUTH) {
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_SOUTH, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            } else if (facing == Direction.UP) {
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_UP, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            } else if (facing == Direction.DOWN) {
                world.addParticle(ModParticles.AMETHYST_MONOCLE_PING_DOWN, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
            }
        }
    }
}
