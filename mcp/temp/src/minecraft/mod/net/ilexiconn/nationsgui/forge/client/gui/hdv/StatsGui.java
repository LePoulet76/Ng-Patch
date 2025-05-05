package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketSimpleButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class StatsGui extends GuiScreen {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market_stats.png");
   private GuiScreen previousGui;
   private int posX;
   private int posY;
   private RenderItem renderItem = new RenderItem();
   private ItemStack itemStack;
   public static final ResourceLocation CHART1_TEXTURE = new ResourceLocation("nationsgui", "tmp/chart1");
   public static final ResourceLocation CHART2_TEXTURE = new ResourceLocation("nationsgui", "tmp/chart2");


   public StatsGui(GuiScreen previousGui, ItemStack itemStack) {
      this.previousGui = previousGui;
      this.itemStack = itemStack;
      PacketCallbacks.MONEY.send(new String[0]);
   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.func_73873_v_();
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 343, 256, 372.0F, 400.0F, false);
      String money = (int)ShopGUI.CURRENT_MONEY + " $";
      this.field_73886_k.func_78276_b(money, this.posX + 312 - this.field_73886_k.func_78256_a(money), this.posY + 9, 16777215);
      this.field_73886_k.func_78276_b(I18n.func_135052_a("hdv.stats.name", new Object[]{this.itemStack.func_82833_r().replaceAll("^\\\u00a7[0-9a-z]", "")}), this.posX + 75, this.posY + 80, 16777215);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.posX + 28), (float)(this.posY + 3), 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      this.field_73886_k.func_78261_a(I18n.func_135053_a("hdv.title"), 0, 0, 16777215);
      GL11.glPopMatrix();
      RenderHelper.func_74520_c();
      GL11.glEnable('\u803a');
      GL11.glPushMatrix();
      float size = 3.0F;
      GL11.glTranslatef((float)(this.posX + 37) - 8.0F * size, (float)(this.posY + 117) - 8.0F * size, 0.0F);
      GL11.glScalef(size, size, size);
      this.renderItem.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), this.itemStack, 0, 0);
      GL11.glPopMatrix();
      GL11.glDisable(2896);
      GL11.glDisable('\u803a');
      RenderHelper.func_74518_a();
      super.func_73863_a(par1, par2, par3);
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)this.field_73882_e.field_71443_c, (double)this.field_73882_e.field_71440_d, 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(CHART1_TEXTURE);
      this.drawPriceChart(this.posX + 70, this.posY + 90, 260, 75);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(CHART2_TEXTURE);
      this.drawPriceChart(this.posX + 70, this.posY + 170, 260, 75);
      if(par1 >= this.posX + 7 && par1 <= this.posX + 7 + 60 && par2 >= this.posY + 75 && par2 <= this.posY + 75 + 83) {
         ScaledResolution scaledresolution = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
         GL11.glClear(256);
         GL11.glMatrixMode(5889);
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
         GL11.glMatrixMode(5888);
         GL11.glLoadIdentity();
         GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
         NationsGUIClientHooks.drawItemStackTooltip(this.itemStack, par1, par2);
         GL11.glDisable(2896);
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.posX = this.field_73880_f / 2 - 171;
      this.posY = this.field_73881_g / 2 - 126;
      this.field_73887_h.clear();
      this.field_73887_h.add(new MarketSimpleButton(0, this.posX + 6, this.posY + 35, 75, 15, I18n.func_135053_a("hdv.return")));
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton.field_73741_f == 0) {
         this.field_73882_e.func_71373_a(this.previousGui);
      }

   }

   private void drawPriceChart(int x, int y, int width, int height) {
      ScaledResolution scaledresolution = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
      x *= scaledresolution.func_78325_e();
      y *= scaledresolution.func_78325_e();
      width *= scaledresolution.func_78325_e();
      height *= scaledresolution.func_78325_e();
      ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 0, width, height, (float)width, (float)height, true);
   }

}
