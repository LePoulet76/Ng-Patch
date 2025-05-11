/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
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
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getTranslationLang", new Class[0]);
                lang = (String)m.invoke(pl, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadTranslations() {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getTranslations", new Class[0]);
                translations = (HashMap)m.invoke(pl, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(String defaultFR) {
        if (System.currentTimeMillis() - lastLoad > 600000L) {
            lastLoad = System.currentTimeMillis();
            Translation.getServerLang();
            Translation.loadTranslations();
        }
        if (translations.containsKey(lang) && translations.get(lang).containsKey(defaultFR) && !translations.get(lang).get(defaultFR).isEmpty()) {
            return translations.get(lang).get(defaultFR);
        }
        if (translations.containsKey("fr") && translations.get("fr").containsKey(defaultFR)) {
            return translations.get("fr").get(defaultFR);
        }
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("addMissingTranslation", String.class);
                m.invoke(pl, defaultFR);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (translations.get("fr") == null) {
            return defaultFR;
        }
        translations.get("fr").put(defaultFR, defaultFR);
        return defaultFR;
    }
}

