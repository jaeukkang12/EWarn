package com.github.jaeukkang12.ewarn.event;

import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void playerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WarnManager warnManager = WarnAPI.get();
        try {
            warnManager.getUUID(player.getName());
            warnManager.isChanged(player);
        } catch (Exception e) {
            warnManager.registerUser(player);
        }
    }
}
