package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.command.abstracts.WarnCommandBase;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

public class WarnRemove extends WarnCommandBase {

    @Command(parent = "경고", sub = "차감", permission = "ewarn.remove")
    public void removeWarn(CommandSender sender, String[] args) {
        String targetName = validatePlayer(args[1], sender);
        if (targetName == null) return;

        Integer amount = validateAmount((args.length == 2) ? null : args[2], sender, false);
        if (amount == null) return;

        try {
            warnManager.remove(targetName, amount);
        } catch (CannotFindPlayer e) {
            sender.sendMessage(CANNOT_FIND_PLAYER);
        }

        String message = REMOVE.replace("{target}", targetName).replace("{amount}", String.valueOf(amount));
        sendMessage(message, sender);
    }

}
