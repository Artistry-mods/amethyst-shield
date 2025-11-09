package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class AmethystMonoclePing extends BillboardParticle {
    private final SpriteProvider spriteProvider;
    private final Direction facing;

    protected AmethystMonoclePing(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, Direction isFlat) {
        super(world, x, y, z, spriteProvider.getFirst());
        this.spriteProvider = spriteProvider;

        this.facing = isFlat;
        this.age = 0;
        this.maxAge = 12;
        this.scale = 0.5F;
    }

    @Override
    protected void renderVertex(BillboardParticleSubmittable submittable, Quaternionf rotation, float x, float y, float z, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        if (this.facing == Direction.UP) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(-90)));
        } else if (this.facing == Direction.DOWN) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(90)));
        } else if (this.facing == Direction.NORTH) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(180)));
        } else if (this.facing == Direction.SOUTH) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        } else if (this.facing == Direction.WEST) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateY((float) Math.toRadians(-90)));
        } else if (this.facing == Direction.EAST) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateY((float) Math.toRadians(90)));
        }

        super.renderVertex(submittable, quaternionf, x, y , z, tickProgress);
    }

    @Override
    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.updateSprite(this.spriteProvider);
        }
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        private final Direction faceing;

        public Factory(SpriteProvider spriteProvider, Direction isFlat) {
            this.spriteProvider = spriteProvider;
            this.faceing = isFlat;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new AmethystMonoclePing(world, x, y, z, this.spriteProvider, this.faceing);
        }
    }
}
