package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
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
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerAbsenceRequestUpdateStatusPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AbsenceRequestGUI extends GuiScreen {

   public static HashMap<String, String> absenceInfos = new HashMap();
   public static boolean loaded = false;
   public static String playerName;
   public String hoveredAction = "";
   public HashMap<String, Integer> conditions = new HashMap();
   public HashMap<String, Integer> rewards = new HashMap();
   public boolean conditionsTypeOpened = false;
   public boolean conditionsOpened = false;
   public boolean rewardsOpened = false;
   public String hoveredConditionsType = "";
   public String hoveredAvailableCondition = "";
   public String hoveredCondition = "";
   public String hoveredAvailableReward = "";
   public String hoveredReward = "";
   public String selectedConditionsType = "and";
   public String selectedCondition = "";
   public List<String> availableConditions = Arrays.asList(new String[]{"exams", "vacation", "other"});
   public String selectedReward = "";
   public ArrayList<String> availableRewards = new ArrayList(Arrays.asList(new String[]{"dollars", "power", "claims", "peace"}));
   public HashMap<String, Integer> data_ATT = null;
   public HashMap<String, Integer> data_DEF = null;
   public String winner = null;
   protected int xSize = 319;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   private GuiTextField linkForumInput;
   private GuiTextField conditionInput;
   private GuiTextField rewardInput;
   private GuiScrollBarFaction scrollBarAvailableConditions;
   private GuiScrollBarFaction scrollBarAvailableRewards;
   private GuiScrollBarFaction scrollBarConditions;
   private GuiScrollBarFaction scrollBarRewards;
   private GuiScrollBarFaction scrollBarFinishConditions;
   private GuiScrollBarFaction scrollBarFinishRewards;
   private GuiScreen guiFrom;


   public AbsenceRequestGUI(String playerName, HashMap<String, String> absenceInfos, GuiScreen guiFrom) {
      playerName = playerName;
      absenceInfos = absenceInfos;
      this.guiFrom = guiFrom;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBarAvailableConditions = new GuiScrollBarFaction((float)(this.guiLeft + 280), (float)(this.guiTop + 100), 71);
      this.scrollBarAvailableRewards = new GuiScrollBarFaction((float)(this.guiLeft + 280), (float)(this.guiTop + 172), 71);
      this.scrollBarConditions = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 104), 41);
      this.scrollBarRewards = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 175), 41);
      this.scrollBarFinishConditions = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 99), 55);
      this.scrollBarFinishRewards = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 174), 55);
      this.linkForumInput = new GuiTextField(this.field_73886_k, this.guiLeft + 48, this.guiTop + 126, 237, 10);
      this.linkForumInput.func_73786_a(false);
      this.linkForumInput.func_73804_f(200);
      this.conditionInput = new GuiTextField(this.field_73886_k, this.guiLeft + 153, this.guiTop + 85, 47, 10);
      this.conditionInput.func_73786_a(false);
      this.conditionInput.func_73804_f(3);
      this.conditionInput.func_73782_a("0");
      this.rewardInput = new GuiTextField(this.field_73886_k, this.guiLeft + 150, this.guiTop + 157, 50, 10);
      this.rewardInput.func_73786_a(false);
      this.rewardInput.func_73804_f(7);
      this.rewardInput.func_73782_a("0");
      this.selectedCondition = (String)this.availableConditions.get(0);
      this.selectedReward = (String)this.availableRewards.get(0);
   }

   public void func_73876_c() {
      this.linkForumInput.func_73780_a();
      this.conditionInput.func_73780_a();
      this.rewardInput.func_73780_a();
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      this.hoveredAction = "";
      this.hoveredConditionsType = "";
      this.hoveredAvailableCondition = "";
      this.hoveredCondition = "";
      this.hoveredReward = "";
      this.hoveredAvailableReward = "";
      String absenceStartTime = (String)absenceInfos.get("startTime");
      String absenceEndTime = (String)absenceInfos.get("endTime");
      String tooltipToDraw = "";
      if(absenceInfos.size() > 0) {
         String MAIN_TEXTURE = this.bindTextureDependingOnStatus();
         ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
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
         this.drawScaledString(I18n.func_135053_a("faction.absence.title"), this.guiLeft + 14, this.guiTop + 210, 16777215, 1.5F, false, false);
         GL11.glPopMatrix();
         ClientProxy.loadCountryFlag((String)absenceInfos.get("name"));
         if(ClientProxy.flagsTexture.containsKey(absenceInfos.get("name"))) {
            GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(absenceInfos.get("name"))).func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 27), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
         }

         if(((String)absenceInfos.get("name")).contains("Empire")) {
            this.drawScaledString("Empire", this.guiLeft + 90, this.guiTop + 27, 16777215, 1.0F, false, false);
            this.drawScaledString(((String)absenceInfos.get("name")).replace("Empire", ""), this.guiLeft + 90, this.guiTop + 37, 16777215, 1.0F, false, false);
         } else {
            this.drawScaledString((String)absenceInfos.get("name"), this.guiLeft + 90, this.guiTop + 32, 16777215, 1.0F, false, false);
         }

         if(!ClientProxy.cacheHeadPlayer.containsKey(absenceInfos.get("playerName"))) {
            try {
               ResourceLocation startDate = AbstractClientPlayer.field_110314_b;
               startDate = AbstractClientPlayer.func_110311_f((String)absenceInfos.get("playerName"));
               AbstractClientPlayer.func_110304_a(startDate, (String)absenceInfos.get("playerName"));
               ClientProxy.cacheHeadPlayer.put(absenceInfos.get("playerName"), startDate);
            } catch (Exception var22) {
               System.out.println(var22.getMessage());
            }
         } else {
            Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
            this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 205 + 13, this.guiTop + 37 + 10, 8.0F, 16.0F, 8, -8, -27, -25, 64.0F, 64.0F);
         }

         this.drawScaledString((String)absenceInfos.get("playerName"), this.guiLeft + 225, this.guiTop + 32, 16777215, 1.0F, false, false);
         this.drawScaledString(((String)absenceInfos.get("playerRole")).toLowerCase(), this.guiLeft + 245, this.guiTop + 58, 16777215, 1.0F, true, false);
         Date var23 = new Date(Double.valueOf(absenceStartTime).longValue());
         SimpleDateFormat startDateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date endDate = new Date(Double.valueOf(absenceEndTime).longValue());
         SimpleDateFormat endDateFormat = new SimpleDateFormat("dd-MM-yyyy");
         this.drawScaledString("\u00a7b" + startDateFormat.format(var23), this.guiLeft + 110 - this.field_73886_k.func_78256_a(startDateFormat.format(var23)), this.guiTop + 125, 16777215, 1.0F, false, false);
         this.drawScaledString("\u00a7b" + endDateFormat.format(endDate), this.guiLeft + 245 - this.field_73886_k.func_78256_a(endDateFormat.format(endDate)) + this.field_73886_k.func_78256_a(startDateFormat.format(var23)), this.guiTop + 125, 16777215, 1.0F, false, false);
         String status = I18n.func_135053_a("faction.absence.status." + (String)absenceInfos.get("status"));
         this.drawScaledString(status, this.guiLeft + 108 + 6, this.guiTop + 58, 16777215, 1.0F, true, false);
         ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
         if(status.startsWith("\u00a72")) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8), (float)(this.guiTop + 56), 110, 251, 10, 11, 512.0F, 512.0F, false);
         } else if(status.startsWith("\u00a74")) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8), (float)(this.guiTop + 56), 120, 251, 10, 11, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8), (float)(this.guiTop + 56), 130, 251, 10, 11, 512.0F, 512.0F, false);
         }

         this.drawScaledString(I18n.func_135053_a("faction.absence.reason"), this.guiLeft + 46, this.guiTop + 78, 0, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("faction.absence.reason." + (String)absenceInfos.get("reason")), this.guiLeft + 50, this.guiTop + 94, 16777215, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("faction.enemy.creationDate"), this.guiLeft + 182, this.guiTop + 78, 0, 1.0F, false, false);
         Date date = new Date(Double.valueOf((String)absenceInfos.get("creationTime")).longValue());
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
         this.drawScaledString(simpleDateFormat.format(date), this.guiLeft + 186, this.guiTop + 94, 16777215, 1.0F, false, false);
         String[] descriptionWords = null;
         descriptionWords = I18n.func_135053_a("faction.absence.status.details." + (String)absenceInfos.get("status")).split(" ");
         String line = "";
         int lineNumber = 0;
         String[] var18 = descriptionWords;
         int var19 = descriptionWords.length;

         for(int var20 = 0; var20 < var19; ++var20) {
            String descWord = var18[var20];
            if((double)this.field_73886_k.func_78256_a(line + descWord) * 0.9D <= 190.0D) {
               if(!line.equals("")) {
                  line = line + " ";
               }

               line = line + descWord;
            } else {
               this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 16777215, 0.9F, false, false);
               ++lineNumber;
               line = descWord;
            }
         }

         this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 16777215, 0.9F, false, false);
         if(((String)absenceInfos.get("status")).equals("waiting_validation")) {
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
               this.hoveredAction = "staff_refuse";
            }

            this.drawScaledString(I18n.func_135053_a("faction.enemy.refuse_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 402, 127, 15, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 417, 127, 15, 512.0F, 512.0F, false);
               this.hoveredAction = "staff_accept";
            }

            this.drawScaledString(I18n.func_135053_a("faction.enemy.accept_request"), this.guiLeft + 245, this.guiTop + 223, 16777215, 1.0F, true, false);
         } else if(((String)absenceInfos.get("status")).equals("accepted")) {
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
               this.hoveredAction = "staff_cancel";
            }

            this.drawScaledString(I18n.func_135053_a("faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
         }

         if(((String)absenceInfos.get("status")).equals("in_progress") || ((String)absenceInfos.get("status")).equals("validated")) {
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
               this.hoveredAction = "cancel";
            }

            this.drawScaledString(I18n.func_135053_a("faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
         }
      }

      if(tooltipToDraw != "") {
         this.drawHoveringText(Arrays.asList(tooltipToDraw.split("##")), mouseX, mouseY, this.field_73886_k);
      }

   }

   private float getSlideAvailableConditions() {
      return this.availableConditions.size() > 4?(float)(-(this.availableConditions.size() - 4) * 19) * this.scrollBarAvailableConditions.getSliderValue():0.0F;
   }

   private float getSlideAvailableRewards() {
      return this.availableRewards.size() > 4?(float)(-(this.availableRewards.size() - 4) * 19) * this.scrollBarAvailableRewards.getSliderValue():0.0F;
   }

   private float getSlideConditions() {
      return this.conditions.size() > 2?(float)(-(this.conditions.size() - 2) * 23) * this.scrollBarConditions.getSliderValue():0.0F;
   }

   private float getSlideRewards() {
      return this.rewards.size() > 2?(float)(-(this.rewards.size() - 2) * 23) * this.scrollBarRewards.getSliderValue():0.0F;
   }

   private float getSlideFinishConditions() {
      return ((String)absenceInfos.get("conditions")).split(",").length > 3?(float)(-(((String)absenceInfos.get("conditions")).split(",").length - 3) * 20) * this.scrollBarFinishConditions.getSliderValue():0.0F;
   }

   private float getSlideFinishRewards() {
      return ((String)absenceInfos.get("rewards")).split(",").length > 3?(float)(-(((String)absenceInfos.get("rewards")).split(",").length - 3) * 20) * this.scrollBarFinishRewards.getSliderValue():0.0F;
   }

   public void drawTooltip(String text, int mouseX, int mouseY) {
      int var10000 = mouseX - this.guiLeft;
      var10000 = mouseY - this.guiTop;
      this.drawHoveringText(Arrays.asList(new String[]{text}), mouseX, mouseY, this.field_73886_k);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a(this.guiFrom);
         }

         if(!this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            if(this.hoveredAction.equals("cancel")) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "cancelled")));
               this.field_73882_e.func_71373_a((GuiScreen)null);
            } else if(this.hoveredAction.equals("staff_refuse")) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "refused")));
               this.field_73882_e.func_71373_a(this.guiFrom);
            } else if(!this.hoveredAction.equals("staff_accept") && !this.hoveredAction.equals("accept")) {
               if(this.hoveredAction.equals("staff_cancel")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "cancelled")));
                  this.field_73882_e.func_71373_a(this.guiFrom);
               }
            } else {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "accepted")));
               this.field_73882_e.func_71373_a(this.guiFrom);
            }
         }
      }

      this.linkForumInput.func_73793_a(mouseX, mouseY, mouseButton);
      this.conditionInput.func_73793_a(mouseX, mouseY, mouseButton);
      this.rewardInput.func_73793_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.linkForumInput.func_73802_a(typedChar, keyCode);
      if(this.conditionInput.func_73802_a(typedChar, keyCode)) {
         this.conditionInput.func_73782_a(this.conditionInput.func_73781_b().replaceAll("^0", ""));
      }

      if(this.rewardInput.func_73802_a(typedChar, keyCode)) {
         this.rewardInput.func_73782_a(this.rewardInput.func_73781_b().replaceAll("^0", ""));
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

   public String bindTextureDependingOnStatus() {
      return "faction_war_request_1";
   }

   public boolean isNumeric(String str) {
      if(str != null && str.length() != 0) {
         char[] var2 = str.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if(!Character.isDigit(c)) {
               return false;
            }
         }

         if(Integer.parseInt(str) <= 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

}
