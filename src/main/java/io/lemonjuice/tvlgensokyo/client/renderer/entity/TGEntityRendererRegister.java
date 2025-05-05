package io.lemonjuice.tvlgensokyo.client.renderer.entity;

import io.lemonjuice.tvlgensokyo.client.renderer.entity.creature.KappaRenderer;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.misc.WindRenderer;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.npc.GensokyoVillagerRenderer;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.AmuletRenderer;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.DanmakuRenderer;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile.GungnirRenderer;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.utils.TGDanmakuUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class TGEntityRendererRegister {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRenderersRegistry() {
        IReloadableResourceManager resourceManager = (IReloadableResourceManager) MC.getResourceManager();

        //Projectiles & Misc
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.WIND.get(), WindRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.AMULET.get(), AmuletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.GUNGNIR.get(), GungnirRenderer::new);

        //Creature
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.KAPPA.get(), KappaRenderer::new);

        //NPC
        RenderingRegistry.registerEntityRenderingHandler(TGEntityRegister.GENSOKYO_VILLAGER.get(), (manager) -> new GensokyoVillagerRenderer(manager, resourceManager));

        //Danmaku
        TGDanmakuUtils.DANMAKU_ENTITY_MAP.values().forEach((danmakuEntity) -> {
            RenderingRegistry.registerEntityRenderingHandler(danmakuEntity, DanmakuRenderer::new);
        });
    }
}
