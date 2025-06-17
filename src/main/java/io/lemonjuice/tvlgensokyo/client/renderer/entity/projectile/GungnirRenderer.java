package io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.models.entity.projectile.GungnirModel;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.GungnirEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class GungnirRenderer extends EntityRenderer<GungnirEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/entity/projectile/gungnir.png");

    private EntityModel<GungnirEntity> model;

    public GungnirRenderer(EntityRendererManager manager) {
        super(manager);
        this.model = new GungnirModel();
    }

    @Override
    public ResourceLocation getEntityTexture(GungnirEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GungnirEntity gungnir, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        stack.push();
        stack.rotate(Vector3f.YP.rotationDegrees(-MathHelper.lerp(partialTicks, gungnir.prevRotationYaw, gungnir.rotationYaw) + 180F));
        stack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, -gungnir.prevRotationPitch, -gungnir.rotationPitch)));
        IVertexBuilder iVertexBuilder = buffer.getBuffer(this.model.getRenderType(this.getEntityTexture(gungnir)));
        this.model.render(stack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.7F);
        stack.pop();
    }
}
