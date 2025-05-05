package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.shop.Category;
import net.ilexiconn.nationsgui.forge.client.gui.shop.CategoryItem;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.component.HorizontalArrowButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.component.VerticalArrowButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ICategoryType;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BuyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ShopType implements ICategoryType {

   private int currentAmount = 1;
   private int currentPage = 0;
   private int totalPages = 0;
   private GuiButton buttonUp;
   private GuiButton buttonDown;
   private GuiButton buttonBuy;
   private GuiButton buttonNext;
   private GuiButton buttonPrevious;


   public void init(int x, int y, ShopGUI gui, Category category, List<GuiButton> buttonList) {
      gui.selectedItem = category.getItems()[0];
      buttonList.add(this.buttonUp = new VerticalArrowButtonGUI(1, x + 208, y + 109, true));
      buttonList.add(this.buttonDown = new VerticalArrowButtonGUI(2, x + 208, y + 119, false));
      buttonList.add(this.buttonBuy = new GuiButton(3, x + 232, y + 109, 53, 20, StatCollector.func_74838_a("nationsgui.shop.buy")));
      buttonList.add(this.buttonNext = new HorizontalArrowButtonGUI(4, x + 272, y + 218, false));
      buttonList.add(this.buttonPrevious = new HorizontalArrowButtonGUI(5, x + 123, y + 218, true));
      if(this.currentAmount == gui.selectedItem.getMaxAmount()) {
         this.buttonUp.field_73742_g = false;
      }

      if(this.currentAmount == 1) {
         this.buttonDown.field_73742_g = false;
      }

      this.currentAmount = 1;
      this.currentPage = 0;
      this.buttonBuy.field_73742_g = gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
      this.totalPages = category.getItems().length / 36;
      this.buttonPrevious.field_73742_g = false;
      this.buttonNext.field_73742_g = this.totalPages > 0;
   }

   public void render(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

      int buyDiff;
      for(buyDiff = 0; buyDiff < 9; ++buyDiff) {
         for(int item = 0; item < 4; ++item) {
            gui.func_73729_b(x + 123 + buyDiff * 18, y + 143 + item * 18, 179, 38, 18, 18);
         }
      }

      int categoryX;
      for(buyDiff = 0; buyDiff < category.getItems().length; ++buyDiff) {
         CategoryItem var14 = category.getItems()[buyDiff];
         if(var14.getPage() == this.currentPage) {
            int pages = var14.getX();
            categoryX = var14.getY();
            if(var14.getPreviewIcon() != null) {
               if(var14.isPreviewIconLoaded()) {
                  Minecraft.func_71410_x().field_71446_o.func_110577_a(var14.getPreviewIcon());
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  Tessellator categoryY = Tessellator.field_78398_a;
                  categoryY.func_78382_b();
                  categoryY.func_78374_a((double)(x + 124 + pages * 18), (double)(y + 144 + categoryX * 18 + 16), 0.0D, 0.0D, 1.0D);
                  categoryY.func_78374_a((double)(x + 124 + pages * 18 + 16), (double)(y + 144 + categoryX * 18 + 16), 0.0D, 1.0D, 1.0D);
                  categoryY.func_78374_a((double)(x + 124 + pages * 18 + 16), (double)(y + 144 + categoryX * 18), 0.0D, 1.0D, 0.0D);
                  categoryY.func_78374_a((double)(x + 124 + pages * 18), (double)(y + 144 + categoryX * 18), 0.0D, 0.0D, 0.0D);
                  categoryY.func_78381_a();
               }
            } else if(var14.getID() != 0) {
               GL11.glEnable(2929);
               RenderHelper.func_74520_c();
               gui.itemRenderer.func_82406_b(fontRenderer, Minecraft.func_71410_x().func_110434_K(), var14.getItem(), x + 124 + pages * 18, y + 144 + categoryX * 18);
               gui.itemRenderer.func_94148_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), var14.getItem(), x + 124 + pages * 18, y + 144 + categoryX * 18, "");
               RenderHelper.func_74518_a();
               GL11.glDisable(2896);
            }
         }
      }

      long var13 = (System.currentTimeMillis() - ShopGUI.lastBuy) / 1000L;
      if(ShopGUI.forbiddenCategories.contains(category.getName().toLowerCase().replace(" ", "_"))) {
         this.buttonBuy.field_73742_g = false;
         this.buttonBuy.field_73744_e = StatCollector.func_74838_a("nationsgui.shop.buy");
         if(mouseX >= this.buttonBuy.field_73746_c && mouseX <= this.buttonBuy.field_73746_c + 53 && mouseY >= this.buttonBuy.field_73743_d && mouseY <= this.buttonBuy.field_73743_d + 20) {
            gui.tooltipToDraw = Arrays.asList(I18n.func_135053_a("nationsgui.shop.forbidden_tooltip").split("##"));
         }
      } else if(var13 < 3L && var13 >= 0L && !ClientData.isOp) {
         this.buttonBuy.field_73742_g = false;
         this.buttonBuy.field_73744_e = 3L - var13 + " s";
      } else {
         this.buttonBuy.field_73744_e = StatCollector.func_74838_a("nationsgui.shop.buy");
         this.buttonBuy.field_73742_g = ShopGUI.CAN_BUY && gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
      }

      this.buttonUp.field_73742_g = this.currentAmount < gui.selectedItem.getMaxAmount();
      this.buttonDown.field_73742_g = this.currentAmount > 1;
      this.buttonNext.field_73742_g = this.currentPage < this.totalPages;
      this.buttonPrevious.field_73742_g = this.currentPage > 0;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      gui.func_73729_b(x + 123, y + 34, 113, 68, 46, 95);
      gui.func_73729_b(x + 169, y + 34, 159, 68, 26, 72);
      gui.func_73729_b(x + 195, y + 34, 159, 68, 26, 72);
      gui.func_73729_b(x + 221, y + 34, 192, 68, 64, 72);
      gui.func_73729_b(x + 172, y + 109, 113, 163, 36, 20);
      fontRenderer.func_78276_b(String.format("%.1f", new Object[]{Double.valueOf(gui.selectedItem.getPrice() * (double)this.currentAmount)}) + "$", x + 147 - fontRenderer.func_78256_a(String.format("%.1f", new Object[]{Double.valueOf(gui.selectedItem.getPrice() * (double)this.currentAmount)}) + "$") / 2, y + 114, 16777215);
      fontRenderer.func_78276_b(this.currentAmount + "", x + 179, y + 115, 16777215);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      if(gui.selectedItem.getName() != null) {
         fontRenderer.func_78276_b(gui.selectedItem.getName().replace("${username}", Minecraft.func_71410_x().field_71439_g.field_71092_bJ), x + 130, y + 42, 16777215);
      }

      if(gui.selectedItem.getIcon() != null && gui.selectedItem.isIconLoaded()) {
         Minecraft.func_71410_x().field_71446_o.func_110577_a(gui.selectedItem.getIcon());
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Tessellator var15 = Tessellator.field_78398_a;
         var15.func_78382_b();
         categoryX = x + 227;
         int var17 = y + 38;
         var15.func_78374_a((double)categoryX, (double)(var17 + 64), 0.0D, 0.0D, 1.0D);
         var15.func_78374_a((double)(categoryX + 53), (double)(var17 + 64), 0.0D, 1.0D, 1.0D);
         var15.func_78374_a((double)(categoryX + 53), (double)var17, 0.0D, 1.0D, 0.0D);
         var15.func_78374_a((double)categoryX, (double)var17, 0.0D, 0.0D, 0.0D);
         var15.func_78381_a();
      }

      if(gui.selectedItem.getID() != 0) {
         if(gui.selectedItem.getName() == null) {
            fontRenderer.func_78276_b(gui.selectedItem.getItem().func_82833_r(), x + 130, y + 42, 16777215);
         }

         if(gui.selectedItem.getIcon() == null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 230.0F, (float)y + 46.0F, 0.0F);
            GL11.glScalef(3.0F, 3.0F, 3.0F);
            GL11.glEnable(2929);
            RenderHelper.func_74520_c();
            gui.itemRenderer.func_82406_b(fontRenderer, Minecraft.func_71410_x().func_110434_K(), gui.selectedItem.getItem(), 0, 0);
            gui.itemRenderer.func_94148_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), gui.selectedItem.getItem(), 0, 0, "");
            RenderHelper.func_74518_a();
            GL11.glDisable(2896);
            GL11.glPopMatrix();
         }
      }

      String var16 = this.currentPage + 1 + "/" + (this.totalPages + 1);
      fontRenderer.func_78276_b(var16, x + 204 - fontRenderer.func_78256_a(var16) / 2, y + 221, 2894892);
      GL11.glDisable(3042);
   }

   public void renderPost(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
      int i;
      CategoryItem item;
      int j;
      int k;
      for(i = 0; i < category.getItems().length; ++i) {
         item = category.getItems()[i];
         if(item.getPage() == this.currentPage) {
            j = item.getX();
            k = item.getY();
            if(gui.selectedItem == item) {
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               gui.setZLevel(300.0F);
               gui.func_73729_b(x + 120 + j * 18, y + 140 + k * 18, 155, 38, 24, 24);
               gui.setZLevel(0.0F);
            }
         }
      }

      for(i = 0; i < category.getItems().length; ++i) {
         item = category.getItems()[i];
         if(item.getPage() == this.currentPage) {
            j = item.getX();
            k = item.getY();
            if(item.getName() != null) {
               if(item.getName() != null && mouseX > x + 123 + j * 18 && mouseX < x + 123 + j * 18 + 18 && mouseY > y + 143 + k * 18 && mouseY < y + 143 + k * 18 + 18) {
                  gui.drawHoveringText(Lists.newArrayList(new String[]{item.getName().replace("${username}", Minecraft.func_71410_x().field_71439_g.field_71092_bJ), String.format("%.1f", new Object[]{Double.valueOf(item.getPrice())}) + "$"}), mouseX, mouseY, fontRenderer);
               }
            } else if(item.getID() != 0) {
               ItemStack stack = new ItemStack(item.getID(), 1, item.getMetadata());
               if(mouseX > x + 123 + j * 18 && mouseX < x + 123 + j * 18 + 18 && mouseY > y + 143 + k * 18 && mouseY < y + 143 + k * 18 + 18) {
                  gui.drawHoveringText(Lists.newArrayList(new String[]{stack.func_82833_r(), String.format("%.1f", new Object[]{Double.valueOf(item.getPrice())}) + "$"}), mouseX, mouseY, fontRenderer);
               }
            }
         }
      }

   }

   public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, ShopGUI gui, Category category) {
      for(int i = 0; i < category.getItems().length; ++i) {
         CategoryItem item = category.getItems()[i];
         if(item.getPage() == this.currentPage) {
            int j = item.getX();
            int k = item.getY();
            if(mouseX > x + 123 + j * 18 && mouseX < x + 123 + j * 18 + 18 && mouseY > y + 143 + k * 18 && mouseY < y + 143 + k * 18 + 18) {
               gui.selectedItem = item;
               Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               this.currentAmount = 1;
               this.buttonUp.field_73742_g = this.currentAmount < gui.selectedItem.getMaxAmount();
               this.buttonDown.field_73742_g = false;
               this.buttonBuy.field_73742_g = gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
               break;
            }
         }
      }

   }

   public void actionPerformed(GuiButton button, ShopGUI gui, Category category) {
      if(button.field_73741_f == 1) {
         if(Keyboard.isKeyDown(42)) {
            this.currentAmount = gui.selectedItem.getMaxAmount();
         } else {
            ++this.currentAmount;
         }
      } else if(button.field_73741_f == 2) {
         if(Keyboard.isKeyDown(42)) {
            this.currentAmount = 1;
         } else {
            --this.currentAmount;
         }
      } else if(button.field_73741_f == 3) {
         if(System.currentTimeMillis() - ShopGUI.lastBuy > 3000L || ClientData.isOp) {
            if(gui.selectedItem.getPrice() > 0.0D) {
               ShopGUI.lastBuy = System.currentTimeMillis();
            }

            ShopGUI.CAN_BUY = false;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new BuyPacket(ArrayUtils.indexOf(gui.categories, gui.selectedCategory), gui.selectedItem.getIndex(), this.currentAmount, gui.selectedCategory.getName())));
         }
      } else if(button.field_73741_f == 4) {
         ++this.currentPage;
      } else if(button.field_73741_f == 5) {
         --this.currentPage;
      }

   }
}
