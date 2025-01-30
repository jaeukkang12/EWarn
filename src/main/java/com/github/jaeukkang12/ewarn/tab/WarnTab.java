package com.github.jaeukkang12.ewarn.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarnTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("ewarn.help")) list.add("도움말");
            if (sender.hasPermission("ewarn.action")) list.add("액션");
            if (sender.hasPermission("ewarn.add")) list.add("추가");
            if (sender.hasPermission("ewarn.remove")) list.add("차감");
            if (sender.hasPermission("ewarn.set")) list.add("설정");
            if (sender.hasPermission("ewarn.check")) list.add("확인");
            if (sender.hasPermission("ewarn.reload")) list.add("리로드");
            return list;
        } else if (args.length == 2) {
            if (args[0].equals("추가") || args[0].equals("설정") || args[0].equals("차감") || args[0].equals("확인")) {
                return null;
            } else if (args[0].equals("액션")) {
                return List.of("KICK", "BAN", "BAN_IP");
            }
        } else if (args.length == 3) {
            if (args[0].equals("추가") || args[0].equals("설정") || args[0].equals("차감")) {
                return List.of("<횟수>");
            }
        }


        return Collections.emptyList();
    }
}
