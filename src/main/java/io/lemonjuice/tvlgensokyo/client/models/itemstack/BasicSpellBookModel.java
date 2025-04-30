// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports
package io.lemonjuice.tvlgensokyo.client.models.itemstack;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BasicSpellBookModel extends SpellBookModelBase {
	public static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/item/tile_entity/basic_spell_book.png");

	private final ModelRenderer pagesRight;
	private final ModelRenderer pagesLeft;
	private final ModelRenderer flippingPageRight;
	private final ModelRenderer flippingPageLeft;
	private final ModelRenderer coverRight;
	private final ModelRenderer coverLeft;
	private final ModelRenderer bookSpine;

	public BasicSpellBookModel() {
		textureWidth = 64;
		textureHeight = 64;

		pagesRight = new ModelRenderer(this);
		pagesRight.setRotationPoint(0.0F, 9.5F, 5.0F);
		pagesRight.setTextureOffset(30, 2).addBox(0.0F, -7.5F, -9.0F, 1.0F, 15.0F, 9.0F, 0.0F, true);

		pagesLeft = new ModelRenderer(this);
		pagesLeft.setRotationPoint(0.0F, 9.5F, 5.0F);
		pagesLeft.setTextureOffset(30, 2).addBox(-1.0F, -7.5F, -9.0F, 1.0F, 15.0F, 9.0F, 0.0F, false);

		flippingPageRight = new ModelRenderer(this);
		flippingPageRight.setRotationPoint(0.0F, 9.5F, 5.0F);
		flippingPageRight.setTextureOffset(0, 18).addBox(0.0F, -7.5F, -9.0F, 0.01F, 15.0F, 9.0F, 0.0F, false);

		flippingPageLeft = new ModelRenderer(this);
		flippingPageLeft.setRotationPoint(0.0F, 9.5F, 5.0F);
		flippingPageLeft.setTextureOffset(0, 18).addBox(0.0F, -7.5F, -9.0F, 0.01F, 15.0F, 9.0F, 0.0F, false);

		coverRight = new ModelRenderer(this);
		coverRight.setRotationPoint(1.0F, 9.5F, 5.75F);
		coverRight.setTextureOffset(0, 0).addBox(0.0F, -8.0F, -10.25F, 1.0F, 16.0F, 10.0F, 0.0F, true);

		coverLeft = new ModelRenderer(this);
		coverLeft.setRotationPoint(-1.0F, 9.5F, 5.75F);
		coverLeft.setTextureOffset(0, 0).addBox(-1.0F, -8.0F, -10.25F, 1.0F, 16.0F, 10.0F, 0.0F, false);

		bookSpine = new ModelRenderer(this);
		bookSpine.setRotationPoint(-1.0F, 9.5F, 5.75F);
		bookSpine.setTextureOffset(23, 9).addBox(0.0F, -8.0F, -0.75F, 2.0F, 16.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@Override
	public void setBookState(float openAmount, float leftPageFlipAmount, float rightPageFlipAmount, float partialTicks, boolean isOpened) {
		float f1 = isOpened ? 1.0F : -1.0F;
		boolean isStatic = openAmount == 0.0F || openAmount == 1.0F;
		double animOpenAmount = MathHelper.clamp(!isStatic ? Math.sin(Math.PI / 2.0F * (openAmount + partialTicks / 5.0F * f1)) : openAmount, 0.0F, 1.0F);
		this.coverLeft.rotateAngleY = (float)(Math.PI / 3.0F * animOpenAmount);
		this.coverRight.rotateAngleY = -(float)(Math.PI / 3.0F * animOpenAmount);
		this.pagesLeft.rotateAngleY = (float)(Math.PI / 3.0F * animOpenAmount);
		this.pagesRight.rotateAngleY = -(float)(Math.PI / 3.0F * animOpenAmount);
		this.flippingPageLeft.rotateAngleY = (float)(Math.PI / 3.5F * animOpenAmount);
		this.flippingPageRight.rotateAngleY = -(float)(Math.PI / 3.5F * animOpenAmount);

		if(openAmount == 1.0F) {
			double animLeftPageFlipAmount = MathHelper.clamp(!isStatic ? Math.sin(Math.PI / 2.0F * (leftPageFlipAmount + partialTicks * 0.2F)) : leftPageFlipAmount, 0.0F, 1.0F);
			double animRightPageFlipAmount = MathHelper.clamp(!isStatic ? Math.sin(Math.PI / 2.0F * (rightPageFlipAmount + partialTicks * 0.2F)) : rightPageFlipAmount, 0.0F, 1.0F);
			this.flippingPageLeft.rotateAngleY = animLeftPageFlipAmount < 0.5F ?
					(float)(Math.PI / 3.5F * (1.0F - animLeftPageFlipAmount * 4.0F)) :
					(float)(Math.PI / 3.5F + (Math.PI / 3.0F - Math.PI / 3.5F) * (1.0F - (animLeftPageFlipAmount - 0.5F) * 2.0F));
				this.flippingPageRight.rotateAngleY = animRightPageFlipAmount < 0.5F ?
						-(float) (Math.PI / 3.5F * (1.0F - animRightPageFlipAmount * 4.0F)) :
						-(float) (Math.PI / 3.5F + (Math.PI / 3.0F - Math.PI / 3.5F) * (1.0F - (animRightPageFlipAmount - 0.5F) * 2.0F));
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		pagesRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		pagesLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		flippingPageRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		flippingPageLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		coverRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		coverLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bookSpine.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}