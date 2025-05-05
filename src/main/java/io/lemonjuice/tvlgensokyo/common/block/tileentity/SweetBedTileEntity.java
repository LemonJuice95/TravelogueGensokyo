package io.lemonjuice.tvlgensokyo.common.block.tileentity;

import io.lemonjuice.tvlgensokyo.common.block.SweetBedBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.item.DyeColor;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SweetBedTileEntity extends TileEntity {
    private DyeColor color;
    private BedBlock bed;

    public SweetBedTileEntity() {
        super(TGTileEntityRegister.SWEET_BED.get());
    }

    public SweetBedTileEntity(BedBlock bed, DyeColor color) {
        this();
        this.bed = bed;
        this.setColor(color);
    }

    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 11, this.getUpdateTag());
    }

    public BedBlock getBed() {
        return this.bed;
    }

    @OnlyIn(Dist.CLIENT)
    public DyeColor getColor() {
        if (this.color == null) {
            this.color = ((SweetBedBlock)this.getBlockState().getBlock()).getColor();
        }

        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }
}
