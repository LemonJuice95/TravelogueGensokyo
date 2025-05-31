package io.lemonjuice.tvlgensokyo.mixin;

import io.lemonjuice.tvlgensokyo.common.world.WorldGenInfoHolder;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionGeneratorSettings.class)
public class MixinDimensionGeneratorSettings {
    @Shadow @Final private long seed;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        WorldGenInfoHolder.getInstance().setSeed(this.seed);
    }
}
