package io.lemonjuice.tvlgensokyo.common.item.weapon;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.BasicSpellBookModel;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.SpellBookModelBase;
import io.lemonjuice.tvlgensokyo.client.renderer.itemstack.SpellBookISTER;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.ISpellInstrument;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.common.item.misc.ItemSpellBookPage;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.common.spell.TGSpellInit;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemSpellBook extends Item implements IRenderPowerHUD, IScrollable, ISpellInstrument {
    public final int slotCount;
    public final float speedMultiply;
    public final float powerMultiply;

    public ItemSpellBook(int slotCount, float speedMultiply, float powerMultiply, boolean hasISTER) {
        super(new Item.Properties().group(TGItemGroups.COMBAT).maxStackSize(1).setISTER(hasISTER ? () -> SpellBookISTER::new : null));
        this.slotCount = slotCount;
        this.speedMultiply = speedMultiply;
        this.powerMultiply = powerMultiply;
    }

    public static void setOpened(ItemStack stack, boolean isOpened) {
        if(stack.getItem() instanceof ItemSpellBook) {
            stack.getOrCreateTag().putBoolean("Opened", isOpened);
        }
    }

    public static void setOpenAmount(ItemStack stack, float openAmount) {
        if(stack.getItem() instanceof ItemSpellBook) {
            stack.getOrCreateTag().putFloat("OpenAmount", openAmount);
        }
    }

    public static void pushFlipAction(ItemStack stack, double direction) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ListNBT actions = new ListNBT();
            if(stack.getOrCreateTag().contains("FlipActions")) {
                actions = stack.getOrCreateTag().getList("FlipActions", Constants.NBT.TAG_INT);
            }
            if(direction != 0.0) {
                actions.add(direction < 0.0 ? IntNBT.valueOf(-1) : IntNBT.valueOf(1));
                stack.getOrCreateTag().put("FlipActions", actions);
            }
        }
    }

    public static void popFlipAction(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ListNBT actions = new ListNBT();
            if(stack.getOrCreateTag().contains("FlipActions")) {
                actions = stack.getOrCreateTag().getList("FlipActions", Constants.NBT.TAG_INT);
            }
            if(!actions.isEmpty()) {
                actions.remove(0);
            }
        }
    }

    public static void clearFlipAction(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ListNBT actions = new ListNBT();
            if(stack.getOrCreateTag().contains("FlipActions")) {
                actions = stack.getOrCreateTag().getList("FlipActions", Constants.NBT.TAG_INT);
            }
            if(!actions.isEmpty()) {
                actions.clear();
            }
        }
    }

    public static int getFirstFlipAction(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ListNBT actions = new ListNBT();
            if(stack.getOrCreateTag().contains("FlipActions")) {
                actions = stack.getOrCreateTag().getList("FlipActions", Constants.NBT.TAG_INT);
            }
            if(!actions.isEmpty()) {
                return actions.getInt(0);
            }
        }
        return 0;
    }

    public static boolean isFlipActionEmpty(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ListNBT actions = new ListNBT();
            if(stack.getOrCreateTag().contains("FlipActions")) {
                actions = stack.getOrCreateTag().getList("FlipActions", Constants.NBT.TAG_INT);
            }
            return actions.isEmpty();
        }
        return true;
    }

    public static float getOpenAmount(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            if(stack.getOrCreateTag().contains("OpenAmount"))
                return stack.getOrCreateTag().getFloat("OpenAmount");
        }
        return 0.0F;
    }

    public static boolean isOpened(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            if(stack.getOrCreateTag().contains("Opened"))
                return stack.getOrCreateTag().getBoolean("Opened");
        }
        return false;
    }

    public static void setLeftPageFlipAmount(ItemStack stack, float amount) {
        if(stack.getItem() instanceof ItemSpellBook) {
            stack.getOrCreateTag().putFloat("LeftPageFlipAmount", amount);
        }
    }

    public static void setRightPageFlipAmount(ItemStack stack, float amount) {
        if(stack.getItem() instanceof ItemSpellBook) {
            stack.getOrCreateTag().putFloat("RightPageFlipAmount", amount);
        }
    }

    public static float getLeftPageFlipAmount(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            if(stack.getOrCreateTag().contains("LeftPageFlipAmount"))
                return stack.getOrCreateTag().getFloat("LeftPageFlipAmount");
        }
        return 0.0F;
    }


    public static float getRightPageFlipAmount(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            if(stack.getOrCreateTag().contains("RightPageFlipAmount"))
                return stack.getOrCreateTag().getFloat("RightPageFlipAmount");
        }
        return 0.0F;
    }

    protected static void tickOpenAmount(ItemStack stack) {
        if(stack.getItem() instanceof ItemSpellBook) {
            float openAmount = getOpenAmount(stack);
            if(isOpened(stack)) {
                if(openAmount < 1.0F) {
                    openAmount = MathHelper.clamp(openAmount + 0.2F, 0.0F, 1.0F);
                }
            } else if(openAmount > 0.0F){
                openAmount = MathHelper.clamp(openAmount - 0.2F, 0.0F, 1.0F);
            }
            setOpenAmount(stack, openAmount);
        }
    }

    protected static void tickPageFlip(ItemStack stack) {
        float leftPageFlipAmount = getLeftPageFlipAmount(stack);
        float rightPageFlipAmount = getRightPageFlipAmount(stack);
        int action = getFirstFlipAction(stack);

        if(leftPageFlipAmount == 1.0F) {
            leftPageFlipAmount = 0.0F;
            action = 0;
            popFlipAction(stack);
        }
        if(rightPageFlipAmount == 1.0F) {
            rightPageFlipAmount = 0.0F;
            action = 0;
            popFlipAction(stack);
        }

        if(leftPageFlipAmount < 1.0F && action == 1) {
            leftPageFlipAmount = MathHelper.clamp(leftPageFlipAmount + 0.2F, 0.0F, 1.0F);
        }


        if(rightPageFlipAmount < 1.0F && action == -1) {
            rightPageFlipAmount = MathHelper.clamp(rightPageFlipAmount + 0.2F, 0.0F, 1.0F);
        }

        setLeftPageFlipAmount(stack, leftPageFlipAmount);
        setRightPageFlipAmount(stack, rightPageFlipAmount);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(!world.isRemote) {
            boolean shouldTick = isSelected;
            if(entity instanceof PlayerEntity) {
                if (!isSelected && ((PlayerEntity) entity).getHeldItemOffhand().equals(stack)) {
                    shouldTick = true;
                }
                if(!isOpened(stack) && !isFlipActionEmpty(stack)) {
                    clearFlipAction(stack);
                    setLeftPageFlipAmount(stack, 0.0F);
                    setRightPageFlipAmount(stack, 0.0F);
                }
                if (shouldTick) {
                    tickOpenAmount(stack);
                    if(getOpenAmount(stack) == 1.0F)
                        tickPageFlip(stack);

                } else {
                    if (isOpened(stack))
                        setOpened(stack, false);
                    if (getOpenAmount(stack) != 0.0F)
                        setOpenAmount(stack, 0.0F);
                    if(!isFlipActionEmpty(stack))
                        clearFlipAction(stack);
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return ItemSpellBookPage.getSpell(this.getCurrentPage(stack)).getPowerCost();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.book_speed_multiply", this.speedMultiply).mergeStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.book_power_multiply", this.powerMultiply).mergeStyle(TextFormatting.AQUA));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.book_pages"));
        SpellBookInventory inventory = this.getInventory(stack);
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            if(!inventory.getStackInSlot(i).isEmpty()) {
                Spell spell = ItemSpellBookPage.getSpell(inventory.getStackInSlot(i));
                tooltip.add(new TranslationTextComponent(spell.getTranslationKey()).mergeStyle(spell.getTextFormattings()));
            } else {
                tooltip.add(new TranslationTextComponent(TGSpellInit.EMPTY.getTranslationKey()).mergeStyle(TextFormatting.GRAY));
            }
        }
        tooltip.add(new StringTextComponent(""));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if(entity instanceof PlayerEntity) {
            Spell spell = ItemSpellBookPage.getSpell(this.getInventory(stack).getCurrentPage());
            int powerCost = ((PlayerEntity) entity).isCreative() ? 0 : TGMathUtils.calculatePowerCost((int)(spell.getPowerCost() * this.powerMultiply), (PlayerEntity) entity, stack);
            if(TGCapabilityUtils.getPower((PlayerEntity) entity) >= powerCost) {
                TravelogueGensokyo.PROXY.setChantingProgress(0.0F);
                TGCapabilityUtils.addPower((PlayerEntity) entity, -powerCost);
                spell.onActivate((PlayerEntity) entity, stack);
                return stack;
            }
        }
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack page = this.getCurrentPage(stack);
        if(player.isCreative() || TGCapabilityUtils.getPower(player) >= TGMathUtils.calculatePowerCost((int)(ItemSpellBookPage.getSpell(page).getPowerCost() * this.powerMultiply), player, stack)) {
            player.setActiveHand(hand);
        }
        return ActionResult.resultFail(stack);
    }

    @Override
    public void onUse(World world, LivingEntity entity, ItemStack stack, int count) {
        if(entity instanceof PlayerEntity) {
            if(world.isRemote) {
                Random rand = new Random();
                world.addParticle(ParticleTypes.ENCHANT, entity.getPosX() + rand.nextDouble() - 0.5D, entity.getPosY() + 0.5D + rand.nextDouble(), entity.getPosZ() + rand.nextDouble() - 0.5D, rand.nextDouble() / 5.0D, rand.nextDouble() / 5.0D, rand.nextDouble() / 5.0D);
                TravelogueGensokyo.PROXY.setChantingProgress(1.0F - 1.0F * count / this.getUseDuration(stack));
            } else {
                if(!isOpened(stack))
                    setOpened(stack, true);
            }
        }
        super.onUse(world, entity, stack, count);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        if(!world.isRemote) {
            TravelogueGensokyo.PROXY.setChantingProgress(0.0F);
            setOpened(stack, false);
        }
    }

    public SpellBookInventory getInventory(ItemStack stack) {
        return new SpellBookInventory(stack, this.slotCount);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return (int)(ItemSpellBookPage.getSpell(this.getCurrentPage(stack)).getChantTime() * 1.0 / this.speedMultiply);
    }

    @Override
    public void specialScroll(ItemStack stack, double direction) {
        if(stack.getItem() instanceof ItemSpellBook) {
            SpellBookInventory inventory = ((ItemSpellBook) stack.getItem()).getInventory(stack);
            if (direction > 0.0D) {
                direction = 1.0D;
            } else if (direction < 0.0D) {
                direction = -1.0D;
            }

            int targetPage = inventory.currentPage;
            do {
                targetPage += (int) direction;
                if(targetPage >= inventory.getSizeInventory()) {
                    targetPage = 0;
                }
                if(targetPage < 0) {
                    targetPage = inventory.getSizeInventory();
                }
            } while(targetPage != inventory.currentPage && inventory.getStackInSlot(targetPage).isEmpty());
            if(targetPage != inventory.currentPage) {
                pushFlipAction(stack, -direction);
            }
            setCurrentPage(stack, targetPage);
        }

    }

    public ItemStack getCurrentPage(ItemStack stack) {
        return this.getInventory(stack).getCurrentPage();
    }

    public static void setCurrentPage(ItemStack stack, int index) {
        if(stack.getItem() instanceof ItemSpellBook) {
            ((ItemSpellBook) stack.getItem()).getInventory(stack).setCurrentPage(index);
        }
    }

    public class SpellBookInventory extends Inventory {
        public static final String NAME = "Pages";
        private final ItemStack spellBook;
        public int currentPage = 0;

        public SpellBookInventory(ItemStack spellBook, int slotCount) {
            super(slotCount);
            this.spellBook = spellBook;

            ListNBT nbt = new ListNBT();
            if(!spellBook.isEmpty() && spellBook.getItem() instanceof ItemSpellBook && spellBook.getOrCreateTag().contains(NAME)) {
                nbt = spellBook.getOrCreateTag().getList(NAME, Constants.NBT.TAG_COMPOUND);
                this.currentPage = spellBook.getOrCreateTag().getInt("CurrentPage");
            }
            if(!nbt.isEmpty()) {
                for(int i = 0; i < nbt.size(); i++) {
                    this.setInventorySlotContents(i, ItemStack.read(nbt.getCompound(i)));
                }
            }
        }

        @Override
        public int getInventoryStackLimit() {
            return 1;
        }

        public void setCurrentPage(int index) {
            if(index >= 0 && index < this.getSizeInventory()) {
                this.currentPage = index;
                this.markDirty();
            }
        }

        public ItemStack getCurrentPage() {
            return this.getStackInSlot(this.currentPage);
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return stack.getItem() instanceof ItemSpellBookPage;
        }

        @Override
        public boolean isUsableByPlayer(PlayerEntity player) {
            return !this.spellBook.isEmpty() && this.spellBook.getItem() instanceof ItemSpellBook;
        }

        @Override
        public void markDirty() {
            ListNBT nbt = new ListNBT();
            for(int i = 0; i < this.getSizeInventory(); i++) {
                nbt.add(this.getStackInSlot(i).write(new CompoundNBT()));
            }
            this.spellBook.getOrCreateTag().put(NAME, nbt);
            this.spellBook.getOrCreateTag().putInt("CurrentPage", this.currentPage);
            super.markDirty();
        }
    }
}
