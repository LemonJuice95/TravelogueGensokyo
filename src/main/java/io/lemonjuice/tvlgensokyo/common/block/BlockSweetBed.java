package io.lemonjuice.tvlgensokyo.common.block;

import io.lemonjuice.tvlgensokyo.common.block.tileentity.SweetBedTileEntity;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class BlockSweetBed extends BedBlock {
    BedBlock bed;
    DyeColor color;

    public BlockSweetBed(BedBlock bed, DyeColor color) {
        super(color, AbstractBlock.Properties.create(Material.WOOL, (state) ->
                        state.get(PART) == BedPart.FOOT ? color.getMapColor() : MaterialColor.WOOL)
                .sound(SoundType.WOOD).hardnessAndResistance(0.2F).notSolid());
        this.color = color;
        this.bed = bed;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(world.isRemote) {
            return ActionResultType.CONSUME;
        } else {
            if (state.get(PART) != BedPart.HEAD) {
                pos = pos.offset(state.get(HORIZONTAL_FACING));
                state = world.getBlockState(pos);
                if (!state.matchesBlock(this)) {
                    return ActionResultType.CONSUME;
                }
            }

            if (!doesBedWork(world)) {
                world.removeBlock(pos, false);
                BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
                if (world.getBlockState(blockpos).matchesBlock(this)) {
                    world.removeBlock(blockpos, false);
                }

                world.createExplosion((Entity) null, DamageSource.causeBedExplosionDamage(), (ExplosionContext) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                return ActionResultType.SUCCESS;
            } else if (state.get(OCCUPIED)) {
                if (!this.tryWakeUpVillager(world, pos)) {
                    player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
                }

                return ActionResultType.SUCCESS;
            } else {
                //set player's spawn point
                if(player instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) player).func_242111_a(player.world.getDimensionKey(), pos, player.rotationYaw, false, true);
                }

                if(world instanceof ServerWorld && player.canChangeDimension() && player.world.getDimensionKey() != TGDimensionRegister.DREAM_WORLD) {
                    ServerWorld dimension = player.getServer().getWorld(TGDimensionRegister.DREAM_WORLD);
                    if(dimension != null) {
                        player.changeDimension(dimension, new ITeleporter() {
                            @Override
                            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                                entity = repositionEntity.apply(false);
                                entity.teleportKeepLoaded(0, 66, 0);
                                return entity;
                            }
                        });
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
    }

    private boolean tryWakeUpVillager(World world, BlockPos pos) {
        List<VillagerEntity> list = world.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).wakeUp();
            return true;
        }
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return null;
    }

    public BedBlock getBed() {
        return this.bed;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SweetBedTileEntity(this.bed, this.color);
    }
}
