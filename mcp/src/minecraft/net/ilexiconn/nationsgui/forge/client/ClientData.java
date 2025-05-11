/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.server.Mail;

public class ClientData {
    public static boolean isOp = false;
    private static List<Mail> MAILS = new ArrayList<Mail>();
    private static ArrayList<HashMap<String, String>> ACHIEVEMENTS = new ArrayList();
    public static String currentFaction = "";
    public static int clickLimit = 0;
    public static boolean textureClean = true;
    public static List<Objective> objectives = new ArrayList<Objective>();
    public static int currentObjectiveIndex = 0;
    public static byte[] minimapColors = null;
    public static String[] mapFactions;
    public static ArrayList<Notification> notifications;
    public static String hotbarMessage;
    public static ArrayList<HashMap<String, Object>> dialogs;
    public static ArrayList<Map<String, Object>> markers;
    public static HashMap<String, String> currentAssault;
    public static HashMap<String, String> currentIsland;
    public static HashMap<String, String> currentWarzone;
    public static HashMap<String, String> currentFoot;
    public static HashMap<String, Integer> skillsLevel;
    public static Map<String, String> topPlayersSkills;
    public static ArrayList<HashMap<String, Object>> eventsInfos;
    public static ArrayList<String> eventPointsValidated;
    public static HashMap<String, String> playerInfos;
    public static HashMap<String, String> countryTitleInfos;
    public static ArrayList<String> playerCombatArmorDurability;
    public static HashMap<String, String> playerCombatArmorInfos;
    public static HashMap<String, Object> versusOverlayData;
    public static HashMap<String, Object> versusOverlayAnimations;
    public static HashMap<String, Object> autelOverlayData;
    public static HashMap<String, Object> bossOverlayData;
    public static String currentJumpRecord;
    public static Long currentJumpStartTime;
    public static String currentJumpLocation;
    public static Long lastPlayerWantDisconnect;
    public static String waitingServerName;
    public static String waitingServerNeedConfirmation;
    public static String waitingServerIpPort;
    public static int waitingPosition;
    public static int waitingTotal;
    public static boolean waitingPriority;
    public static Long waitingJoinTime;
    public static Long serverTime;
    public static String worldName;
    public static Long clientTimeWhenServerTimeReceived;
    public static boolean useShaderMod;
    public static HashMap<String, Long> lastCaptureScreenshot;
    public static String capturedFactionImage;
    public static boolean isCombatTagged;
    public static HashMap<String, String> GUIWithHelp;
    public static HashMap<String, Float> bonuses;
    public static Long bonusStartTime;
    public static Long bonusEndTime;
    public static float customRenderColorRed;
    public static float customRenderColorGreen;
    public static float customRenderColorBlue;
    public static String lastCustomConnectionIP;
    public static int lastCustomConnectionPort;
    public static String lastCustomConnectionServerName;
    public static Long noelMegaGiftTimeSpawn;

    public static List<Mail> getMail() {
        return MAILS;
    }

    public static ArrayList<HashMap<String, String>> getAchievements() {
        return ACHIEVEMENTS;
    }

    public static void setAchievements(ArrayList<HashMap<String, String>> achievements) {
        ACHIEVEMENTS = achievements;
    }

    public static void setCurrentAssault(HashMap<String, String> assault) {
        currentAssault = assault;
    }

    public static void addEventInfos(HashMap<String, Object> eventInfos) {
        eventsInfos.add(eventInfos);
    }

    public static void setCurrentIsland(HashMap<String, String> island) {
        currentIsland = island;
    }

    public static void setCurrentWarzone(HashMap<String, String> warzone) {
        currentWarzone = warzone;
    }

    public static void setCurrentFoot(HashMap<String, String> footInfos) {
        currentFoot = footInfos;
    }

    public static void removeMail(Mail mail) {
        MAILS.remove(mail);
    }

    public static void addMail(Mail mail) {
        MAILS.add(0, mail);
    }

    public static int getPlayerSkillLevel(String skillName) {
        return skillsLevel.containsKey(skillName) ? skillsLevel.get(skillName) : 0;
    }

    static {
        notifications = new ArrayList();
        hotbarMessage = "";
        dialogs = new ArrayList();
        markers = new ArrayList();
        currentAssault = new HashMap();
        currentIsland = new HashMap();
        currentWarzone = new HashMap();
        currentFoot = new HashMap();
        skillsLevel = new HashMap();
        topPlayersSkills = new HashMap<String, String>();
        eventsInfos = new ArrayList();
        eventPointsValidated = new ArrayList();
        playerInfos = new HashMap();
        countryTitleInfos = new HashMap();
        playerCombatArmorDurability = new ArrayList();
        playerCombatArmorInfos = new HashMap();
        versusOverlayData = new HashMap();
        versusOverlayAnimations = new HashMap();
        autelOverlayData = new HashMap();
        bossOverlayData = new HashMap();
        currentJumpRecord = "";
        currentJumpStartTime = -1L;
        currentJumpLocation = "";
        lastPlayerWantDisconnect = 0L;
        waitingPriority = false;
        serverTime = 0L;
        worldName = "world";
        clientTimeWhenServerTimeReceived = 0L;
        useShaderMod = false;
        lastCaptureScreenshot = new HashMap();
        capturedFactionImage = "";
        isCombatTagged = false;
        GUIWithHelp = new HashMap();
        bonuses = new HashMap();
        bonusStartTime = 0L;
        bonusEndTime = 0L;
        customRenderColorRed = 1.0f;
        customRenderColorGreen = 1.0f;
        customRenderColorBlue = 1.0f;
        lastCustomConnectionIP = "";
        lastCustomConnectionPort = 0;
        lastCustomConnectionServerName = "";
        noelMegaGiftTimeSpawn = 0L;
    }
}

