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
   private static List<Mail> MAILS = new ArrayList();
   private static ArrayList<HashMap<String, String>> ACHIEVEMENTS = new ArrayList();
   public static String currentFaction = "";
   public static int clickLimit = 0;
   public static boolean textureClean = true;
   public static List<Objective> objectives = new ArrayList();
   public static int currentObjectiveIndex = 0;
   public static byte[] minimapColors = null;
   public static String[] mapFactions;
   public static ArrayList<Notification> notifications = new ArrayList();
   public static String hotbarMessage = "";
   public static ArrayList<HashMap<String, Object>> dialogs = new ArrayList();
   public static ArrayList<Map<String, Object>> markers = new ArrayList();
   public static HashMap<String, String> currentAssault = new HashMap();
   public static HashMap<String, String> currentIsland = new HashMap();
   public static HashMap<String, String> currentWarzone = new HashMap();
   public static HashMap<String, String> currentFoot = new HashMap();
   public static HashMap<String, Integer> skillsLevel = new HashMap();
   public static Map<String, String> topPlayersSkills = new HashMap();
   public static ArrayList<HashMap<String, Object>> eventsInfos = new ArrayList();
   public static ArrayList<String> eventPointsValidated = new ArrayList();
   public static HashMap<String, String> playerInfos = new HashMap();
   public static HashMap<String, String> countryTitleInfos = new HashMap();
   public static ArrayList<String> playerCombatArmorDurability = new ArrayList();
   public static HashMap<String, String> playerCombatArmorInfos = new HashMap();
   public static HashMap<String, Object> versusOverlayData = new HashMap();
   public static HashMap<String, Object> versusOverlayAnimations = new HashMap();
   public static HashMap<String, Object> autelOverlayData = new HashMap();
   public static HashMap<String, Object> bossOverlayData = new HashMap();
   public static String currentJumpRecord = "";
   public static Long currentJumpStartTime = Long.valueOf(-1L);
   public static String currentJumpLocation = "";
   public static Long lastPlayerWantDisconnect = Long.valueOf(0L);
   public static String waitingServerName;
   public static String waitingServerNeedConfirmation;
   public static String waitingServerIpPort;
   public static int waitingPosition;
   public static int waitingTotal;
   public static boolean waitingPriority = false;
   public static Long waitingJoinTime;
   public static Long serverTime = Long.valueOf(0L);
   public static String worldName = "world";
   public static Long clientTimeWhenServerTimeReceived = Long.valueOf(0L);
   public static boolean useShaderMod = false;
   public static HashMap<String, Long> lastCaptureScreenshot = new HashMap();
   public static String capturedFactionImage = "";
   public static boolean isCombatTagged = false;
   public static HashMap<String, String> GUIWithHelp = new HashMap();
   public static HashMap<String, Float> bonuses = new HashMap();
   public static Long bonusStartTime = Long.valueOf(0L);
   public static Long bonusEndTime = Long.valueOf(0L);
   public static float customRenderColorRed = 1.0F;
   public static float customRenderColorGreen = 1.0F;
   public static float customRenderColorBlue = 1.0F;
   public static String lastCustomConnectionIP = "";
   public static int lastCustomConnectionPort = 0;
   public static String lastCustomConnectionServerName = "";
   public static Long noelMegaGiftTimeSpawn = Long.valueOf(0L);


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
      return skillsLevel.containsKey(skillName)?((Integer)skillsLevel.get(skillName)).intValue():0;
   }

}
