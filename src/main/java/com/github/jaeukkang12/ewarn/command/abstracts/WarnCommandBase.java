package com.github.jaeukkang12.ewarn.command.abstracts;

import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import com.github.jaeukkang12.ewarn.warn.enums.Type;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

public abstract class WarnCommandBase {

    protected static WarnManager warnManager;

    public static void init() {
        warnManager = WarnAPI.get();
    }


    protected String validatePlayer(String targetName, CommandSender sender) {
        if (targetName == null) {
            sender.sendMessage(NOT_EXIST_PLAYER);
            return null;
        }

        return targetName;
    }

    protected Integer validateAmount(String amountStr, CommandSender sender, boolean allowZero) {
        try {
            int amount = (amountStr == null) ? (allowZero ? 0 : 1) : Integer.parseInt(amountStr);

            if (amount < 0 || (!allowZero && amount == 0)) {
                sender.sendMessage(AMOUNT_CANNOT_BE_MINUS);
                return null;
            }

            return amount;
        } catch (NumberFormatException e) {
            sender.sendMessage(INVALID_AMOUNT);
            return null;
        }
    }

    protected void sendMessage(String message, CommandSender sender) {
        Type type = warnManager.getMessageType();
        if (type == Type.PRIVATE) {
            sender.sendMessage(message);
        } else if (type == Type.PUBLIC) {
            Bukkit.broadcast(Component.text(message));
        }
    }
}
