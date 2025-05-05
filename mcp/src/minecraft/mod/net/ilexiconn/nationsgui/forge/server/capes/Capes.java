package net.ilexiconn.nationsgui.forge.server.capes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.util.JsonUtils;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Capes
{
    public static List<Cape> capesList;

    public static void refresh()
    {
        capesList = getCapesFromURL("https://apiv2.nationsglory.fr/json/capes/capes.json");
    }

    public static List<Cape> getCapesFromURL(String url)
    {
        ArrayList capes = new ArrayList();
        JSONArray list = (JSONArray)JsonUtils.getJsonFromURL(url, "capes.json");

        if (list != null)
        {
            System.out.println("Parsing capes...");
            Iterator var3 = list.iterator();

            while (var3.hasNext())
            {
                Object o = var3.next();

                if (o instanceof JSONObject)
                {
                    Cape cape = Cape.getCapeFromJSon((JSONObject)o);

                    if (cape != null)
                    {
                        capes.add(cape);

                        try
                        {
                            ServerProxy.getCacheManager().downloadAndStockInCache(cape.getTextureURL(), cape.getIdentifier() + ".png");
                        }
                        catch (IOException var7)
                        {
                            var7.printStackTrace();
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Fail Parsing capes...");
        }

        return capes;
    }

    public static Cape getCapeFromIdentifier(String data)
    {
        if (capesList != null)
        {
            Iterator var1 = capesList.iterator();

            while (var1.hasNext())
            {
                Cape cape = (Cape)var1.next();

                if (data.equalsIgnoreCase(cape.getIdentifier()))
                {
                    return cape;
                }
            }
        }

        return null;
    }
}
