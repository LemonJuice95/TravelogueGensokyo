package io.lemonjuice.tvlgensokyo.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.StoneMortarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.*;
import net.minecraft.world.IBlockDisplayReader;

public class StoneMortarTileEntityRenderer extends TileEntityRenderer<StoneMortarTileEntity> {

    public StoneMortarTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(StoneMortarTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        matrixStack.push();
        if(!tileEntity.getHoldingStack().isEmpty()) {
            this.renderItemStack(matrixStack, tileEntity.getHoldingStack(), combinedLight, combinedOverlay, buffer);
        }

        if(tileEntity.getFluid() != Fluids.EMPTY) {
            this.renderFluid(tileEntity.getWorld(), tileEntity.getPos(), matrixStack, tileEntity.getFluid(), buffer, combinedLight, combinedOverlay);
        }
        matrixStack.pop();
    }

    private void renderItemStack(MatrixStack matrixStack, ItemStack stack, int combinedLight, int combinedOverlay, IRenderTypeBuffer buffer) {
        matrixStack.push();
        int count = (stack.getCount() - 1) / 16 + 1;
        matrixStack.translate(0.5F, 0.20F, 0.5F);
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
        for(int i = 1; i <= count; i++) {
            matrixStack.translate(0.0F, 0.0F, -0.05F);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(45.0F));
        }
        matrixStack.pop();
    }

    private void renderFluid(IBlockDisplayReader world, BlockPos pos, MatrixStack matrixStack, Fluid fluid, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        matrixStack.push();
        ResourceLocation fluidTexture = fluid.getAttributes().getStillTexture();
        TextureAtlasSprite fluidSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluidTexture);
        float[] u = {fluidSprite.getMinU(), fluidSprite.getInterpolatedU(8)};
        float[] v = {fluidSprite.getMinV(), fluidSprite.getInterpolatedV(8)};
        int i = fluid.getAttributes().getColor(world, pos);
        float alpha = (float)(i >> 24 & 255) / 255.0F;
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        float f4 = world.func_230487_a_(Direction.UP, true);
        float red = f4 * f;
        float green = f4 * f1;
        float blue = f4 * f2;
        Matrix3f matrix3f = matrixStack.getLast().getNormal();
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
        normal.transform(matrix3f);

        Vector4f[] vertexPos = {
                new Vector4f(4.0F / 16.0F, 7.0F / 16.0F, 4.0F / 16.0F, 1.0F),
                new Vector4f(4.0F / 16.0F, 7.0F / 16.0F, 12.0F / 16.0F, 1.0F),
                new Vector4f(12.0F / 16.0F, 7.0F / 16.0F, 12.0F / 16.0F, 1.0F),
                new Vector4f(12.0F / 16.0F, 7.0F / 16.0F, 4.0F / 16.0F, 1.0F),
        };
        IVertexBuilder fluidVertexBuilder = buffer.getBuffer(RenderType.getTranslucentNoCrumbling());
        for(int j = 0; j < 4; j++) {
            Vector4f vp = vertexPos[j];
            vp.transform(matrix4f);
//            fluidVertexBuilder.pos(vp.getX(), vp.getY(), vp.getZ()).color(red, green, blue, alpha).tex(u[(j>>1)&1], v[(j&1)^(j>>1)]).lightmap(combinedLight).normal(0.0F, 1.0F, 0.0F).endVertex();
            fluidVertexBuilder.addVertex(vp.getX(), vp.getY(), vp.getZ(), red, green, blue, alpha, u[(j>>1)&1], v[(j&1)^(j>>1)], combinedOverlay, combinedLight, normal.getX(), normal.getY(), normal.getZ());
        }
        matrixStack.pop();
    }
}
