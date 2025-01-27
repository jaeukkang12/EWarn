package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.command.abstracts.WarnCommandBase;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

public class WarnSet extends WarnCommandBase {

    @Command(parent = "경고", sub = "설정", permission = "ewarn.set")
    public void setWarn(CommandSender sender, String[] args) {
        String targetName = validatePlayer(args[1], sender);
        if (targetName == null) return;

        Integer amount = validateAmount(args[2], sender, true);
        if (amount == null) return;

        try {
            warnManager.set(targetName, amount);
        } catch (CannotFindPlayer e) {
            sender.sendMessage(CANNOT_FIND_PLAYER);
        }

        String message = SET.replace("{target}", targetName).replace("{amount}", String.valueOf(amount));
        sendMessage(message, sender);
    }

}
