package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class AmethystCritParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;

    protected AmethystCritParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider.getFirst());

        this.age = 0;
        this.spriteProvider = spriteProvider;
        this.maxAge = 18 + this.random.nextInt(10);
        this.scale = 0.2F;
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

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new AmethystCritParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
