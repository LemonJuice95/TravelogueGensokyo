package io.lemonjuice.tvlgensokyo.common.danmaku;

import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DanmakuActions {
    public static final Map<String, Function<CompoundNBT, ? extends Action>> ACTION_FROM_NBT = new HashMap<>();

    static {
        register("change_type", ActionChangeDanmakuType::fromCompoundNBT);
        register("generate_danmakus", ActionGenerateDanmaku::fromCompoundNBT);
        register("remove", ActionRemove::fromCompoundNBT);
        register("rotate", ActionRotate::fromCompoundNBT);
        register("shoot_entity", ActionAimEntity::fromCompoundNBT);
    }

    public static void register(String name, Function<CompoundNBT, ? extends Action> func) {
        ACTION_FROM_NBT.put(name, func);
    }
}
