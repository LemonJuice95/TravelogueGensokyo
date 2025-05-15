package io.lemonjuice.tvlgensokyo.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nullable;
import java.util.Random;

public class FallingLeafParticle extends SpriteTexturedParticle {
    private final float rotSpeed;

    public FallingLeafParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        Random random = new Random();
        this.particleGravity = 0.5F;
        this.particleScale = 0.075F;
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.maxAge = 400;
        this.rotSpeed = 1.0F / (float)(random.nextInt(240) + 240);
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
            FallingLeafParticle particle = new FallingLeafParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(this.sprite);
            return particle;
        }
    }
}
