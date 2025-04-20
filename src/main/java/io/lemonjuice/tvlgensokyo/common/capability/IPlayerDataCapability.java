package io.lemonjuice.tvlgensokyo.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerDataCapability extends INBTSerializable<CompoundNBT> {
    public PlayerDataManager getManager();
}
