package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public abstract class TabbedGUI extends GuiScreen {

   protected final EntityPlayer player;
   protected int xSize = 182;
   protected int ySize = 223;
   protected int guiLeft;
   protected int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   private final Map<Class<? extends GuiScreen>, Integer> tabAlerts = new HashMap();


   public TabbedGUI(EntityPlayer player) {
      this.player = player;
      this.calculateAlerts();
   }

   protected void calculateAlerts() {
      this.tabAlerts.clear();
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_73873_v_();
      this.drawScreen(mouseX, mouseY);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("inventory");

      int i;
      GuiScreenTab type;
      int x;
      int y;
      for(i = 0; i <= 4; ++i) {
         type = (GuiScreenTab)InventoryGUI.TABS.get(i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         x = i % 3;
         y = i / 3;
         if(this.getClass() == type.getClassReferent()) {
            this.func_73729_b(this.guiLeft - 23, this.guiTop + 55 + i * 31, 23, 223, 29, 30);
            this.func_73729_b(this.guiLeft - 23 + 3, this.guiTop + 55 + i * 31 + 5, 182 + x * 20, y * 20, 20, 20);
         } else {
            this.func_73729_b(this.guiLeft - 20, this.guiTop + 55 + i * 31, 0, 223, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.guiLeft - 20 + 3, this.guiTop + 55 + i * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      for(i = 5; i < InventoryGUI.TABS.size(); ++i) {
         type = (GuiScreenTab)InventoryGUI.TABS.get(i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         x = i % 3;
         y = i / 3;
         if(this.getClass() == type.getClassReferent()) {
            this.func_73729_b(this.guiLeft + 178, this.guiTop + 55 + (i - 4) * 31, 85, 223, 29, 30);
            this.func_73729_b(this.guiLeft + 175, this.guiTop + 55 + (i - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
         } else {
            this.func_73729_b(this.guiLeft + 178, this.guiTop + 55 + (i - 4) * 31, 114, 223, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.guiLeft + 179, this.guiTop + 55 + (i - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      if(ClientData.currentFaction != null) {
         ClientEventHandler.STYLE.bindTexture("faction_main");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 7), (float)(this.guiTop + 223), 0, 451, 169, 23, 512.0F, 512.0F, false);
         ClientEventHandler.STYLE.bindTexture("inventory");
         Double var8;
         if(!ClientData.currentFaction.contains("Wilderness")) {
            var8 = Double.valueOf((double)this.field_73886_k.func_78256_a(I18n.func_135053_a("gui.inventory.tab.country")) * 1.2D / 2.0D);
            this.func_73729_b(this.guiLeft + 91 - var8.intValue() - 17, this.guiTop + 225, 201, 107, 12, 17);
            this.drawScaledString(I18n.func_135053_a("gui.inventory.tab.country"), this.guiLeft + 93 - var8.intValue(), this.guiTop + 230, 16777215, 1.2F, false, true);
         } else {
            var8 = Double.valueOf((double)this.field_73886_k.func_78256_a(I18n.func_135053_a("gui.inventory.tab.create")) * 1.2D / 2.0D);
            this.func_73729_b(this.guiLeft + 91 - var8.intValue() - 17, this.guiTop + 225, 185, 107, 12, 17);
            this.drawScaledString(I18n.func_135053_a("gui.inventory.tab.create"), this.guiLeft + 93 - var8.intValue(), this.guiTop + 230, 16777215, 1.2F, false, true);
         }
      }

      this.drawScaledString(this.player.getDisplayName(), this.guiLeft + 90, this.guiTop + 10, 16777215, 1.7F, true, true);
      this.drawScaledString(ClientData.currentFaction, this.guiLeft + 90, this.guiTop + 28, 13027014, 1.0F, true, true);
      super.func_73863_a(mouseX, mouseY, partialTicks);

      for(i = 0; i <= 4; ++i) {
         if(mouseX >= this.guiLeft - (this.getClass() == ((GuiScreenTab)InventoryGUI.TABS.get(i)).getClassReferent()?23:20) && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 55 + i * 31 && mouseY <= this.guiTop + 85 + i * 31) {
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a("gui.inventory.tab." + i)), mouseX, mouseY, this.field_73886_k);
         }
      }

      for(i = 5; i < InventoryGUI.TABS.size(); ++i) {
         if((i != 5 || !InventoryGUI.isInAssault) && mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 201 && mouseY >= this.guiTop + 55 + (i - 4) * 31 && mouseY <= this.guiTop + 55 + 31 + (i - 4) * 31) {
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a("gui.inventory.tab." + i)), mouseX, mouseY, this.field_73886_k);
         }
      }

      this.drawTooltip(mouseX, mouseY);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   public abstract void drawScreen(int var1, int var2);

   public abstract void drawTooltip(int var1, int var2);

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         int i;
         GuiScreenTab type;
         for(i = 0; i <= 4; ++i) {
            type = (GuiScreenTab)InventoryGUI.TABS.get(i);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 55 + i * 31 && mouseY <= this.guiTop + 85 + i * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     this.field_73882_e.field_71439_g.func_71053_j();
                     type.call();
                  } catch (Exception var8) {
                     var8.printStackTrace();
                  }
               }
            }
         }

         for(i = 5; i < InventoryGUI.TABS.size(); ++i) {
            if(i != 5 || !InventoryGUI.isInAssault) {
               type = (GuiScreenTab)InventoryGUI.TABS.get(i);
               if(mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 201 && mouseY >= this.guiTop + 55 + (i - 4) * 31 && mouseY <= this.guiTop + 55 + 31 + (i - 4) * 31) {
                  this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
                  if(this.getClass() != type.getClassReferent()) {
                     try {
                        this.field_73882_e.field_71439_g.func_71053_j();
                        type.call();
                     } catch (Exception var7) {
                        var7.printStackTrace();
                     }
                  }
               }
            }
         }

         if(mouseX >= this.guiLeft + 7 && mouseX <= this.guiLeft + 7 + 169 && mouseY >= this.guiTop + 223 && mouseY <= this.guiTop + 223 + 23 && ClientData.currentFaction != null) {
            if(!ClientData.currentFaction.contains("Wilderness")) {
               Minecraft.func_71410_x().func_71373_a(new FactionGUI(ClientData.currentFaction));
            } else {
               Minecraft.func_71410_x().func_71373_a(new FactionCreateGUI());
            }
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
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
