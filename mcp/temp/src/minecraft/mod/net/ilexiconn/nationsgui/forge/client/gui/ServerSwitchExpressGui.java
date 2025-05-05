package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.CustomConnectingGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

public class ServerSwitchExpressGui extends GuiScreen {

   private String address;
   private int port;
   private String serverName;
   private int update = 0;


   public ServerSwitchExpressGui(String address, int port, String serverName) {
      this.address = address;
      this.port = port;
      this.serverName = serverName;
   }

   public void func_73876_c() {
      if(this.update < 2) {
         if(this.update == 1) {
            Minecraft mc = Minecraft.func_71410_x();
            if(mc.func_71391_r() != null) {
               mc.func_71391_r().func_72553_e();
               mc.func_71403_a((WorldClient)null);
            }

            Minecraft.func_71410_x().func_71373_a(new CustomConnectingGui(new MainGUI(), this.address, this.port, this.serverName));
         }

         ++this.update;
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_join_all");
      ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0F, 2160.0F, false);
   }
}
