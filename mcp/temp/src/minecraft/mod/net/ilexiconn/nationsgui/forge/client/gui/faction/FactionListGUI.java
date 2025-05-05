package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI$TAB;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAskToJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDetailsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class FactionListGUI extends TabbedFactionListGUI {

   public static HashMap<String, HashMap<String, String>> countriesExtraData = new HashMap();
   public static ArrayList<HashMap<String, String>> countriesData = new ArrayList();
   public static HashMap<String, String> selectedCountry = new HashMap();
   public static HashMap<String, String> hoveredCountry = new HashMap();
   public static boolean loaded = false;
   public static Long lastClick = Long.valueOf(0L);
   private GuiScrollBarGeneric scrollBar;
   private GuiTextField searchInput;
   public int countCountriesBySearch = -1;
   public static Long lastExtraDataPacket = Long.valueOf(0L);
   private CFontRenderer cFontSemiBold28;
   private FactionListGUI$TAB activeTab;
   public boolean foundReferent;
   public static HashMap<String, Integer> iconsXPerRelation = new FactionListGUI$1();
   public static HashMap<String, Integer> iconsYByName = new FactionListGUI$2();


   public FactionListGUI() {
      this.activeTab = FactionListGUI$TAB.LIST;
      this.foundReferent = false;
      countriesData.clear();
      countriesExtraData.clear();
      loaded = false;
      initTabs();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if(countriesData.isEmpty()) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionListDataPacket()));
      }

      this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 297), (float)(this.guiTop + 70), 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBar.setScrollIncrement(0.005F);
      this.searchInput = new CustomInputFieldGUI(this.guiLeft + 343, this.guiTop + 54, 94, 12, "georamaSemiBold", 28);
      this.searchInput.func_73786_a(false);
      this.searchInput.func_73804_f(15);
      this.countCountriesBySearch = -1;
      this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(28));
   }

   public void drawScreen(int mouseX, int mouseY) {}

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      ModernGui.drawDefaultBackground(this, this.field_73880_f, this.field_73881_g, mouseX, mouseY);
      tooltipToDraw.clear();
      this.hoveredAction = "";
      hoveredCountry = new HashMap();
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.manage_countries"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.list_countries"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
      ClientEventHandler.STYLE.bindTexture("faction_list");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 324), (float)(this.guiTop + 48), (float)(276 * GUI_SCALE), (float)(157 * GUI_SCALE), 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      this.searchInput.func_73795_f();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int index = 0;
      FactionListGUI$TAB[] addSeparatorForNonReferentCountries = FactionListGUI$TAB.values();
      int addSeparatorForReferentCountries = addSeparatorForNonReferentCountries.length;

      for(int countryExtraData = 0; countryExtraData < addSeparatorForReferentCountries; ++countryExtraData) {
         FactionListGUI$TAB action = addSeparatorForNonReferentCountries[countryExtraData];
         ClientEventHandler.STYLE.bindTexture("faction_list");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42 + 55 * index), (float)(this.guiTop + 36), (float)(409 * GUI_SCALE), (float)((this.activeTab.equals(action)?103:89) * GUI_SCALE), 52 * GUI_SCALE, 12 * GUI_SCALE, 52, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.tab." + action.name().toLowerCase()), (float)(this.guiLeft + 42 + 55 * index + 26), (float)(this.guiTop + 39), this.activeTab.equals(action)?7239406:14342893, 0.5F, "center", false, "georamaSemiBold", 26);
         if(mouseX >= this.guiLeft + 42 + 55 * index && mouseX <= this.guiLeft + 42 + 55 * index + 52 && mouseY >= this.guiTop + 36 && mouseY <= this.guiTop + 36 + 12) {
            this.hoveredAction = "switch_tab#" + action.name();
         }

         ++index;
      }

      ClientEventHandler.STYLE.bindTexture("faction_list");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 48), (float)(0 * GUI_SCALE), (float)(191 * GUI_SCALE), 266 * GUI_SCALE, 173 * GUI_SCALE, 266, 173, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.list.label.countries"), (float)(this.guiLeft + 50), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
      ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.list.label.rank"), (float)(this.guiLeft + 147), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
      ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.list.label.level"), (float)(this.guiLeft + 179), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
      ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.list.label.column4." + this.activeTab.name().toLowerCase()), (float)(this.guiLeft + 222), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
      this.foundReferent = false;
      boolean var13 = false;
      boolean var14 = false;
      GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 70, 247, 151);
      if(countriesData.size() > 0) {
         index = 0;
         Iterator var15 = countriesData.iterator();

         while(var15.hasNext()) {
            HashMap var17 = (HashMap)var15.next();
            if((this.searchInput.func_73781_b().isEmpty() || ((String)var17.get("name")).toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) && (!this.activeTab.equals(FactionListGUI$TAB.BUY) || !((String)var17.get("price")).equals("-1")) && (!this.activeTab.equals(FactionListGUI$TAB.JOIN) || ((String)var17.get("recruitment")).equals("true") && !((String)var17.get("recruter_online")).equals("true")) && (!this.activeTab.equals(FactionListGUI$TAB.ACTIONS) || !((String)var17.get("nb_actions")).equals("0"))) {
               int offsetX = this.guiLeft + 50;
               Float offsetY = Float.valueOf((float)(this.guiTop + 70 + index * 16) + this.getSlideCountries());
               if(this.activeTab.equals(FactionListGUI$TAB.JOIN) || this.activeTab.equals(FactionListGUI$TAB.LIST)) {
                  if(((String)var17.get("isReferent")).equals("true")) {
                     this.foundReferent = true;
                     if(!var14) {
                        var14 = true;
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.label.countries_referent"), (float)offsetX, (float)(offsetY.intValue() + 2), 16109642, 0.5F, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.label.countries_referent")) / 2 + 2), (float)(offsetY.intValue() + 2), (float)(86 * GUI_SCALE), (float)(5 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        if(mouseX >= offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.label.countries_referent")) / 2 + 2 && mouseX <= offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.label.countries_referent")) / 2 + 2 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                           tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("faction.common.badge.referent").split("##")));
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     }

                     offsetY = Float.valueOf(offsetY.floatValue() + 16.0F);
                  }

                  if(this.foundReferent && !((String)var17.get("isReferent")).equals("true")) {
                     offsetY = Float.valueOf(offsetY.floatValue() + 20.0F);
                     if(!var13) {
                        var13 = true;
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.label.countries_classic"), (float)offsetX, (float)(offsetY.intValue() + 2), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                        Gui.func_73734_a(offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.label.countries_classic")) / 2 + 5, offsetY.intValue() + 7, offsetX + 260 - ((int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.label.countries_classic")) / 2 + 5), offsetY.intValue() + 8, -6382141);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     }

                     offsetY = Float.valueOf(offsetY.floatValue() + 16.0F);
                  }
               }

               if(offsetY.intValue() >= this.guiTop + 60 && offsetY.intValue() <= this.guiTop + 70 + 160) {
                  ClientProxy.loadCountryFlag((String)var17.get("name"));
                  if(ClientProxy.flagsTexture.containsKey(var17.get("name"))) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(var17.get("name"))).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 0), (float)(offsetY.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                  }
               }

               if(((String)var17.get("isReferent")).equals("true")) {
                  ClientEventHandler.STYLE.bindTexture("faction_list");
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 21), (float)(offsetY.intValue() + 1), (float)(463 * GUI_SCALE), (float)(15 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont((String)var17.get("name"), (float)(offsetX + 31), (float)(offsetY.intValue() + 2), 16109642, 0.5F, "left", false, "georamaSemiBold", 28);
                  if(mouseX >= offsetX + 21 && mouseX <= offsetX + 21 + 13 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 13) {
                     tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("faction.common.badge.referent").split("##")));
                  }
               } else if(var17.containsKey("isInPillage") && ((String)var17.get("isInPillage")).equals("true")) {
                  ClientEventHandler.STYLE.bindTexture("faction_list");
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 21), (float)(offsetY.intValue() + 1), (float)(463 * GUI_SCALE), (float)(46 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont((String)var17.get("name"), (float)(offsetX + 31), (float)(offsetY.intValue() + 2), 14900238, 0.5F, "left", false, "georamaSemiBold", 28);
                  if(mouseX >= offsetX + 21 && mouseX <= offsetX + 21 + 13 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 13) {
                     tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("faction.common.badge.pillage").split("##")));
                  }
               } else {
                  ModernGui.drawScaledStringCustomFont((String)var17.get("name"), (float)(offsetX + 21), (float)(offsetY.intValue() + 2), ((String)var17.get("relation_my_country")).equals("neutral")?(((String)var17.get("name")).equals(selectedCountry.get("name"))?7239406:16514302):((Integer)FactionGUI.textColor.get(var17.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaSemiBold", 28);
               }

               ModernGui.drawScaledStringCustomFont((String)var17.get("position"), (float)(offsetX + 98), (float)(offsetY.intValue() + 2), selectedCountry.containsKey("name") && ((String)var17.get("name")).equals(selectedCountry.get("name"))?((Integer)FactionGUI.textColor.get(var17.get("relation_my_country"))).intValue():16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont((String)var17.get("level"), (float)(offsetX + 130), (float)(offsetY.intValue() + 2), selectedCountry.containsKey("name") && ((String)var17.get("name")).equals(selectedCountry.get("name"))?((Integer)FactionGUI.textColor.get(var17.get("relation_my_country"))).intValue():16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               String data = (String)var17.get("players");
               if(this.activeTab.equals(FactionListGUI$TAB.BUY)) {
                  data = (String)var17.get("price") + "$";
               } else if(this.activeTab.equals(FactionListGUI$TAB.ACTIONS)) {
                  data = 10000 + Integer.parseInt((String)var17.get("level")) * Integer.parseInt((String)var17.get("level")) * 100 + "$";
               }

               ModernGui.drawScaledStringCustomFont(data, (float)(offsetX + 173), (float)(offsetY.intValue() + 2), selectedCountry.containsKey("name") && ((String)var17.get("name")).equals(selectedCountry.get("name"))?((Integer)FactionGUI.textColor.get(var17.get("relation_my_country"))).intValue():16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               if(mouseX >= offsetX && mouseX <= offsetX + 247 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 16.0F) {
                  hoveredCountry = var17;
               }

               ++index;
            }
         }

         if(this.countCountriesBySearch == -1) {
            this.countCountriesBySearch = index + 1;
         }
      }

      GUIUtils.endGLScissor();
      if(mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173) {
         this.scrollBar.draw(mouseX, mouseY);
      }

      ClientEventHandler.STYLE.bindTexture("faction_list");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 324), (float)(this.guiTop + 81), (float)(137 * GUI_SCALE), (float)(372 * GUI_SCALE), 129 * GUI_SCALE, 140 * GUI_SCALE, 129, 140, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      if(selectedCountry != null && !selectedCountry.isEmpty()) {
         if(!countriesExtraData.containsKey(selectedCountry.get("name"))) {
            if(System.currentTimeMillis() - lastExtraDataPacket.longValue() > 500L) {
               lastExtraDataPacket = Long.valueOf(System.currentTimeMillis());
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionListDetailsPacket((String)selectedCountry.get("name"))));
            }
         } else {
            HashMap var16 = (HashMap)countriesExtraData.get(selectedCountry.get("name"));
            if(((String)selectedCountry.get("isReferent")).equals("true")) {
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 77), (float)(461 * GUI_SCALE), (float)(0 * GUI_SCALE), 13 * GUI_SCALE, 13 * GUI_SCALE, 13, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               if(mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 13 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 13) {
                  tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("faction.common.badge.referent").split("##")));
               }
            }

            ClientProxy.loadCountryFlag((String)selectedCountry.get("name"));
            if(ClientProxy.flagsTexture.containsKey(selectedCountry.get("name"))) {
               GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(selectedCountry.get("name"))).func_110552_b());
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 328), (float)(this.guiTop + 85), 0.0F, 0.0F, 156, 78, 43, 25, 156.0F, 78.0F, false);
            }

            ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("name"), (float)(this.guiLeft + 378), (float)(this.guiTop + 88), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.75F, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.since").replaceAll("#day#", (String)var16.get("age")), (float)(this.guiLeft + 378), (float)(this.guiTop + 100), 10395075, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.country_leader"), (float)(this.guiLeft + 353), (float)(this.guiTop + 126), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
            ModernGui.drawScaledStringCustomFont((String)var16.get("leader"), (float)(this.guiLeft + 353), (float)(this.guiTop + 132), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
            if(((String)var16.get("leader_online")).equals("true")) {
               ModernGui.drawScaledStringCustomFont("(" + I18n.func_135053_a("faction.list.label.online") + ")", (float)(this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth((String)var16.get("leader")) / 2), (float)(this.guiTop + 133), 10395075, 0.5F, "left", false, "georamaMedium", 24);
            }

            if(!ClientProxy.cacheHeadPlayer.containsKey(var16.get("leader"))) {
               try {
                  ResourceLocation var18 = AbstractClientPlayer.field_110314_b;
                  var18 = AbstractClientPlayer.func_110311_f((String)var16.get("leader"));
                  AbstractClientPlayer.func_110304_a(var18, (String)var16.get("leader"));
                  ClientProxy.cacheHeadPlayer.put(var16.get("leader"), var18);
               } catch (Exception var12) {
                  ;
               }
            } else {
               Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var16.get("leader")));
               this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var16.get("leader")));
               GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 346, this.guiTop + 139, 8.0F, 16.0F, 8, -8, -13, -13, 64.0F, 64.0F);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               if(((String)var16.get("leader_online")).equals("true")) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 343), (float)(this.guiTop + 124), (float)(481 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 343), (float)(this.guiTop + 124), (float)(491 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               }
            }

            if(this.activeTab.equals(FactionListGUI$TAB.LIST)) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.relation_my_country"), (float)(this.guiLeft + 353), (float)(this.guiTop + 151), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.common." + (String)selectedCountry.get("relation_my_country") + ".nocolor"), (float)(this.guiLeft + 353), (float)(this.guiTop + 157), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 144), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("relation")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.recruitment"), (float)(this.guiLeft + 353), (float)(this.guiTop + 176), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.recruitment." + (String)selectedCountry.get("recruitment")), (float)(this.guiLeft + 353), (float)(this.guiTop + 182), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 169), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("people")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            } else if(this.activeTab.equals(FactionListGUI$TAB.JOIN)) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.recruitment"), (float)(this.guiLeft + 353), (float)(this.guiTop + 151), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.recruitment." + (String)selectedCountry.get("recruitment")), (float)(this.guiLeft + 353), (float)(this.guiTop + 157), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 144), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("people")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.recruitmentThisWeek"), (float)(this.guiLeft + 353), (float)(this.guiTop + 176), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont((String)var16.get("recruitmentThisWeek"), (float)(this.guiLeft + 353), (float)(this.guiTop + 182), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 169), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("stats")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            } else if(this.activeTab.equals(FactionListGUI$TAB.BUY)) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.members_online"), (float)(this.guiLeft + 353), (float)(this.guiTop + 151), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("players"), (float)(this.guiLeft + 353), (float)(this.guiTop + 157), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 144), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("people")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.sell_country"), (float)(this.guiLeft + 353), (float)(this.guiTop + 176), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.price"), (float)(this.guiLeft + 353), (float)(this.guiTop + 182), 14342893, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("price") + "$", (float)(this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.price")) / 2), (float)(this.guiTop + 182), 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 169), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("bank")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            } else if(this.activeTab.equals(FactionListGUI$TAB.ACTIONS)) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.members_online"), (float)(this.guiLeft + 353), (float)(this.guiTop + 151), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("players"), (float)(this.guiLeft + 353), (float)(this.guiTop + 157), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 144), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("people")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.sell_actions"), (float)(this.guiLeft + 353), (float)(this.guiTop + 176), ((Integer)FactionGUI.textColor.get(selectedCountry.get("relation_my_country"))).intValue(), 0.5F, "left", false, "georamaMedium", 22);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.price"), (float)(this.guiLeft + 353), (float)(this.guiTop + 182), 14342893, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont(10000 + Integer.parseInt((String)selectedCountry.get("level")) * Integer.parseInt((String)selectedCountry.get("level")) * 100 + "$", (float)(this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.price")) / 2), (float)(this.guiTop + 182), 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label.number"), (float)(this.guiLeft + 353), (float)(this.guiTop + 189), 14342893, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("nb_actions"), (float)(this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("faction.list.label.number")) / 2), (float)(this.guiTop + 189), 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
               ClientEventHandler.STYLE.bindTexture("faction_list");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 169), (float)(((Integer)iconsXPerRelation.get(selectedCountry.get("relation_my_country"))).intValue() * GUI_SCALE), (float)(((Integer)iconsYByName.get("bank")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            ClientEventHandler.STYLE.bindTexture("faction_list");
            String var19 = "see_country";
            if(!this.activeTab.equals(FactionListGUI$TAB.LIST) && !((String)var16.get("playerFaction")).equals(selectedCountry.get("name"))) {
               if(this.activeTab.equals(FactionListGUI$TAB.JOIN) && !((String)var16.get("playerFaction")).isEmpty()) {
                  var19 = "join_country";
               } else if(this.activeTab.equals(FactionListGUI$TAB.BUY)) {
                  var19 = "buy_country";
               } else if(this.activeTab.equals(FactionListGUI$TAB.ACTIONS)) {
                  var19 = "see_actions";
               }
            } else {
               var19 = "see_country";
            }

            if(mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 103 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 204), (float)(276 * GUI_SCALE), (float)(222 * GUI_SCALE), 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               this.hoveredAction = var19;
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 204), (float)(276 * GUI_SCALE), (float)(204 * GUI_SCALE), 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.label." + var19), (float)(this.guiLeft + 337 + 52), (float)(this.guiTop + 207), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
         }
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   private float getSlideCountries() {
      return this.countCountriesBySearch > 10?(float)(-((this.countCountriesBySearch - 10) * 16 + (this.foundReferent?36:0))) * this.scrollBar.getSliderValue():0.0F;
   }

   public void func_73876_c() {
      this.searchInput.func_73780_a();
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.searchInput.func_73802_a(typedChar, keyCode);
      this.countCountriesBySearch = -1;
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.hoveredAction.isEmpty()) {
            if((this.hoveredAction.equals("see_country") || this.hoveredAction.equals("buy_country")) && selectedCountry != null && !selectedCountry.isEmpty()) {
               Minecraft.func_71410_x().func_71373_a(new FactionGUI((String)selectedCountry.get("name")));
            } else if(this.hoveredAction.equals("see_actions") && selectedCountry != null && !selectedCountry.isEmpty()) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)selectedCountry.get("name"), false)));
               if(selectedCountry.containsKey("id")) {
                  FactionGUI.factionInfos.put("id", selectedCountry.get("id"));
               }

               Minecraft.func_71410_x().func_71373_a(new BankGUI());
            } else if(this.hoveredAction.equals("join_country") && selectedCountry != null && !selectedCountry.isEmpty()) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionAskToJoinPacket((String)selectedCountry.get("name"))));
            } else if(this.hoveredAction.contains("switch_tab")) {
               this.activeTab = FactionListGUI$TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
            }
         } else if(hoveredCountry != null && !hoveredCountry.isEmpty()) {
            if(selectedCountry.equals(hoveredCountry) && System.currentTimeMillis() - lastClick.longValue() < 200L) {
               Minecraft.func_71410_x().func_71373_a(new FactionGUI((String)selectedCountry.get("name")));
            } else {
               selectedCountry = hoveredCountry;
            }
         }

         lastClick = Long.valueOf(System.currentTimeMillis());
      }

      this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
