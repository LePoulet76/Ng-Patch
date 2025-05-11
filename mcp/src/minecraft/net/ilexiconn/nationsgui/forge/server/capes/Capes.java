/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.capes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.util.JsonUtils;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.ilexiconn.nationsgui.forge.server.capes.Cape;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Capes {
    public static List<Cape> capesList;

    public static void refresh() {
        capesList = Capes.getCapesFromURL("https://apiv2.nationsglory.fr/json/capes/capes.json");
    }

    public static List<Cape> getCapesFromURL(String url) {
        ArrayList<Cape> capes = new ArrayList<Cape>();
        JSONArray list = (JSONArray)JsonUtils.getJsonFromURL(url, "capes.json");
        if (list != null) {
            System.out.println("Parsing capes...");
            for (Object o : list) {
                Cape cape;
                if (!(o instanceof JSONObject) || (cape = Cape.getCapeFromJSon((JSONObject)o)) == null) continue;
                capes.add(cape);
                try {
                    ServerProxy.getCacheManager().downloadAndStockInCache(cape.getTextureURL(), cape.getIdentifier() + ".png");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Fail Parsing capes...");
        }
        return capes;
    }

    public static Cape getCapeFromIdentifier(String data) {
        if (capesList != null) {
            for (Cape cape : capesList) {
                if (!data.equalsIgnoreCase(cape.getIdentifier())) continue;
                return cape;
            }
        }
        return null;
    }
}

