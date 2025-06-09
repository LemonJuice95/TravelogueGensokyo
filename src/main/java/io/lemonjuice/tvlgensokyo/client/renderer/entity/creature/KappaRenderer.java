package io.lemonjuice.tvlgensokyo.client.renderer.entity.creature;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.models.entity.creature.KappaModel;
import io.lemonjuice.tvlgensokyo.common.entity.creature.KappaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class KappaRenderer extends MobRenderer<KappaEntity, KappaModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/entity/creature/kappa.png");

    public KappaRenderer(EntityRendererManager manager) {
        super(manager, new KappaModel(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(KappaEntity entity) {
        return TEXTURE;
    }
}
