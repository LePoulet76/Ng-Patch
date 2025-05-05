package net.ilexiconn.nationsgui.forge.client.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import org.json.simple.parser.JSONParser;

public class JsonUtils {

   public static Object getJsonFromURL(String url, String name) {
      try {
         ServerProxy.getCacheManager().downloadAndStockInCache(url, name);
      } catch (IOException var17) {
         var17.printStackTrace();
      }

      JSONParser ps = new JSONParser();
      if(ServerProxy.getCacheManager().isCacheExist(name)) {
         try {
            FileReader e = new FileReader(ServerProxy.getCacheManager().getCachedFileFromName(name));
            Throwable var4 = null;

            Object var5;
            try {
               var5 = ps.parse((Reader)e);
            } catch (Throwable var16) {
               var4 = var16;
               throw var16;
            } finally {
               if(e != null) {
                  if(var4 != null) {
                     try {
                        e.close();
                     } catch (Throwable var15) {
                        var4.addSuppressed(var15);
                     }
                  } else {
                     e.close();
                  }
               }

            }

            return var5;
         } catch (Exception var19) {
            var19.printStackTrace();
         }
      }

      return null;
   }
}
