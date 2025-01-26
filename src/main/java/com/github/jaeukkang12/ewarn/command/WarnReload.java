package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import org.bukkit.command.CommandSender;


public class WarnReload {
    @Command(parent = "경고", sub = "리로드", permission = "ewarn.reload")
    public void warnReload(CommandSender sender, String[] args) {
        WarnManager warnManager = WarnAPI.get();
        warnManager.reload();

        sender.sendMessage("");
    }
}
