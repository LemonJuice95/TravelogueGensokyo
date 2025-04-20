package io.lemonjuice.tvlgensokyo.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class TGCapabilityRegister {
    public static void capabilitiesRegistry() {
        CapabilityManager.INSTANCE.register(
                IPlayerDataCapability.class,
                new Capability.IStorage<IPlayerDataCapability>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IPlayerDataCapability> capability, IPlayerDataCapability instance, Direction side) {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IPlayerDataCapability> capability, IPlayerDataCapability instance, Direction side, INBT nbt) {
                        if(nbt instanceof CompoundNBT)
                            instance.deserializeNBT((CompoundNBT) nbt);
                    }
                },
                PlayerDataCapability::new
        );

    }
}
