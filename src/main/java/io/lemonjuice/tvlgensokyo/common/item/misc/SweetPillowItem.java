package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.utils.TGBlockUtils;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SweetPillowItem extends Item {
    public SweetPillowItem() {
        super(new Item.Properties().group(TGItemGroups.MISC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        BlockRayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.NONE);

        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK && world.getBlockState(rayTraceResult.getPos()).getBlock().getClass().equals(BedBlock.class)) {
            if(!world.isRemote) {

                BlockPos pos = rayTraceResult.getPos();

                BlockState state = world.getBlockState(rayTraceResult.getPos());
                Block block = state.getBlock();

                BlockState newState = TGBlockUtils.SWEET_BED_MAP.get(block).getDefaultState();
                newState = newState.with(BedBlock.HORIZONTAL_FACING, state.get(BedBlock.HORIZONTAL_FACING));
                newState = newState.with(BedBlock.PART, state.get(BedBlock.PART));
                newState = newState.with(BedBlock.OCCUPIED, state.get(BedBlock.OCCUPIED));

                BlockPos pos1;
                if(state.get(BedBlock.PART) == BedPart.HEAD) {
                    pos1 = pos.offset(state.get(BedBlock.HORIZONTAL_FACING).getOpposite());
                } else {
                    pos1 = pos.offset(state.get(BedBlock.HORIZONTAL_FACING).rotateY().rotateY().getOpposite());
                }

                world.removeTileEntity(pos);
                world.setBlockState(pos, newState, 2, 0);

                if(world.getBlockState(pos1).matchesBlock(block)) {
                    BlockState state1 = world.getBlockState(pos1);
                    BlockState newState1 = TGBlockUtils.SWEET_BED_MAP.get(block).getDefaultState();
                    newState1 = newState1.with(BedBlock.HORIZONTAL_FACING, state1.get(BedBlock.HORIZONTAL_FACING));
                    newState1 = newState1.with(BedBlock.PART, state1.get(BedBlock.PART));
                    newState1 = newState1.with(BedBlock.OCCUPIED, state1.get(BedBlock.OCCUPIED));

                    world.removeTileEntity(pos1);
                    world.setBlockState(pos1, newState1);
                }

                if(!player.isCreative()) {
                    stack.shrink(1);
                }

                return ActionResult.resultConsume(stack);
            }
            return ActionResult.resultSuccess(stack);
        }

        return ActionResult.resultPass(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.sweet_pillow").mergeStyle(TextFormatting.DARK_PURPLE).mergeStyle(TextFormatting.ITALIC));
    }
}
