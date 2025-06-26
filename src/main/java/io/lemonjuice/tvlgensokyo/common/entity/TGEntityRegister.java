package io.lemonjuice.tvlgensokyo.common.entity;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.entity.creature.KappaEntity;
import io.lemonjuice.tvlgensokyo.common.entity.npc.GensokyoVillagerEntity;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.AmuletEntity;
import io.lemonjuice.tvlgensokyo.common.entity.misc.WindEntity;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.GungnirEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGEntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TravelogueGensokyo.MODID);

    //Projectiles & Misc
    public static final RegistryObject<EntityType<WindEntity>> WIND = ENTITIES.register("wind", () -> {
        return EntityType.Builder.create((EntityType.IFactory<WindEntity>) WindEntity::new, EntityClassification.MISC).size(3.0F, 0.5F).immuneToFire().build("wind");
    });
    public static final RegistryObject<EntityType<AmuletEntity>> AMULET = ENTITIES.register("amulet", () -> {
        return EntityType.Builder.create((EntityType.IFactory<AmuletEntity>) AmuletEntity::new ,EntityClassification.MISC).size(0.3F, 0.3F).build("amulet");
    });
    public static final RegistryObject<EntityType<GungnirEntity>> GUNGNIR = ENTITIES.register("gungnir", () -> {
        return EntityType.Builder.create((EntityType.IFactory<GungnirEntity>) GungnirEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("gungnir");
    });


    //Danmaku
    public static final RegistryObject<EntityType<DanmakuEntity>> DANMAKU_DOT = ENTITIES.register("danmaku_dot", () -> {
        return EntityType.Builder.create((EntityType.IFactory<DanmakuEntity>) DanmakuEntity::new, EntityClassification.MISC).size(0.2F, 0.2F).build("danmaku_dot");
    });
    public static final RegistryObject<EntityType<DanmakuEntity>> DANMAKU_SMALL_JADE = ENTITIES.register("danmaku_small_jade", () -> {
        return EntityType.Builder.create((EntityType.IFactory<DanmakuEntity>) DanmakuEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("danmaku_small_jade");
    });

    //Creatures
    public static final RegistryObject<EntityType<KappaEntity>> KAPPA = ENTITIES.register("kappa", () -> {
        return EntityType.Builder.create((EntityType.IFactory<KappaEntity>) KappaEntity::new, EntityClassification.CREATURE).size(0.65F, 1.75F).build("kappa");
    });

    //npc
    public static final RegistryObject<EntityType<GensokyoVillagerEntity>> GENSOKYO_VILLAGER = ENTITIES.register("gensokyo_villager", () -> {
        return EntityType.Builder.create((EntityType.IFactory<GensokyoVillagerEntity>) GensokyoVillagerEntity::new, EntityClassification.CREATURE).size(0.6F, 1.95F).trackingRange(10).build("gensokyo_villager");
    });

}
