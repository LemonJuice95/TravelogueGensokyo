package io.lemonjuice.tvlgensokyo.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.resources.data.VillagerMetadataSection;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class GensokyoVillagerLevelPendantLayer<T extends LivingEntity & IVillagerDataHolder, M extends EntityModel<T> & IHeadToggle> extends VillagerLevelPendantLayer<T, M> {
    public GensokyoVillagerLevelPendantLayer(IEntityRenderer<T, M> renderer, IReloadableResourceManager manager, String name) {
        super(renderer, manager, name);
    }

    @Override
    @SuppressWarnings("deprecated")
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            VillagerData villagerdata = entity.getVillagerData();
            VillagerType villagertype = villagerdata.getType();
            VillagerProfession villagerprofession = villagerdata.getProfession();
            VillagerMetadataSection.HatType villagermetadatasection$hattype1 = this.func_215350_a(this.field_215354_c, "profession", Registry.VILLAGER_PROFESSION, villagerprofession);
            M m = this.getEntityModel();
            m.func_217146_a(villagermetadatasection$hattype1 == VillagerMetadataSection.HatType.NONE || villagermetadatasection$hattype1 == VillagerMetadataSection.HatType.PARTIAL);
            ResourceLocation resourcelocation = this.genResourcePath("type", new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));
            renderCutoutModel(m, resourcelocation, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
            m.func_217146_a(true);
            if (villagerprofession != VillagerProfession.NONE && !entity.isChild()) {
                ResourceLocation resourcelocation1 = this.genResourcePath("profession", Registry.VILLAGER_PROFESSION.getKey(villagerprofession));
                renderCutoutModel(m, resourcelocation1, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
                if (villagerprofession != VillagerProfession.NITWIT) {
                    ResourceLocation resourcelocation2 = this.genResourcePath("profession_level", field_215352_a.get(MathHelper.clamp(villagerdata.getLevel(), 1, field_215352_a.size())));
                    renderCutoutModel(m, resourcelocation2, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    private ResourceLocation genResourcePath(String p_215351_1_, ResourceLocation p_215351_2_) {
        return new ResourceLocation(p_215351_2_.getNamespace(), "textures/entity/" + this.field_215356_e + "/" + p_215351_1_ + "/" + p_215351_2_.getPath() + ".png");
    }

}
