package io.lemonjuice.tvlgensokyo.common.entity.api;

import net.minecraft.entity.player.PlayerEntity;

public interface ITalkerEntity {
    void setTalkingPlayer(PlayerEntity player);
    PlayerEntity getTalkingPlayer();
}
