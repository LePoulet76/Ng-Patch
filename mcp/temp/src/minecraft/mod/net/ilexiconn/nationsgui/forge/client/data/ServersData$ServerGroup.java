package net.ilexiconn.nationsgui.forge.client.data;

import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;

public class ServersData$ServerGroup {

   private String iconTexture;
   private Map<String, ServersData$Server> serverList;
   // $FF: synthetic field
   final ServersData this$0;


   public ServersData$ServerGroup(ServersData this$0) {
      this.this$0 = this$0;
      this.iconTexture = "";
      this.serverList = new HashMap();
   }

   public String getIconTexture() {
      return this.iconTexture;
   }

   public Map<String, ServersData$Server> getServerMap() {
      return this.serverList;
   }
}
