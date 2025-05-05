package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet19EntityAction;

public class SleepGUI extends ChatGUI {

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new GuiButton(256, this.field_73880_f / 2 - 100, this.field_73881_g - 40, I18n.func_135053_a("multiplayer.stopSleeping")));
   }

   protected void func_73869_a(char par1, int par2) {
      if(par2 == 1) {
         this.wakeEntity();
      } else if(par2 != 28 && par2 != 156) {
         super.func_73869_a(par1, par2);
      } else {
         String s = this.field_73901_a.func_73781_b().trim();
         if(s.length() > 0) {
            this.field_73882_e.field_71439_g.func_71165_d(s);
         }

         this.field_73901_a.func_73782_a("");
         this.field_73882_e.field_71456_v.func_73827_b().func_73764_c();
      }

   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton.field_73741_f == 256) {
         this.wakeEntity();
      } else {
         super.func_73875_a(par1GuiButton);
      }

   }

   private void wakeEntity() {
      NetClientHandler netclienthandler = this.field_73882_e.field_71439_g.field_71174_a;
      netclienthandler.func_72552_c(new Packet19EntityAction(this.field_73882_e.field_71439_g, 3));
   }
}
