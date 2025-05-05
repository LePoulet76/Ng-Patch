package net.ilexiconn.nationsgui.forge.client.cache;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;

public class CachedResource {

   private URL url;
   protected File file;


   public CachedResource(String url) {
      if(url != "" && url != null) {
         try {
            this.url = new URL(url);
         } catch (MalformedURLException var4) {
            var4.printStackTrace();
         }

         try {
            this.file = ClientProxy.getCacheManager().downloadAndStockInCache(url, MD5(url.toString()));
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      }
   }

   public URL getURL() {
      return this.url;
   }

   public File getFile() {
      return this.file;
   }

   public static String MD5(String md5) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] array = md.digest(md5.getBytes());
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString(array[i] & 255 | 256).substring(1, 3));
         }

         return sb.toString();
      } catch (NoSuchAlgorithmException var5) {
         return "";
      }
   }

   public String getName() {
      return MD5(this.url.toString());
   }
}
