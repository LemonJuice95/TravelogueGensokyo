package io.lemonjuice.tvlgensokyo.client.renderer.entity.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.models.entity.projectile.AmuletModel;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityAmulet;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RendererAmulet extends EntityRenderer<EntityAmulet> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/entity/projectile/amulet.png");

    private EntityModel<EntityAmulet> model;

    public RendererAmulet(EntityRendererManager manager) {
        super(manager);
        this.model = new AmuletModel();
    }

    @Override
    public ResourceLocation getEntityTexture(EntityAmulet amulet) {
        return TEXTURE;
    }

    @Override
    public void render(EntityAmulet amulet, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        stack.push();
        stack.rotate(Vector3f.YP.rotationDegrees(-MathHelper.lerp(partialTicks, amulet.prevRotationYaw, amulet.rotationYaw)));
        stack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, amulet.prevRotationPitch, amulet.rotationPitch)));
        IVertexBuilder iVertexBuilder = buffer.getBuffer(this.model.getRenderType(this.getEntityTexture(amulet)));
        this.model.render(stack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.pop();
    }
}
