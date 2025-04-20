package io.lemonjuice.tvlgensokyo.common.misc;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityAmulet;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityGungnir;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class TGProjectileDamageSource extends IndirectEntityDamageSource {
    public TGProjectileDamageSource(String name, Entity source, Entity indirectEntity) {
        super(name, source, indirectEntity);
    }

    public static DamageSource causeAmuletDamage(EntityAmulet entity) {
        return new TGProjectileDamageSource("amulet", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }

    public static DamageSource causeGungnirDamage(EntityGungnir entity) {
        return new TGProjectileDamageSource("gungnir", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }

    public static DamageSource causeDanmakuDamage(EntityDanmaku entity) {
        return new TGProjectileDamageSource("danmaku", entity.getOwner(), entity).setDamageBypassesArmor().setProjectile();
    }
}
