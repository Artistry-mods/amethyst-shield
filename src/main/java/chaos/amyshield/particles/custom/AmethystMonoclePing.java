package chaos.amyshield.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class AmethystMonoclePing extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private final Direction facing;

    protected AmethystMonoclePing(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, Direction isFlat) {
        super(world, x, y, z, 0, 0, 0);
        this.setSpriteForAge(spriteProvider);

        this.facing = isFlat;
        this.age = 0;
        this.spriteProvider = spriteProvider;
        this.maxAge = 12;
        this.scale = 0.5F;
    }


    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternionf quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        if (this.facing == Direction.UP) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(90)));
        } else if (this.facing == Direction.DOWN) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(-90)));
        } else if (this.facing == Direction.NORTH) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        } else if (this.facing == Direction.SOUTH) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateX((float) Math.toRadians(-180)));
        } else if (this.facing == Direction.WEST) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateY((float) Math.toRadians(90)));
        } else if (this.facing == Direction.EAST) {
            quaternionf = new Quaternionf(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).rotateY((float) Math.toRadians(-90)));
        }

        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float i = this.getSize(tickDelta);

        for (int j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }

        float k = this.getMinU();
        float l = this.getMaxU();
        float m = this.getMinV();
        float n = this.getMaxV();
        int o = this.getBrightness(tickDelta);
        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o);
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o);
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o);
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        private final Direction faceing;

        public Factory(SpriteProvider spriteProvider, Direction isFlat) {
            this.spriteProvider = spriteProvider;
            this.faceing = isFlat;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new AmethystMonoclePing(world, x, y, z, this.spriteProvider, this.faceing);
        }
    }
}
