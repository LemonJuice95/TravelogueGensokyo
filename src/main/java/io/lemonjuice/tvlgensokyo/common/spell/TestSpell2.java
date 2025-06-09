package io.lemonjuice.tvlgensokyo.common.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TestSpell2 extends Spell{
    public TestSpell2() {
        super(200, 40);
    }

    @Override
    public void onActivate(LivingEntity entity, ItemStack book) {
        Random random = new Random();
        for(int i = 1; i <= 12; i++) {
            TNTEntity tnt = new TNTEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), null);
            tnt.setMotion(random.nextDouble() - 0.5D, 0.5, random.nextDouble() - 0.5D);
            entity.world.addEntity(tnt);
        }
    }
}
