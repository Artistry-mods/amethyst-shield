package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class AmethystMonoclePing extends SingleQuadParticle {
    private final SpriteSet spriteProvider;
    private final Direction facing;

    protected AmethystMonoclePing(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, Direction isFlat) {
        super(world, x, y, z, spriteProvider.first());
        this.spriteProvider = spriteProvider;

        this.facing = isFlat;
        this.age = 0;
        this.lifetime = 12;
        this.quadSize = 0.5F;
    }

    @Override
    protected void extractRotatedQuad(QuadParticleRenderState submittable, Quaternionf rotation, float x, float y, float z, float tickProgress) {
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

        super.extractRotatedQuad(submittable, quaternionf, x, y , z, tickProgress);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteProvider);
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        private final Direction faceing;

        public Factory(SpriteSet spriteProvider, Direction isFlat) {
            this.spriteProvider = spriteProvider;
            this.faceing = isFlat;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new AmethystMonoclePing(world, x, y, z, this.spriteProvider, this.faceing);
        }
    }
}
