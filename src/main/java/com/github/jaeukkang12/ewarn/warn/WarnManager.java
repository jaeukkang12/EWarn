package com.github.jaeukkang12.ewarn.warn;

import com.github.jaeukkang12.elib.config.Config;
import com.github.jaeukkang12.ewarn.EWarnPlugin;
import com.github.jaeukkang12.ewarn.command.abstracts.WarnCommandBase;
import com.github.jaeukkang12.ewarn.warn.enums.Action;
import com.github.jaeukkang12.ewarn.warn.enums.Type;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

public final class WarnManager implements Warn {
    private static Config warnData;
    private static Config playerData;
    private static Config config;

    private boolean isLoaded = false;

    public WarnManager() {
        if (isLoaded) {
            throw new UnsatisfiedLinkError("이미 로드되었습니다.");
        }

        // PLUGIN INSTANCE
        JavaPlugin plugin = EWarnPlugin.getPlugin();

        // WARN DATA
        warnData = new Config("warnData", plugin);
        warnData.loadDefaultConfig();

        // PLAYER DATA
        playerData = new Config("playerData", plugin);
        playerData.loadDefaultConfig();

        // CONFIG
        config = new Config("config", plugin);
        config.loadDefaultConfig();

        WarnAPI.register(this);
        WarnCommandBase.init();

        isLoaded = true;
    }

    /**
     * 경고를 추가합니다.
     * @param name 플레이어 이름
     * @param amount 경고 횟수
     */
    @Override
    public void add(String name, int amount) {
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", get(name) + amount);
        check(name);
    }

    /**
     * 경고를 추가합니다.
     * @param player {@link Player} 플레이어
     * @param amount 경고 횟수
     */
    @Override
    public void add(Player player, int amount) {
        String name = player.getName();
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", get(name) + amount);
        check(name);
    }

    /**
     * 경고를 제거합니다.
     * @param name 플레이어 이름
     * @param amount 경고 횟수
     */
    @Override
    public void remove(String name, int amount) {
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", get(name) - amount);
        check(name);
    }

    /**
     * 경고를 제거합니다.
     * @param player {@link Player} 플레이어
     * @param amount 경고 횟수
     */
    @Override
    public void remove(Player player, int amount) {
        String name = player.getName();
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", get(name) - amount);
        check(name);
    }

    /**
     * 경고를 설정합니다.
     * @param name 플레이어 이름
     * @param amount 경고 횟수
     */
    @Override
    public void set(String name, int amount) {
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", amount);
        check(name);
    }

    /**
     * 경고를 설정합니다.
     * @param player {@link Player} 플레이어
     * @param amount 경고 횟수
     */
    @Override
    public void set(Player player, int amount) {
        String name = player.getName();
        UUID uuid = getUUID(name);
        warnData.setInt(uuid + "", amount);
        check(name);
    }

    /**
     * 경고횟수를 리턴합니다.
     * @param name 플레이어 이름
     * @return 경고 횟수
     */
    @Override
    public int get(String name) {
        UUID uuid = getUUID(name);
        return warnData.getInt(uuid + "");
    }

    /**
     * 경고횟수를 리턴합니다.
     * @param player {@link Player} 플레이어
     * @return 경고 횟수
     */
    @Override
    public int get(Player player) {
        String name = player.getName();
        UUID uuid = getUUID(name);
        return warnData.getInt(uuid + "");
    }

    /**
     * 경고가 최대 횟수에 도달 시 실행할 행동을 설정합니다.
     * @param action {@link Action} 액션
     */
    public void setAction(Action action) {
        config.setString("action", action.name());
    }

    /**
     * 경고가 최대 횟수에 도달 시 실행할 행동을 설정합니다.
     * @param actionName 액션 이름
     */
    public void setAction(String actionName) {
        config.setString("action", actionName);
    }

    /**
     * 경고가 최대 횟수에 도달 시 실행할 행동값을 리턴합니다.
     * @return action
     */
    public Action getAction() {
        return Action.valueOf(config.getString("action"));
    }

    /**
     * 경고 최대 횟수를 리턴합니다.
     * @return 최대 횟수
     */
    public int getMax() {
        return config.getInt("maxWarn");
    }

    private void check(String name) {
        if (!(get(name) >= getMax())) {
            return;
        }

        Action action = getAction();
        Player player = Bukkit.getPlayer(name);
        String reason = KICK;
        if (action == Action.BAN) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(name, BAN, null, null);
            reason = BAN;
        } else if (action == Action.BAN_IP) {
            Bukkit.getBanList(BanList.Type.IP).addBan(name, BAN_IP, null, null);
            reason = BAN_IP;
        }

        if (player == null) return;
        player.kick(Component.text(reason), PlayerKickEvent.Cause.PLUGIN);
    }

    /**
     * {@link Bukkit#getOfflinePlayer(UUID)} 의 불안정성으로인해 제작한 메소드
     * 플레이어의 이름과 UUID를 {@link Config} 파일을 통해 저장 관리합니다.
     *
     * 플레이어의 이름과 UUID를 {@link Config} 파일에 등록합니다.
     * @param player 플레이어
     */
    public void registerUser(Player player) {
        playerData.setString(player.getName(), player.getUniqueId() + "");
    }

    /**
     * {@link Bukkit#getOfflinePlayer(UUID)} 의 불안정성으로인해 제작한 메소드
     * 플레이어의 이름과 UUID를 {@link Config} 파일을 통해 저장 관리합니다.
     *
     * 플레이어가 닉네임을 변경하였는지 확인합니다.
     * 변경이 되었을 시 변경 전 기록을 제거합니다.
     * @param player 플레이어
     */
    public void isChanged(Player player) {
        for (String name : playerData.getConfig().getKeys(false)) {
            String targetName = player.getName();
            String targetUuid = player.getUniqueId() + "";

            String uuid = playerData.getString(name);

            if (name != targetName && uuid == targetUuid) {
                playerData.delete(name);
                registerUser(player);
                break;
            }
        }
    }

    /**
     * {@link Bukkit#getOfflinePlayer(UUID)} 의 불안정성으로인해 제작한 메소드
     * 플레이어의 이름과 UUID를 {@link Config} 파일을 통해 저장 관리합니다.
     *
     * 플레이어의 UUID 를 리턴합니다.
     * @param name 플레이어 이름
     * @return 플레이어 UUID
     */
    public UUID getUUID(String name) {
        try {
            return UUID.fromString(playerData.getString(name));
        } catch (Exception e) {
            throw new CannotFindPlayer();
        }
    }

    /**
     * warnData.yml 파일을 리로드합니다.
     */
    public void reload() {
        warnData.reloadConfig();
    }

    /**
     * 경고 추가, 제거, 설정 시 출력되는 메시지의 타입을 리턴합니다.
     * @return {@link Type}
     */
    public Type getMessageType() {
        return Type.valueOf(config.getString("log"));
    }
}
