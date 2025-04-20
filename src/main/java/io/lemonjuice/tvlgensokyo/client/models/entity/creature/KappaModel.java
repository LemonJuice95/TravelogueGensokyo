// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports
package io.lemonjuice.tvlgensokyo.client.models.entity.creature;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.common.entity.creature.EntityKappa;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class KappaModel extends EntityModel<EntityKappa> {
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer body;
	private final ModelRenderer skirt;
	private final ModelRenderer key;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer backpack;
	private final ModelRenderer head;
	private final ModelRenderer hair;
	private final ModelRenderer hat;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftArm_r1;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightArm_r1;

	public KappaModel() {
		textureWidth = 128;
		textureHeight = 64;

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(-2.25F, 16.0F, 0.5F);
		rightLeg.setTextureOffset(0, 52).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(-2.25F, 16.0F, 0.5F);
		leftLeg.setTextureOffset(0, 52).addBox(3.0F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.75F, 0.0F);
		body.setTextureOffset(0, 10).addBox(-3.5F, -13.25F, -2.0F, 7.0F, 9.0F, 5.0F, 0.0F, false);

		skirt = new ModelRenderer(this);
		skirt.setRotationPoint(0.0F, 0.0F, 0.25F);
		body.addChild(skirt);
		skirt.setTextureOffset(0, 42).addBox(-6.0F, -1.75F, -3.75F, 12.0F, 2.0F, 8.0F, 0.0F, false);
		skirt.setTextureOffset(0, 33).addBox(-5.5F, -3.0F, -3.25F, 11.0F, 2.0F, 7.0F, 0.0F, false);
		skirt.setTextureOffset(0, 25).addBox(-4.5F, -4.25F, -2.75F, 9.0F, 2.0F, 6.0F, 0.0F, false);

		key = new ModelRenderer(this);
		key.setRotationPoint(0.0F, -10.0F, -2.75F);
		body.addChild(key);
		key.setTextureOffset(1, 1).addBox(-1.0F, -1.0F, 0.25F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		key.setTextureOffset(1, 1).addBox(-0.75F, 1.0F, 0.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		key.setTextureOffset(1, 1).addBox(0.0F, 1.5F, 0.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.5F, 0.75F);
		key.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.7854F, 0.0F, -0.5236F);
		cube_r1.setTextureOffset(2, 5).addBox(-3.25F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 0.5F, 0.75F);
		key.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.7854F, 0.0F, 0.5236F);
		cube_r2.setTextureOffset(1, 5).addBox(-3.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);

		backpack = new ModelRenderer(this);
		backpack.setRotationPoint(0.0F, -7.0F, 3.25F);
		body.addChild(backpack);
		backpack.setTextureOffset(94, 25).addBox(-3.0F, -2.0F, -0.75F, 6.0F, 4.0F, 4.0F, 0.0F, false);
		backpack.setTextureOffset(103, 15).addBox(-2.5F, -5.0F, -0.25F, 5.0F, 3.0F, 3.0F, 0.0F, false);
		backpack.setTextureOffset(86, 29).addBox(-0.5F, -3.0F, 2.75F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		backpack.setTextureOffset(117, 28).addBox(-3.5F, -1.5F, 0.5F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		backpack.setTextureOffset(117, 28).addBox(2.5F, -1.5F, 0.5F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 3.5F, 0.5F);
		head.setTextureOffset(33, 3).addBox(-4.0F, -6.0F, -3.5F, 8.0F, 6.0F, 7.0F, 0.0F, false);

		hair = new ModelRenderer(this);
		hair.setRotationPoint(0.0F, -1.5F, 0.0F);
		head.addChild(hair);
		hair.setTextureOffset(50, 27).addBox(-3.0F, -4.5F, -4.5F, 6.0F, 2.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 23).addBox(-3.0F, -2.5F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 23).addBox(-1.0F, -2.5F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 23).addBox(2.0F, -2.5F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(39, 22).addBox(3.0F, -4.5F, -4.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(39, 22).addBox(-4.0F, -4.5F, -4.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(52, 32).addBox(-5.0F, -4.5F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		hair.setTextureOffset(52, 32).addBox(4.0F, -4.5F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(-5.0F, -4.5F, -3.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(4.0F, -4.5F, -3.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(4.0F, -4.5F, 2.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(-5.0F, -4.5F, 2.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(-4.0F, -4.5F, 3.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(45, 26).addBox(3.0F, -4.5F, 3.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		hair.setTextureOffset(37, 36).addBox(-3.0F, -4.5F, 3.5F, 6.0F, 6.0F, 1.0F, 0.0F, false);

		hat = new ModelRenderer(this);
		hat.setRotationPoint(0.0F, -0.5F, 0.5F);
		head.addChild(hat);
		hat.setTextureOffset(65, 0).addBox(-3.0F, -6.5F, -7.0F, 6.0F, 1.0F, 10.0F, 0.0F, false);
		hat.setTextureOffset(98, 3).addBox(-3.0F, -9.5F, -3.5F, 6.0F, 3.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(67, 12).addBox(-4.0F, -7.5F, -4.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
		hat.setTextureOffset(86, 13).addBox(-4.0F, -8.5F, -3.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(86, 13).addBox(3.0F, -8.5F, -3.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(67, 24).addBox(-3.0F, -8.5F, 2.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		hat.setTextureOffset(67, 24).addBox(-3.0F, -7.5F, 3.0F, 6.0F, 2.0F, 1.0F, 0.0F, false);
		hat.setTextureOffset(67, 25).addBox(-3.0F, -7.5F, -5.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		hat.setTextureOffset(67, 24).addBox(-3.0F, -8.5F, -4.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		hat.setTextureOffset(86, 13).addBox(-5.0F, -7.5F, -3.5F, 1.0F, 2.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(86, 13).addBox(4.0F, -7.5F, -3.5F, 1.0F, 2.0F, 6.0F, 0.0F, false);
		hat.setTextureOffset(67, 12).addBox(3.0F, -7.5F, -4.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
		hat.setTextureOffset(84, 24).addBox(-4.0F, -6.5F, -6.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		hat.setTextureOffset(84, 24).addBox(3.0F, -6.5F, -6.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(4.0F, 3.25F, 0.5F);
		

		leftArm_r1 = new ModelRenderer(this);
		leftArm_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftArm.addChild(leftArm_r1);
		leftArm_r1.setTextureOffset(16, 53).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, true);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-4.0F, 3.25F, 0.5F);
		

		rightArm_r1 = new ModelRenderer(this);
		rightArm_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightArm.addChild(rightArm_r1);
		rightArm_r1.setTextureOffset(16, 53).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(EntityKappa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.leftArm.rotateAngleX = MathHelper.cos(limbSwing* 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.leftArm.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.05F) * 0.05F - 0.16F;
		this.rightArm.rotateAngleZ = MathHelper.cos(ageInTicks * 0.05F) * 0.05F + 0.16F;
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}