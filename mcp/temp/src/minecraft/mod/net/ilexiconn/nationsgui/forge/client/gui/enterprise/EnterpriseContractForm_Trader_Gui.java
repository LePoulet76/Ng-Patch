package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Default_Packet;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormTraderPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseContractForm_Trader_Gui extends GuiScreen {

   private GuiButton cancelButton;
   private GuiButton validButton;
   private GuiTextField priceInput;
   private RenderItem itemRenderer = new RenderItem();
   protected int xSize = 371;
   protected int ySize = 223;
   private int guiLeft;
   private int guiTop;
   public static boolean loaded = false;
   public static HashMap<String, Object> data = new HashMap();


   public void func_73876_c() {
      this.priceInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractFormTraderPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.cancel"));
      this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.valid"));
      this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 219, this.guiTop + 166, 58, 10);
      this.priceInput.func_73786_a(false);
      this.priceInput.func_73804_f(7);
      this.priceInput.func_73782_a("0");
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      List tooltipToDraw = null;
      ModernGui.drawModalRectWithCustomSizedTexture((float)((int)((double)(this.guiLeft + 91) - (double)this.field_73886_k.func_78256_a(I18n.func_135053_a("enterprise.contract.title.services")) * 1.2D / 2.0D - 8.0D - 2.0D)), (float)(this.guiTop + 16), 0, 276, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 16777215, 1.2F, true, false);
      int index = 0;
      String[] type = ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##");
      int investLimit = type.length;

      for(int playerInvest = 0; playerInvest < investLimit; ++playerInvest) {
         String canInvest = type[playerInvest];
         this.drawScaledString(canInvest.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 16777215, 0.8F, false, false);
         ++index;
      }

      this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 16777215, 1.7F, true, true);
      String var11 = I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase());
      ClientEventHandler.STYLE.bindTexture("enterprise_main");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 280 - this.field_73886_k.func_78256_a(var11) / 2 - 7 - 4), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 280 + 7, this.guiTop + 29, 11842740, 1.0F, true, false);
      if(loaded) {
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.mymoney"), this.guiLeft + 197, this.guiTop + 70 - 9, 1644825, 1.0F, false, false);
         ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 70, 165, 20);
         this.drawScaledString(String.format("%.0f", new Object[]{(Double)data.get("playerMoney")}) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 70 + 6, 16777215, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.invested").replace("<enterprise>", (String)EnterpriseGui.enterpriseInfos.get("name")), this.guiLeft + 197, this.guiTop + 115 - 9, 1644825, 1.0F, false, false);
         ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 115, 165, 20);
         this.drawScaledString(String.format("%.2f", new Object[]{(Double)data.get("totalPlayerInvestment")}) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 115 + 6, 16777215, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.investment"), this.guiLeft + 197, this.guiTop + 151, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 160, 165, 20);
         this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 16777215, 1.3F, true, false);
         this.priceInput.func_73795_f();
         if(this.isNumeric(this.priceInput.func_73781_b())) {
            Double var12 = Double.valueOf(((Double)data.get("totalInvestment")).doubleValue() * 0.1D);
            var12 = Double.valueOf(Math.max(50000.0D, var12.doubleValue()));
            Double var13 = (Double)data.get("totalPlayerInvestment");
            var13 = Double.valueOf(var13.doubleValue() + (double)Integer.parseInt(this.priceInput.func_73781_b()));
            if(var13.doubleValue() > var12.doubleValue()) {
               this.validButton.field_73742_g = false;
               double var14 = var12.doubleValue() - ((Double)data.get("totalPlayerInvestment")).doubleValue();
               if(mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20) {
                  tooltipToDraw = Arrays.asList(I18n.func_135053_a("enterprise.contract.trader.investment_limit").replace("<limit>", var14 + "").split("##"));
               }
            } else if(data.containsKey("countContract") && ((Double)data.get("countContract")).doubleValue() >= 2.0D) {
               this.validButton.field_73742_g = false;
               if(mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20) {
                  tooltipToDraw = Arrays.asList(I18n.func_135053_a("enterprise.contract.trader.count_limit").split("##"));
               }
            } else {
               this.validButton.field_73742_g = true;
            }
         }
      }

      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      if(tooltipToDraw != null) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.priceInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
         }

         if(this.validButton.field_73742_g && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            EnterpriseGui.lastContractDemand = Long.valueOf(System.currentTimeMillis());
            String content = I18n.func_135053_a("enterprise.contract.content.trader");
            content = content.replace("<amount>", this.priceInput.func_73781_b());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Default_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), content, Integer.valueOf(Integer.parseInt(this.priceInput.func_73781_b())), Long.valueOf(-10L))));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }
      }

      this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean isNumeric(String str) {
      if(str != null && str.length() != 0) {
         char[] var2 = str.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if(!Character.isDigit(c)) {
               return false;
            }
         }

         if(Integer.parseInt(str) <= 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
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
