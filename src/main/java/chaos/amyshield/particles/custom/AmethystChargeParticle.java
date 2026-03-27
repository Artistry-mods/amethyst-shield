package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SingleQuadParticle.Layer;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class AmethystChargeParticle extends SingleQuadParticle {
    private final SpriteSet spriteProvider;
    private final boolean isFlat;

    protected AmethystChargeParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, boolean isFlat) {
        super(world, x, y, z, spriteProvider.first());

        this.isFlat = isFlat;
        this.age = 0;
        this.spriteProvider = spriteProvider;
        this.lifetime = 12;
        this.quadSize = 2F;
    }

    @Override
    protected void extractRotatedQuad(QuadParticleRenderState submittable, Quaternionf rotation, float x, float y, float z, float tickProgress) {
        if (this.isFlat) {
            super.extractRotatedQuad(submittable, new Quaternionf(-1,0,0,1), x, y, z, tickProgress);
            super.extractRotatedQuad(submittable, new Quaternionf(1,0,0,1), x, y, z, tickProgress);
            return;
        }

        super.extractRotatedQuad(submittable, rotation, x, y, z, tickProgress);
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
        private final boolean isFlat;

        public Factory(SpriteSet spriteProvider, boolean isFlat) {
            this.spriteProvider = spriteProvider;
            this.isFlat = isFlat;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new AmethystChargeParticle(world, x, y, z, this.spriteProvider, this.isFlat);
        }
    }
}
