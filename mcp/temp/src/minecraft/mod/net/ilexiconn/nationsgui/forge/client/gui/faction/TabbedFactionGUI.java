package net.ilexiconn.nationsgui.forge.client.gui.faction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$10;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$11;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI$9;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public abstract class TabbedFactionGUI extends GuiScreen {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   public static int GUI_SCALE = 3;
   protected int xSize = 463;
   protected int ySize = 235;
   protected int guiLeft;
   protected int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static List<String> tooltipToDraw = new ArrayList();
   protected String hoveredAction = "";
   public static HashMap<String, Integer> tabIconsPositionX = new TabbedFactionGUI$1();
   public static HashMap<String, Integer> tabIconsY = new TabbedFactionGUI$2();


   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public static void initTabs() {
      TABS.clear();
      TABS.add(new TabbedFactionGUI$3());
      TABS.add(new TabbedFactionGUI$4());
      TABS.add(new TabbedFactionGUI$5());
      TABS.add(new TabbedFactionGUI$6());
      TABS.add(new TabbedFactionGUI$7());
      TABS.add(new TabbedFactionGUI$8());
      TABS.add(new TabbedFactionGUI$9());
      TABS.add(new TabbedFactionGUI$10());
      TABS.add(new TabbedFactionGUI$11());
   }

   public abstract void drawScreen(int var1, int var2);

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         for(int i = 0; i < TABS.size(); ++i) {
            GuiScreenTab type = (GuiScreenTab)TABS.get(i);
            if(mouseX >= this.guiLeft + 11 && mouseX <= this.guiLeft + 11 + 19 && mouseY >= this.guiTop + 1 + i * 19 && mouseY <= this.guiTop + 1 + i * 19 + 19) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     type.call();
                  } catch (Exception var7) {
                     var7.printStackTrace();
                  }
               }
            }
         }

         if(mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_global");
      if(mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 445), (float)(this.guiTop + 8), (float)(0 * GUI_SCALE), (float)(18 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0F, 1536.0F, false);
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 445), (float)(this.guiTop + 8), (float)(0 * GUI_SCALE), (float)(2 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0F, 1536.0F, false);
      }

      if(FactionGUI.factionInfos != null && (this instanceof FactionGUI && !FactionGUI.displayLevels || this instanceof MembersGUI || this instanceof FactionSkillsGUI || this instanceof WarGUI || this instanceof FactionStatsGUI || this instanceof DiplomatieGUI) && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
         ClientEventHandler.STYLE.bindTexture("faction_global");
         if(mouseX >= this.guiLeft + 430 && mouseX <= this.guiLeft + 430 + 9 && mouseY >= this.guiTop + 8 && mouseY <= this.guiTop + 8 + 8) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 430), (float)(this.guiTop + 8), (float)(30 * GUI_SCALE), (float)(18 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            if(FactionGUI.getResearchLevel("general") >= 11) {
               this.hoveredAction = "edit_photo";
            } else {
               tooltipToDraw = Arrays.asList(I18n.func_135053_a("gui.faction.not_enough_research").split("##"));
            }
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 430), (float)(this.guiTop + 8), (float)(30 * GUI_SCALE), (float)(2 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }
      }

      ClientEventHandler.STYLE.bindTexture("faction_global");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(280 * GUI_SCALE), 19 * GUI_SCALE, 209 * GUI_SCALE, 19, 209, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

      int i;
      GuiScreenTab type;
      for(i = 0; i < TABS.size(); ++i) {
         type = (GuiScreenTab)TABS.get(i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int x = i % 9;
         int y = i / 9;
         if(this.getClass() == type.getClassReferent()) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + i * 19), (float)(399 * GUI_SCALE), (float)(64 * GUI_SCALE + i * 19 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + 1 + i * 19), (float)(((Integer)tabIconsPositionX.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), (float)((FactionGUI.factionInfos != null?((Integer)tabIconsY.get(FactionGUI.factionInfos.get("actualRelation"))).intValue():129) * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         } else if(mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 19 && mouseY < this.guiTop + 1 + i * 19 + 19) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + 1 + i * 19), (float)(((Integer)tabIconsPositionX.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), (float)(34 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + 1 + i * 19), (float)(((Integer)tabIconsPositionX.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), (float)(129 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }
      }

      for(i = 0; i < TABS.size(); ++i) {
         type = (GuiScreenTab)TABS.get(i);
         if(mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 19 && mouseY < this.guiTop + 1 + i * 19 + 19) {
            this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.tab." + type.getClassReferent().getSimpleName())}), mouseX, mouseY, this.field_73886_k);
         }
      }

      if(tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, partialTicks);
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

}
