package com.github.jaeukkang12.ewarn;

import com.github.jaeukkang12.elib.command.CommandHandler;
import com.github.jaeukkang12.ewarn.command.*;
import com.github.jaeukkang12.ewarn.command.abstracts.WarnCommandBase;
import com.github.jaeukkang12.ewarn.event.PlayerJoinEvent;
import com.github.jaeukkang12.ewarn.messages.Messages;
import com.github.jaeukkang12.ewarn.tab.WarnTab;
import com.github.jaeukkang12.ewarn.warn.WarnAPI;
import com.github.jaeukkang12.ewarn.warn.WarnManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EWarnPlugin extends JavaPlugin {
    // PLUGIN INSTANCE
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("eLib") == null) {
            getLogger().warning("[" + getName() + "]  eLib 플러그인이 감지되지 않았습니다. 플러그인을 종료합니다.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // PLUGIN INSTANCE
        plugin = this;

        // COMMAND
        commandSetUp();

        // EVENT
        eventSetUp();

        // MESSAGES
        Messages.init();

        // WARN
        new WarnManager();
    }

    private void commandSetUp() {
        CommandHandler commandHandler = new CommandHandler(plugin);
        // /경고
        List.of(new WarnAdd(), new WarnRemove(), new WarnSet(), new WarnCheck(), new WarnHelp(), new WarnAction(), new WarnReload())
                .forEach(commandHandler::registerCommandClass);
        Bukkit.getPluginCommand("경고").setExecutor(commandHandler);
        Bukkit.getPluginCommand("경고").setTabCompleter(new WarnTab());
    }

    private void eventSetUp() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), plugin);
    }

    @Override
    public void onDisable() {
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}