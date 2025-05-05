package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.RefuseColonyConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DiplomatieGUI extends TabbedFactionGUI {

   public static boolean loaded = false;
   public static TreeMap<String, Object> factionDiplomatieInfos;
   public String displayMode = "relations";
   private GuiScrollBarGeneric scrollBarAllies;
   private GuiScrollBarGeneric scrollBarEnnemies;
   private GuiScrollBarGeneric scrollBarColonies;
   private GuiScrollBarGeneric scrollBarReceived;
   private GuiScrollBarGeneric scrollBarSent;
   private String hoveredCountry = "";
   private String hoveredRelationType = "";


   public DiplomatieGUI() {
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scrollBarAllies = new GuiScrollBarGeneric((float)(this.guiLeft + 160), (float)(this.guiTop + 125), 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarEnnemies = new GuiScrollBarGeneric((float)(this.guiLeft + 301), (float)(this.guiTop + 125), 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarColonies = new GuiScrollBarGeneric((float)(this.guiLeft + 442), (float)(this.guiTop + 125), 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarReceived = new GuiScrollBarGeneric((float)(this.guiLeft + 235), (float)(this.guiTop + 124), 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarSent = new GuiScrollBarGeneric((float)(this.guiLeft + 441), (float)(this.guiTop + 124), 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw.clear();
      this.hoveredAction = "";
      this.hoveredCountry = "";
      this.hoveredRelationType = "";
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      if(loaded) {
         if(FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("diplomatie")) {
            ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("diplomatie"));
         } else {
            ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33N_N33NN.png");
         }

         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
         ClientEventHandler.STYLE.bindTexture("faction_global");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(280 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 292), (float)(this.guiTop - 15), (float)(382 * GUI_SCALE), (float)(0 * GUI_SCALE), 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), (float)(this.guiLeft + 43), (float)(this.guiTop + 32), 10395075, 0.5F, "left", false, "georamaMedium", 25, 8, 350);
         ArrayList sent = (ArrayList)factionDiplomatieInfos.get("sent");
         ArrayList received = (ArrayList)factionDiplomatieInfos.get("received");
         if(this.displayMode.equals("relations")) {
            ArrayList i = (ArrayList)factionDiplomatieInfos.get("allies");
            ArrayList offsetX = (ArrayList)factionDiplomatieInfos.get("ennemies");
            ArrayList offsetY = (ArrayList)factionDiplomatieInfos.get("colonies");
            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 77), (float)(0 * GUI_SCALE), (float)(145 * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.relations"), (float)(this.guiLeft + 42 + 32), (float)(this.guiTop + 79), 7239406, 0.5F, "center", false, "georamaSemiBold", 28);
            if(mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
               this.hoveredAction = "tab_relations";
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 108), (float)(this.guiTop + 77), (float)(68 * GUI_SCALE), (float)(145 * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.requests"), (float)(this.guiLeft + 108 + 32), (float)(this.guiTop + 79), 14803951, 0.5F, "center", false, "georamaSemiBold", 28);
            if(received.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 168), (float)(this.guiTop + 75), (float)(134 * GUI_SCALE), (float)(145 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            }

            if(mouseX >= this.guiLeft + 108 && mouseX <= this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
               this.hoveredAction = "tab_requests";
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 104), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 104), (float)(126 * GUI_SCALE), (float)(124 * GUI_SCALE), 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.allies"), (float)(this.guiLeft + 48), (float)(this.guiTop + 109), 0, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(i.size() + "", (float)(this.guiLeft + 159), (float)(this.guiTop + 109), 0, 0.5F, "right", false, "georamaSemiBold", 30);
            int countryName;
            int relationType;
            Float offsetY1;
            String countryName1;
            if(i.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 160), (float)(this.guiTop + 125), (float)(0 * GUI_SCALE), (float)(160 * GUI_SCALE), 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
               GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 121, 117, 103);

               for(countryName = 0; countryName < i.size(); ++countryName) {
                  relationType = this.guiLeft + 43;
                  offsetY1 = Float.valueOf((float)(this.guiTop + 121 + 5 + countryName * 14) + this.getSlideAllies());
                  countryName1 = (String)((LinkedTreeMap)i.get(countryName)).get("factionName");
                  ClientProxy.loadCountryFlag(countryName1);
                  if(ClientProxy.flagsTexture.containsKey(countryName1)) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(countryName1)).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(relationType + 5), (float)(offsetY1.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(countryName1, (float)(relationType + 26), (float)(offsetY1.intValue() + 2), 16514302, 0.5F, "left", false, "georamaMedium", 28);
                  if(!((String)((LinkedTreeMap)i.get(countryName)).get("relationTime")).isEmpty() && !((String)((LinkedTreeMap)i.get(countryName)).get("relationTime")).equals("null")) {
                     ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)i.get(countryName)).get("relationTime")), (float)(relationType + 114), (float)(offsetY1.intValue() + 2), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                  }

                  if(offsetY1.floatValue() >= (float)(this.guiTop + 110) && offsetY1.floatValue() <= (float)(this.guiTop + 225) && mouseX >= relationType && mouseX <= relationType + 117 && mouseY >= offsetY1.intValue() && mouseY <= offsetY1.intValue() + 14) {
                     this.hoveredAction = "open_country";
                     this.hoveredCountry = countryName1;
                  }
               }

               GUIUtils.endGLScissor();
               if(mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                  this.scrollBarAllies.draw(mouseX, mouseY);
               }
            } else {
               ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.diplomatie.label.no_relation"), (float)(this.guiLeft + 43 + 5), (float)(this.guiTop + 104 + 24), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 104), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 104), (float)(252 * GUI_SCALE), (float)(124 * GUI_SCALE), 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.enemies"), (float)(this.guiLeft + 189), (float)(this.guiTop + 109), 0, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(offsetX.size() + "", (float)(this.guiLeft + 300), (float)(this.guiTop + 109), 0, 0.5F, "right", false, "georamaSemiBold", 30);
            if(offsetX.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 301), (float)(this.guiTop + 125), (float)(0 * GUI_SCALE), (float)(160 * GUI_SCALE), 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
               GUIUtils.startGLScissor(this.guiLeft + 184, this.guiTop + 121, 117, 103);

               for(countryName = 0; countryName < offsetX.size(); ++countryName) {
                  relationType = this.guiLeft + 184;
                  offsetY1 = Float.valueOf((float)(this.guiTop + 121 + 5 + countryName * 14) + this.getSlideEnnemies());
                  countryName1 = (String)((LinkedTreeMap)offsetX.get(countryName)).get("factionName");
                  ClientProxy.loadCountryFlag(countryName1);
                  if(ClientProxy.flagsTexture.containsKey(countryName1)) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(countryName1)).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(relationType + 5), (float)(offsetY1.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(countryName1, (float)(relationType + 26), (float)(offsetY1.intValue() + 2), 16514302, 0.5F, "left", false, "georamaMedium", 28);
                  if(!((String)((LinkedTreeMap)offsetX.get(countryName)).get("relationTime")).isEmpty() && !((String)((LinkedTreeMap)offsetX.get(countryName)).get("relationTime")).equals("null")) {
                     ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)offsetX.get(countryName)).get("relationTime")), (float)(relationType + 114), (float)(offsetY1.intValue() + 2), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                  }

                  if(offsetY1.floatValue() >= (float)(this.guiTop + 110) && offsetY1.floatValue() <= (float)(this.guiTop + 225) && mouseX >= relationType && mouseX <= relationType + 117 && mouseY >= offsetY1.intValue() && mouseY <= offsetY1.intValue() + 14) {
                     this.hoveredAction = "open_country";
                     this.hoveredCountry = countryName1;
                  }
               }

               GUIUtils.endGLScissor();
               if(mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                  this.scrollBarEnnemies.draw(mouseX, mouseY);
               }
            } else {
               ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.diplomatie.label.no_relation"), (float)(this.guiLeft + 184 + 5), (float)(this.guiTop + 104 + 24), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 104), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 104), (float)(0 * GUI_SCALE), (float)(124 * GUI_SCALE), 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.colonies"), (float)(this.guiLeft + 330), (float)(this.guiTop + 109), 0, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(offsetY.size() + "", (float)(this.guiLeft + 441), (float)(this.guiTop + 109), 0, 0.5F, "right", false, "georamaSemiBold", 30);
            if(offsetY.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 442), (float)(this.guiTop + 125), (float)(0 * GUI_SCALE), (float)(160 * GUI_SCALE), 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
               GUIUtils.startGLScissor(this.guiLeft + 325, this.guiTop + 121, 117, 103);

               for(countryName = 0; countryName < offsetY.size(); ++countryName) {
                  relationType = this.guiLeft + 325;
                  offsetY1 = Float.valueOf((float)(this.guiTop + 121 + 5 + countryName * 14) + this.getSlideColonies());
                  countryName1 = (String)((LinkedTreeMap)offsetY.get(countryName)).get("factionName");
                  ClientProxy.loadCountryFlag(countryName1);
                  if(ClientProxy.flagsTexture.containsKey(countryName1)) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(countryName1)).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(relationType + 5), (float)(offsetY1.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(countryName1, (float)(relationType + 26), (float)(offsetY1.intValue() + 2), 16514302, 0.5F, "left", false, "georamaMedium", 28);
                  if(!((String)((LinkedTreeMap)offsetY.get(countryName)).get("relationTime")).isEmpty() && !((String)((LinkedTreeMap)offsetY.get(countryName)).get("relationTime")).equals("null")) {
                     ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)offsetY.get(countryName)).get("relationTime")), (float)(relationType + 114), (float)(offsetY1.intValue() + 2), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                  }

                  if(offsetY1.floatValue() >= (float)(this.guiTop + 110) && offsetY1.floatValue() <= (float)(this.guiTop + 225) && mouseX >= relationType && mouseX <= relationType + 117 && mouseY >= offsetY1.intValue() && mouseY <= offsetY1.intValue() + 14) {
                     this.hoveredAction = "open_country";
                     this.hoveredCountry = countryName1;
                  }
               }

               GUIUtils.endGLScissor();
               if(mouseX >= this.guiLeft + 325 && mouseX <= this.guiLeft + 325 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                  this.scrollBarColonies.draw(mouseX, mouseY);
               }
            } else {
               ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.diplomatie.label.no_relation"), (float)(this.guiLeft + 325 + 5), (float)(this.guiTop + 104 + 24), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            }
         } else if(this.displayMode.equals("requests")) {
            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 77), (float)(68 * GUI_SCALE), (float)(145 * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.relations"), (float)(this.guiLeft + 42 + 32), (float)(this.guiTop + 79), 14803951, 0.5F, "center", false, "georamaSemiBold", 28);
            if(mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
               this.hoveredAction = "tab_relations";
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 108), (float)(this.guiTop + 77), (float)(0 * GUI_SCALE), (float)(145 * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.requests"), (float)(this.guiLeft + 108 + 32), (float)(this.guiTop + 79), 7239406, 0.5F, "center", false, "georamaSemiBold", 28);
            if(received.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 168), (float)(this.guiTop + 75), (float)(134 * GUI_SCALE), (float)(145 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            }

            if(mouseX >= this.guiLeft + 108 && mouseX <= this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
               this.hoveredAction = "tab_requests";
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 103), (float)(126 * GUI_SCALE), (float)(0 * GUI_SCALE), 197 * GUI_SCALE, 120 * GUI_SCALE, 197, 120, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.received"), (float)(this.guiLeft + 48), (float)(this.guiTop + 108), 0, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(received.size() + "", (float)(this.guiLeft + 232), (float)(this.guiTop + 108), 0, 0.5F, "right", false, "georamaSemiBold", 30);
            int var13;
            int var14;
            Float var15;
            String var16;
            String var17;
            if(received.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 235), (float)(this.guiTop + 124), (float)(0 * GUI_SCALE), (float)(160 * GUI_SCALE), 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
               GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 121, 192, 103);

               for(var13 = 0; var13 < received.size(); ++var13) {
                  var14 = this.guiLeft + 43;
                  var15 = Float.valueOf((float)(this.guiTop + 121 + 5 + var13 * 14) + this.getSlideReceived());
                  var16 = (String)((LinkedTreeMap)received.get(var13)).get("factionName");
                  var17 = (String)((LinkedTreeMap)received.get(var13)).get("relationType");
                  ClientProxy.loadCountryFlag(var16);
                  if(ClientProxy.flagsTexture.containsKey(var16)) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(var16)).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 5), (float)(var15.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(var16, (float)(var14 + 26), (float)(var15.intValue() + 2), 16514302, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont(var17, (float)(var14 + 104), (float)(var15.intValue() + 2), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                  if(FactionGUI.hasPermissions("relations")) {
                     ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                     if(var15.floatValue() >= (float)(this.guiTop + 110) && var15.floatValue() <= (float)(this.guiTop + 225) && mouseX >= var14 + 169 && mouseX <= var14 + 169 + 9 && mouseY >= var15.intValue() + 2 && mouseY <= var15.intValue() + 2 + 7) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 169), (float)(var15.intValue() + 2), (float)(142 * GUI_SCALE), (float)(155 * GUI_SCALE), 9 * GUI_SCALE, 7 * GUI_SCALE, 9, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredCountry = var16;
                        this.hoveredAction = "relation_yes";
                        this.hoveredRelationType = var17;
                     } else {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 169), (float)(var15.intValue() + 2), (float)(142 * GUI_SCALE), (float)(145 * GUI_SCALE), 9 * GUI_SCALE, 7 * GUI_SCALE, 9, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }

                     if(mouseX >= var14 + 181 && mouseX <= var14 + 181 + 8 && mouseY >= var15.intValue() + 2 && mouseY <= var15.intValue() + 2 + 8) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 181), (float)(var15.intValue() + 2), (float)(155 * GUI_SCALE), (float)(155 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredCountry = var16;
                        this.hoveredAction = "relation_no";
                        this.hoveredRelationType = var17;
                     } else {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 181), (float)(var15.intValue() + 2), (float)(155 * GUI_SCALE), (float)(145 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }
                  }
               }

               GUIUtils.endGLScissor();
               if(mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 160 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                  this.scrollBarReceived.draw(mouseX, mouseY);
               }
            } else {
               ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.diplomatie.label.no_relation"), (float)(this.guiLeft + 43 + 5), (float)(this.guiTop + 104 + 24), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            }

            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 249), (float)(this.guiTop + 103), (float)(126 * GUI_SCALE), (float)(0 * GUI_SCALE), 197 * GUI_SCALE, 120 * GUI_SCALE, 197, 120, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.diplomatie.label.sent"), (float)(this.guiLeft + 254), (float)(this.guiTop + 108), 0, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(sent.size() + "", (float)(this.guiLeft + 438), (float)(this.guiTop + 108), 0, 0.5F, "right", false, "georamaSemiBold", 30);
            if(sent.size() > 0) {
               ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 441), (float)(this.guiTop + 124), (float)(0 * GUI_SCALE), (float)(160 * GUI_SCALE), 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
               GUIUtils.startGLScissor(this.guiLeft + 249, this.guiTop + 121, 192, 103);

               for(var13 = 0; var13 < sent.size(); ++var13) {
                  var14 = this.guiLeft + 249;
                  var15 = Float.valueOf((float)(this.guiTop + 121 + 5 + var13 * 14) + this.getSlideSent());
                  var16 = (String)((LinkedTreeMap)sent.get(var13)).get("factionName");
                  var17 = (String)((LinkedTreeMap)sent.get(var13)).get("relationType");
                  ClientProxy.loadCountryFlag(var16);
                  if(ClientProxy.flagsTexture.containsKey(var16)) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(var16)).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 5), (float)(var15.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(var16, (float)(var14 + 26), (float)(var15.intValue() + 2), 16514302, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont(var17, (float)(var14 + 104), (float)(var15.intValue() + 2), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                  if(FactionGUI.hasPermissions("relations")) {
                     ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                     if(var15.floatValue() >= (float)(this.guiTop + 110) && var15.floatValue() <= (float)(this.guiTop + 225) && mouseX >= var14 + 181 && mouseX <= var14 + 181 + 8 && mouseY >= var15.intValue() + 2 && mouseY <= var15.intValue() + 2 + 8) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 181), (float)(var15.intValue() + 2), (float)(155 * GUI_SCALE), (float)(155 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredCountry = var16;
                        this.hoveredAction = "relation_cancel";
                        this.hoveredRelationType = var17;
                     } else {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 181), (float)(var15.intValue() + 2), (float)(155 * GUI_SCALE), (float)(145 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }
                  }
               }

               GUIUtils.endGLScissor();
               if(mouseX >= this.guiLeft + 249 && mouseX <= this.guiLeft + 249 + 160 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                  this.scrollBarSent.draw(mouseX, mouseY);
               }
            } else {
               ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.diplomatie.label.no_relation"), (float)(this.guiLeft + 249 + 5), (float)(this.guiTop + 104 + 24), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            }
         }
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   public void drawScreen(int mouseX, int mouseY) {}

   private float getSlideAllies() {
      return ((ArrayList)factionDiplomatieInfos.get("allies")).size() > 7?(float)(-(((ArrayList)factionDiplomatieInfos.get("allies")).size() - 7) * 14) * this.scrollBarAllies.getSliderValue():0.0F;
   }

   private float getSlideEnnemies() {
      return ((ArrayList)factionDiplomatieInfos.get("ennemies")).size() > 8?(float)(-(((ArrayList)factionDiplomatieInfos.get("ennemies")).size() - 8) * 13) * this.scrollBarEnnemies.getSliderValue():0.0F;
   }

   private float getSlideColonies() {
      return ((ArrayList)factionDiplomatieInfos.get("colonies")).size() > 8?(float)(-(((ArrayList)factionDiplomatieInfos.get("colonies")).size() - 8) * 13) * this.scrollBarColonies.getSliderValue():0.0F;
   }

   private float getSlideReceived() {
      return ((ArrayList)factionDiplomatieInfos.get("received")).size() > 8?(float)(-(((ArrayList)factionDiplomatieInfos.get("received")).size() - 8) * 13) * this.scrollBarReceived.getSliderValue():0.0F;
   }

   private float getSlideSent() {
      return ((ArrayList)factionDiplomatieInfos.get("sent")).size() > 8?(float)(-(((ArrayList)factionDiplomatieInfos.get("sent")).size() - 8) * 13) * this.scrollBarSent.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if(this.hoveredAction.equals("tab_relations")) {
            this.displayMode = "relations";
         } else if(this.hoveredAction.equals("tab_requests")) {
            this.displayMode = "requests";
         } else if(this.hoveredAction.equals("edit_photo")) {
            ClientData.lastCaptureScreenshot.put("diplomatie", Long.valueOf(System.currentTimeMillis()));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d(I18n.func_135053_a("faction.take_picture")));
         } else if(this.hoveredAction.equalsIgnoreCase("open_country") && !this.hoveredCountry.isEmpty()) {
            Minecraft.func_71410_x().func_71373_a(new FactionGUI(this.hoveredCountry));
         } else if(!this.hoveredAction.isEmpty() && !this.hoveredCountry.isEmpty()) {
            if(this.hoveredAction.equals("relation_no") && this.hoveredRelationType.equalsIgnoreCase("colony")) {
               Minecraft.func_71410_x().func_71373_a(new RefuseColonyConfirmGui(this, this.hoveredCountry, this.hoveredAction.replaceAll("relation_", ""), this.hoveredRelationType));
            } else {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishActionPacket((String)FactionGUI.factionInfos.get("name"), this.hoveredCountry, this.hoveredAction.replaceAll("relation_", ""), this.hoveredRelationType)));
            }
         }

         this.hoveredAction = "";
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private String convertDate(String time) {
      String date = "";
      long diff = System.currentTimeMillis() - Long.parseLong(time);
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
               date = date + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds");
            } else {
               date = date + " " + minutes + " " + I18n.func_135053_a("faction.common.minutes");
            }
         } else {
            date = date + " " + hours + " " + I18n.func_135053_a("faction.common.hours");
         }
      } else {
         date = date + " " + days + " " + I18n.func_135053_a("faction.common.days");
      }

      return date;
   }

}
