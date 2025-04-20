package io.lemonjuice.tvlgensokyo.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TGCapabilityList {
    @CapabilityInject(PlayerDataCapability.class)
    public static Capability<IPlayerDataCapability> PLAYER_DATA;
}
