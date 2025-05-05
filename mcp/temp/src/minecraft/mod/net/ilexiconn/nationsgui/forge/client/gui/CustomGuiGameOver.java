package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SpawnPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class CustomGuiGameOver extends GuiScreen {

   private int cooldownTimer;


   public void func_73866_w_() {
      this.field_73887_h.clear();
      if(this.field_73882_e.field_71441_e.func_72912_H().func_76093_s()) {
         if(this.field_73882_e.func_71387_A()) {
            this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a("deathScreen.deleteWorld")));
         } else {
            this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a("deathScreen.leaveServer")));
         }
      } else {
         if(this.field_73882_e.func_71387_A()) {
            this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 72, I18n.func_135053_a("deathScreen.respawn")));
         } else {
            this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 72, 98, 20, I18n.func_135053_a("deathScreen.respawn.spawn")));
            this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 + 2, this.field_73881_g / 4 + 72, 98, 20, I18n.func_135053_a("deathScreen.respawn.faction")));
         }

         this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a("deathScreen.titleScreen")));
         if(this.field_73882_e.func_110432_I() == null) {
            ((GuiButton)this.field_73887_h.get(1)).field_73742_g = false;
         }
      }

      GuiButton guibutton;
      for(Iterator iterator = this.field_73887_h.iterator(); iterator.hasNext(); guibutton.field_73742_g = false) {
         guibutton = (GuiButton)iterator.next();
      }

   }

   protected void func_73869_a(char par1, int par2) {}

   protected void func_73875_a(GuiButton par1GuiButton) {
      SpawnPacket spawnPacket = new SpawnPacket();
      switch(par1GuiButton.field_73741_f) {
      case 0:
         spawnPacket.factionSpawn = true;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(spawnPacket));
         this.field_73882_e.field_71439_g.func_71004_bE();
         this.field_73882_e.func_71373_a((GuiScreen)null);
         break;
      case 1:
         if(!this.field_73882_e.func_71387_A()) {
            spawnPacket.factionSpawn = false;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(spawnPacket));
         }

         this.field_73882_e.field_71439_g.func_71004_bE();
         this.field_73882_e.func_71373_a((GuiScreen)null);
         break;
      case 2:
         if(this.field_73882_e.field_71441_e != null) {
            this.field_73882_e.field_71441_e.func_72882_A();
         }

         this.field_73882_e.func_71403_a((WorldClient)null);
         this.field_73882_e.func_71373_a(new GuiMainMenu());
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.func_73733_a(0, 0, this.field_73880_f, this.field_73881_g, 1615855616, -1602211792);
      GL11.glPushMatrix();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      boolean flag = this.field_73882_e.field_71441_e.func_72912_H().func_76093_s();
      String s = flag?I18n.func_135053_a("deathScreen.title.hardcore"):I18n.func_135053_a("deathScreen.title");
      this.func_73732_a(this.field_73886_k, s, this.field_73880_f / 2 / 2, 30, 16777215);
      GL11.glPopMatrix();
      if(flag) {
         this.func_73732_a(this.field_73886_k, I18n.func_135053_a("deathScreen.hardcoreInfo"), this.field_73880_f / 2, 144, 16777215);
      }

      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("deathScreen.score") + ": " + EnumChatFormatting.YELLOW + this.field_73882_e.field_71439_g.func_71037_bA(), this.field_73880_f / 2, 100, 16777215);
      super.func_73863_a(par1, par2, par3);
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.cooldownTimer;
      GuiButton guibutton;
      if(this.cooldownTimer == 20) {
         for(Iterator iterator = this.field_73887_h.iterator(); iterator.hasNext(); guibutton.field_73742_g = true) {
            guibutton = (GuiButton)iterator.next();
         }
      }

   }
}
