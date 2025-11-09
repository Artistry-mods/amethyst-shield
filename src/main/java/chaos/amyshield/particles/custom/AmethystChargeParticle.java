package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class AmethystChargeParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;
    private final boolean isFlat;

    protected AmethystChargeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, boolean isFlat) {
        super(world, x, y, z, spriteProvider.getFirst());

        this.isFlat = isFlat;
        this.age = 0;
        this.spriteProvider = spriteProvider;
        this.maxAge = 12;
        this.scale = 2F;
    }

    @Override
    protected void renderVertex(BillboardParticleSubmittable submittable, Quaternionf rotation, float x, float y, float z, float tickProgress) {
        if (this.isFlat) {
            super.renderVertex(submittable, new Quaternionf(-1,0,0,1), x, y, z, tickProgress);
            super.renderVertex(submittable, new Quaternionf(1,0,0,1), x, y, z, tickProgress);
            return;
        }

        super.renderVertex(submittable, rotation, x, y, z, tickProgress);
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
        private final boolean isFlat;

        public Factory(SpriteProvider spriteProvider, boolean isFlat) {
            this.spriteProvider = spriteProvider;
            this.isFlat = isFlat;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new AmethystChargeParticle(world, x, y, z, this.spriteProvider, this.isFlat);
        }
    }
}
