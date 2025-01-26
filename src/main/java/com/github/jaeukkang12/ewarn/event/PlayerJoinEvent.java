package com.github.jaeukkang12.ewarn.event;

import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void playerJoin(Player event) {
        Player player = event.getPlayer();
        WarnManager warnManager = WarnAPI.get();
        if (!player.hasPlayedBefore()) {
            warnManager.registerPlayer(player);
        } else {
            warnManager.nickIsChanged(player);
        }
    }
}
