package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.data.Auction;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.SuffixedNumberField;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionFilterSelectorGui;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$5;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$6;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui$7;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionRemoveGui;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionSimpleButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.ChangePageButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionBidPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionRemovePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class AuctionGui extends GuiScreen {

   private List<Auction> auctions = new ArrayList();
   private List<Auction> filteredAuctions = new ArrayList();
   private int posX;
   private int posY;
   private RenderItem renderItem = new RenderItem();
   private GuiScrollBar guiScrollBar;
   private Auction selected = null;
   private GuiButton bidButton;
   private SuffixedNumberField bidField;
   public static int currentPage = 1;
   private List<Auction> currentAuctions = new ArrayList();
   public static GuiTextField search;
   public static boolean myAuctionsOnly = false;
   private AuctionFilterSelectorGui<Comparator<Auction>> filterSelector;
   private boolean openBid;
   private static Map<String, Comparator<Auction>> filters = new LinkedHashMap();
   private static long lastBuy = 0L;
   private RenderItem itemRenderer = new RenderItem();
   public static int totalResults = 0;
   public static boolean canRemoveAll = false;
   public static boolean achievementDone = false;


   public AuctionGui() {
      search = null;
      this.bidField = null;
      currentPage = 1;
      myAuctionsOnly = false;
   }

   public void func_73866_w_() {
      PacketCallbacks.MONEY.send(new String[0]);
      this.posX = (this.field_73880_f - 342) / 2;
      this.posY = (this.field_73881_g - 256) / 2;
      this.field_73887_h.clear();
      ChangePageButton b1 = new ChangePageButton(0, this.posX + 270, this.posY + 235, false);
      b1.field_73742_g = currentPage > 1;
      this.field_73887_h.add(b1);
      ChangePageButton b2 = new ChangePageButton(1, this.posX + 322, this.posY + 235, true);
      b2.field_73742_g = currentPage < totalResults / 10 + 1;
      this.field_73887_h.add(b2);
      this.field_73887_h.add(new AuctionSimpleButton(2, this.posX + 230, this.posY + 35, 100, 17, I18n.func_135053_a(myAuctionsOnly?"auctions.othersales":"auctions.mysales")));
      Entry s = null;
      if(this.filterSelector != null) {
         s = this.filterSelector.saveSelection();
      }

      this.filterSelector = new AuctionFilterSelectorGui(this.posX + 128, this.posY + 36, 82, filters);
      if(s != null) {
         this.filterSelector.restoreSelection(s);
      }

      if(search == null) {
         search = new GuiTextField(this.field_73886_k, this.posX + 29, this.posY + 40, 90, 16);
         search.func_73786_a(false);
      }

      float save = 0.0F;
      if(this.guiScrollBar != null) {
         save = this.guiScrollBar.getSliderValue();
      }

      this.guiScrollBar = new AuctionGui$7(this, (float)(this.posX + 324), (float)(this.posY + 80), 148);
      this.guiScrollBar.setSliderValue(save);
      this.bidButton = null;
      this.bidField = null;
      if(this.filteredAuctions != null) {
         int i = 0;

         for(Iterator var6 = this.currentAuctions.iterator(); var6.hasNext(); ++i) {
            Auction auction = (Auction)var6.next();
            int y = i * 20;
            if(auction.equals(this.selected)) {
               if(auction.getCreator().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                  this.bidButton = null;
                  this.bidField = null;
               } else if(auction.getExpiry() > System.currentTimeMillis() && this.openBid) {
                  this.bidButton = new GuiButton(1, this.posX + 6 + 69 + 109 + 1, this.posY + 79 + y + 63, 50, 20, I18n.func_135053_a("auctions.bid"));
                  this.bidField = new SuffixedNumberField(this.field_73886_k, this.posX + 6 + 68, this.posY + 79 + y + 65, 110, 17, " $");
                  this.bidField.setText((int)Math.ceil((double)auction.getCurrentAuction() * 1.05D) + "");
                  this.bidField.setMin((int)Math.ceil((double)auction.getCurrentAuction() * 1.05D));
               }
            }
         }
      }

      super.func_73866_w_();
   }

   public void func_73876_c() {
      if(this.bidField != null) {
         this.bidField.updateCursorCounter();
      }

      if(this.selected != null && !this.selected.getCreator().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && this.bidButton != null) {
         long buyDiff = (System.currentTimeMillis() - lastBuy) / 1000L;
         this.bidButton.field_73742_g = this.selected.getExpiry() > System.currentTimeMillis() && (double)this.getNumber() >= Math.ceil((double)this.selected.getCurrentAuction() * 1.05D) && ShopGUI.CURRENT_MONEY >= (double)this.getNumber() && buyDiff >= 3L;
         if(buyDiff < 3L) {
            this.bidButton.field_73744_e = 3L - buyDiff + " s";
         } else {
            this.bidButton.field_73744_e = I18n.func_135053_a("auctions.bid");
         }
      }

      if(search != null) {
         search.func_73780_a();
      }

      super.func_73876_c();
   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.func_73873_v_();
      ArrayList biddersToDraw = new ArrayList();
      ClientEventHandler.STYLE.bindTexture("auction_house");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 343, 256, 372.0F, 400.0F, false);
      this.field_73882_e.func_110434_K().func_110577_a(MarketGui.BACKGROUND);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 217), (float)(this.posY + 259), 244, 373, 125, 26, 372.0F, 400.0F, false);
      int textWidth = (int)((double)this.field_73886_k.func_78256_a(I18n.func_135053_a("hdv.title.short")) * 1.2D);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 217 + 5), (float)(this.posY + 264), 6, 2, 17, 19, 372.0F, 400.0F, false);
      ModernGui.drawScaledString(I18n.func_135053_a("hdv.title.short"), this.posX + 288, this.posY + 269, 16777215, 1.2F, true, true);
      ClientEventHandler.STYLE.bindTexture("auction_house");
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(search != null) {
         search.func_73795_f();
      }

      String page = currentPage + "";
      this.func_73732_a(this.field_73886_k, page, this.posX + 269 + 33, this.posY + 238, 16777215);
      String money = (int)ShopGUI.CURRENT_MONEY + " $";
      this.field_73886_k.func_78276_b(money, this.posX + 312 - this.field_73886_k.func_78256_a(money), this.posY + 10, 16777215);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.posX + 40), (float)(this.posY + 3), 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      this.field_73886_k.func_78261_a(I18n.func_135053_a("auctions.title"), 0, 0, 16777215);
      GL11.glPopMatrix();
      int offset;
      int i;
      byte add;
      Iterator var12;
      Auction auction;
      ItemStack itemStack;
      int y;
      int var25;
      if(this.auctions != null && this.filteredAuctions != null && !this.filteredAuctions.isEmpty()) {
         GUIUtils.startGLScissor(this.posX + 6, this.posY + 79, 317, 149);
         GL11.glPushMatrix();
         var25 = this.selected == null?0:67;
         offset = this.currentAuctions.size() <= 7 && (this.selected == null || this.currentAuctions.size() <= 4)?0:(int)((float)((this.currentAuctions.size() - 7) * 20 + var25) * -this.guiScrollBar.getSliderValue());
         GL11.glTranslatef((float)(this.posX + 6), (float)(this.posY + 79 + offset), 0.0F);
         i = 0;
         add = 0;

         for(var12 = this.currentAuctions.iterator(); var12.hasNext(); ++i) {
            auction = (Auction)var12.next();
            itemStack = auction.getItemStack();
            if(itemStack != null) {
               itemStack.field_77994_a = 1;
               y = i * 20 + add;
               int price = auction.getCurrentAuction();
               String priceString;
               if(auction.getExpiry() > System.currentTimeMillis()) {
                  priceString = (price < 100000?Integer.valueOf(price):String.format("%.0f", new Object[]{Double.valueOf((double)price / 1000.0D)}) + "k") + "\u00a72$";
               } else if(auction.getSuperExpiry() > System.currentTimeMillis()) {
                  priceString = I18n.func_135053_a("hdv.expired");
               } else {
                  priceString = I18n.func_135053_a("hdv.superexpired");
               }

               ClientEventHandler.STYLE.bindTexture("auction_house");
               if(!auction.equals(this.selected)) {
                  if(par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset && this.cursorOnMarket(par1, par2)) {
                     GL11.glColor3f(0.7F, 0.7F, 0.7F);
                  }

                  ModernGui.drawModalRectWithCustomSizedTexture(0.0F, (float)y, 1, 262, 317, 18, 372.0F, 400.0F, false);
                  GL11.glColor3f(1.0F, 1.0F, 1.0F);
                  this.field_73886_k.func_78276_b(itemStack.func_82833_r().replaceAll("^\\\u00a7[0-9a-z]", ""), 22, y + 5, -6381922);
                  this.field_73886_k.func_78276_b(priceString, 267 + (63 - this.field_73886_k.func_78256_a(priceString)) / 2, y + 5, -1);
                  this.drawDeleteButton(auction, 242, y + 1, par1, par2);
                  RenderHelper.func_74520_c();
                  GL11.glEnable('\u803a');
                  this.renderItem.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 1, y + 1);
                  this.renderItem.func_94148_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 1, y + 1, "\u00a77" + Integer.toString(auction.getQuantity()));
                  GL11.glColor3f(1.0F, 1.0F, 1.0F);
                  GL11.glDisable(2896);
                  GL11.glDisable('\u803a');
                  RenderHelper.func_74518_a();
                  GL11.glColor3f(1.0F, 1.0F, 1.0F);
               } else {
                  add = 67;
                  ModernGui.drawModalRectWithCustomSizedTexture(0.0F, (float)y, 1, 283, 317, 85, 372.0F, 400.0F, false);
                  this.field_73886_k.func_78276_b(priceString, 267 + (63 - this.field_73886_k.func_78256_a(priceString)) / 2, y + 5, -1);
                  String itemName = itemStack.func_82833_r().replaceAll("^\\\u00a7[0-9a-z]", "");
                  if(itemName.length() > (itemName.contains("\u00a7l")?15:25)) {
                     itemName = itemName.substring(0, itemName.contains("\u00a7l")?14:24) + "...";
                  }

                  this.field_73886_k.func_78276_b("\u00a7l" + itemName, 65, y + 5, -6381922);
                  this.field_73886_k.func_78276_b(I18n.func_135052_a("hdv.seller", new Object[]{auction.getCreator()}), 65, y + 20, -1);
                  this.field_73886_k.func_78276_b((!auction.getBidders().isEmpty()?auction.getBidders().split(";").length - 1:0) + "", 238, y + 5, -1);
                  String text = auction.getLastBidder().equals(this.field_73882_e.field_71439_g.field_71092_bJ)?(auction.getLastBidder().equals(auction.getCreator())?I18n.func_135053_a("auctions.noBidder"):I18n.func_135053_a("auctions.isLastBidder")):(auction.getLastBidder().equals(auction.getCreator())?I18n.func_135053_a("auctions.noBidder"):I18n.func_135052_a("auctions.lastBidder", new Object[]{auction.getLastBidder()}));
                  ModernGui.drawScaledString(text, 152, y + 58, -1, 0.5F, true, true);
                  if(!this.openBid) {
                     if(auction.getCreator().equals(this.field_73882_e.field_71439_g.field_71092_bJ)) {
                        this.func_73732_a(this.field_73886_k, I18n.func_135053_a("auctions.isCreator"), 152, y + 69, -1);
                     } else {
                        this.func_73732_a(this.field_73886_k, I18n.func_135053_a("auctions.bid"), 152, y + 69, -1);
                     }
                  }

                  String size;
                  String output;
                  if(auction.getExpiry() > System.currentTimeMillis()) {
                     size = I18n.func_135053_a("hdv.expiry.time");
                     this.field_73886_k.func_78276_b(size, 314 - this.field_73886_k.func_78256_a(size), y + 40, -1);
                     output = this.getCountdownString(auction.getExpiry());
                     this.field_73886_k.func_78276_b(output, 314 - this.field_73886_k.func_78256_a(output), y + 50, -1);
                  } else if(auction.getCreator().equals(this.field_73882_e.field_71439_g.field_71092_bJ) && auction.getSuperExpiry() > System.currentTimeMillis()) {
                     size = I18n.func_135053_a("hdv.superexpiry.time");
                     this.field_73886_k.func_78276_b(size, 314 - this.field_73886_k.func_78256_a(size), y + 40, -1);
                     output = this.getCountdownString(auction.getSuperExpiry());
                     this.field_73886_k.func_78276_b(output, 314 - this.field_73886_k.func_78256_a(output), y + 50, -1);
                  }

                  this.drawDeleteButton(auction, 192, y + 1, par1, par2);
                  if(par1 >= this.posX + 6 + 212 && par1 <= this.posX + 6 + 212 + 45 && par2 >= this.posY + 79 + offset + y && par2 <= this.posY + 79 + offset + y + 16) {
                     String[] var26 = auction.getBidders().split(";");
                     String[] var28 = var26;
                     int var22 = var26.length;

                     for(int var23 = 0; var23 < var22; ++var23) {
                        String bidder = var28[var23];
                        if(!bidder.isEmpty()) {
                           biddersToDraw.add(bidder);
                        }
                     }
                  }

                  RenderHelper.func_74520_c();
                  GL11.glEnable('\u803a');
                  GL11.glPushMatrix();
                  float var27 = 3.0F;
                  GL11.glTranslatef(30.0F - 8.0F * var27 + 1.0F, (float)(y + 42) - 8.0F * var27, 0.0F);
                  GL11.glScalef(var27, var27, var27);
                  this.renderItem.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 0, 0);
                  this.renderItem.func_94148_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 0, 0, "\u00a77" + Integer.toString(auction.getQuantity()));
                  GL11.glColor3f(1.0F, 1.0F, 1.0F);
                  GL11.glPopMatrix();
                  GL11.glDisable(2896);
                  GL11.glDisable('\u803a');
                  RenderHelper.func_74518_a();
               }
            }
         }

         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, (float)offset, 0.0F);
         if(this.openBid) {
            if(this.bidButton != null) {
               this.bidButton.func_73737_a(this.field_73882_e, par1, par2 - offset);
            }

            if(this.bidField != null) {
               GL11.glColor3f(1.0F, 1.0F, 1.0F);
               this.bidField.drawTextBox();
            }
         }

         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
         GUIUtils.endGLScissor();
      } else {
         String a = I18n.func_135053_a("auctions.unavailable");
         this.func_73732_a(this.field_73886_k, a, this.posX + 170, this.posY + 78, 16777215);
      }

      if(this.guiScrollBar != null) {
         this.guiScrollBar.draw(par1, par2);
      }

      if(this.filterSelector != null) {
         this.filterSelector.draw(par1, par2);
      }

      if(this.auctions != null && this.filteredAuctions != null && !this.filteredAuctions.isEmpty() && par1 >= this.posX + 9 && par1 <= this.posX + 9 + 317 && par2 >= this.posY + 79 && par2 <= this.posY + 79 + 149) {
         var25 = this.selected == null?0:67;
         offset = this.currentAuctions.size() <= 7 && (this.selected == null || this.currentAuctions.size() <= 4)?0:(int)((float)((this.currentAuctions.size() - 7) * 20 + var25) * -this.guiScrollBar.getSliderValue());
         i = 0;
         add = 0;

         for(var12 = this.currentAuctions.iterator(); var12.hasNext(); ++i) {
            auction = (Auction)var12.next();
            itemStack = auction.getItemStack();
            if(itemStack != null) {
               itemStack.field_77994_a = 1;
               y = i * 20 + add;
               if(this.selected != null && this.selected.equals(auction)) {
                  add = 67;
               }

               if(par1 >= this.posX + 7 && par2 >= this.posY + 79 + offset + y + 1) {
                  if(auction.equals(this.selected)) {
                     if(par1 > this.posX + 7 + 60 || par2 > this.posY + 79 + offset + y + 84) {
                        continue;
                     }
                  } else if(par1 > this.posX + 7 + 16 || par2 > this.posY + 79 + offset + y + 17) {
                     continue;
                  }

                  NationsGUIClientHooks.drawItemStackTooltip(itemStack, par1, par2);
                  GL11.glDisable(2896);
               }
            }
         }
      }

      if(!biddersToDraw.isEmpty()) {
         this.drawHoveringText(biddersToDraw, par1, par2, this.field_73886_k);
      }

      super.func_73863_a(par1, par2, par3);
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

   private String getCountdownString(long time) {
      int SECONDS_IN_A_DAY = 86400;
      long diff = time - System.currentTimeMillis();
      long diffSec = diff / 1000L;
      long secondsDay = diffSec % (long)SECONDS_IN_A_DAY;
      long seconds = secondsDay % 60L;
      long minutes = secondsDay / 60L % 60L;
      long hours = diffSec / 3600L;
      String output = "";
      if(hours > 0L) {
         output = output + hours + "h ";
      }

      if(minutes > 0L) {
         output = output + minutes + "m ";
      }

      output = output + seconds + "s";
      return output;
   }

   protected void func_73869_a(char par1, int par2) {
      if(search.func_73802_a(par1, par2)) {
         this.resetSelection();
         currentPage = 1;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
      }

      if(this.bidField != null && this.openBid) {
         this.bidField.textboxKeyTyped(par1, par2);
      }

      super.func_73869_a(par1, par2);
   }

   protected void func_73864_a(int par1, int par2, int par3) {
      search.func_73793_a(par1, par2, par3);
      byte add = 0;
      int i = 0;
      int a = this.selected == null?0:67;
      int offset = this.currentAuctions.size() <= 7 && (this.selected == null || this.currentAuctions.size() <= 4)?0:(int)((float)((this.currentAuctions.size() - 7) * 20 + a) * -this.guiScrollBar.getSliderValue());
      if(this.bidField != null && this.openBid) {
         this.bidField.mouseClicked(par1, par2 - offset, par3);
      }

      if(this.filterSelector != null && this.filterSelector.mousePressed(par1, par2)) {
         this.resetSelection();
      }

      if(this.bidButton != null && this.bidButton.func_73736_c(this.field_73882_e, par1, par2 - offset) && this.openBid && this.bidField != null && par1 > this.posX + 3 && par1 < this.posX + 336 && par2 > this.posY + 79 && par2 < this.posY + 228) {
         if(this.getNumber() >= this.bidField.getMin()) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionBidPacket(this.selected.getUuid(), this.getNumber())));
            this.openBid = false;
            PacketCallbacks.MONEY.send(new String[0]);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
            lastBuy = System.currentTimeMillis();
            this.func_73866_w_();
         } else {
            this.bidField.setText(Integer.toString(this.bidField.getMin()));
         }
      }

      if(this.cursorOnMarket(par1, par2)) {
         for(Iterator var8 = this.currentAuctions.iterator(); var8.hasNext(); ++i) {
            Auction auction = (Auction)var8.next();
            int y = i * 20 + add;
            if(auction.equals(this.selected)) {
               add = 67;
            }

            if(auction.equals(this.selected) && par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + 67 + offset || !auction.equals(this.selected) && par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset) {
               label195: {
                  if((auction.getCreator().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && auction.getLastBidder().equals(this.field_73882_e.field_71439_g.field_71092_bJ) || canRemoveAll) && auction.equals(this.selected)) {
                     if(par1 < this.posX + 6 + 192 || par1 > this.posX + 6 + 192 + 19 || par2 < this.posY + 79 + y + offset + 1 || par2 > this.posY + 79 + y + offset + 20) {
                        break label195;
                     }
                  } else if(par1 < this.posX + 6 + 242 || par1 > this.posX + 6 + 242 + 19 || par2 < this.posY + 79 + y + offset + 1 || par2 > this.posY + 79 + y + offset + 20) {
                     break label195;
                  }

                  if(this.canRemove(auction)) {
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionRemovePacket(auction.getUuid())));
                     if(auction == this.selected) {
                        this.resetSelection();
                     }

                     this.removeAuction(auction);
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
                  } else {
                     this.field_73882_e.func_71373_a(new AuctionRemoveGui(this, auction));
                  }
                  break;
               }

               if(!this.openBid && auction.equals(this.selected) && par1 >= this.posX + 6 + 69 && par1 <= this.posX + 6 + 69 + 160 && par2 >= this.posY + 79 + y + offset + 66 && par2 <= this.posY + 79 + y + offset + 66 + 15) {
                  if(!auction.getCreator().equals(this.field_73882_e.field_71439_g.field_71092_bJ)) {
                     this.openBid = true;
                     this.func_73866_w_();
                  }
                  break;
               }

               if(this.selected == null || !this.selected.equals(auction)) {
                  this.selected = auction;
                  this.openBid = false;
                  this.func_73866_w_();
               }
               break;
            }
         }
      }

      if(par1 > this.posX + 217 && par1 < this.posX + 217 + 125 && par2 > this.posY + 259 && par2 < this.posY + 259 + 26) {
         this.field_73882_e.func_71373_a(new MarketGui());
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket("", "", 0, false)));
      }

      super.func_73864_a(par1, par2, par3);
   }

   public void setAuctions(List<Auction> auctions) {
      this.auctions = auctions;
      this.currentAuctions = this.getCurrentList();
   }

   public List<Auction> getAuctions() {
      return this.auctions;
   }

   public void resetSelection() {
      this.selected = null;
      this.openBid = false;
      this.bidButton = null;
      this.bidField = null;
   }

   public Auction getSelected() {
      return this.selected;
   }

   private void drawDeleteButton(Auction auction, int x, int y, int mouseX, int mouseY) {
      if((auction.getCreator().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && auction.getLastBidder().equals(this.field_73882_e.field_71439_g.field_71092_bJ) || canRemoveAll) && auction.getSuperExpiry() > System.currentTimeMillis()) {
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         ClientEventHandler.STYLE.bindTexture("auction_house");
         ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 371, 17, 16, 372.0F, 400.0F, false);
      }

   }

   private boolean canRemove(Auction auction) {
      if(auction.getSuperExpiry() < System.currentTimeMillis()) {
         return false;
      } else {
         int quantity = auction.getQuantity();
         if(quantity <= 0) {
            return true;
         } else {
            ItemStack[] var3 = Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ItemStack itemStack = var3[var5];
               if(itemStack != null && itemStack.func_77969_a(auction.getItemStack()) && ItemStack.func_77970_a(itemStack, auction.getItemStack())) {
                  int o = itemStack.func_77976_d() - itemStack.field_77994_a;
                  quantity -= o;
                  if(quantity <= 0) {
                     return true;
                  }
               } else if(itemStack == null) {
                  if(quantity <= auction.getItemStack().func_77976_d()) {
                     return true;
                  }

                  quantity -= auction.getItemStack().func_77976_d();
               }
            }

            return false;
         }
      }
   }

   public void updateList() {
      this.currentAuctions = this.getCurrentList();
   }

   private List<Auction> getCurrentList() {
      this.filteredAuctions = new ArrayList();
      if(this.auctions != null) {
         Iterator var1 = this.auctions.iterator();

         while(var1.hasNext()) {
            Auction auction = (Auction)var1.next();
            if(auction.getSuperExpiry() > System.currentTimeMillis()) {
               if(auction.getCreator().equals(this.field_73882_e.field_71439_g.field_71092_bJ) && myAuctionsOnly) {
                  this.filteredAuctions.add(auction);
               } else if(!auction.getCreator().equals(this.field_73882_e.field_71439_g.field_71092_bJ) && !myAuctionsOnly) {
                  this.filteredAuctions.add(auction);
               }
            }
         }
      }

      return (List)(this.filteredAuctions.size() > 0?this.filteredAuctions.subList(10 * (currentPage - 1), this.filteredAuctions.size() / 10 + 1 == currentPage?this.filteredAuctions.size():10 * currentPage):new ArrayList());
   }

   private List<Auction> searchFilter(List<Auction> target) {
      Object result = new ArrayList();
      if(search != null && !search.func_73781_b().equals("")) {
         Iterator var3 = target.iterator();

         while(var3.hasNext()) {
            Auction auction = (Auction)var3.next();
            if(search.func_73781_b().startsWith("@")) {
               String id1 = search.func_73781_b().substring(1, search.func_73781_b().length());
               if(StringUtils.containsIgnoreCase(auction.getCreator(), id1)) {
                  ((List)result).add(auction);
               }
            } else {
               int id = -1;

               try {
                  id = Integer.parseInt(search.func_73781_b());
               } catch (NumberFormatException var7) {
                  ;
               }

               if(auction != null && auction.getItemStack() != null && (StringUtils.containsIgnoreCase(auction.getItemStack().func_82833_r(), search.func_73781_b()) || id != -1 && id == auction.getItemStack().func_77973_b().field_77779_bT)) {
                  ((List)result).add(auction);
               }
            }
         }
      } else {
         result = target;
      }

      return (List)result;
   }

   private boolean cursorOnMarket(int mouseX, int mouseY) {
      return mouseX >= this.posX + 6 && mouseX <= this.posX + 6 + 317 && mouseY >= this.posY + 79 && mouseY <= this.posY + 79 + 149;
   }

   public void removeAuction(Auction auction) {
      if(auction.equals(this.selected)) {
         this.resetSelection();
      }

      this.currentAuctions.remove(auction);
      this.filteredAuctions.remove(auction);
      this.auctions.remove(auction);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      switch(par1GuiButton.field_73741_f) {
      case 0:
         if(currentPage - 1 >= 1) {
            --currentPage;
            this.resetSelection();
            this.guiScrollBar.setSliderValue(0.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
         }
         break;
      case 1:
         if(currentPage + 1 <= this.filteredAuctions.size() / 10 + 1) {
            ++currentPage;
            this.resetSelection();
            this.guiScrollBar.setSliderValue(0.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
         }
         break;
      case 2:
         myAuctionsOnly = !myAuctionsOnly;
         currentPage = 1;
         this.resetSelection();
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, myAuctionsOnly)));
      }

   }

   public int getNumber() {
      try {
         return Integer.parseInt(this.bidField.getText());
      } catch (NumberFormatException var2) {
         return 0;
      }
   }

   static {
      filters.put(I18n.func_135053_a("hdv.filter.price.increasing"), new AuctionGui$1());
      filters.put(I18n.func_135053_a("hdv.filter.price.declining"), new AuctionGui$2());
      filters.put(I18n.func_135053_a("hdv.filter.quantity.increasing"), new AuctionGui$3());
      filters.put(I18n.func_135053_a("hdv.filter.quantity.declining"), new AuctionGui$4());
      filters.put(I18n.func_135053_a("hdv.filter.date.increasing"), new AuctionGui$5());
      filters.put(I18n.func_135053_a("hdv.filter.date.declining"), new AuctionGui$6());
   }
}
