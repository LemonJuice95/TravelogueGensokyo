package io.lemonjuice.tvlgensokyo.common.entity.interfaces;

public interface IHasGroup {
    TGGroups getGroup();

    enum TGGroups {
        FRIENDLY,
        NEUTRAL,
        HOSTILE
    }
}
