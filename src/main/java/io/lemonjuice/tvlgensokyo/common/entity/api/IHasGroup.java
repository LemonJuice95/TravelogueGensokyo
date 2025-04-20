package io.lemonjuice.tvlgensokyo.common.entity.api;

public interface IHasGroup {
    TGGroups getGroup();

    enum TGGroups {
        FRIENDLY,
        NEUTRAL,
        HOSTILE
    }
}
