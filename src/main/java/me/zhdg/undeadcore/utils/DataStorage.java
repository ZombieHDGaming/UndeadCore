package me.zhdg.undeadcore.utils;

import org.bukkit.entity.Player;
import org.magicwerk.brownies.collections.GapList;

import java.util.HashMap;
import java.util.List;

public class DataStorage {

    private static boolean MAINTENANCE = false;

    private static List<Player> STAFF = new GapList<>();
    private static List<Player> ONLINE = new GapList<>();
    private static List<Player> FLYING = new GapList<>();
    private static HashMap<Player, Player> PMS = new HashMap<>();
    private static List<Player> TOGGLE = new GapList<>();
    private static List<Player> SPY = new GapList<>();
    private static List<Player> CHATTING = new GapList<>();

    public static boolean getMaintenance() {
        return MAINTENANCE;
    }

    public static void setMaintenance(boolean setting) {
        MAINTENANCE = setting;
    }

    public static List<Player> getStaff() {
        return STAFF;
    }

    public static List<Player> getOnline() {
        return ONLINE;
    }

    public static List<Player> getFlying() {
        return FLYING;
    }

    public static HashMap<Player, Player> getPMs() {
        return PMS;
    }

    public static List<Player> getToggle() {
        return TOGGLE;
    }

    public static List<Player> getSpy() {
        return SPY;
    }

    public static List<Player> getChatting() {
        return CHATTING;
    }

}
