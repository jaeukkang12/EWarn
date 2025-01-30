package com.github.jaeukkang12.ewarn.messages;

import com.github.jaeukkang12.elib.config.Config;
import com.github.jaeukkang12.elib.utils.StringUtil;

import java.util.List;

import static com.github.jaeukkang12.ewarn.EWarnPlugin.getPlugin;

public final class Messages {
    private static Config messages;

    // ERROR MESSAGE
    public static String NOT_EXIST_ACTION;
    public static String INVALID_ACTION;
    public static String NOT_EXIST_PLAYER;
    public static String CANNOT_FIND_PLAYER;
    public static String AMOUNT_CANNOT_BE_MINUS;
    public static String INVALID_AMOUNT;
    public static String NOT_EXIST_AMOUNT;

    // MESSAGE
    public static String ACTION;
    public static String ADD;
    public static String REMOVE;
    public static String SET;
    public static String CHECK;
    public static List<String> HELP;
    public static List<String> RELOAD;
    public static String KICK;
    public static String BAN;
    public static String BAN_IP;


    private Messages() {
    }

    public static void init() {
        messages = new Config("messages", getPlugin());
        messages.loadDefaultConfig();
        messages.setPrefix("prefix");

        // ERROR MESSAGES
        NOT_EXIST_ACTION = messages.getMessage("errorMessages.notExistAction");
        INVALID_ACTION = messages.getMessage("errorMessages.invalidAction");
        NOT_EXIST_PLAYER = messages.getMessage("errorMessages.notExistPlayer");
        CANNOT_FIND_PLAYER = messages.getMessage("errorMessages.cannotFindPlayer");
        AMOUNT_CANNOT_BE_MINUS = messages.getMessage("errorMessages.amountCannotBeMinus");
        INVALID_AMOUNT = messages.getMessage("errorMessages.invalidAmount");
        NOT_EXIST_AMOUNT = messages.getMessage("errorMessages.notExistAmount");

        // MESSAGES
        ACTION = messages.getMessage("messages.action");
        ADD = messages.getMessage("messages.add");
        REMOVE = messages.getMessage("messages.remove");
        SET = messages.getMessage("messages.set");
        CHECK = messages.getMessage("messages.check");
        HELP = messages.getMessages("messages.help");
        RELOAD = messages.getMessages("messages.reload");
        KICK = StringUtil.color(messages.getString("messages.reason.kick"));
        BAN = StringUtil.color(messages.getString("messages.reason.ban"));
        BAN_IP = StringUtil.color(messages.getString("messages.reason.banIp"));
    }
}
