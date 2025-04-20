package io.lemonjuice.tvlgensokyo.client.models.entity.projectile;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityGungnir;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GungnirModel extends EntityModel<EntityGungnir> {
	private final ModelRenderer gungnir;

	public GungnirModel() {
		textureWidth = 64;
		textureHeight = 64;

		gungnir = new ModelRenderer(this);
		gungnir.setRotationPoint(0.5F, 0.0F, 0.0F);
		gungnir.setTextureOffset(0, 13).addBox(-0.5F, -0.5F, -6.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		gungnir.setTextureOffset(0, 10).addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		gungnir.setTextureOffset(0, 7).addBox(-2.5F, -0.5F, -2.0F, 5.0F, 1.0F, 2.0F, 0.0F, false);
		gungnir.setTextureOffset(0, 0).addBox(-3.0F, -0.5F, 0.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
		gungnir.setTextureOffset(0, 5).addBox(-1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		gungnir.setTextureOffset(16, 0).addBox(-3.5F, -0.5F, 2.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
		gungnir.setTextureOffset(0, 39).addBox(-0.5F, -0.5F, 4.0F, 1.0F, 1.0F, 24.0F, 0.0F, false);
		gungnir.setTextureOffset(14, 4).addBox(-4.0F, -0.5F, 3.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		gungnir.setTextureOffset(14, 4).addBox(3.0F, -0.5F, 3.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(EntityGungnir entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.push();
		matrixStack.scale(1.25F, 1.25F, 1.4F);
		gungnir.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.pop();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}