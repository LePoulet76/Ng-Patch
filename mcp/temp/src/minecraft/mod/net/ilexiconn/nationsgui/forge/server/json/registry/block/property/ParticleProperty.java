package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONParticle;

public class ParticleProperty implements JSONProperty<JSONBlock> {

   private static Gson GSON = new Gson();


   public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
      return name.equals("particle");
   }

   public void setProperty(String name, JsonElement element, JSONBlock block) {
      JsonObject object = element.getAsJsonObject();
      Iterator var5 = object.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry = (Entry)var5.next();
         ArrayList particles = new ArrayList();
         JsonArray array = ((JsonElement)entry.getValue()).getAsJsonArray();
         Iterator var9 = array.iterator();

         while(var9.hasNext()) {
            JsonElement e = (JsonElement)var9.next();
            particles.add(GSON.fromJson(e, JSONParticle.class));
         }

         block.particles.put(entry.getKey(), particles);
      }

   }

   // $FF: synthetic method
   // $FF: bridge method
   public void setProperty(String var1, JsonElement var2, Object var3) {
      this.setProperty(var1, var2, (JSONBlock)var3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public boolean isApplicable(String var1, JsonElement var2, Object var3) {
      return this.isApplicable(var1, var2, (JSONBlock)var3);
   }

}
