package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI$TAB;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionStatsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;

public class FactionStatsGUI extends TabbedFactionGUI {

   public static boolean loaded = false;
   public static HashMap<FactionStatsGUI$TAB, ResourceLocation> TEXTURES = new HashMap();
   public FactionStatsGUI$TAB activeTab;


   public FactionStatsGUI() {
      this.activeTab = FactionStatsGUI$TAB.NOTATIONS;
      loaded = false;
      FactionStatsGUI$TAB[] var1 = FactionStatsGUI$TAB.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         FactionStatsGUI$TAB tab = var1[var3];
         TEXTURES.put(tab, new ResourceLocation("nationsgui", "tmp/faction_stats_" + tab.name()));
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionStatsDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw.clear();
      this.hoveredAction = "";
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      if(loaded) {
         if(FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("stats")) {
            ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("stats"));
         } else {
            ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33N_N33NN.png");
         }

         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
         ClientEventHandler.STYLE.bindTexture("faction_global");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(280 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 292), (float)(this.guiTop - 15), (float)(382 * GUI_SCALE), (float)(0 * GUI_SCALE), 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.stats.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), (float)(this.guiLeft + 43), (float)(this.guiTop + 32), 10395075, 0.5F, "left", false, "georamaMedium", 25, 8, 350);
         int index = 0;
         FactionStatsGUI$TAB[] chartTexture = FactionStatsGUI$TAB.values();
         int var6 = chartTexture.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            FactionStatsGUI$TAB tab = chartTexture[var7];
            ClientEventHandler.STYLE.bindTexture("faction_stats");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42 + 66 * index), (float)(this.guiTop + 77), (float)(0 * GUI_SCALE), (float)((this.activeTab.equals(tab)?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.stats.tab." + tab.name().toLowerCase()), (float)(this.guiLeft + 42 + 66 * index + 32), (float)(this.guiTop + 80), this.activeTab.equals(tab)?7239406:14342893, 0.5F, "center", false, "georamaSemiBold", 26);
            if(mouseX >= this.guiLeft + 42 + 66 * index && mouseX <= this.guiLeft + 42 + 66 * index + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
               this.hoveredAction = "switch_tab#" + tab.name();
            }

            ++index;
         }

         ResourceLocation var9 = (ResourceLocation)TEXTURES.get(this.activeTab);
         Minecraft.func_71410_x().func_110434_K().func_110577_a(var9);
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 43), (float)(this.guiTop + 102), 0, 0, 411, 125, 411.0F, 125.0F, true);
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   public void drawScreen(int mouseX, int mouseY) {}

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if(this.hoveredAction.equals("edit_photo")) {
            ClientData.lastCaptureScreenshot.put("stats", Long.valueOf(System.currentTimeMillis()));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d(I18n.func_135053_a("faction.take_picture")));
         } else if(this.hoveredAction.contains("switch_tab")) {
            this.activeTab = FactionStatsGUI$TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
         }

         this.hoveredAction = "";
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
