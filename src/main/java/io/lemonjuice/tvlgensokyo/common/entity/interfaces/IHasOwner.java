package io.lemonjuice.tvlgensokyo.common.entity.interfaces;

import net.minecraft.entity.Entity;

import java.util.Optional;
import java.util.UUID;

public interface IHasOwner {
    Optional<UUID> getOwnerUUID();
    Entity getOwner();
}
