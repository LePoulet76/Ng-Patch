package net.ilexiconn.nationsgui.forge.client.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;

public enum ChatHandler {

   INSTANCE("INSTANCE", 0);
   private JsonParser jsonParser = new JsonParser();
   private Map<String, IChatFallback> fallbackMap = new HashMap();
   // $FF: synthetic field
   private static final ChatHandler[] $VALUES = new ChatHandler[]{INSTANCE};


   private ChatHandler(String var1, int var2) {}

   public boolean executeFallback(String message) {
      JsonObject object = this.jsonParser.parse(message).getAsJsonObject();
      if(object != null && object.has("text")) {
         message = object.get("text").getAsString();
         if(this.fallbackMap.containsKey(message)) {
            ((IChatFallback)this.fallbackMap.get(message)).call();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void registerCallback(IChatFallback fallback, String ... messages) {
      String[] var3 = messages;
      int var4 = messages.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String message = var3[var5];
         this.fallbackMap.put(message, fallback);
      }

   }

}
