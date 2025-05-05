package net.ilexiconn.nationsgui.forge.client.util;

import java.nio.IntBuffer;
import net.ilexiconn.nationsgui.forge.client.util.ScreenshotHelper$1;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenshotHelper {

   public static void saveScreenshotToBase64(int par2, int par3) {
      int k = par2 * par3;
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      IntBuffer field_74293_b = BufferUtils.createIntBuffer(k);
      GL11.glReadPixels(0, 0, par2, par3, '\u80e1', '\u8367', field_74293_b);
      Thread thread = new Thread(new ScreenshotHelper$1(k, field_74293_b, par2, par3));
      thread.start();
   }

   private static void func_74289_a(int[] par0ArrayOfInteger, int par1, int par2) {
      int[] aint1 = new int[par1];
      int k = par2 / 2;

      for(int l = 0; l < k; ++l) {
         System.arraycopy(par0ArrayOfInteger, l * par1, aint1, 0, par1);
         System.arraycopy(par0ArrayOfInteger, (par2 - 1 - l) * par1, par0ArrayOfInteger, l * par1, par1);
         System.arraycopy(aint1, 0, par0ArrayOfInteger, (par2 - 1 - l) * par1, par1);
      }

   }

   // $FF: synthetic method
   static void access$000(int[] x0, int x1, int x2) {
      func_74289_a(x0, x1, x2);
   }
}
