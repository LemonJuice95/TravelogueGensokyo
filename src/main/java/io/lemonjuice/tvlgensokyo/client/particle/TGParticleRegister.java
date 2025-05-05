package io.lemonjuice.tvlgensokyo.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TGParticleRegister {
    public static final BasicParticleType FALLING_MAPLE_LEAF = registerParticleType("falling_maple_leaf", false);

    private static BasicParticleType registerParticleType(String name, boolean alwaysShow) {
        BasicParticleType particleType = new BasicParticleType(alwaysShow);
        particleType.setRegistryName(name);
        ForgeRegistries.PARTICLE_TYPES.register(particleType);
        return particleType;
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistry(ParticleFactoryRegisterEvent event) {
        ParticleManager manager = Minecraft.getInstance().particles;
        manager.registerFactory(FALLING_MAPLE_LEAF, FallingLeafParticle.Factory::new);
    }
}
