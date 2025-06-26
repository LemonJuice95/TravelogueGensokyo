package io.lemonjuice.tvlgensokyo.common.danmaku;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DanmakuActions {
    public static final Map<String, Supplier<? extends Action.Serializer<? extends Action>>> ACTION_SERIALIZERS = new HashMap<>();

    static {
        register("change_type", ActionChangeDanmakuType.Serializer::new);
        register("shoot_entity", ActionAimEntity.Serializer::new);
        register("generate_danmakus", ActionGenerateDanmaku.Serializer::new);
        register("remove", ActionRemove.Serializer::new);
        register("rotate", ActionRotate.Serializer::new);

    }

    public static void register(String name, Supplier<Action.Serializer<? extends Action>> serializer) {
        ACTION_SERIALIZERS.put(name, serializer);
    }
}
