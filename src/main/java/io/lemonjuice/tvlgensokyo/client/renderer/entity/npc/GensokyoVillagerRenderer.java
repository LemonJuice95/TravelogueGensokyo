package io.lemonjuice.tvlgensokyo.client.renderer.entity.npc;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.layers.GensokyoVillagerLevelPendantLayer;
import io.lemonjuice.tvlgensokyo.common.entity.npc.EntityGensokyoVillager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GensokyoVillagerRenderer extends MobRenderer<EntityGensokyoVillager, VillagerModel<EntityGensokyoVillager>> {
    private static final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/villager.png");

    public GensokyoVillagerRenderer(EntityRendererManager renderManagerIn, IReloadableResourceManager resourceManagerIn) {
        super(renderManagerIn, new VillagerModel<>(0.0F), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new GensokyoVillagerLevelPendantLayer<>(this, resourceManagerIn, "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(EntityGensokyoVillager entity) {
        return VILLAGER_TEXTURES;
    }

    protected void preRenderCallback(EntityGensokyoVillager entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 0.9375F;
        if (entitylivingbaseIn.isChild()) {
            f = (float)((double)f * 0.5D);
            this.shadowSize = 0.25F;
        } else {
            this.shadowSize = 0.5F;
        }

        matrixStackIn.scale(f, f, f);
    }
}
