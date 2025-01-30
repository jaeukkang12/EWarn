package com.github.jaeukkang12.ewarn.warn;

import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;

public final class WarnAPI {

    private static WarnManager warnManager = null;

    private WarnAPI() {
        throw new UnsupportedOperationException("이 클래스는 인스턴스화 할 수 없습니다!");
    }

    @ApiStatus.Internal
    static void register(WarnManager warnManager) {
        WarnAPI.warnManager = warnManager;
    }

    @ApiStatus.Internal
    static void unregister() {
        warnManager = null;
    }


    public static @NonNull WarnManager get() {
        WarnManager warnManager = WarnAPI.warnManager;
        if (warnManager == null) {
            throw new NullPointerException("E-Warn 플러그인이 아직 로드되지 않았습니다!");
        }
        return warnManager;
    }
}
