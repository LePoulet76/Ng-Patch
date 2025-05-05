package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumOS;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

public class GuiScreenshotOptions extends GuiScreen {

   private GuiScreen previous;
   private GuiButton datedButton;


   public GuiScreenshotOptions(GuiScreen previous) {
      this.previous = previous;
   }

   public void func_73866_w_() {
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(231, this.field_73880_f / 2 - 157, this.field_73881_g / 6 - 12, 150, 20, I18n.func_135053_a("options.screenshots.openScreenshot")));
      this.field_73887_h.add(this.datedButton = new GuiButton(541, this.field_73880_f / 2 + 4, this.field_73881_g / 6 - 12, 150, 20, I18n.func_135053_a("options.screenshots.datedScreenshot")));
      this.field_73887_h.add(new GuiButton(100, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 38, I18n.func_135053_a("gui.done")));
   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("options.screenshots"), this.field_73880_f / 2, 15, 16777215);
      this.datedButton.field_73744_e = I18n.func_135053_a("options.screenshots.datedScreenshot") + " " + (NBTConfig.CONFIG.getCompound().func_74767_n("DatedScreenshot")?I18n.func_135053_a("options.screenshots.datedScreenshot.dated"):I18n.func_135053_a("options.screenshots.datedScreenshot.notdated"));
      super.func_73863_a(par1, par2, par3);
   }

   protected void func_73875_a(GuiButton button) {
      super.func_73875_a(button);
      switch(button.field_73741_f) {
      case 100:
         this.field_73882_e.func_71373_a(this.previous);
         break;
      case 231:
         File file = new File(Minecraft.func_71410_x().field_71412_D, "screenshots");
         String string = file.getAbsolutePath();
         if(Util.func_110647_a() == EnumOS.MACOS) {
            try {
               this.field_73882_e.func_98033_al().func_98233_a(string);
               Runtime.getRuntime().exec(new String[]{"/usr/bin/open", string});
               return;
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         } else if(Util.func_110647_a() == EnumOS.WINDOWS) {
            String bl = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{string});

            try {
               Runtime.getRuntime().exec(bl);
               return;
            } catch (IOException var8) {
               var8.printStackTrace();
            }
         }

         boolean bl1 = false;

         try {
            Class throwable = Class.forName("java.awt.Desktop");
            Object object = throwable.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            throwable.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{file.toURI()});
         } catch (Throwable var7) {
            var7.printStackTrace();
            bl1 = true;
         }

         if(bl1) {
            this.field_73882_e.func_98033_al().func_98233_a("Opening via system class!");
            Sys.openURL("file://" + string);
         }
         break;
      case 541:
         NBTConfig.CONFIG.getCompound().func_74757_a("DatedScreenshot", !NBTConfig.CONFIG.getCompound().func_74767_n("DatedScreenshot"));
      }

   }

   public void func_73874_b() {
      NBTConfig.CONFIG.save();
   }
}
