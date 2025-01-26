package com.github.jaeukkang12.ewarn.command;

import com.github.jaeukkang12.elib.command.Command;
import org.bukkit.command.CommandSender;

import static com.github.jaeukkang12.ewarn.messages.Messages.HELP;

public class WarnHelp {
    @Command(parent = "경고", sub = "도움말", permission = "ewarn.help")
    public void warnHelp(CommandSender sender, String[] args) {
        HELP.forEach(sender::sendMessage);
    }
}
