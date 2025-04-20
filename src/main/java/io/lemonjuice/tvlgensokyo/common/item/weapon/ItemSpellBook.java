package io.lemonjuice.tvlgensokyo.common.item.weapon;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.ISpellInstrument;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.common.item.misc.ItemSpellBookPage;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.common.spell.TGSpellInit;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemSpellBook extends Item implements IRenderPowerHUD, IScrollable, ISpellInstrument {
    public final int slotCount;
    public final float speedMultiply;
    public final float powerMultiply;

    public ItemSpellBook(int slotCount, float speedMultiply, float powerMultiply) {
        super(new Item.Properties().group(TGItemGroups.COMBAT).maxStackSize(1));
        this.slotCount = slotCount;
        this.speedMultiply = speedMultiply;
        this.powerMultiply = powerMultiply;
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
            return ActionResult.resultConsume(stack);
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
            }
        }
        super.onUse(world, entity, stack, count);
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
            super.markDirty();
            ListNBT nbt = new ListNBT();
            for(int i = 0; i < this.getSizeInventory(); i++) {
                nbt.add(this.getStackInSlot(i).write(new CompoundNBT()));
            }
            this.spellBook.getOrCreateTag().put(NAME, nbt);
            this.spellBook.getOrCreateTag().putInt("CurrentPage", this.currentPage);
        }
    }
}
