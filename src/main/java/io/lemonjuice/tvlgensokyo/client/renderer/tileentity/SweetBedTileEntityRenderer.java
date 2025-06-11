package io.lemonjuice.tvlgensokyo.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.SweetBedTileEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.BedTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SweetBedTileEntityRenderer extends TileEntityRenderer<SweetBedTileEntity> {
    private final ModelRenderer pillow;
    private final TileEntityRendererDispatcher dispatcher;

    private static final ResourceLocation PILLOW_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/tileentity/sweet_pillow.png");

    public SweetBedTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
        this.dispatcher = dispatcher;

        pillow = new ModelRenderer(64, 64, 0, 0);
        pillow.setRotationPoint(-7.0F, 9.0F, 0.0F);
        pillow.setTextureOffset(0, 0).addBox(0.0F, 0.5F, 0.0F, 14.0F, 1.0F, 8.0F, 0.0F, false);
        pillow.setTextureOffset(0, 10).addBox(1.0F, 0.0F, 1.0F, 12.0F, 2.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void render(SweetBedTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        BlockState state = tileEntity.getBlockState();

        if(tileEntity.getWorld() != null) {
            //Render a single part of bed
            RenderMaterial renderMaterial = Atlases.BED_TEXTURES[tileEntity.getColor().getId()];
            BedTileEntityRenderer bedTileEntityRenderer = new BedTileEntityRenderer(this.dispatcher);
            bedTileEntityRenderer.func_228847_a_(matrixStack, buffer, state.get(BedBlock.PART) == BedPart.HEAD, state.get(BedBlock.HORIZONTAL_FACING), renderMaterial, combinedLight, combinedOverlay, false);

            //Render sweet pillow
            if (state.get(BedBlock.PART) == BedPart.HEAD) {
                IVertexBuilder iVertexBuilder = buffer.getBuffer(RenderType.getEntitySolid(PILLOW_TEXTURE));

                matrixStack.push();
                matrixStack.translate(0.5D, 0.0D, 0.5D);
                matrixStack.scale(0.85F, 1.0F, 0.85F);
                matrixStack.rotate(Vector3f.YN.rotationDegrees(state.get(BedBlock.HORIZONTAL_FACING).getHorizontalAngle()));
                this.pillow.render(matrixStack, iVertexBuilder, combinedLight, combinedOverlay);
                matrixStack.pop();
            }
        }
    }
}