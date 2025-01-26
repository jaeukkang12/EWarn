package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.jaeukkang12.ewarn.messages.Messages.CHECK;

// TODO: 플레이어를 찾을 수 없을 때 예외 처리하기.
public class WarnCheck {

    @Command(parent = "경고", sub = "확인", permission = "ewarn.check")
    public void checkWarn(CommandSender sender, String[] args) {
        WarnManager warnManager = WarnAPI.get();

        String name = (args.length == 1) ? sender.getName() : args[1];

        sender.sendMessage(CHECK
                .replace("{target}", name)
                .replace("{amount}", String.valueOf(warnManager.get(name))));
    }

    @Command(parent = "경고", sub = "", permission = "ewarn.check")
    public void checkWarnNoneArg(CommandSender sender, String[] args) {
        ((Player)sender).performCommand("경고 확인");
    }

}
