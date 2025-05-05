package net.ilexiconn.nationsgui.forge.client.gui.trade;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade$VoidButton;
import net.ilexiconn.nationsgui.forge.client.gui.trade.ITrade;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeUpdateMoneyPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiTrade extends GuiContainer implements ITrade {

   private static ResourceLocation resource = new ResourceLocation("nationsgui", "textures/gui/trade/troc.png");
   public Map items = new HashMap();
   public EntityPlayer trader = null;
   private ContainerTrade container;
   private GuiButton accept;
   private GuiTextField moneyField;
   private int moneyUpdater;
   public int moneyTrader;
   public boolean traderIsReady = false;
   public boolean hasEnoughMoney = true;
   private boolean imReady = false;
   public long lastInteraction = 0L;
   public static long stopCooldown;


   public GuiTrade(Container container) {
      super(container);
      this.container = (ContainerTrade)container;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketCallbacks.MONEY.send(new String[0]);
      this.field_74194_b = 401;
      this.field_74195_c = 216;
      this.field_74198_m = (this.field_73880_f - this.field_74194_b) / 2;
      this.field_74197_n = (this.field_73881_g - this.field_74195_c) / 2;
      this.field_73887_h.clear();
      this.field_73887_h.add(this.accept = new GuiButton(0, this.field_74198_m + 16, this.field_74197_n + 95, 35, 20, "Ok"));
      this.field_73887_h.add(new GuiTrade$VoidButton(this, 1, this.field_74198_m + 181, this.field_74197_n + 17, 9, 10));
      this.moneyField = new GuiTextField(this.field_73886_k, this.field_74198_m + 113, this.field_74197_n + 102, 58, 20);
      this.moneyField.func_73804_f(8);
      this.moneyField.func_73796_b(false);
      this.moneyField.func_73782_a("0");
      this.moneyField.func_73786_a(false);
      if(this.trader != null) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, true)));
      }

   }

   protected void func_74185_a(float partialsTicks, int mouseX, int mouseY) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_74198_m = (this.field_73880_f - this.field_74194_b) / 2;
      this.field_74197_n = (this.field_73881_g - this.field_74195_c) / 2;
      GL11.glPushMatrix();
      GUIUtils.startGLScissor(this.field_74198_m + 16, this.field_74197_n + 56, 35, 35);
      GuiInventory.func_110423_a(this.field_74198_m + 35, this.field_74197_n + 125, 35, (float)((-mouseX + this.field_74198_m + 32) / 4), (float)((-mouseY + this.field_74197_n + 32) / 4), this.field_73882_e.field_71439_g);
      GUIUtils.endGLScissor();
      if(this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING) {
         GUIUtils.startGLScissor(this.field_74198_m + 16, this.field_74197_n + 146, 35, 35);
         GuiInventory.func_110423_a(this.field_74198_m + 35, this.field_74197_n + 215, 35, (float)((-mouseX + this.field_74198_m + 32) / 4), (float)((-mouseY + this.field_74197_n + 32) / 4), this.trader);
         GUIUtils.endGLScissor();
      }

      GL11.glPopMatrix();
      this.field_73882_e.func_110434_K().func_110577_a(resource);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_74198_m, (float)this.field_74197_n, 0, 0, this.field_74194_b, this.field_74195_c, 512.0F, 512.0F, false);
      if(this.imReady) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.field_74198_m + 57), (float)(this.field_74197_n + 56), 0, 218, 4, 59, 512.0F, 512.0F, false);
      }

      if(this.traderIsReady) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.field_74198_m + 57), (float)(this.field_74197_n + 146), 0, 218, 4, 59, 512.0F, 512.0F, false);
      }

      this.func_73731_b(this.field_73886_k, (int)ShopGUI.CURRENT_MONEY + "$", this.field_74198_m + 378 - this.field_73886_k.func_78256_a((int)ShopGUI.CURRENT_MONEY + "$"), this.field_74197_n + 63, -1);
      this.func_73731_b(this.field_73886_k, "Mon Inventaire", this.field_74198_m + 249, this.field_74197_n + 63, -1);
      this.func_73731_b(this.field_73886_k, String.valueOf(this.moneyTrader), this.field_74198_m + 113, this.field_74197_n + 193, -1);
      if(this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING) {
         this.func_73731_b(this.field_73886_k, "Echange", this.field_74198_m + 33, this.field_74197_n + 12, -1);
         this.func_73731_b(this.field_73886_k, "Avec " + this.trader.field_71092_bJ, this.field_74198_m + 33, this.field_74197_n + 23, 11842740);
         ItemStack hover = null;

         for(int k = 0; k < 14; ++k) {
            ItemStack item = (ItemStack)this.items.get(Integer.valueOf(k));
            if(item != null) {
               int x = this.field_74198_m + 66 + k % 7 * 18;
               int y = this.field_74197_n + 147 + k / 7 * 18;
               this.drawItemStack(item, x, y, (String)null);
               if(this.func_74188_c(x - this.field_74198_m, y - this.field_74197_n, 16, 16, mouseX, mouseY)) {
                  hover = item;
               }
            }
         }

         if(hover != null) {
            this.func_74184_a(hover, mouseX, mouseY);
         }
      }

      if(!this.accept.field_73742_g && mouseX >= this.field_74198_m + 16 && mouseX <= this.field_74198_m + 16 + 35 && mouseY >= this.field_74197_n + 95 && mouseY <= this.field_74197_n + 95 + 20 && !this.hasEnoughMoney) {
         this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("trade.not_enough_money")}), mouseX, mouseY, this.field_73886_k);
      }

   }

   protected void func_73869_a(char par1, int par2) {
      super.func_73869_a(par1, par2);
      this.lastInteraction = System.currentTimeMillis();
      if(this.moneyField.func_73802_a(par1, par2)) {
         if(Character.isDigit(par1) || par2 == 14 || par2 == 54 || par2 == 42 || par2 == 205 || par2 == 203 || par2 == 211) {
            if(Character.isDigit(par1) && this.moneyField.func_73781_b().equals("0")) {
               this.moneyField.func_73782_a("");
            }

            if(this.moneyField.func_73781_b().isEmpty() || this.moneyField.func_73781_b().matches("0*")) {
               this.moneyField.func_73782_a("0");
            }
         }

         if(this.moneyField.func_73781_b() != null && !this.moneyField.func_73781_b().isEmpty() && this.isNumeric(this.moneyField.func_73781_b()) && this.trader != null) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, true)));
         } else if(this.trader != null) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(0, this.trader.field_71092_bJ, true)));
         }
      }

   }

   protected void func_73864_a(int par1, int par2, int par3) {
      super.func_73864_a(par1, par2, par3);
      this.moneyField.func_73793_a(par1, par2, par3);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.moneyField.func_73780_a();
      if(this.moneyUpdater > 10) {
         PacketCallbacks.MONEY.send(new String[0]);
         if(this.isNumeric(this.moneyField.func_73781_b()) && this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING && this.container.state != EnumTradeState.TRADER_ACCEPTED) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, false)));
         }

         this.moneyUpdater = 0;
      }

      ++this.moneyUpdater;
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

         if(Integer.parseInt(str) < 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void func_73863_a(int par1, int par2, float par3) {
      super.func_73863_a(par1, par2, par3);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.moneyField.func_73795_f();
      this.accept.field_73742_g = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
   }

   protected void func_73875_a(GuiButton btn) {
      switch(btn.field_73741_f) {
      case 0:
         if(!this.isNumeric(this.moneyField.func_73781_b())) {
            this.moneyField.func_73782_a("0");
            return;
         }

         if(this.accept.field_73742_g && System.currentTimeMillis() - this.lastInteraction > 1000L && this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING) {
            TradeManager.sendData(EnumPacketServer.TRADE_COMPLETE, Integer.parseInt(this.moneyField.func_73781_b()));
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         }
         break;
      case 1:
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }

   public void func_73874_b() {
      super.func_73874_b();
      PacketCallbacks.MONEY.send(new String[0]);
      TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);
   }

   public EntityPlayer getTrader() {
      return this.trader;
   }

   public static void addCooldown(long timeMillis) {
      stopCooldown = System.currentTimeMillis() + timeMillis;
   }

   public void updateState(EnumTradeState state) {
      this.container.state = state;
      this.imReady = state != EnumTradeState.STARTED && state != EnumTradeState.TRADER_ACCEPTED;
      this.accept.field_73742_g = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
      this.traderIsReady = state == EnumTradeState.TRADER_ACCEPTED;
   }

   private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 32.0F);
      RenderHelper.func_74520_c();
      GL11.glDisable(2896);
      GL11.glEnable('\u803a');
      GL11.glEnable(2903);
      GL11.glEnable(2896);
      this.field_73735_i = 200.0F;
      field_74196_a.field_77023_b = 200.0F;
      FontRenderer font = null;
      if(par1ItemStack != null) {
         font = par1ItemStack.func_77973_b().getFontRenderer(par1ItemStack);
      }

      if(font == null) {
         font = this.field_73886_k;
      }

      field_74196_a.func_82406_b(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3);
      field_74196_a.func_94148_a(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3, par4Str);
      this.field_73735_i = 0.0F;
      field_74196_a.field_77023_b = 0.0F;
      GL11.glPopMatrix();
      GL11.glEnable(2896);
      GL11.glEnable(2929);
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
         field_74196_a.field_77023_b = 300.0F;
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
         field_74196_a.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
