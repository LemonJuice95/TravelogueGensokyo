package io.lemonjuice.tvlgensokyo.client.renderer.block;

import io.lemonjuice.tvlgensokyo.client.renderer.tileentity.SweetBedTileEntityRenderer;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.TGTileEntityRegister;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TGBlockRenderHandler {
    public static void registerBlockRenderType() {
        RenderTypeLookup.setRenderLayer(TGBlockRegister.DREAM_BASE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(TGBlockRegister.CUCUMBER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TGBlockRegister.MAPLE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TGBlockRegister.FALLEN_MAPLE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TGBlockRegister.MAPLE_SAPLING.get(), RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(TGBlockRegister.SPELL_WRITING_TABLE.get(), RenderType.getTranslucent());
    }


    public static void registerTileEntityRenderer() {
        ClientRegistry.bindTileEntityRenderer(TGTileEntityRegister.SWEET_BED.get(), SweetBedTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TGTileEntityRegister.TG_SIGN.get(), SignTileEntityRenderer::new);
    }
}
