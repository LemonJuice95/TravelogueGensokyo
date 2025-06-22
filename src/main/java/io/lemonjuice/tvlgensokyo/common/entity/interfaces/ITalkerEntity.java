package io.lemonjuice.tvlgensokyo.common.entity.interfaces;

import net.minecraft.entity.player.PlayerEntity;

public interface ITalkerEntity {
    void setTalkingPlayer(PlayerEntity player);
    PlayerEntity getTalkingPlayer();
}
