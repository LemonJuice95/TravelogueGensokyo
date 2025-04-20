package io.lemonjuice.tvlgensokyo.client.renderer.environment;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;
import org.lwjgl.opengl.GL11;

public class DreamWorldSkyRenderer implements ISkyRenderHandler {
    private static final ResourceLocation SKY_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/environment/dream_world_sky.png");
    private static final ResourceLocation SKY_TOP_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/environment/dream_world_sky_top.png");

    @Override
    @SuppressWarnings("deprecation")
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
        TextureManager textureManager = mc.getTextureManager();

        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        for(int i = 0; i < 6; ++i) {
            matrixStack.push();

            if(i <= 3) {
                textureManager.bindTexture(SKY_TEXTURE);
            } else {
                textureManager.bindTexture(SKY_TOP_TEXTURE);
            }

            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));

            if (i == 1) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-90.0F));
            }



            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);



            bufferbuilder.pos(matrix4f, -100.0F, -100.0F, -100.0F).tex(0.0F, 0.0F).endVertex();
            bufferbuilder.pos(matrix4f, -100.0F, -100.0F, 100.0F).tex(0.0F, 1.0F).endVertex();
            bufferbuilder.pos(matrix4f, 100.0F, -100.0F, 100.0F).tex(1.0F, 1.0F).endVertex();
            bufferbuilder.pos(matrix4f, 100.0F, -100.0F, -100.0F).tex(1.0F, 0.0F).endVertex();

            bufferbuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferbuilder);
            matrixStack.pop();
        }

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
    }

}
