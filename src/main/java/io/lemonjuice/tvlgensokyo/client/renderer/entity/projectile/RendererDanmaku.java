package io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.utils.TGDanmakuUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RendererDanmaku extends EntityRenderer<EntityDanmaku> {
    public RendererDanmaku(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityDanmaku entity) {
        return new ResourceLocation(TravelogueGensokyo.MODID, "none");
    }

    @Override
    public void render(EntityDanmaku danmaku, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        float scale = danmaku.danmaku.type.getRenderingScale();

        stack.push();
        stack.translate(0.0, danmaku.getBoundingBox().getYSize() / 2, 0.0);
        stack.scale(scale, scale, scale);
        stack.rotate(Vector3f.YP.rotationDegrees(-MathHelper.lerp(partialTicks, danmaku.prevRotationYaw, danmaku.rotationYaw)));
        stack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, danmaku.prevRotationPitch + 90.0F, danmaku.rotationPitch + 90.0F)));
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = new ItemStack(TGDanmakuUtils.DANMAKU_ITEM_MAP.get(Danmaku.Type.values()[danmaku.getDataManager().get(EntityDanmaku.DANMAKU_TYPE)]));
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Color", danmaku.getDataManager().get(EntityDanmaku.DANMAKU_COLOR));
        itemStack.setTag(nbt);
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED, 200, OverlayTexture.NO_OVERLAY, stack, buffer);
        stack.pop();
    }
}
