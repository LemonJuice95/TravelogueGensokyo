package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionGenerateDanmaku extends Action{
    public final List<Danmaku> danmakus = new ArrayList<>();

    public ActionGenerateDanmaku(Danmaku... danmakus) {
        super("generate_danmakus");

        this.danmakus.addAll(Arrays.asList(danmakus));
    }

    private ActionGenerateDanmaku(List<Danmaku> danmakus) {
        super("generate_danmakus");

        this.danmakus.addAll(danmakus);
    }

    @Override
    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT listNBT = new ListNBT();
        this.danmakus.forEach((d) -> {
            listNBT.add(d.toCompoundNBT());
        });
        nbt.putString("ActionType", this.getName());
        nbt.put("Danmakus", listNBT);
        return nbt;
    }

    public static ActionGenerateDanmaku fromCompoundNBT(CompoundNBT nbt) {
        List<Danmaku> danmakus = new ArrayList<>();
        ListNBT danmakuNBT = nbt.getList("Danmakus", 10);
        danmakuNBT.forEach((d) -> {
            danmakus.add(new Danmaku((CompoundNBT) d));
        });
        return new ActionGenerateDanmaku(danmakus);
    }

    @Override
    public void applyAction(EntityDanmaku danmaku) {
        World world = danmaku.world;
        if(!world.isRemote) {
            for(Danmaku i : this.danmakus) {
                EntityDanmaku newDanmaku = new EntityDanmaku(world, i, danmaku.getGroup(), danmaku.getOwner());
                world.addEntity(newDanmaku);
            }
        }
    }
}
