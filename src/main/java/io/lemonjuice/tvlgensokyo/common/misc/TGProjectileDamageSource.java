package io.lemonjuice.tvlgensokyo.common.misc;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.AmuletEntity;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.GungnirEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class TGProjectileDamageSource extends IndirectEntityDamageSource {
    public TGProjectileDamageSource(String name, Entity source, Entity indirectEntity) {
        super(name, source, indirectEntity);
    }

    public static DamageSource causeAmuletDamage(AmuletEntity entity) {
        return new TGProjectileDamageSource("amulet", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }

    public static DamageSource causeGungnirDamage(GungnirEntity entity) {
        return new TGProjectileDamageSource("gungnir", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }

    public static DamageSource causeDanmakuDamage(DanmakuEntity entity) {
        return new TGProjectileDamageSource("danmaku", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }
}
