package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistries$1;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlockRegistry;

public class JSONRegistries {

   private static List<JSONRegistry<?>> REGISTRIES = new ArrayList();
   private static Gson GSON = new Gson();


   public static <T extends Object> JSONRegistry<T> getRegistry(Class<T> type) {
      Iterator var1 = REGISTRIES.iterator();

      JSONRegistry registry;
      do {
         if(!var1.hasNext()) {
            return null;
         }

         registry = (JSONRegistry)var1.next();
      } while(registry.getType() != type);

      return registry;
   }

   public static <T extends Object> void parseJSON(JSONRegistry<T> registry, String url) throws IOException {
      InputStream stream = (new URL(url)).openStream();
      parseJSON(registry, stream);
      stream.close();
   }

   public static <T extends Object> void parseJSON(JSONRegistry<T> registry, InputStream stream) {
      Type type = (new JSONRegistries$1()).getType();
      Map map = (Map)GSON.fromJson(new InputStreamReader(stream), type);
      HashMap names = new HashMap();
      Iterator var5 = map.entrySet().iterator();

      while(var5.hasNext()) {
         Entry object = (Entry)var5.next();
         String name = (String)object.getKey();
         JsonObject json = ((JsonElement)object.getValue()).getAsJsonObject();
         Object object1 = registry.constructNew(name, json);
         if(object1 != null) {
            Iterator var10 = json.entrySet().iterator();

            while(var10.hasNext()) {
               Entry values = (Entry)var10.next();
               Iterator var12 = registry.getProperties().iterator();

               while(var12.hasNext()) {
                  JSONProperty property = (JSONProperty)var12.next();
                  if(property.isApplicable((String)values.getKey(), (JsonElement)values.getValue(), object1)) {
                     property.setProperty((String)values.getKey(), (JsonElement)values.getValue(), object1);
                  }
               }
            }

            names.put(object1, name);
            registry.getRegistry().add(object1);
         }
      }

      var5 = registry.getRegistry().iterator();

      while(var5.hasNext()) {
         Object object2 = var5.next();
         registry.register((String)names.get(object2), object2);
      }

   }

   static {
      REGISTRIES.add(new JSONBlockRegistry());
      REGISTRIES.add(new JSONArmorRegistry());
   }
}
