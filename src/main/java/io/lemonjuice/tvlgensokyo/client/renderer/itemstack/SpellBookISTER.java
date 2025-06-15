package io.lemonjuice.tvlgensokyo.client.renderer.itemstack;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.client.TGClientUtils;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.BasicSpellBookModel;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.SpellBookModelBase;
import io.lemonjuice.tvlgensokyo.common.item.weapon.SpellBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class SpellBookISTER extends ItemStackTileEntityRenderer {
    private final Minecraft MC = Minecraft.getInstance();

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if(stack.getItem() instanceof SpellBookItem) {
            float partialTicks = MC.isGamePaused() ? MC.renderPartialTicksPaused : MC.getRenderPartialTicks();

            matrixStack.push();
            matrixStack.scale(0.65F, 0.65F, 0.65F);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(165F));
            matrixStack.translate(0.74, -1.5, -1.27);
            SpellBookModelBase model;
            model = TGClientUtils.SPELL_BOOK_MODEL_MAP.containsKey(stack.getItem()) ? TGClientUtils.SPELL_BOOK_MODEL_MAP.get(stack.getItem()) : new BasicSpellBookModel();
            model.setBookState(SpellBookItem.getOpenAmount(stack), SpellBookItem.getLeftPageFlipAmount(stack), SpellBookItem.getRightPageFlipAmount(stack), partialTicks, SpellBookItem.isOpened(stack));
            IVertexBuilder iVertexBuilder = ItemRenderer.getBuffer(buffer, model.getRenderType(model.getTexture()), false, stack.hasEffect());
            model.render(matrixStack, iVertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
