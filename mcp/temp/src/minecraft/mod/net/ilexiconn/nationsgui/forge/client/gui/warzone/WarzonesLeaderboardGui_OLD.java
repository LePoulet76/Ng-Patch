package net.ilexiconn.nationsgui.forge.client.gui.warzone;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WarzonesLeaderboardGui_OLD extends GuiScreen {

   protected int xSize = 325;
   protected int ySize = 238;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   public static HashMap<String, Object> infos = new HashMap();
   public String tabToDisplay = "daily";
   private GuiScrollBarFaction scrollBar;


   public WarzonesLeaderboardGui_OLD() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzonesLeaderboardDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 309), (float)(this.guiTop + 91), 131);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("warzones");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 274, this.xSize, this.ySize, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 140), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 140)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("warzones.leader.title"), this.guiLeft + 14, this.guiTop + 140, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      Object tooltipToDraw = new ArrayList();
      if(mouseX >= this.guiLeft + 313 && mouseX <= this.guiLeft + 313 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ClientEventHandler.STYLE.bindTexture("warzones");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 313), (float)(this.guiTop - 8), 110, 186, 9, 10, 512.0F, 512.0F, false);
      } else {
         ClientEventHandler.STYLE.bindTexture("warzones");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 313), (float)(this.guiTop - 8), 110, 176, 9, 10, 512.0F, 512.0F, false);
      }

      ClientEventHandler.STYLE.bindTexture("warzones");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 30), 0, 213, 23, 30, 512.0F, 512.0F, false);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 5), (float)(this.guiTop + 30 + 9), 2, 250, 13, 12, 512.0F, 512.0F, false);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 25), (float)(this.guiTop + 61), 23, 213, 29, 30, 512.0F, 512.0F, false);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 25 + 11), (float)(this.guiTop + 61 + 10), 24, 252, 9, 10, 512.0F, 512.0F, false);
      if(this.tabToDisplay.equals("daily")) {
         this.drawScaledString(I18n.func_135053_a("warzone.leaderboard.daily"), this.guiLeft + 83, this.guiTop + 78, 16777215, 1.0F, true, true);
         this.drawScaledString(I18n.func_135053_a("warzone.leaderboard.weekly"), this.guiLeft + 167, this.guiTop + 78, 11842740, 1.0F, true, true);
      } else {
         this.drawScaledString(I18n.func_135053_a("warzone.leaderboard.daily"), this.guiLeft + 83, this.guiTop + 78, 11842740, 1.0F, true, true);
         this.drawScaledString(I18n.func_135053_a("warzone.leaderboard.weekly"), this.guiLeft + 167, this.guiTop + 78, 16777215, 1.0F, true, true);
      }

      if(loaded && infos != null) {
         ArrayList data = (ArrayList)infos.get(this.tabToDisplay);
         if(data.size() > 0) {
            ArrayList lineInfos = new ArrayList(Arrays.asList(((String)data.get(0)).split("##")));
            this.drawScaledString((String)lineInfos.get(0) + " " + I18n.func_135053_a("warzone.leaderboard.first"), this.guiLeft + 123, this.guiTop + 39, 16777215, 1.0F, false, false);
            if(ClientProxy.flagsTexture.containsKey(lineInfos.get(0))) {
               GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(lineInfos.get(0))).func_110552_b());
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 53), (float)(this.guiTop + 33), 0.0F, 0.0F, 156, 78, 47, 26, 156.0F, 78.0F, false);
            }

            if(this.tabToDisplay.equals("daily") && ((Double)infos.get("dailyCountryPosition")).doubleValue() != 0.0D) {
               this.drawScaledString("\u00a77" + I18n.func_135053_a("warzone.leaderboard.your_position") + "\u00a7e" + ((Double)infos.get("dailyCountryPosition")).intValue(), this.guiLeft + 123, this.guiTop + 53, 16777215, 0.9F, false, false);
            } else if(this.tabToDisplay.equals("weekly") && ((Double)infos.get("weeklyCountryPosition")).doubleValue() != 0.0D) {
               this.drawScaledString("\u00a77" + I18n.func_135053_a("warzone.leaderboard.your_position") + "\u00a7e" + ((Double)infos.get("weeklyCountryPosition")).intValue(), this.guiLeft + 123, this.guiTop + 53, 16777215, 0.9F, false, false);
            } else {
               this.drawScaledString("\u00a77" + I18n.func_135053_a("warzone.leaderboard.no_position"), this.guiLeft + 123, this.guiTop + 53, 16777215, 0.9F, false, false);
            }

            int position = 1;
            GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 88, 264, 136);

            for(int i = 0; i < data.size(); ++i) {
               lineInfos = new ArrayList(Arrays.asList(((String)data.get(i)).split("##")));
               int offsetX = this.guiLeft + 45;
               int offsetY = (int)((float)(this.guiTop + 88 + i * 28) + this.getSlide());
               if(offsetY >= this.guiTop + 88 && offsetY <= this.guiTop + 224 && !ClientProxy.flagsTexture.containsKey(lineInfos.get(0))) {
                  if(!ClientProxy.base64FlagsByFactionName.containsKey(lineInfos.get(0))) {
                     ClientProxy.base64FlagsByFactionName.put(lineInfos.get(0), "");
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket((String)lineInfos.get(0))));
                  } else if(ClientProxy.base64FlagsByFactionName.containsKey(lineInfos.get(0)) && !((String)ClientProxy.base64FlagsByFactionName.get(lineInfos.get(0))).isEmpty()) {
                     BufferedImage image = ClientProxy.decodeToImage((String)ClientProxy.base64FlagsByFactionName.get(lineInfos.get(0)));
                     ClientProxy.flagsTexture.put(lineInfos.get(0), new DynamicTexture(image));
                  }
               }

               ClientEventHandler.STYLE.bindTexture("warzones");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 45, 362, 264, 28, 512.0F, 512.0F, false);
               if(ClientProxy.flagsTexture.containsKey(lineInfos.get(0))) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(lineInfos.get(0))).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 6), (float)(offsetY + 6), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
               }

               this.drawScaledString("\u00a77" + position + ". \u00a7f" + (String)lineInfos.get(0), offsetX + 37, offsetY + 10, 16777215, 1.0F, false, false);
               this.drawScaledString((String)lineInfos.get(1), offsetX + 260 - this.field_73886_k.func_78256_a((String)lineInfos.get(1)) - 13, offsetY + 10, 11842740, 1.0F, false, false);
               ClientEventHandler.STYLE.bindTexture("warzones");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 260 - 10), (float)(offsetY + 8), 96, 175, 10, 11, 512.0F, 512.0F, false);
               if(mouseX >= offsetX + 260 - 10 && mouseX <= offsetX + 260 - 10 + 10 && mouseY >= offsetY + 8 && mouseY <= offsetY + 8 + 11) {
                  tooltipToDraw = Arrays.asList(new String[]{"\u00a76Bateau: \u00a77" + (String)lineInfos.get(2), "\u00a76P\u00e9trole: \u00a77" + (String)lineInfos.get(3), "\u00a77---------", "\u00a7eKills: \u00a77" + (String)lineInfos.get(4), "\u00a7eMorts: \u00a77" + (String)lineInfos.get(5)});
               }

               ++position;
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
         }
      }

      if(mouseX >= this.guiLeft + 306 && mouseX <= this.guiLeft + 306 + 12 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 60 + 12) {
         tooltipToDraw = Arrays.asList(I18n.func_135053_a("warzone.leaderboard.help").split("##"));
      }

      if(!((List)tooltipToDraw).isEmpty()) {
         this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlide() {
      int counter = ((ArrayList)infos.get(this.tabToDisplay)).size();
      return counter > 5?(float)(-(counter - 5) * 28) * this.scrollBar.getSliderValue():0.0F;
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

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 313 && mouseX < this.guiLeft + 313 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft - 23 && mouseX < this.guiLeft - 23 + 23 && mouseY > this.guiTop + 30 && mouseY < this.guiTop + 30 + 30) {
            Minecraft.func_71410_x().func_71373_a(new WarzonesGui());
         }

         if(mouseX > this.guiLeft + 44 && mouseX < this.guiLeft + 44 + 79 && mouseY > this.guiTop + 75 && mouseY < this.guiTop + 75 + 12) {
            this.tabToDisplay = "daily";
         } else if(mouseX > this.guiLeft + 127 && mouseX < this.guiLeft + 127 + 79 && mouseY > this.guiTop + 75 && mouseY < this.guiTop + 75 + 12) {
            this.tabToDisplay = "weekly";
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean func_73868_f() {
      return false;
   }

}
