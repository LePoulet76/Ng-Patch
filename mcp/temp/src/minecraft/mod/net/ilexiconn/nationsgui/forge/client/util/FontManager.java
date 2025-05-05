package net.ilexiconn.nationsgui.forge.client.util;

import java.awt.Font;
import net.halalaboos.cfont.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {

   public static CFontRenderer createFont(String modID, String name) {
      try {
         CFontRenderer e = new CFontRenderer(Font.createFont(0, Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation(modID, "fonts/" + name)).func_110527_b()), true, false);
         return e;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static CFontRenderer createClientHookFontDungeons() {
      try {
         CFontRenderer e = new CFontRenderer(Font.createFont(0, FontManager.class.getResourceAsStream("/assets/nationsgui/fonts/minecraftDungeons.ttf")), true, false);

         for(char c = 32; c < 127; ++c) {
            e.getStringWidth(String.valueOf(c));
         }

         return e;
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static CFontRenderer createClientHookFontGeorama() {
      try {
         CFontRenderer e = new CFontRenderer(Font.createFont(0, FontManager.class.getResourceAsStream("/assets/nationsgui/fonts/georamaSemiBold.ttf")), true, false);

         for(char c = 32; c < 127; ++c) {
            e.getStringWidth(String.valueOf(c));
         }

         return e;
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
