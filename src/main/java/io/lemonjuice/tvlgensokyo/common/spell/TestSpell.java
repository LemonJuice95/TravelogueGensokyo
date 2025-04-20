package io.lemonjuice.tvlgensokyo.common.spell;

import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TestSpell extends Spell{
    public TestSpell() {
        super(10, 60);
    }

    @Override
    public void onActivate(PlayerEntity player, ItemStack book) {
        Danmaku danmaku = new Danmaku(Danmaku.Type.DOT, Danmaku.Color.WHITE, 400, 0);
        EntityDanmaku danmakuEntity = new EntityDanmaku(player.world, danmaku, IHasGroup.TGGroups.FRIENDLY, player);
        danmakuEntity.setPosition(player.getPosX(), player.getPosYEye(), player.getPosZ());
        danmakuEntity.setRotationAndMotion(player.rotationYaw, player.rotationPitch);
        danmakuEntity.setSpeed(0.2F);
        player.world.addEntity(danmakuEntity);
    }
}
