package io.lemonjuice.tvlgensokyo.client.models.baked;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SpellBookBakedModel implements IBakedModel {
    private IBakedModel existingModel;

    public SpellBookBakedModel(IBakedModel existingModel) {
        this.existingModel = existingModel;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random rand) {
        return this.existingModel.getQuads(state, direction, rand);
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.existingModel.isAmbientOcclusion();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.existingModel.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.existingModel.getItemCameraTransforms();
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND ||
                cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            return this;
        }
        return this.existingModel.handlePerspective(cameraTransformType, mat);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.existingModel.getOverrides();
    }
}
