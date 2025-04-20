package io.lemonjuice.tvlgensokyo.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private IPlayerDataCapability capability;
    
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == TGCapabilityList.PLAYER_DATA ? LazyOptional.of(() -> {
            return this.getOrCreateCapability();
        }).cast() : LazyOptional.empty();
    }
    
    @Nonnull
    private IPlayerDataCapability getOrCreateCapability() {
        if(this.capability == null) {
            this.capability = new PlayerDataCapability();
        }
        return this.capability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return this.getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getOrCreateCapability().deserializeNBT(nbt);
    }
}
