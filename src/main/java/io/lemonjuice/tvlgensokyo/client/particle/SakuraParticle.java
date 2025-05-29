package io.lemonjuice.tvlgensokyo.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nullable;

public class SakuraParticle extends SpriteTexturedParticle {
    private float rotSpeed;
    private final float spinAcceleration;

    public SakuraParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.particleGravity = 0.075F;
        float size = this.rand.nextBoolean() ? 0.075F : 0.05F;
        this.particleScale = size;
        this.setSize(size, size);
        this.maxAge = 300;
        this.rotSpeed = (float)Math.toRadians(this.rand.nextBoolean() ? -5.0 : 5.0);
        this.spinAcceleration = (float)Math.toRadians(this.rand.nextBoolean() ? -0.5 : 0.5);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            if(this.motionY > -0.04) {
                this.motionY -= 0.04 * (double) this.particleGravity;
            }
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.onGround) {
                this.age += 10;
                this.motionX *= 0.4;
                this.motionZ *= 0.4;
            } else {
                this.motionX *= 1.005;
                this.motionZ *= 1.005;
            }
        }

        if(!this.onGround) {
            this.particleAngle += (float) (Math.PI * this.rotSpeed);
            this.rotSpeed += this.spinAcceleration / 20.0F;
            if (this.particleAngle > 2 * Math.PI)
                this.particleAngle = 0.0F;

            this.prevParticleAngle = particleAngle;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SakuraParticle particle = new SakuraParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setSprite(this.sprite.get(particle.rand.nextInt(12), 12));
            return particle;
        }
    }
}
