package net.ilexiconn.nationsgui.forge.client.cache;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.cache.CachedResource;

public class CachedTextureResource extends CachedResource {

   private int width;
   private int height;


   public CachedTextureResource(String url) {
      super(url);

      try {
         BufferedImage ima = ImageIO.read(this.file);
         this.width = ima.getWidth();
         this.height = ima.getHeight();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }
}
