package io.lemonjuice.tvlgensokyo.common.spell;

import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TestSpell extends Spell{
    public TestSpell() {
        super(10, 60);
    }

    @Override
    public void onActivate(LivingEntity entity, ItemStack book) {
        Danmaku danmaku = new Danmaku(Danmaku.Type.DOT, Danmaku.Color.WHITE, 400, 0);
        EntityDanmaku danmakuEntity = new EntityDanmaku(entity.world, danmaku, IHasGroup.TGGroups.FRIENDLY, entity);
        danmakuEntity.setOwner(entity);
        danmakuEntity.setPosition(entity.getPosX(), entity.getPosYEye(), entity.getPosZ());
        danmakuEntity.setRotationAndMotion(entity.rotationYaw, entity.rotationPitch);
        danmakuEntity.setSpeed(0.2F);
        entity.world.addEntity(danmakuEntity);
    }
}
