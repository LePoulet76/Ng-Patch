package net.ilexiconn.nationsgui.forge.server.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Translation {

   public static HashMap<String, HashMap<String, String>> translations = new HashMap();
   public static String lang = "fr";
   public static long lastLoad = 0L;


   public static void getServerLang() {
      Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
      if(pl != null) {
         try {
            Method e = pl.getClass().getDeclaredMethod("getTranslationLang", new Class[0]);
            lang = (String)e.invoke(pl, new Object[0]);
         } catch (InvocationTargetException var2) {
            var2.printStackTrace();
         }
      }

   }

   public static void loadTranslations() {
      Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
      if(pl != null) {
         try {
            Method e = pl.getClass().getDeclaredMethod("getTranslations", new Class[0]);
            translations = (HashMap)e.invoke(pl, new Object[0]);
         } catch (InvocationTargetException var2) {
            var2.printStackTrace();
         }
      }

   }

   public static String get(String defaultFR) {
      if(System.currentTimeMillis() - lastLoad > 600000L) {
         lastLoad = System.currentTimeMillis();
         getServerLang();
         loadTranslations();
      }

      if(translations.containsKey(lang) && ((HashMap)translations.get(lang)).containsKey(defaultFR) && !((String)((HashMap)translations.get(lang)).get(defaultFR)).isEmpty()) {
         return (String)((HashMap)translations.get(lang)).get(defaultFR);
      } else if(translations.containsKey("fr") && ((HashMap)translations.get("fr")).containsKey(defaultFR)) {
         return (String)((HashMap)translations.get("fr")).get(defaultFR);
      } else {
         Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
         if(pl != null) {
            try {
               Method e = pl.getClass().getDeclaredMethod("addMissingTranslation", new Class[]{String.class});
               e.invoke(pl, new Object[]{defaultFR});
            } catch (InvocationTargetException var3) {
               var3.printStackTrace();
            }
         }

         if(translations.get("fr") == null) {
            return defaultFR;
         } else {
            ((HashMap)translations.get("fr")).put(defaultFR, defaultFR);
            return defaultFR;
         }
      }
   }

}
