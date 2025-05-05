package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngcontent.NGContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseGlobalMarketDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseGlobalMarketGui extends GuiScreen {

   protected int xSize = 405;
   protected int ySize = 250;
   private int guiLeft;
   private int guiTop;
   public static HashMap<String, Integer> cerealsPrice;
   public static HashMap<String, String> cerealsPriceChange;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   public String chartToDisplay = "prices";
   public String hoveredChartToDisplay = "";
   public static final ResourceLocation PRICES_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_prices");
   public static final ResourceLocation SALES_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_sales");
   public static final ResourceLocation STOCKS_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_stocks");


   public EnterpriseGlobalMarketGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseGlobalMarketDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      Object tooltipToDraw = new ArrayList();
      this.hoveredChartToDisplay = "";
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
      if(mouseX >= this.guiLeft + 392 && mouseX <= this.guiLeft + 392 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 392), (float)(this.guiTop - 8), 83, 254, 9, 10, 512.0F, 512.0F, false);
      } else {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 392), (float)(this.guiTop - 8), 83, 264, 9, 10, 512.0F, 512.0F, false);
      }

      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 170), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 170)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("globalmarket.title"), this.guiLeft + 14, this.guiTop + 170, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      if(this.chartToDisplay.equals("prices")) {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 258), (float)(this.guiTop + 19), 0, 259, 43, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("globalmarket.label.prices"), this.guiLeft + 281, this.guiTop + 21, 0, 1.0F, true, false);
      if(mouseX > this.guiLeft + 258 && mouseX < this.guiLeft + 258 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
         this.hoveredChartToDisplay = "prices";
      }

      if(this.chartToDisplay.equals("sales")) {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 303), (float)(this.guiTop + 19), 0, 259, 43, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("globalmarket.label.sales"), this.guiLeft + 326, this.guiTop + 21, 0, 1.0F, true, false);
      if(mouseX > this.guiLeft + 303 && mouseX < this.guiLeft + 303 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
         this.hoveredChartToDisplay = "sales";
      }

      if(this.chartToDisplay.equals("stocks")) {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 348), (float)(this.guiTop + 19), 0, 259, 43, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("globalmarket.label.stocks"), this.guiLeft + 371, this.guiTop + 21, 0, 1.0F, true, false);
      if(mouseX > this.guiLeft + 348 && mouseX < this.guiLeft + 348 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
         this.hoveredChartToDisplay = "stocks";
      }

      if(loaded) {
         ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
         ResourceLocation chartTexture = PRICES_TEXTURE;
         if(this.chartToDisplay.equals("sales")) {
            chartTexture = SALES_TEXTURE;
         } else if(this.chartToDisplay.equals("stocks")) {
            chartTexture = STOCKS_TEXTURE;
         }

         Minecraft.func_71410_x().func_110434_K().func_110577_a(chartTexture);
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 32), 0, 0, 338, 133, 338.0F, 133.0F, true);
         int indexX = 0;
         int indexY = 0;

         for(int i = 0; i < cerealsPrice.size(); ++i) {
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            String cerealName = (String)cerealsPrice.keySet().toArray()[i];
            Integer cerealPrice = (Integer)cerealsPrice.get(cerealName);
            int offsetX = this.guiLeft + 47 + indexX * 88;
            int offsetY = this.guiTop + 173 + indexY * 22;
            ModernGui.drawNGBlackSquare(offsetX, offsetY, 80, 18);
            if(mouseX >= offsetX && mouseX <= offsetX + 80 && mouseY >= offsetY && mouseY <= offsetY + 18) {
               tooltipToDraw = Arrays.asList(new String[]{cerealName.substring(0, 1).toUpperCase() + cerealName.substring(1)});
            }

            String price = cerealPrice + (((String)cerealsPriceChange.get(cerealName)).equals("+")?"\u00a7a":"\u00a7c") + "$";
            this.drawScaledString(price, offsetX + 80 - this.field_73886_k.func_78256_a(price) - 4, offsetY + 5, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 80 - this.field_73886_k.func_78256_a(price) - 12), (float)(offsetY + 6), ((String)cerealsPriceChange.get(cerealName)).equals("+")?70:77, 311, 6, 6, 512.0F, 512.0F, false);
            GL11.glEnable(2896);
            ItemStack cereal = new ItemStack(NGContent.getCerealIdFromName(cerealName).intValue(), 1, 0);
            this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), cereal, offsetX + 3, offsetY + 1);
            ++indexX;
            if(indexX == 4) {
               ++indexY;
               indexX = 0;
            }
         }
      }

      if(!((List)tooltipToDraw).isEmpty()) {
         this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
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

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 392 && mouseX < this.guiLeft + 392 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(!this.hoveredChartToDisplay.isEmpty()) {
            this.chartToDisplay = this.hoveredChartToDisplay;
            this.hoveredChartToDisplay = "";
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
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

}
