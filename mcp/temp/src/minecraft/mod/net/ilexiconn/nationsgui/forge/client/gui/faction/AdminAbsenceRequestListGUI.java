package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.AbsenceRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAdminAbsenceListPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class AdminAbsenceRequestListGUI extends GuiScreen {

   public static ArrayList<HashMap<String, String>> absenceInfos = new ArrayList();
   public static boolean loaded = false;
   public static List<String> availableStatus = Arrays.asList(new String[]{"all", "waiting_validation", "in_progress", "refused", "cancelled", "finished", "accepted"});
   public String hoveredRequest = "";
   public String hoveredStatus = "";
   public String selectedStatus = "";
   public boolean expandStatus = false;
   protected int xSize = 319;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   private GuiScrollBarFaction scrollBarRequests;
   private GuiTextField countrySearch;


   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      absenceInfos.clear();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionAdminAbsenceListPacket()));
      this.scrollBarRequests = new GuiScrollBarFaction((float)(this.guiLeft + 303), (float)(this.guiTop + 55), 172);
      this.countrySearch = new GuiTextField(this.field_73886_k, this.guiLeft + 209, this.guiTop + 23, 97, 10);
      this.countrySearch.func_73786_a(false);
      this.countrySearch.func_73804_f(25);
      this.selectedStatus = (String)availableStatus.get(0);
   }

   public void func_73876_c() {
      this.countrySearch.func_73780_a();
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      this.hoveredRequest = "";
      this.hoveredStatus = "";
      ClientEventHandler.STYLE.bindTexture("faction_war_requests");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 258, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 248, 9, 10, 512.0F, 512.0F, false);
      }

      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 210), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 16)), (float)(-(this.guiTop + 210)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("faction.absence.requests.title"), this.guiLeft + 14, this.guiTop + 210, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      this.countrySearch.func_73795_f();
      if(loaded && absenceInfos.size() > 0) {
         String tooltipToDraw = "";
         GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 53, 256, 176);
         int yOffset = 0;
         Iterator i = absenceInfos.iterator();

         while(i.hasNext()) {
            HashMap countryInfos = (HashMap)i.next();
            String countryName = (String)countryInfos.get("name");
            String absenceStatus = (String)countryInfos.get("status");
            String absenceReason = (String)countryInfos.get("reason");
            String absenceStartTime = (String)countryInfos.get("startTime");
            String absenceEndTime = (String)countryInfos.get("endTime");
            if((this.countrySearch.func_73781_b().isEmpty() || countryName.toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase())) && (this.selectedStatus.equals("all") || absenceStatus.startsWith(this.selectedStatus))) {
               int offsetX = this.guiLeft + 47;
               Float offsetY = Float.valueOf((float)(this.guiTop + 53 + yOffset) + this.getSlideWars());
               ClientEventHandler.STYLE.bindTexture("faction_war_requests");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, offsetY.floatValue(), 47, 53, 256, 48, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 178 && mouseX >= offsetX && mouseX <= offsetX + 256 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 48.0F) {
                  this.hoveredRequest = (String)countryInfos.get("playerName");
               }

               ClientEventHandler.STYLE.bindTexture("faction_war_requests");
               if(!ClientProxy.flagsTexture.containsKey(countryName)) {
                  if(!ClientProxy.base64FlagsByFactionName.containsKey(countryName)) {
                     ClientProxy.base64FlagsByFactionName.put(countryName, "");
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(countryName)));
                  } else if(ClientProxy.base64FlagsByFactionName.containsKey(countryName) && !((String)ClientProxy.base64FlagsByFactionName.get(countryName)).isEmpty()) {
                     BufferedImage startDate = ClientProxy.decodeToImage((String)ClientProxy.base64FlagsByFactionName.get(countryName));
                     ClientProxy.flagsTexture.put(countryName, new DynamicTexture(startDate));
                  }
               }

               if(ClientProxy.flagsTexture.containsKey(countryName)) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(countryName)).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 7), (float)(offsetY.intValue() + 5), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
               }

               this.drawScaledString(countryName + " \u00a77(" + (String)countryInfos.get("playerName") + ")", offsetX + 28, offsetY.intValue() + 7, 16777215, 1.0F, false, false);
               this.drawScaledString(I18n.func_135053_a("faction.absence.status." + absenceStatus), offsetX + 253 - this.field_73886_k.func_78256_a(I18n.func_135053_a("faction.absence.status." + absenceStatus)), offsetY.intValue() + 7, 16777215, 1.0F, false, false);
               this.drawScaledString(I18n.func_135053_a("faction.absence.reason." + absenceReason), offsetX + 7, offsetY.intValue() + 33, 16777215, 1.0F, false, false);
               Date var19 = new Date(Double.valueOf(absenceStartTime).longValue());
               Date endDate = new Date(Double.valueOf(absenceEndTime).longValue());
               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
               this.drawScaledString("\u00a7b" + dateFormat.format(var19), offsetX + 175 - this.field_73886_k.func_78256_a(dateFormat.format(var19)), offsetY.intValue() + 33, 16777215, 1.0F, false, false);
               this.drawScaledString("\u00a7b" + dateFormat.format(endDate), offsetX + 195 - this.field_73886_k.func_78256_a(dateFormat.format(endDate)) + this.field_73886_k.func_78256_a(dateFormat.format(var19)), offsetY.intValue() + 33, 16777215, 1.0F, false, false);
               yOffset += 48;
            }
         }

         GUIUtils.endGLScissor();
         if(!this.expandStatus) {
            this.scrollBarRequests.draw(mouseX, mouseY);
         }

         this.drawScaledString(I18n.func_135053_a("faction.absence.status." + this.selectedStatus), this.guiLeft + 50, this.guiTop + 23, 16777215, 1.0F, false, false);
         if(this.expandStatus) {
            ClientEventHandler.STYLE.bindTexture("faction_war_requests");

            for(int var18 = 0; var18 < availableStatus.size(); ++var18) {
               ClientEventHandler.STYLE.bindTexture("faction_war_requests");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 36 + 19 * var18), 0, 311, 120, 20, 512.0F, 512.0F, false);
               this.drawScaledString(I18n.func_135053_a("faction.absence.status." + (String)availableStatus.get(var18)), this.guiLeft + 50, this.guiTop + 36 + 19 * var18 + 6, 16777215, 1.0F, false, false);
               if(mouseX > this.guiLeft + 46 && mouseX < this.guiLeft + 46 + 120 && mouseY > this.guiTop + 36 + 19 * var18 && mouseY < this.guiTop + 36 + 19 * var18 + 20) {
                  this.hoveredStatus = (String)availableStatus.get(var18);
               }
            }
         }
      }

   }

   private float getSlideWars() {
      int countWars = 0;
      Iterator var2 = absenceInfos.iterator();

      while(var2.hasNext()) {
         HashMap countryInfos = (HashMap)var2.next();
         String countryName = (String)countryInfos.get("name");
         String absenceStatus = (String)countryInfos.get("status");
         if((this.countrySearch.func_73781_b().isEmpty() || countryName.toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase())) && (this.selectedStatus.equals("all") || absenceStatus.startsWith(this.selectedStatus))) {
            ++countWars;
         }
      }

      return countWars > 3?(float)(-(countWars - 3) * 48) * this.scrollBarRequests.getSliderValue():0.0F;
   }

   public void drawTooltip(String text, int mouseX, int mouseY) {
      int var10000 = mouseX - this.guiLeft;
      var10000 = mouseY - this.guiTop;
      this.drawHoveringText(Arrays.asList(new String[]{text}), mouseX, mouseY, this.field_73886_k);
   }

   private HashMap<String, String> getCountryInfoFromName(String name) {
      Iterator var2 = absenceInfos.iterator();

      HashMap countryInfos;
      String playerName;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         countryInfos = (HashMap)var2.next();
         playerName = (String)countryInfos.get("playerName");
      } while(!name.equalsIgnoreCase(playerName));

      return countryInfos;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a((GuiScreen)null);
         }

         if(!this.expandStatus && this.hoveredRequest != "") {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new AbsenceRequestGUI(this.hoveredRequest, this.getCountryInfoFromName(this.hoveredRequest), this));
         }

         if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 120 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20) {
            this.expandStatus = !this.expandStatus;
         }

         if(this.hoveredStatus != null && !this.hoveredStatus.isEmpty()) {
            this.selectedStatus = this.hoveredStatus;
            this.hoveredStatus = "";
            this.expandStatus = false;
            this.scrollBarRequests.reset();
         }
      }

      this.countrySearch.func_73793_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if(this.countrySearch.func_73802_a(typedChar, keyCode)) {
         this.scrollBarRequests.reset();
      }

      super.func_73869_a(typedChar, keyCode);
   }

   private String formatDiff(long diff) {
      String date = "";
      long days = diff / 86400000L;
      long hours = 0L;
      long minutes = 0L;
      long seconds = 0L;
      if(days == 0L) {
         hours = diff / 3600000L;
         if(hours == 0L) {
            minutes = diff / 60000L;
            if(minutes == 0L) {
               seconds = diff / 1000L;
               date = date + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds") + " " + I18n.func_135053_a("faction.bank.date_2");
            } else {
               date = date + " " + minutes + " " + I18n.func_135053_a("faction.common.minutes") + " " + I18n.func_135053_a("faction.bank.date_2");
            }
         } else {
            date = date + " " + hours + " " + I18n.func_135053_a("faction.common.hours") + " " + I18n.func_135053_a("faction.bank.date_2");
         }
      } else {
         date = date + " " + days + " " + I18n.func_135053_a("faction.common.days") + " " + I18n.func_135053_a("faction.bank.date_2");
      }

      return date;
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

}
