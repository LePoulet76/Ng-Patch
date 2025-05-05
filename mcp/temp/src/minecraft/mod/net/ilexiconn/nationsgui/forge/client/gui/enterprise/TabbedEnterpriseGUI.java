package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public abstract class TabbedEnterpriseGUI extends GuiScreen {

   protected int xSize = 400;
   protected int ySize = 250;
   protected int guiLeft;
   protected int guiTop;
   public static boolean mapLoaded;
   private RenderItem itemRenderer = new RenderItem();
   public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);


   public TabbedEnterpriseGUI() {
      mapLoaded = false;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_73873_v_();
      this.drawScreen(mouseX, mouseY);
      if(EnterpriseGui.enterpriseInfos != null) {
         if(!mapLoaded && EnterpriseGui.enterpriseInfos.size() > 0 && EnterpriseGui.enterpriseInfos.get("position") != null && !((String)EnterpriseGui.enterpriseInfos.get("position")).isEmpty()) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)EnterpriseGui.enterpriseInfos.get("position")).split("##")[0]), Integer.parseInt(((String)EnterpriseGui.enterpriseInfos.get("position")).split("##")[1]), 6, 6)));
            mapLoaded = true;
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ClientEventHandler.STYLE.bindTexture("enterprise_main");
         if(mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
         }

         if(((String)EnterpriseGui.enterpriseInfos.get("name")).length() <= 9) {
            this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.8F, true, true);
         } else {
            this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.0F, true, true);
         }

         this.drawScaledString(I18n.func_135053_a("faction.common.age_1") + " " + EnterpriseGui.enterpriseInfos.get("age") + " " + I18n.func_135053_a("faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 11842740, 0.65F, true, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_main");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 12), (float)(this.guiTop + 61), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 32, this.guiTop + 66, 16777215, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_main");

         int i;
         for(i = 0; i < EnterpriseGui.TABS.size(); ++i) {
            GuiScreenTab x = (GuiScreenTab)EnterpriseGui.TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int x1 = EnterpriseGui.getTabIndex((GuiScreenTab)EnterpriseGui.TABS.get(i));
            if(this.getClass() == x.getClassReferent()) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 23, 250, 29, 31, 512.0F, 512.0F, false);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), x1 * 20, 301, 20, 20, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 0, 250, 23, 31, 512.0F, 512.0F, false);
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), x1 * 20, 301, 20, 20, 512.0F, 512.0F, false);
               GL11.glDisable(3042);
            }
         }

         if(mapLoaded) {
            GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
            GL11.glDisable(2929);
            this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + 11), (float)(this.guiTop + 83), 0, 362, 98, 78, 512.0F, 512.0F, false);
            GUIUtils.endGLScissor();
         }

         for(i = 0; i < EnterpriseGui.TABS.size(); ++i) {
            int var7 = EnterpriseGui.getTabIndex((GuiScreenTab)EnterpriseGui.TABS.get(i));
            if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31) {
               this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("enterprise.tab." + var7)}), mouseX, mouseY, this.field_73886_k);
            }
         }
      }

      super.func_73863_a(mouseX, mouseY, partialTicks);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   public abstract void drawScreen(int var1, int var2);

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         for(int i = 0; i < EnterpriseGui.TABS.size(); ++i) {
            GuiScreenTab type = (GuiScreenTab)EnterpriseGui.TABS.get(i);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 50 + i * 31) {
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

         if(mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
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
