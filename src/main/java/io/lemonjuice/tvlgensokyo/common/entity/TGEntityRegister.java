package io.lemonjuice.tvlgensokyo.common.entity;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.entity.creature.EntityKappa;
import io.lemonjuice.tvlgensokyo.common.entity.npc.EntityGensokyoVillager;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityAmulet;
import io.lemonjuice.tvlgensokyo.common.entity.misc.EntityWind;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityGungnir;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGEntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TravelogueGensokyo.MODID);

    //Projectiles & Misc
    public static final RegistryObject<EntityType<EntityWind>> WIND = ENTITIES.register("wind", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityWind>) EntityWind::new, EntityClassification.MISC).size(3.0F, 0.5F).immuneToFire().build("wind");
    });
    public static final RegistryObject<EntityType<EntityAmulet>> AMULET = ENTITIES.register("amulet", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityAmulet>) EntityAmulet::new ,EntityClassification.MISC).size(0.3F, 0.3F).build("amulet");
    });
    public static final RegistryObject<EntityType<EntityGungnir>> GUNGNIR = ENTITIES.register("gungnir", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityGungnir>) EntityGungnir::new, EntityClassification.MISC).size(0.5F, 0.5F).build("gungnir");
    });


    //Danmaku
    public static final RegistryObject<EntityType<EntityDanmaku>> DANMAKU_SPOT = ENTITIES.register("danmaku_spot", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityDanmaku>) EntityDanmaku::new, EntityClassification.MISC).size(0.2F, 0.2F).build("danmaku_spot");
    });
    public static final RegistryObject<EntityType<EntityDanmaku>> DANMAKU_SMALL_JADE = ENTITIES.register("danmaku_small_jade", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityDanmaku>) EntityDanmaku::new, EntityClassification.MISC).size(0.25F, 0.25F).build("danmaku_small_jade");
    });

    //Creatures
    public static final RegistryObject<EntityType<EntityKappa>> KAPPA = ENTITIES.register("kappa", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityKappa>) EntityKappa::new, EntityClassification.CREATURE).size(0.65F, 1.75F).build("kappa");
    });

    //npc
    public static final RegistryObject<EntityType<EntityGensokyoVillager>> GENSOKYO_VILLAGER = ENTITIES.register("gensokyo_villager", () -> {
        return EntityType.Builder.create((EntityType.IFactory<EntityGensokyoVillager>) EntityGensokyoVillager::new, EntityClassification.CREATURE).size(0.6F, 1.95F).trackingRange(10).build("gensokyo_villager");
    });

}
