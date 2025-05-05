package net.ilexiconn.nationsgui.forge.client.data;

import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$ServerGroup;

public class ServersData {

   private Map<String, ServersData$ServerGroup> permanentServers = new HashMap();
   private Map<String, ServersData$Server> temporaryServers = new HashMap();
   private Map<String, Map<String, Map<String, String>>> lang = new HashMap();


   public Map<String, ServersData$ServerGroup> getPermanentServers() {
      return this.permanentServers;
   }

   public Map<String, ServersData$Server> getTemporaryServers() {
      return this.temporaryServers;
   }

   public Map<String, Map<String, Map<String, String>>> getLang() {
      return this.lang;
   }
}
