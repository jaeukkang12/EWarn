package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.command.abstracts.WarnCommandBase;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.ADD;
import static com.github.jaeukkang12.ewarn.messages.Messages.CANNOT_FIND_PLAYER;

public class WarnAdd extends WarnCommandBase {

    @Command(parent = "경고", sub = "추가", permission = "ewarn.add")
    public void addWarn(CommandSender sender, String[] args) {
        String targetName = validatePlayer(args[1], sender);
        if (targetName == null) return;

        Integer amount = validateAmount((args.length == 2) ? null : args[2], sender, false);
        if (amount == null) return;

        try {
            warnManager.add(targetName, amount);
        } catch (CannotFindPlayer e) {
            sender.sendMessage(CANNOT_FIND_PLAYER);
        }

        String message = ADD.replace("{target}", targetName).replace("{amount}", String.valueOf(amount));
        sendMessage(message, sender);
    }

}
