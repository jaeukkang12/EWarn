package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import com.github.jaeukkang12.ewarn.warn.enums.Type;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

// TODO: 플레이어를 찾을 수 없을 때 예외 처리하기.
public class WarnAdd {

    @Command(parent = "경고", sub = "추가", permission = "ewarn.add")
    public void addWarn(CommandSender sender, String[] args) {

        // IF PLAYER IS NOT SET
        String targetName = args[1];
        if (targetName == null) {
            sender.sendMessage(NOT_EXIST_PLAYER);
            return;
        }

        // IF PLAYER IS NOT FOUND
        Player targetPlayer = Bukkit.getOfflinePlayer(targetName).getPlayer();
        if (targetPlayer == null) {
            sender.sendMessage(CANNOT_FIND_PLAYER);
            return;
        }

        // CHECK AMOUNT
        try {
            int amount = (args[2] == null) ? 1 : Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage(AMOUNT_CANNOT_BE_MINUS);
                return;
            }

            WarnManager warnManager = WarnAPI.get();
            warnManager.add(targetName, amount);

            String message = ADD
                    .replace("{target}", targetName)
                    .replace("{amount}", String.valueOf(amount));

            Type type = warnManager.getMessageType();
            if (type == Type.PRIVATE) {
                sender.sendMessage(message);
            } else if (type == Type.PUBLIC) {
                Bukkit.broadcast(Component.text(message));
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(INVALID_AMOUNT);
        }
    }
}
