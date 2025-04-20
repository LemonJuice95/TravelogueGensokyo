package io.lemonjuice.tvlgensokyo.client.renderer.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.entity.misc.EntityWind;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RendererWind extends EntityRenderer<EntityWind> {
    public RendererWind(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityWind wind) {
        return new ResourceLocation(TravelogueGensokyo.MODID, "none");
    }

    @Override
    public void render(EntityWind entity, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {

    }
}
