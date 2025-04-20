package io.lemonjuice.tvlgensokyo.mixin;

import net.minecraft.client.util.Splashes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.LocalDate;
import java.time.Month;

@Mixin(Splashes.class)
public class MixinSplashText {
    @Inject(method = "getSplashText", at = @At("HEAD"), cancellable = true)
    public void getSplashText(CallbackInfoReturnable<String> cir) {
        LocalDate date = LocalDate.now();
        if(date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            cir.setReturnValue("Happy Flandre's Day!");
        }
    }
}