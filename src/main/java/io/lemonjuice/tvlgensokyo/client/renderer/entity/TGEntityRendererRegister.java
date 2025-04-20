package io.lemonjuice.tvlgensokyo.client.renderer.entity;

import io.lemonjuice.tvlgensokyo.client.renderer.entity.creature.RendererKappa;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.misc.RendererWind;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.npc.RendererGensokyoVillager;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.RendererAmulet;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.RendererDanmaku;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.RendererGungnir;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.utils.TGDanmakuUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class TGEntityRendererRegister {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRenderersRegistry() {
        IReloadableResourceManager resourceManager = (IReloadableResourceManager) MC.getResourceManager();

        //Projectiles & Misc
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.WIND.get(), RendererWind::new);
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.AMULET.get(), RendererAmulet::new);
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.GUNGNIR.get(), RendererGungnir::new);

        //Creature
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.KAPPA.get(), RendererKappa::new);

        //NPC
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.GENSOKYO_VILLAGER.get(), (manager) -> new RendererGensokyoVillager(manager, resourceManager));

        //Danmaku
        TGDanmakuUtils.DANMAKU_ENTITY_MAP.values().forEach((danmakuEntity) -> {
            RenderingRegistry.registerEntityRenderingHandler(danmakuEntity, RendererDanmaku::new);
        });
    }
}
