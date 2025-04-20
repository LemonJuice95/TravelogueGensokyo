package io.lemonjuice.tvlgensokyo.common.spell;

import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TestSpell2 extends Spell{
    public TestSpell2() {
        super(200, 40);
    }

    @Override
    public void onActivate(PlayerEntity player, ItemStack book) {
        Random random = new Random();
        for(int i = 1; i <= 12; i++) {
            TNTEntity tnt = new TNTEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), null);
            tnt.setMotion(random.nextDouble() - 0.5D, 0.5, random.nextDouble() - 0.5D);
            player.world.addEntity(tnt);
        }
    }
}
