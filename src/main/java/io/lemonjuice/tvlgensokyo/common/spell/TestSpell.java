package io.lemonjuice.tvlgensokyo.common.spell;

import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.interfaces.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TestSpell extends Spell{
    public TestSpell() {
        super(10, 60);
    }

    @Override
    public void onActivate(LivingEntity entity, ItemStack book) {
        Danmaku danmaku = new Danmaku(Danmaku.Type.DOT, Danmaku.Color.WHITE, 400, 0);
        DanmakuEntity danmakuEntity = new DanmakuEntity(entity.world, danmaku, IHasGroup.TGGroups.FRIENDLY, entity);
        danmakuEntity.setOwner(entity);
        danmakuEntity.setPosition(entity.getPosX(), entity.getPosYEye(), entity.getPosZ());
        danmakuEntity.setRotationAndMotion(entity.rotationYaw, entity.rotationPitch);
        danmakuEntity.setSpeed(0.2F);
        entity.world.addEntity(danmakuEntity);
    }
}
