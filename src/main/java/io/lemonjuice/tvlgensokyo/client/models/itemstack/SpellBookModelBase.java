package io.lemonjuice.tvlgensokyo.client.models.itemstack;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class SpellBookModelBase extends EntityModel<Entity> {

    public abstract void setBookState(float openAmount, float leftPageFlipAmount, float rightPageFlipAmount, float partialTicks, boolean isOpened);

    public abstract ResourceLocation getTexture();
}
