package com.github.jaeukkang12.ewarn.warn;

import org.bukkit.entity.Player;

public interface Warn {
    void add(String name, int amount);
    void add(Player player, int amount);

    void remove(String name, int amount);
    void remove(Player player, int amount);

    void set(String name, int amount);
    void set(Player player, int amount);

    int get(String name);
    int get(Player player);
}
