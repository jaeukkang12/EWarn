package com.github.jaeukkang12.ewarn.warn;

import com.github.jaeukkang12.elib.config.Config;
import com.github.jaeukkang12.ewarn.EWarnPlugin;
import com.github.jaeukkang12.ewarn.warn.enums.Action;
import com.github.jaeukkang12.ewarn.warn.enums.Type;
import com.github.jaeukkang12.ewarn.warn.exception.CannotFindPlayer;
import com.github.jaeukkang12.ewarn.warn.exception.WarningCannotBeMinus;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

import static com.github.jaeukkang12.ewarn.messages.Messages.*;

// TODO: playerData.yml 과 대조해서 데이터가 존재하지 않는다면, 예외 발생시키기.
public final class WarnManager extends JavaPlugin {
    private final Config warnData;
    private final Config playerData;
    private final Config config;

    private WarnManager() {
        // PLUGIN INSTANCE
        JavaPlugin plugin = EWarnPlugin.getPlugin();

        // WARN DATA
        this.warnData = new Config("warnData", plugin);
        warnData.loadDefaultConfig();

        // PLAYER DATA
        playerData = new Config("playerData", plugin);
        playerData.loadDefaultConfig();

        // CONFIG
        this.config = new Config("config", plugin);
        config.loadDefaultConfig();

    }

    /**
     * 플러그인 실행 시 자동으로 WarnManager 객체가 생성됩니다.
     */
    @Override
    public void onEnable() {
        WarnManager warnManager = new WarnManager();
        WarnAPI.register(warnManager);
    }

    /**
     * 플러그인 종료 시 WarnAPI 에서 객체 제거
     */
    @Override
    public void onDisable() {
        WarnAPI.unregister();
    }

    /**
     * 플레이어의 경고 횟수를 리턴합니다.
     * @param name 플레이어 이름
     * @return int 경고횟수
     */
    public int get(String name) {
        return warnData.getInt(getUUID(name) + "");
    }

    /**
     * 플레이어의 경고 횟수를 리턴합니다.
     * @param player 플레이어
     * @return int 경고횟수
     */
    public int get(Player player) {
        return warnData.getInt(getUUID(player.getName()) + "");
    }

    /**
     * 최대경고 횟수를 리턴합니다.
     * @return int 최대경고 횟수
     */
    public int getMax() {
        return config.getInt("maxWarn");
    }

    /**
     * 플레이어에게 경고를 추가합니다.
     * @param name 플레이어 이름
     * @param amount 횟수
     */
    public void add(String name, int amount) {
        warnData.setInt(getUUID(name) + "", get(name) + amount);
        check(name);
    }

    public void add(Player player, int amount) {
        String name = player.getName();
        warnData.setInt(getUUID(name) + "", get(name) + amount);
        check(name);
    }

    /**
     * 플레이어의 경고를 차감합니다.
     * @param player 플레이어
     * @param amount 횟수
     */
    public void remove(Player player, int amount) {
        String name = player.getName();
        int currentAmount = get(name);
        if (currentAmount - amount < 0) {
            throw new WarningCannotBeMinus();
        }

        warnData.setInt(getUUID(name) + "", get(name) - amount);
        check(name);
    }

    /**
     * 플레이어의 경고를 차감합니다.
     * @param name 플레이어 이름
     * @param amount 횟수
     */
    public void remove(String name, int amount) {
        int currentAmount = get(name);
        if (currentAmount - amount < 0) {
            throw new WarningCannotBeMinus();
        }

        warnData.setInt(getUUID(name) + "", get(name) - amount);
        check(name);
    }

    /**
     * 플레이어의 경고를 설정합니다.
     * @param name 플레이어 이름
     * @param amount 횟수
     */
    public void set(String name, int amount) {
        if (amount < 0) {
            throw new WarningCannotBeMinus();
        }

        warnData.setInt(getUUID(name) + "", amount);
        check(name);
    }

    /**
     * 플레이어의 경고를 설정합니다.
     * @param player 플레이어
     * @param amount 횟수
     */
    public void set(Player player, int amount) {
        if (amount < 0) {
            throw new WarningCannotBeMinus();
        }

        String name = player.getName();

        warnData.setInt(getUUID(name) + "", amount);
        check(name);
    }

    /**
     * 플레이어의 경고횟수가 최대경고 횟수와 같은지 확인합니다.
     * 같다면, ACTION 이 일어납니다.
     * @param name 확인할 플레이어
     */
    private void check(String name) {
        if (!(get(name) >= getMax())) return;

        Action action = getAction();
        if (action == Action.KICK) {
            Component component = Component.text(KICK);
            Bukkit.getOfflinePlayer(getUUID(name)).getPlayer().kick(component, PlayerKickEvent.Cause.PLUGIN);
        } else if (action == Action.BAN) {
            Bukkit.getOfflinePlayer(getUUID(name)).getPlayer().banPlayer(BAN, null, null, true);
        } else if (action == Action.BAN_IP) {
            Bukkit.getOfflinePlayer(getUUID(name)).getPlayer().banPlayerIP(BAN_IP, null, null, true);
        }
    }

    /**
     * 액션을 리턴합니다.
     * @return {@link Action} 액션
     */
    public Action getAction() {
        return Action.valueOf(config.getString("action"));
    }

    /**
     * 액션을 설정합니다.
     * @param action {@link Action} 액션
     */
    public void setAction(Action action) {
        config.setString("action", action.name());
    }

    /**
     * 플레이어의 경고 변화 시 출력되는 메세지의 타입을 리턴합니다
     * @return Type 타입
     */
    public Type getMessageType() {
        return Type.valueOf(config.getString("message"));
    }

    /**
     * warnData.yml 을 다시 로드합니다.
     */
    public void reload() {
        warnData.reloadConfig();
    }

    /**
     * {@link Bukkit#getOfflinePlayer(String)} 의 불안성으로 인해 사용.
     * playerData.yml 파일에 플레이어의 이름과 UUID 를 저장.
     * {@link Bukkit#getOfflinePlayer(String)} 과 유사하게 사용.
     * @param player 저장할 플레이어
     */
    public void registerPlayer(Player player) {
        playerData.setString(player.getName(), player.getUniqueId() + "");
    }

    /**
     * 플레이어의 UUID 를 리턴합니다.
     * 사용 전 {@link WarnManager#registerPlayer(Player)} 에 플레이어 등록할 것.
     * @param name 플레이어 이름
     * @return {@link UUID} UUID
     */
    public UUID getUUID(String name) {
        if (!playerData.containsKey(name)) {
            throw new CannotFindPlayer();
        }
        return UUID.fromString(playerData.getString(playerData.getString(name)));
    }

    /**
     * 플레이어의 닉네임과 UUID가 playerData.yml 에 저장 된 데이터와 일치하는지를 확인합니다.
     * 다르다면, 값을 갱신합니다.
     * @param player 확인할 플레이어
     * @return 닉네임 변경 여부
     */
    public boolean nickIsChanged(Player player) {
        boolean isChanged = false;

        if (!playerData.containsKey(player.getName())) {
            registerPlayer(player);
        }

        for (String name : playerData.getConfig().getKeys(false)) {
            UUID uuidData = getUUID(name);

            String playerName = player.getName();
            UUID playerUuid = player.getUniqueId();
            if (uuidData == playerUuid && name != playerName) {
                isChanged = true;

                playerData.delete(name);
                break;
            }
        }
        return isChanged;
    }
}
