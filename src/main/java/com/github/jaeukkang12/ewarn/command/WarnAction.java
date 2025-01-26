package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import com.github.jaeukkang12.ewarn.warn.enums.Action;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

public class WarnAction {
    @Command(parent = "경고", sub = "액션", permission = "ewarn.action")
    public void warnHelp(CommandSender sender, String[] args) {
        // IF ACTION IS NOT SET
        if (args.length == 1) {
            sender.sendMessage(NOT_EXIST_ACTION);
            return;
        }

        // IF ACTION IS INVALID
        Action action = Action.valueOf(args[1].toUpperCase());
        if (action == null) {
            sender.sendMessage(INVALID_ACTION);
            return;
        }

        WarnManager warnManager = WarnAPI.get();
        warnManager.setAction(action);

        sender.sendMessage(ACTION.replace("{action}", action.name()));
    }
}
