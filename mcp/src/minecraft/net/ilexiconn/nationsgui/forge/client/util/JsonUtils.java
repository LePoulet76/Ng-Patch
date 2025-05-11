/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.util;

import java.io.FileReader;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import org.json.simple.parser.JSONParser;

public class JsonUtils {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object getJsonFromURL(String url, String name) {
        try {
            ServerProxy.getCacheManager().downloadAndStockInCache(url, name);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        JSONParser ps = new JSONParser();
        if (!ServerProxy.getCacheManager().isCacheExist(name)) return null;
        try (FileReader rd = new FileReader(ServerProxy.getCacheManager().getCachedFileFromName(name));){
            Object object = ps.parse(rd);
            return object;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

