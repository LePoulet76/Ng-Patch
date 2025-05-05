package fr.nationsglory.itemmanager.data;


public class LocalConfig {

   private String serverName = "";
   private boolean multipleRespawn = false;
   private String playerListTopText = "";
   private String playerListBottomText = "";
   private int clickLimit = 25;
   private String serverType = "ng";


   public String getServerName() {
      return this.serverName;
   }

   public boolean isMultipleRespawn() {
      return this.multipleRespawn;
   }

   public String getServerType() {
      return this.serverType;
   }

   public String getPlayerListTopText() {
      return this.playerListTopText;
   }

   public String getPlayerListBottomText() {
      return this.playerListBottomText;
   }

   public int getClickLimit() {
      return this.clickLimit;
   }
}
