package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelAventDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelAventGivePacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NoelAventGui extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static int COLOR_GOLD = 13279592;
   public static HashMap<Integer, String> gifts = new HashMap();
   public static long serverTime = 0L;
   public long lastMusicCheck = 0L;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   public static List<Integer> daysOrder = Arrays.asList(new Integer[]{Integer.valueOf(22), Integer.valueOf(16), Integer.valueOf(20), Integer.valueOf(9), Integer.valueOf(23), Integer.valueOf(2), Integer.valueOf(6), Integer.valueOf(13), Integer.valueOf(3), Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(11), Integer.valueOf(15), Integer.valueOf(14), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(17), Integer.valueOf(4), Integer.valueOf(21), Integer.valueOf(7), Integer.valueOf(10), Integer.valueOf(5), Integer.valueOf(19), Integer.valueOf(24)});
   public String hoveredAction = "";
   protected int xSize = 463;
   protected int ySize = 235;
   public ArrayList<String> stars = new ArrayList();
   public static String currentDay = "";
   public static String currentMonth = "";


   public NoelAventGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NoelAventDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      if(System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
         this.lastMusicCheck = System.currentTimeMillis();
         if(ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N336_56N2y.mp3");
            ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15F);
            (new Thread(ClientProxy.commandPlayer)).start();
         }
      }

      this.func_73873_v_();
      this.hoveredAction = "";
      ArrayList tooltipToDraw = new ArrayList();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("noel_avent");
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 31), (float)(this.guiTop + 49), (float)(19 * GUI_SCALE), (float)(345 * GUI_SCALE), 109 * GUI_SCALE, 4 * GUI_SCALE, 109, 4, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      long december1 = 1701385200000L;
      long december23 = 1703329200000L;
      float progress = (float)Math.max(0L, december23 - System.currentTimeMillis()) / ((float)december23 - (float)december1 * 1.0F);
      progress = 1.0F - Math.min(1.0F, progress);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 31), (float)(this.guiTop + 49), (float)(19 * GUI_SCALE), (float)(336 * GUI_SCALE), (int)(109.0F * progress) * GUI_SCALE, 4 * GUI_SCALE, (int)(109.0F * progress), 4, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      if(this.stars.size() < 30) {
         Random newStars = new Random();
         String foundMissedDay = newStars.nextInt(450) + "#" + newStars.nextInt(75) + "#" + System.currentTimeMillis() + "#" + (System.currentTimeMillis() + (long)(newStars.nextInt(2000) + 1000)) + "#" + (newStars.nextInt(3) + 1) + "#" + (newStars.nextInt(80) + 20);
         this.stars.add(foundMissedDay);
      }

      ArrayList var19 = new ArrayList();
      Iterator var20 = this.stars.iterator();

      while(var20.hasNext()) {
         String i = (String)var20.next();
         GL11.glPushMatrix();
         String[] offsetX = i.split("#");
         float offsetY = (float)(System.currentTimeMillis() - Long.parseLong(offsetX[2])) * 1.0F / (float)(Long.parseLong(offsetX[3]) - Long.parseLong(offsetX[2]));
         offsetY *= (float)Integer.parseInt(offsetX[5]) / 100.0F;
         GL11.glScalef(offsetY, offsetY, offsetY);
         GL11.glTranslatef((float)(this.guiLeft + 5 + Integer.parseInt(offsetX[0])) * (1.0F / offsetY) - 7.0F * offsetY, (float)(this.guiTop + 5 + Integer.parseInt(offsetX[1])) * (1.0F / offsetY) - 7.0F * offsetY, 0.0F);
         ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, (float)(488 * GUI_SCALE), (float)((104 + (14 * Integer.parseInt(offsetX[4]) - 14)) * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         GL11.glPopMatrix();
         if(System.currentTimeMillis() < Long.parseLong(offsetX[3])) {
            var19.add(i);
         }
      }

      this.stars = var19;
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 163), (float)(this.guiTop + 0), (float)((this.field_73882_e.field_71474_y.field_74363_ab.startsWith("fr_")?43:195) * GUI_SCALE), (float)(247 * GUI_SCALE), 128 * GUI_SCALE, 68 * GUI_SCALE, 128, 68, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("noel_avent.subtitle.left").toUpperCase(), (float)(this.guiLeft + 32), (float)(this.guiTop + 34), COLOR_GOLD, 0.5F, "left", false, "georamaSemiBold", 22, 7, 250);
      ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("noel_avent.subtitle.right").toUpperCase(), (float)(this.guiLeft + this.xSize - 32), (float)(this.guiTop + 34), COLOR_GOLD, 0.5F, "right", false, "georamaSemiBold", 22, 7, 200);
      ClientEventHandler.STYLE.bindTexture("noel_avent");
      if(mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 13), (float)(490 * GUI_SCALE), (float)(147 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         this.hoveredAction = "close";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 13), (float)(490 * GUI_SCALE), (float)(159 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      }

      if(loaded) {
         ClientEventHandler.STYLE.bindTexture("noel_avent");
         int var21 = 0;

         for(int var22 = 0; var22 < Math.min(24, daysOrder.size()); ++var22) {
            int var23 = var22 % 8;
            int var24 = var22 / 8;
            String giftDataStr = (String)gifts.get(daysOrder.get(var22));
            String[] giftData = giftDataStr.split("#");
            ClientEventHandler.STYLE.bindTexture("noel_avent");
            if(giftData[3].equals("false") && Integer.parseInt(currentDay) > ((Integer)daysOrder.get(var22)).intValue() && currentMonth.equals("12")) {
               ++var21;
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24 + var23 * 47), (float)(this.guiTop + 77 + var24 * 47), (float)(538 * GUI_SCALE), (float)(1 * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            } else if(giftData[3].equals("true") && Integer.parseInt(currentDay) >= ((Integer)daysOrder.get(var22)).intValue()) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24 + var23 * 47), (float)(this.guiTop + 77 + var24 * 47), (float)(488 * GUI_SCALE), (float)(1 * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 28 + var23 * 47), (float)(this.guiTop + 84 + var24 * 47), (float)(675 * GUI_SCALE), (float)(13 * GUI_SCALE), 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               if(giftData[0].equals("item")) {
                  int itemID = Integer.parseInt(giftData[1].split(":")[0]);
                  int meta = giftData[1].split(":").length > 2?Integer.parseInt(giftData[1].split(":")[1]):0;
                  GL11.glPushMatrix();
                  this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), new ItemStack(itemID, meta, 0), this.guiLeft + 36 + var23 * 47, this.guiTop + 83 + var24 * 47);
                  GL11.glPopMatrix();
                  GL11.glDisable(2896);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               } else if(giftData[0].equals("hat")) {
                  GL11.glPushMatrix();
                  GL11.glScalef(1.5F, 1.5F, 1.5F);
                  GL11.glPopMatrix();
                  GL11.glDisable(2896);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               } else if(!giftData[0].equals("cape") && giftData[0].equals("badge") && NationsGUI.BADGES_RESOURCES.containsKey(giftData[1])) {
                  Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(giftData[1]));
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 37 + var23 * 47), (float)(this.guiTop + 81 + var24 * 47), 0.0F, 0.0F, 18, 18, 14, 14, 18.0F, 18.0F, false);
               }

               ClientEventHandler.STYLE.bindTexture("noel_avent");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 28 + var23 * 47), (float)(this.guiTop + 84 + var24 * 47), (float)(633 * GUI_SCALE), (float)(13 * GUI_SCALE), 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               if(mouseX >= this.guiLeft + 24 + var23 * 47 && mouseX <= this.guiLeft + 24 + var23 * 47 + 43 && mouseY >= this.guiTop + 86 + var24 * 47 && mouseY <= this.guiTop + 86 + var24 * 47 + 43) {
                  tooltipToDraw.add("\u00a74Jour " + daysOrder.get(var22));
                  tooltipToDraw.add("\u00a7c" + giftData[2]);
               }
            } else if(mouseX >= this.guiLeft + 24 + var23 * 47 && mouseX <= this.guiLeft + 24 + var23 * 47 + 43 && mouseY >= this.guiTop + 86 + var24 * 47 && mouseY <= this.guiTop + 86 + var24 * 47 + 43) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 18 + var23 * 47), (float)(this.guiTop + 71 + var24 * 47), (float)((((Integer)daysOrder.get(var22)).intValue() != 24?533:585) * GUI_SCALE), (float)(47 * GUI_SCALE), 50 * GUI_SCALE, 50 * GUI_SCALE, 50, 50, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               ModernGui.drawScaledStringCustomFont(daysOrder.get(var22) + "", (float)(this.guiLeft + 44 + var23 * 47), (float)(this.guiTop + 86 + var24 * 47), var22 != daysOrder.size() - 1?COLOR_GOLD:15788512, 1.0F, "center", false, "georamaExtraBold", 46);
               this.hoveredAction = "give#" + daysOrder.get(var22);
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24 + var23 * 47), (float)(this.guiTop + 77 + var24 * 47), (float)((((Integer)daysOrder.get(var22)).intValue() != 24?487:642) * GUI_SCALE), (float)(52 * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               ModernGui.drawScaledStringCustomFont(daysOrder.get(var22) + "", (float)(this.guiLeft + 45 + var23 * 47), (float)(this.guiTop + 86 + var24 * 47), var22 != daysOrder.size() - 1?COLOR_GOLD:15788512, 1.0F, "center", false, "georamaExtraBold", 44);
            }
         }

         ClientEventHandler.STYLE.bindTexture("noel_avent");
         if(var21 > 5) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 77), (float)(538 * GUI_SCALE), (float)(108 * GUI_SCALE), 40 * GUI_SCALE, 134 * GUI_SCALE, 40, 134, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         } else if(((String)gifts.get(Integer.valueOf(25))).endsWith("true")) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 77), (float)(637 * GUI_SCALE), (float)(108 * GUI_SCALE), 40 * GUI_SCALE, 134 * GUI_SCALE, 40, 134, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 404), (float)(this.guiTop + 131), (float)(675 * GUI_SCALE), (float)(13 * GUI_SCALE), 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            GL11.glPushMatrix();
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            GL11.glPopMatrix();
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("noel_avent");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 404), (float)(this.guiTop + 131), (float)(633 * GUI_SCALE), (float)(13 * GUI_SCALE), 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            if(mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 40 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 134) {
               tooltipToDraw.add("\u00a74Jour 25");
               tooltipToDraw.add("\u00a7c" + ((String)gifts.get(Integer.valueOf(25))).split("#")[2]);
            }
         } else {
            if(mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 40 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 134) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 395), (float)(this.guiTop + 72), (float)(582 * GUI_SCALE), (float)(103 * GUI_SCALE), 50 * GUI_SCALE, 154 * GUI_SCALE, 50, 154, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               this.hoveredAction = "give#25";
            }

            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.guiLeft + 417), (float)(this.guiTop + 106), 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float)(-(this.guiLeft + 417)), (float)(-(this.guiTop + 106)), 0.0F);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("noel_avent.mega_cadeau").toUpperCase(), (float)(this.guiLeft + 380), (float)(this.guiTop + 106), var21 > 5?9045025:15788512, 0.5F, "center", false, "georamaSemiBold", 30);
            GL11.glPopMatrix();
         }
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
         } else if(loaded && this.hoveredAction.contains("give#")) {
            int day = Integer.parseInt(this.hoveredAction.replaceAll("give#", ""));
            if(Integer.parseInt(currentDay) == day && currentMonth.equals("12")) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NoelAventGivePacket()));
               this.field_73882_e.field_71416_A.func_77366_a("random.levelup", 1.0F, 1.0F);
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            } else {
               this.field_73882_e.field_71416_A.func_77366_a("mob.villager.no", 1.0F, 1.0F);
            }
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
   }

}
