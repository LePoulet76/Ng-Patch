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
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionStartAssaultPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WarGUI_OLD extends TabbedFactionGUI_OLD {

   public static boolean loaded = false;
   public static ArrayList<HashMap<String, Object>> factionWars;
   private GuiScrollBarFaction scrollBarMissiles;
   private GuiScrollBarFaction scrollBarAssaults;
   private ArrayList<HashMap<String, Object>> cachedAssaults = new ArrayList();
   private RenderItem itemRenderer = new RenderItem();
   private GuiButton assaultButton;
   private GuiButton warRequestsButton;
   private int currentWarIndex = 0;
   private HashMap<String, Object> currentWar;
   private GuiScrollBarFaction scrollBar;


   public WarGUI_OLD(EntityPlayer player) {
      super(player);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
      this.scrollBarMissiles = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 145), 80);
      this.scrollBarAssaults = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 145), 55);
      this.assaultButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 259, this.guiTop + 210, 125, 20, "faction_btn", 103, 0, "   " + I18n.func_135053_a("faction.war.assault_button"));
      this.warRequestsButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a("faction.wars.button.requests"));
   }

   public void drawScreen(int mouseX, int mouseY) {
      if(loaded && factionWars.size() > 0 && this.currentWar == null) {
         this.currentWar = (HashMap)factionWars.get(this.currentWarIndex);
      }

      ClientEventHandler.STYLE.bindTexture("faction_war");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(this.warRequestsButton != null) {
         this.warRequestsButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      }

      this.drawScaledString(I18n.func_135053_a("faction.war.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
      if(loaded && this.currentWar != null) {
         if(this.assaultButton.field_73742_g && !this.currentWar.get("canAssault").equals("true")) {
            this.assaultButton.field_73742_g = false;
         }

         ClientEventHandler.STYLE.bindTexture("faction_war");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 353), (float)(this.guiTop + 14), 180, 250, 31, 14, 512.0F, 512.0F, false);
         this.drawScaledString(I18n.func_135053_a("faction.war.duration_1") + " " + this.currentWar.get("duration") + " " + I18n.func_135053_a("faction.war.duration_2"), this.guiLeft + 255, this.guiTop + 35, 16777215, 0.7F, true, false);
         if(((String)FactionGui_OLD.factionInfos.get("name")).contains("Empire")) {
            this.drawScaledString("Empire", this.guiLeft + 185, this.guiTop + 55, 16777215, 1.2F, true, false);
            this.drawScaledString(((String)FactionGui_OLD.factionInfos.get("name")).replace("Empire", ""), this.guiLeft + 189, this.guiTop + 67, 16777215, 1.2F, true, false);
         } else {
            this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 185, this.guiTop + 60, 16777215, 1.4F, true, false);
         }

         if(((String)this.currentWar.get("name")).contains("Empire")) {
            this.drawScaledString("Empire", this.guiLeft + 330, this.guiTop + 55, 16777215, 1.2F, true, false);
            this.drawScaledString("\u00a7c" + ((String)this.currentWar.get("name")).replace("Empire", ""), this.guiLeft + 330, this.guiTop + 67, 16777215, 1.2F, true, false);
         } else {
            this.drawScaledString("\u00a7c" + this.currentWar.get("name"), this.guiLeft + 330, this.guiTop + 60, 16777215, 1.4F, true, false);
         }

         if(this.currentWar.containsKey("remainingPoints")) {
            String tooltipToDraw = I18n.func_135053_a("faction.war.summary.sentence").replace("#remainingPoints#", (String)this.currentWar.get("remainingPoints"));
            tooltipToDraw = tooltipToDraw.replace("#days#", (String)this.currentWar.get("daysBeforeReset"));
            this.drawScaledString(tooltipToDraw, this.guiLeft + 157, this.guiTop + 110, 11842740, 0.8F, false, false);
         }

         if(this.currentWar.containsKey("warId")) {
            ClientEventHandler.STYLE.bindTexture("faction_war");
            if(mouseX > this.guiLeft + 327 && mouseX < this.guiLeft + 327 + 50 && mouseY > this.guiTop + 105 && mouseY < this.guiTop + 105 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 327), (float)(this.guiTop + 105), 212, 265, 50, 15, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 327), (float)(this.guiTop + 105), 212, 250, 50, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("faction.war.warRequestBtn"), this.guiLeft + 352, this.guiTop + 109, 16777215, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture("faction_war");
            if(mouseX > this.guiLeft + 300 && mouseX < this.guiLeft + 300 + 50 && mouseY > this.guiTop + 14 && mouseY < this.guiTop + 14 + 14) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 300), (float)(this.guiTop + 14), 263, 264, 50, 14, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 300), (float)(this.guiTop + 14), 263, 250, 50, 14, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("faction.war.warAgreementBtn"), this.guiLeft + 325, this.guiTop + 17, 16777215, 1.0F, true, false);
         }

         ArrayList var26 = new ArrayList();
         this.drawScaledString(I18n.func_135053_a("faction.war.missile_history"), this.guiLeft + 131, this.guiTop + 133, 1644825, 0.85F, false, false);
         int i;
         String mmr;
         int missileID;
         int diff;
         if(this.currentWar.containsKey("missiles") && ((ArrayList)this.currentWar.get("missiles")).size() > 0) {
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 141, 117, 88);

            for(i = 0; i < ((ArrayList)this.currentWar.get("missiles")).size(); ++i) {
               String assault = (String)((ArrayList)this.currentWar.get("missiles")).get(i);
               long offsetX = Long.parseLong(assault.split("#")[0]);
               Date date = new Date(offsetX);
               SimpleDateFormat helper = new SimpleDateFormat("dd/MM/yyyy");
               mmr = helper.format(date);
               String winnerHelpers = assault.split("#")[1];
               String looserHelpers = assault.split("#")[2];
               String now = assault.split("#")[3];
               missileID = Integer.parseInt(assault.split("#")[4]);
               diff = this.guiLeft + 131;
               Float helper1 = Float.valueOf((float)(this.guiTop + 141 + i * 31) + this.getSlideMissiles());
               ClientEventHandler.STYLE.bindTexture("faction_war");
               ModernGui.drawModalRectWithCustomSizedTexture((float)diff, (float)helper1.intValue(), 131, 141, 117, 31, 512.0F, 512.0F, false);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(diff + 5), (float)(helper1.intValue() + 5), 159, 250, 20, 19, 512.0F, 512.0F, false);
               ItemStack date1 = new ItemStack(19483, 1, missileID);
               this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), date1, diff + 7, (int)(helper1.floatValue() + 7.0F));
               this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), date1, diff + 7, (int)(helper1.floatValue() + 7.0F));
               this.drawScaledString(looserHelpers, diff + 32, helper1.intValue() + 8, 16777215, 0.9F, false, false);
               this.drawScaledString("\u00a74-\u00a77" + now + " points", diff + 32, helper1.intValue() + 18, 6710886, 0.7F, false, false);
               ClientEventHandler.STYLE.bindTexture("faction_war");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(diff + 104), (float)(helper1.intValue() + 10), 148, 250, 10, 11, 512.0F, 512.0F, false);
               if(mouseX > diff + 104 && mouseX < diff + 104 + 10 && (float)mouseY > helper1.floatValue() + 10.0F && (float)mouseY < helper1.floatValue() + 10.0F + 11.0F) {
                  var26.add(I18n.func_135053_a("faction.war.missile_firedby") + " " + winnerHelpers + " - " + mmr);
               }
            }

            GUIUtils.endGLScissor();
         }

         HashMap var27;
         if(this.cachedAssaults.size() == 0 && this.currentWar.containsKey("assaults") && ((ArrayList)this.currentWar.get("assaults")).size() > 0) {
            for(i = 0; i < ((ArrayList)this.currentWar.get("assaults")).size(); ++i) {
               var27 = new HashMap();
               String var28 = (String)((ArrayList)this.currentWar.get("assaults")).get(i);
               long offsetY = Long.parseLong(var28.split("#")[0]);
               boolean var32 = var28.split("#")[1].contains("win");
               mmr = var28.split("#")[1].split(",").length > 1?var28.split("#")[1].split(",")[1]:"25";
               ArrayList var34 = new ArrayList();
               String[] var35 = var28.split("#")[2].split(",");
               int var37 = var35.length;

               for(missileID = 0; missileID < var37; ++missileID) {
                  String var40 = var35[missileID];
                  if(!var40.contains("Helper")) {
                     var34.add(var40);
                  }
               }

               ArrayList var36 = new ArrayList();
               String[] var38 = var28.split("#")[3].split(",");
               missileID = var38.length;

               for(diff = 0; diff < missileID; ++diff) {
                  String var42 = var38[diff];
                  if(!var42.contains("Helper")) {
                     var36.add(var42);
                  }
               }

               long var39 = System.currentTimeMillis();
               long var41 = var39 - offsetY;
               String var43 = "\u00a78" + I18n.func_135053_a("faction.bank.date_1");
               long days = var41 / 86400000L;
               long hours = 0L;
               long minutes = 0L;
               long seconds = 0L;
               if(days == 0L) {
                  hours = var41 / 3600000L;
                  if(hours == 0L) {
                     minutes = var41 / 60000L;
                     if(minutes == 0L) {
                        seconds = var41 / 1000L;
                        var43 = var43 + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds") + " " + I18n.func_135053_a("faction.bank.date_2");
                     } else {
                        var43 = var43 + " " + minutes + " " + I18n.func_135053_a("faction.common.minutes") + " " + I18n.func_135053_a("faction.bank.date_2");
                     }
                  } else {
                     var43 = var43 + " " + hours + " " + I18n.func_135053_a("faction.common.hours") + " " + I18n.func_135053_a("faction.bank.date_2");
                  }
               } else {
                  var43 = var43 + " " + days + " " + I18n.func_135053_a("faction.common.days") + " " + I18n.func_135053_a("faction.bank.date_2");
               }

               var27.put("date", var43);
               var27.put("win", Boolean.valueOf(var32));
               var27.put("mmr", mmr);
               var27.put("winnerHelpers", var34);
               var27.put("looserHelpers", var36);
               this.cachedAssaults.add(var27);
            }
         }

         this.drawScaledString(I18n.func_135053_a("faction.war.assault_history"), this.guiLeft + 260, this.guiTop + 133, 1644825, 0.85F, false, false);
         if(this.cachedAssaults.size() > 0) {
            GUIUtils.startGLScissor(this.guiLeft + 260, this.guiTop + 141, 117, 63);

            for(i = 0; i < this.cachedAssaults.size(); ++i) {
               var27 = (HashMap)this.cachedAssaults.get(i);
               int var29 = this.guiLeft + 260;
               Float var30 = Float.valueOf((float)(this.guiTop + 141 + i * 20) + this.getSlideAssaults());
               ClientEventHandler.STYLE.bindTexture("faction_war");
               ModernGui.drawModalRectWithCustomSizedTexture((float)var29, (float)var30.intValue(), 260, 141, 117, 20, 512.0F, 512.0F, false);
               this.drawScaledString(((Boolean)var27.get("win")).booleanValue()?I18n.func_135053_a("faction.common.victory").replace("#mmr#", (String)var27.get("mmr")):I18n.func_135053_a("faction.common.defeat").replace("#mmr#", (String)var27.get("mmr")), var29 + 3, var30.intValue() + 3, 11842740, 0.85F, false, false);
               this.drawScaledString((String)var27.get("date"), var29 + 3, var30.intValue() + 12, 6710886, 0.65F, false, false);
               ClientEventHandler.STYLE.bindTexture("faction_war");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(var29 + 104), (float)(var30.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);
               if(mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 117 && mouseY > this.guiTop + 141 && mouseY < this.guiTop + 141 + 63 && mouseX > var29 + 104 && mouseX < var29 + 104 + 10 && (float)mouseY > var30.floatValue() + 5.0F && (float)mouseY < var30.floatValue() + 5.0F + 11.0F) {
                  var26.add("\u00a72" + I18n.func_135053_a("faction.war.assaults.helpers.ally") + ":");
                  Iterator var31 = (((Boolean)var27.get("win")).booleanValue()?(List)var27.get("winnerHelpers"):(List)var27.get("looserHelpers")).iterator();

                  String var33;
                  while(var31.hasNext()) {
                     var33 = (String)var31.next();
                     var26.add("\u00a77- " + var33);
                  }

                  var26.add("");
                  var26.add("\u00a7c" + I18n.func_135053_a("faction.war.assaults.helpers.ennemy") + ":");
                  var31 = (((Boolean)var27.get("win")).booleanValue()?(List)var27.get("looserHelpers"):(List)var27.get("winnerHelpers")).iterator();

                  while(var31.hasNext()) {
                     var33 = (String)var31.next();
                     var26.add("\u00a77- " + var33);
                  }
               }
            }

            GUIUtils.endGLScissor();
         }

         if(mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 255 && mouseY >= this.guiTop + 141 && mouseY <= this.guiTop + 225) {
            this.scrollBarMissiles.draw(mouseX, mouseY);
         }

         if(mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 384 && mouseY >= this.guiTop + 141 && mouseY <= this.guiTop + 205) {
            this.scrollBarAssaults.draw(mouseX, mouseY);
         }

         if(!this.currentWar.get("canAssault").equals("false")) {
            this.assaultButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            if(!this.assaultButton.field_73742_g && mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 259 + 125 && mouseY > this.guiTop + 210 && mouseY < this.guiTop + 210 + 20) {
               if(((String)this.currentWar.get("canAssault")).contains("cooldown")) {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.war.assault.reason.cooldown").replace("#cooldown#", ((String)this.currentWar.get("canAssault")).replace("cooldown.", ""))}), mouseX, mouseY + 20, this.field_73886_k);
               } else if(((String)this.currentWar.get("canAssault")).contains("self.days.protection")) {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("self.days.protection").replace("#protection#", ((String)this.currentWar.get("canAssault")).replace("self.days.protection.", ""))}), mouseX, mouseY + 20, this.field_73886_k);
               } else if(((String)this.currentWar.get("canAssault")).contains("other.days.protection")) {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("other.days.protection").replace("#protection#", ((String)this.currentWar.get("canAssault")).replace("other.days.protection.", ""))}), mouseX, mouseY + 20, this.field_73886_k);
               } else {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.war.assault.reason." + this.currentWar.get("canAssault"))}), mouseX, mouseY + 20, this.field_73886_k);
               }
            }
         }

         if(!var26.isEmpty()) {
            this.drawTooltip(var26, mouseX, mouseY);
         }
      } else {
         Gui.func_73734_a(this.guiLeft + 124, this.guiTop + 15, this.guiLeft + 124 + 268, this.guiTop + 15 + 224, -1513240);
         this.drawScaledString(I18n.func_135053_a("faction.war.no_result"), this.guiLeft + 258, this.guiTop + 100, 328965, 1.1F, true, false);
      }

   }

   public void drawTooltip(List<String> texts, int mouseX, int mouseY) {
      int var10000 = mouseX - this.guiLeft;
      var10000 = mouseY - this.guiTop;
      this.drawHoveringText(texts, mouseX, mouseY, this.field_73886_k);
   }

   private float getSlideMissiles() {
      return ((ArrayList)this.currentWar.get("missiles")).size() > 2?(float)(-(((ArrayList)this.currentWar.get("missiles")).size() - 2) * 31) * this.scrollBarMissiles.getSliderValue():0.0F;
   }

   private float getSlideAssaults() {
      return ((ArrayList)this.currentWar.get("assaults")).size() > 3?(float)(-(((ArrayList)this.currentWar.get("assaults")).size() - 3) * 20) * this.scrollBarAssaults.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
      if(mouseButton == 0) {
         if(this.currentWar != null && this.currentWar.get("canAssault").equals("true") && mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 259 + 125 && mouseY >= this.guiTop + 210 && mouseY <= this.guiTop + 210 + 20) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionStartAssaultPacket((String)FactionGui_OLD.factionInfos.get("id"), (String)this.currentWar.get("id"))));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
            Minecraft.func_71410_x().func_71373_a(new WarRequestListGUI());
         }

         if(this.currentWar != null && this.currentWar.containsKey("warId") && mouseX > this.guiLeft + 327 && mouseX < this.guiLeft + 327 + 50 && mouseY > this.guiTop + 105 && mouseY < this.guiTop + 105 + 15) {
            Minecraft.func_71410_x().func_71373_a(new WarRequestGUI(((Double)this.currentWar.get("warId")).intValue(), (GuiScreen)null));
         }

         if(this.currentWar != null && this.currentWar.containsKey("warId") && mouseX > this.guiLeft + 300 && mouseX < this.guiLeft + 300 + 50 && mouseY > this.guiTop + 14 && mouseY < this.guiTop + 14 + 14) {
            Minecraft.func_71410_x().func_71373_a(new WarAgreementListGui(this.player, ((Double)this.currentWar.get("warId")).intValue(), (String)FactionGui_OLD.factionInfos.get("name"), (String)this.currentWar.get("name")));
         }

         if(factionWars != null && factionWars.size() > 0 && mouseX >= this.guiLeft + 353 && mouseX <= this.guiLeft + 353 + 14 && mouseY >= this.guiTop + 14 && mouseY <= this.guiTop + 14 + 14) {
            this.cachedAssaults = new ArrayList();
            this.currentWarIndex = this.currentWarIndex - 1 >= 0?this.currentWarIndex - 1:factionWars.size() - 1;
            this.currentWar = (HashMap)factionWars.get(this.currentWarIndex);
         }

         if(factionWars != null && factionWars.size() > 0 && mouseX >= this.guiLeft + 370 && mouseX <= this.guiLeft + 370 + 14 && mouseY >= this.guiTop + 14 && mouseY <= this.guiTop + 14 + 14) {
            this.cachedAssaults = new ArrayList();
            this.currentWarIndex = this.currentWarIndex + 1 < factionWars.size()?this.currentWarIndex + 1:0;
            this.currentWar = (HashMap)factionWars.get(this.currentWarIndex);
         }
      }

   }

}
