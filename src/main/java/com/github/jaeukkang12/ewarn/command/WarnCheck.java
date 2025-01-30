package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.jaeukkang12.ewarn.messages.Messages.CANNOT_FIND_PLAYER;
import static com.github.jaeukkang12.ewarn.messages.Messages.CHECK;

public class WarnCheck {

    @Command(parent = "경고", sub = "확인", permission = "ewarn.check")
    public void checkWarn(CommandSender sender, String[] args) {
        WarnManager warnManager = WarnAPI.get();

        String name = (args.length == 1) ? sender.getName() : args[1];
        try {
            sender.sendMessage(CHECK
                    .replace("{target}", name)
                    .replace("{amount}", String.valueOf(warnManager.get(name))));
        } catch (CannotFindPlayer e) {
            sender.sendMessage(CANNOT_FIND_PLAYER);
        }
    }

    @Command(parent = "경고", sub = "", permission = "ewarn.check")
    public void checkWarnNoneArg(CommandSender sender, String[] args) {
        ((Player)sender).performCommand("경고 확인");
    }

}
