package io.lemonjuice.tvlgensokyo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RabbitEntity.class)
public class MixinRabbitEntity {
    @Inject(method = "func_223321_c", at = @At("HEAD"), cancellable = true)
    private static void func_223321_c(EntityType<RabbitEntity> entityType, IWorld world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> ci) {
        BlockState blockstate = world.getBlockState(pos.down());
        if(blockstate.matchesBlock(Blocks.PODZOL))
            ci.setReturnValue(true);
    }
}
