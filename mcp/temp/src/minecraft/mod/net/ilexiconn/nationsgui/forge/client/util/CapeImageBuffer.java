package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.minecraft.client.renderer.IImageBuffer;

@SideOnly(Side.CLIENT)
public class CapeImageBuffer implements IImageBuffer {

   public BufferedImage func_78432_a(BufferedImage unprocessed) {
      if(unprocessed == null) {
         return null;
      } else {
         BufferedImage result = new BufferedImage(64, 32, 2);
         Graphics graphics = result.getGraphics();
         graphics.drawImage(unprocessed, 0, 0, (ImageObserver)null);
         graphics.dispose();
         return result;
      }
   }
}
