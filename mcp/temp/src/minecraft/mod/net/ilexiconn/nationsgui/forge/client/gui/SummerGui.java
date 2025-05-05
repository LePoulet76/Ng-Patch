package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SummerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SummerTradePacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SummerGui extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static boolean loaded = false;
   public static HashMap<String, Object> data = new HashMap();
   public String hoveredAction = "";
   public long lastMusicCheck = 0L;
   protected int xSize = 232;
   protected int ySize = 231;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();


   public SummerGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SummerDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      if(System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
         this.lastMusicCheck = System.currentTimeMillis();
         if(ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4N66N3y2_.mp3");
            ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.35F);
            (new Thread(ClientProxy.commandPlayer)).start();
         }
      }

      this.func_73873_v_();
      this.hoveredAction = "";
      ArrayList tooltipToDraw = new ArrayList();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("summer");
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.summer.title"), (float)(this.guiLeft + 117), (float)(this.guiTop + 40), 7033896, 0.75F, "center", true, "minecraftDungeons", 23);
      ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("gui.summer.description"), (float)(this.guiLeft + 117), (float)(this.guiTop + 65), 7033896, 0.75F, "center", false, "georamaMedium", 23, 10, 210);
      ClientEventHandler.STYLE.bindTexture("summer");
      if(mouseX >= this.guiLeft + 235 && mouseX <= this.guiLeft + 235 + 16 && mouseY >= this.guiTop + 2 && mouseY <= this.guiTop + 2 + 16) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 235), (float)(this.guiTop + 2), (float)(256 * GUI_SCALE), (float)(106 * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         this.hoveredAction = "close";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 235), (float)(this.guiTop + 2), (float)(235 * GUI_SCALE), (float)(2 * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      }

      if(loaded) {
         ClientEventHandler.STYLE.bindTexture("summer");
         int playerPoints = ((Double)data.get("playerPoints")).intValue();
         int playerTradedPoints = ((Double)data.get("playerTradedPoints")).intValue();
         float progress = (float)(playerPoints - playerTradedPoints) / 20.0F;
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 32), (float)(this.guiTop + 122), (float)(256 * GUI_SCALE), (float)(0 * GUI_SCALE), (int)(148.0F * progress) * GUI_SCALE, 7 * GUI_SCALE, (int)(148.0F * progress), 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         if(progress >= 1.0F) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 180), (float)(this.guiTop + 115), (float)(258 * GUI_SCALE), (float)(16 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }

         ModernGui.drawScaledStringCustomFont(playerPoints + "/" + String.format("%.0f", new Object[]{(Double)data.get("totalPoints")}), (float)(this.guiLeft + 112), (float)(this.guiTop + 136), 7033896, 0.5F, "center", false, "georamaSemiBold", 33);
         boolean isButtonHovered = mouseX >= this.guiLeft + 32 && mouseX <= this.guiLeft + 32 + 168 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20;
         if(progress >= 1.0F) {
            ClientEventHandler.STYLE.bindTexture("summer");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 32), (float)(this.guiTop + 165), (float)(256 * GUI_SCALE), (float)((isButtonHovered?76:46) * GUI_SCALE), 168 * GUI_SCALE, 20 * GUI_SCALE, 168, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            if(isButtonHovered) {
               this.hoveredAction = "get_lootbox";
            }
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.summer.get_lootbox"), (float)(this.guiLeft + 117), (float)(this.guiTop + 172), progress < 1.0F?11970120:4478212, 0.75F, "center", false, "georamaMedium", 24);
      }

      if(!tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void func_73874_b() {
      if(ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
         ClientProxy.commandPlayer.softClose();
      }

      super.func_73874_b();
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(this.hoveredAction.equalsIgnoreCase("get_lootbox")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SummerTradePacket()));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
   }

}
