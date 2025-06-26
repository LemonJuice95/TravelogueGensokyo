package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
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
    public void applyAction(DanmakuEntity danmaku) {
        World world = danmaku.world;
        if(!world.isRemote) {
            for(Danmaku i : this.danmakus) {
                DanmakuEntity newDanmaku = new DanmakuEntity(world, i, danmaku.getGroup(), danmaku.getOwner());
                world.addEntity(newDanmaku);
            }
        }
    }

    public static class Serializer extends Action.Serializer<ActionGenerateDanmaku> {
        @Override
        public ActionGenerateDanmaku read(CompoundNBT nbt) {
            List<Danmaku> danmakus = new ArrayList<>();
            ListNBT danmakuNBT = nbt.getList("Danmakus", 10);
            danmakuNBT.forEach((d) -> {
                danmakus.add(new Danmaku((CompoundNBT) d));
            });
            return new ActionGenerateDanmaku(danmakus);
        }

        @Override
        public CompoundNBT write(ActionGenerateDanmaku action) {
            CompoundNBT nbt = new CompoundNBT();
            ListNBT listNBT = new ListNBT();
            action.danmakus.forEach((d) -> {
                listNBT.add(d.toCompoundNBT());
            });
            nbt.putString("ActionType", action.getName());
            nbt.put("Danmakus", listNBT);
            return nbt;
        }
    }
}
