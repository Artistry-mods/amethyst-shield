package chaos.amyshield.Item.custom;

import chaos.amyshield.AmethystShield;
import chaos.amyshield.particles.ModParticles;
import chaos.amyshield.tag.ModTags;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class AmethystMonocleTrinketItem extends TrinketItem {
    private int activationTimer = 0;

    public AmethystMonocleTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (this.activationTimer >= 1) {
            this.activationTimer--;
        } else {
            this.onPing(entity.getWorld(), entity);
            this.activationTimer = AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_TIMER();
        }

        super.tick(stack, slot, entity);
    }

    private void onPing(World world, Entity entity) {
        if (entity instanceof PlayerEntity) {
            for (BlockPos blockPos : BlockPos.iterate(
                    entity.getBlockPos().add(AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE()),
                    entity.getBlockPos().add(-AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE(), -AmethystShield.CONFIG.monocleNested.AMETHYST_MONOCLE_RANGE()))) {
                BlockState state = world.getBlockState(blockPos);
                if (state.isIn(ModTags.SHINY_ORES)) {
                    if (world.isClient()) {
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
        }
    }
}
