package net.ilexiconn.nationsgui.forge.server.voices;

import java.util.HashMap;
import java.util.Map;

public class VoiceConfig {

   private Map<String, Long> mutedPlayers = new HashMap();


   public Map<String, Long> getMutedPlayers() {
      return this.mutedPlayers;
   }

   public void setMutedPlayers(Map<String, Long> mutedPlayers) {
      this.mutedPlayers = mutedPlayers;
   }
}
