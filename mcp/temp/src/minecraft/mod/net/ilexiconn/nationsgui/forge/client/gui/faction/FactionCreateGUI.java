package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class FactionCreateGUI extends TabbedFactionListGUI {

   public static ArrayList<HashMap<String, String>> availableCountries = new ArrayList();
   public static HashMap<String, String> selectedCountry = new HashMap();
   public static HashMap<String, String> hoveredCountry = new HashMap();
   public MinimapRenderer minimapRenderer = new MinimapRenderer(8, 8);
   public Long lastMapLoading = Long.valueOf(0L);
   public static boolean loaded = false;
   public boolean announceCreation = false;
   private GuiScrollBarGeneric scrollBar;
   private GuiTextField searchInput;
   private CFontRenderer cFontSemiBold28;
   public int countCountriesBySearch = -1;


   public FactionCreateGUI() {
      initTabs();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionCreateDataPacket()));
      this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(28));
      this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 161), (float)(this.guiTop + 78), 135, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.searchInput = new CustomInputFieldGUI(this.guiLeft + 62, this.guiTop + 42, 94, 12, "georamaSemiBold", 28);
      this.searchInput.func_73786_a(false);
      this.searchInput.func_73804_f(15);
      this.countCountriesBySearch = -1;
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
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.create_country"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
      ClientEventHandler.STYLE.bindTexture("faction_list");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 36), (float)(276 * GUI_SCALE), (float)(157 * GUI_SCALE), 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      this.searchInput.func_73795_f();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_list");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 69), (float)(276 * GUI_SCALE), (float)(0 * GUI_SCALE), 129 * GUI_SCALE, 152 * GUI_SCALE, 129, 152, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
      GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 69, 119, 152);
      if(availableCountries != null && availableCountries.size() > 0) {
         int continent = 0;
         Iterator offsetY = availableCountries.iterator();

         while(offsetY.hasNext()) {
            HashMap index = (HashMap)offsetY.next();
            if(this.searchInput.func_73781_b().isEmpty() || ((String)index.get("name")).toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) {
               int offsetX = this.guiLeft + 42;
               Float offsetY1 = Float.valueOf((float)(this.guiTop + 69 + continent * 12) + this.getSlideCountries());
               String continent1 = getContientByCoords(Integer.parseInt((String)selectedCountry.get("x")), Integer.parseInt((String)selectedCountry.get("z")), (String)index.get("name"));
               ModernGui.drawScaledStringCustomFont((String)index.get("name"), (float)(offsetX + 11), (float)(offsetY1.intValue() + 12), ((String)index.get("name")).equals(selectedCountry.get("name"))?7239406:16514302, 0.5F, "left", false, "georamaBold", 28);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("zone.name.zone_" + continent1), (float)(offsetX + 70), (float)(offsetY1.intValue() + 11), 10395075, 0.5F, "left", false, "georamaMedium", 28);
               if(mouseX >= offsetX && mouseX <= offsetX + 154 && (float)mouseY >= offsetY1.floatValue() + 12.0F && (float)mouseY <= offsetY1.floatValue() + 12.0F + 12.0F) {
                  hoveredCountry = index;
               }

               ++continent;
            }
         }

         if(this.countCountriesBySearch == -1) {
            this.countCountriesBySearch = continent + 1;
         }
      } else {
         ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("faction.create.unavailable_1"), (float)(this.guiLeft + 50), (float)(this.guiTop + 80), 10395075, 0.5F, "left", false, "georamaSemiBold", 26, 8, 200);
         ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("faction.create.unavailable_2"), (float)(this.guiLeft + 50), (float)(this.guiTop + 100), 10395075, 0.5F, "left", false, "georamaSemiBold", 26, 8, 200);
      }

      GUIUtils.endGLScissor();
      if(mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 129 && mouseY >= this.guiTop + 69 && mouseY <= this.guiTop + 69 + 152) {
         this.scrollBar.draw(mouseX, mouseY);
      }

      if(availableCountries != null && availableCountries.size() != 0) {
         if(selectedCountry != null && !selectedCountry.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("faction_list");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 187), (float)(this.guiTop + 36), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 266 * GUI_SCALE, 185 * GUI_SCALE, 266, 185, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont((String)selectedCountry.get("name"), (float)(this.guiLeft + 196), (float)(this.guiTop + 49), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
            GUIUtils.startGLScissor(this.guiLeft + 195, this.guiTop + 69, 121, 112);
            if(System.currentTimeMillis() - this.lastMapLoading.longValue() > 2500L) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt((String)selectedCountry.get("x")), Integer.parseInt((String)selectedCountry.get("z")), 8, 8)));
            }

            GL11.glDisable(2929);
            this.minimapRenderer.renderMap(this.guiLeft + 195, this.guiTop + 69, mouseX, mouseY, false);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GUIUtils.endGLScissor();
            String var11 = getContientByCoords(Integer.parseInt((String)selectedCountry.get("x")), Integer.parseInt((String)selectedCountry.get("z")), (String)selectedCountry.get("name"));
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.localisation"), (float)(this.guiLeft + 330), (float)(this.guiTop + 75), 7239406, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("zone.name.zone_" + var11), (float)(this.guiLeft + 330), (float)(this.guiTop + 83), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont("(" + (String)selectedCountry.get("x") + ", " + (String)selectedCountry.get("z") + ")", (float)(this.guiLeft + 333 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a("zone.name.zone_" + var11)) / 2), (float)(this.guiTop + 84), 10395075, 0.5F, "left", false, "georamaSemiBold", 22);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.resources"), (float)(this.guiLeft + 330), (float)(this.guiTop + 97), 7239406, 0.5F, "left", false, "georamaMedium", 26);
            int var12 = 7;
            boolean var13 = false;
            String[] var14 = I18n.func_135053_a("zone.resources.zone_" + var11).split("##");
            int var15 = var14.length;

            String line;
            int var16;
            for(var16 = 0; var16 < var15; ++var16) {
               line = var14[var16];
               ModernGui.drawScaledStringCustomFont(line, (float)(this.guiLeft + 330), (float)(this.guiTop + 97 + var12), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               var12 += 8;
            }

            var12 += 6;
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.culture"), (float)(this.guiLeft + 330), (float)(this.guiTop + 97 + var12), 7239406, 0.5F, "left", false, "georamaMedium", 26);
            var12 += 7;
            var14 = I18n.func_135053_a("zone.cereals.zone_" + var11).split("##");
            var15 = var14.length;

            for(var16 = 0; var16 < var15; ++var16) {
               line = var14[var16];
               ModernGui.drawScaledStringCustomFont(line, (float)(this.guiLeft + 330), (float)(this.guiTop + 97 + var12), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               var12 += 8;
            }

            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 323), (float)(this.guiTop + 185), (float)((this.announceCreation?321:329) * GUI_SCALE), (float)((this.announceCreation?199:189) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.create.announce"), (float)(this.guiLeft + 335), (float)(this.guiTop + 185), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
            if(mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 8 && mouseY >= this.guiTop + 185 && mouseY <= this.guiTop + 185 + 8) {
               this.hoveredAction = "checkbox_announce";
            }

            ClientEventHandler.STYLE.bindTexture("faction_list");
            if(mouseX >= this.guiLeft + 385 && mouseX <= this.guiLeft + 385 + 59 && mouseY >= this.guiTop + 199 && mouseY <= this.guiTop + 199 + 13) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 385), (float)(this.guiTop + 199), (float)(409 * GUI_SCALE), (float)(156 * GUI_SCALE), 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               this.hoveredAction = "country_create";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 385), (float)(this.guiTop + 199), (float)(409 * GUI_SCALE), (float)(122 * GUI_SCALE), 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.create"), (float)(this.guiLeft + 385 + 29), (float)(this.guiTop + 202), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
         }
      } else {
         ClientEventHandler.STYLE.bindTexture("faction_create");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 187), (float)(this.guiTop + 36), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 272 * GUI_SCALE, 185 * GUI_SCALE, 272, 185, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.create.label.join_country"), (float)(this.guiLeft + 195), (float)(this.guiTop + 45), 16514302, 0.75F, "left", false, "georamaSemiBold", 30);
         ClientEventHandler.STYLE.bindTexture("faction_list");
         if(mouseX >= this.guiLeft + 195 && mouseX <= this.guiLeft + 195 + 59 && mouseY >= this.guiTop + 63 && mouseY <= this.guiTop + 63 + 13) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 195), (float)(this.guiTop + 63), (float)(409 * GUI_SCALE), (float)(156 * GUI_SCALE), 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            this.hoveredAction = "country_list";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 195), (float)(this.guiTop + 63), (float)(409 * GUI_SCALE), (float)(122 * GUI_SCALE), 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.list.see_list"), (float)(this.guiLeft + 195 + 29), (float)(this.guiTop + 66), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   private float getSlideCountries() {
      return this.countCountriesBySearch > 10?(float)(-(this.countCountriesBySearch - 10) * 12) * this.scrollBar.getSliderValue():0.0F;
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
            if(this.hoveredAction.equals("checkbox_announce")) {
               this.announceCreation = !this.announceCreation;
            } else if(this.hoveredAction.equals("country_create")) {
               ClientProxy.clientConfig.currentServerTime = Long.valueOf(System.currentTimeMillis());
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionCreatePacket((String)selectedCountry.get("name"), this.announceCreation, true)));
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);

               try {
                  ClientProxy.saveConfig();
               } catch (IOException var5) {
                  var5.printStackTrace();
               }
            } else if(this.hoveredAction.equals("country_list")) {
               Minecraft.func_71410_x().func_71373_a(new FactionListGUI());
            }
         } else if(hoveredCountry != null && !hoveredCountry.isEmpty()) {
            selectedCountry = hoveredCountry;
         }
      }

      this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public static String getContientByCoords(int x, int z, String country) {
      return !country.equals("Ouzbekistan") && !country.equals("Turkmenistan")?(x >= -8195 && x <= -3000 && z >= -4225 && z <= -1000?"america_north":(x >= -8195 && x <= -3000 && z >= -1000 && z <= 300?"america_middle":(x >= -8195 && x <= -3000 && z >= 300 && z <= 3800?"america_south":(x >= -3000 && x <= -2200 && z >= -4225 && z <= 3800?"atlantic":(x >= -2200 && x <= 1800 && z >= -4225 && z <= -1600?"europe":(x >= -2200 && x <= 1800 && z >= -1600 && z <= 3800?"africa":(x >= 1800 && x <= 8320 && z >= -4225 && z <= -1600?"asia":(x >= 1800 && x <= 8320 && z >= -1600 && z <= 3800?"oceania":(x >= -8195 && x <= 8320 && z >= 3800 && z <= 5400?"antarctica":"unknow"))))))))):"asia";
   }

}
