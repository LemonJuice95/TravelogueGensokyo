package io.lemonjuice.tvlgensokyo.common.block.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TGSignTileEntity extends SignTileEntity {
    @Override
    public TileEntityType<?> getType() {
        return TGTileEntityRegister.TG_SIGN.get();
    }
}
