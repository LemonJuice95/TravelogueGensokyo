package io.lemonjuice.tvlgensokyo.utils;

import com.google.common.collect.ImmutableMap;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.DanmakuItem;
import net.minecraft.entity.EntityType;

import java.util.Map;

public class TGDanmakuUtils {
    public static final Map<Danmaku.Type, EntityType<? extends EntityDanmaku>> DANMAKU_ENTITY_MAP = ImmutableMap.of(
            Danmaku.Type.DOT, TGEntityRegister.DANMAKU_SPOT.get(),
            Danmaku.Type.SMALL_JADE, TGEntityRegister.DANMAKU_SMALL_JADE.get()
    );

    public static final Map<Danmaku.Type, DanmakuItem> DANMAKU_ITEM_MAP = ImmutableMap.of(
            Danmaku.Type.DOT, TGItemRegister.DANMAKU_DOT.get(),
            Danmaku.Type.SMALL_JADE, TGItemRegister.DANMAKU_SMALL_JADE.get()
    );
}
