/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.StatCollector
 *  org.apache.commons.lang3.ArrayUtils
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraft.network.packet.Packet;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ShopType
implements ICategoryType {
    private int currentAmount = 1;
    private int currentPage = 0;
    private int totalPages = 0;
    private GuiButton buttonUp;
    private GuiButton buttonDown;
    private GuiButton buttonBuy;
    private GuiButton buttonNext;
    private GuiButton buttonPrevious;

    @Override
    public void init(int x, int y, ShopGUI gui, Category category, List<GuiButton> buttonList) {
        gui.selectedItem = category.getItems()[0];
        this.buttonUp = new VerticalArrowButtonGUI(1, x + 208, y + 109, true);
        buttonList.add(this.buttonUp);
        this.buttonDown = new VerticalArrowButtonGUI(2, x + 208, y + 119, false);
        buttonList.add(this.buttonDown);
        this.buttonBuy = new GuiButton(3, x + 232, y + 109, 53, 20, StatCollector.func_74838_a((String)"nationsgui.shop.buy"));
        buttonList.add(this.buttonBuy);
        this.buttonNext = new HorizontalArrowButtonGUI(4, x + 272, y + 218, false);
        buttonList.add(this.buttonNext);
        this.buttonPrevious = new HorizontalArrowButtonGUI(5, x + 123, y + 218, true);
        buttonList.add(this.buttonPrevious);
        if (this.currentAmount == gui.selectedItem.getMaxAmount()) {
            this.buttonUp.field_73742_g = false;
        }
        if (this.currentAmount == 1) {
            this.buttonDown.field_73742_g = false;
        }
        this.currentAmount = 1;
        this.currentPage = 0;
        this.buttonBuy.field_73742_g = gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
        this.totalPages = category.getItems().length / 36;
        this.buttonPrevious.field_73742_g = false;
        this.buttonNext.field_73742_g = this.totalPages > 0;
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
        int i;
        Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        for (i = 0; i < 9; ++i) {
            for (int j = 0; j < 4; ++j) {
                gui.func_73729_b(x + 123 + i * 18, y + 143 + j * 18, 179, 38, 18, 18);
            }
        }
        for (i = 0; i < category.getItems().length; ++i) {
            CategoryItem item = category.getItems()[i];
            if (item.getPage() != this.currentPage) continue;
            int j = item.getX();
            int k = item.getY();
            if (item.getPreviewIcon() != null) {
                if (!item.isPreviewIconLoaded()) continue;
                Minecraft.func_71410_x().field_71446_o.func_110577_a(item.getPreviewIcon());
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78382_b();
                tessellator.func_78374_a((double)(x + 124 + j * 18), (double)(y + 144 + k * 18 + 16), 0.0, 0.0, 1.0);
                tessellator.func_78374_a((double)(x + 124 + j * 18 + 16), (double)(y + 144 + k * 18 + 16), 0.0, 1.0, 1.0);
                tessellator.func_78374_a((double)(x + 124 + j * 18 + 16), (double)(y + 144 + k * 18), 0.0, 1.0, 0.0);
                tessellator.func_78374_a((double)(x + 124 + j * 18), (double)(y + 144 + k * 18), 0.0, 0.0, 0.0);
                tessellator.func_78381_a();
                continue;
            }
            if (item.getID() == 0) continue;
            GL11.glEnable((int)2929);
            RenderHelper.func_74520_c();
            gui.itemRenderer.func_82406_b(fontRenderer, Minecraft.func_71410_x().func_110434_K(), item.getItem(), x + 124 + j * 18, y + 144 + k * 18);
            gui.itemRenderer.func_94148_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), item.getItem(), x + 124 + j * 18, y + 144 + k * 18, "");
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
        }
        long buyDiff = (System.currentTimeMillis() - ShopGUI.lastBuy) / 1000L;
        if (ShopGUI.forbiddenCategories.contains(category.getName().toLowerCase().replace(" ", "_"))) {
            this.buttonBuy.field_73742_g = false;
            this.buttonBuy.field_73744_e = StatCollector.func_74838_a((String)"nationsgui.shop.buy");
            if (mouseX >= this.buttonBuy.field_73746_c && mouseX <= this.buttonBuy.field_73746_c + 53 && mouseY >= this.buttonBuy.field_73743_d && mouseY <= this.buttonBuy.field_73743_d + 20) {
                gui.tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"nationsgui.shop.forbidden_tooltip").split("##"));
            }
        } else if (buyDiff < 3L && buyDiff >= 0L && !ClientData.isOp) {
            this.buttonBuy.field_73742_g = false;
            this.buttonBuy.field_73744_e = 3L - buyDiff + " s";
        } else {
            this.buttonBuy.field_73744_e = StatCollector.func_74838_a((String)"nationsgui.shop.buy");
            this.buttonBuy.field_73742_g = ShopGUI.CAN_BUY && gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
        }
        this.buttonUp.field_73742_g = this.currentAmount < gui.selectedItem.getMaxAmount();
        this.buttonDown.field_73742_g = this.currentAmount > 1;
        this.buttonNext.field_73742_g = this.currentPage < this.totalPages;
        this.buttonPrevious.field_73742_g = this.currentPage > 0;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        gui.func_73729_b(x + 123, y + 34, 113, 68, 46, 95);
        gui.func_73729_b(x + 169, y + 34, 159, 68, 26, 72);
        gui.func_73729_b(x + 195, y + 34, 159, 68, 26, 72);
        gui.func_73729_b(x + 221, y + 34, 192, 68, 64, 72);
        gui.func_73729_b(x + 172, y + 109, 113, 163, 36, 20);
        fontRenderer.func_78276_b(String.format("%.1f", gui.selectedItem.getPrice() * (double)this.currentAmount) + "$", x + 147 - fontRenderer.func_78256_a(String.format("%.1f", gui.selectedItem.getPrice() * (double)this.currentAmount) + "$") / 2, y + 114, 0xFFFFFF);
        fontRenderer.func_78276_b(this.currentAmount + "", x + 179, y + 115, 0xFFFFFF);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        if (gui.selectedItem.getName() != null) {
            fontRenderer.func_78276_b(gui.selectedItem.getName().replace("${username}", Minecraft.func_71410_x().field_71439_g.field_71092_bJ), x + 130, y + 42, 0xFFFFFF);
        }
        if (gui.selectedItem.getIcon() != null && gui.selectedItem.isIconLoaded()) {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(gui.selectedItem.getIcon());
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78382_b();
            int categoryX = x + 227;
            int categoryY = y + 38;
            tessellator.func_78374_a((double)categoryX, (double)(categoryY + 64), 0.0, 0.0, 1.0);
            tessellator.func_78374_a((double)(categoryX + 53), (double)(categoryY + 64), 0.0, 1.0, 1.0);
            tessellator.func_78374_a((double)(categoryX + 53), (double)categoryY, 0.0, 1.0, 0.0);
            tessellator.func_78374_a((double)categoryX, (double)categoryY, 0.0, 0.0, 0.0);
            tessellator.func_78381_a();
        }
        if (gui.selectedItem.getID() != 0) {
            if (gui.selectedItem.getName() == null) {
                fontRenderer.func_78276_b(gui.selectedItem.getItem().func_82833_r(), x + 130, y + 42, 0xFFFFFF);
            }
            if (gui.selectedItem.getIcon() == null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)((float)x + 230.0f), (float)((float)y + 46.0f), (float)0.0f);
                GL11.glScalef((float)3.0f, (float)3.0f, (float)3.0f);
                GL11.glEnable((int)2929);
                RenderHelper.func_74520_c();
                gui.itemRenderer.func_82406_b(fontRenderer, Minecraft.func_71410_x().func_110434_K(), gui.selectedItem.getItem(), 0, 0);
                gui.itemRenderer.func_94148_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), gui.selectedItem.getItem(), 0, 0, "");
                RenderHelper.func_74518_a();
                GL11.glDisable((int)2896);
                GL11.glPopMatrix();
            }
        }
        String pages = this.currentPage + 1 + "/" + (this.totalPages + 1);
        fontRenderer.func_78276_b(pages, x + 204 - fontRenderer.func_78256_a(pages) / 2, y + 221, 0x2C2C2C);
        GL11.glDisable((int)3042);
    }

    @Override
    public void renderPost(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
        int k;
        int j;
        CategoryItem item;
        int i;
        for (i = 0; i < category.getItems().length; ++i) {
            item = category.getItems()[i];
            if (item.getPage() != this.currentPage) continue;
            j = item.getX();
            k = item.getY();
            if (gui.selectedItem != item) continue;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            gui.setZLevel(300.0f);
            gui.func_73729_b(x + 120 + j * 18, y + 140 + k * 18, 155, 38, 24, 24);
            gui.setZLevel(0.0f);
        }
        for (i = 0; i < category.getItems().length; ++i) {
            item = category.getItems()[i];
            if (item.getPage() != this.currentPage) continue;
            j = item.getX();
            k = item.getY();
            if (item.getName() != null) {
                if (item.getName() == null || mouseX <= x + 123 + j * 18 || mouseX >= x + 123 + j * 18 + 18 || mouseY <= y + 143 + k * 18 || mouseY >= y + 143 + k * 18 + 18) continue;
                gui.drawHoveringText(Lists.newArrayList((Object[])new String[]{item.getName().replace("${username}", Minecraft.func_71410_x().field_71439_g.field_71092_bJ), String.format("%.1f", item.getPrice()) + "$"}), mouseX, mouseY, fontRenderer);
                continue;
            }
            if (item.getID() == 0) continue;
            ItemStack stack = new ItemStack(item.getID(), 1, item.getMetadata());
            if (mouseX <= x + 123 + j * 18 || mouseX >= x + 123 + j * 18 + 18 || mouseY <= y + 143 + k * 18 || mouseY >= y + 143 + k * 18 + 18) continue;
            gui.drawHoveringText(Lists.newArrayList((Object[])new String[]{stack.func_82833_r(), String.format("%.1f", item.getPrice()) + "$"}), mouseX, mouseY, fontRenderer);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, ShopGUI gui, Category category) {
        for (int i = 0; i < category.getItems().length; ++i) {
            CategoryItem item = category.getItems()[i];
            if (item.getPage() != this.currentPage) continue;
            int j = item.getX();
            int k = item.getY();
            if (mouseX <= x + 123 + j * 18 || mouseX >= x + 123 + j * 18 + 18 || mouseY <= y + 143 + k * 18 || mouseY >= y + 143 + k * 18 + 18) continue;
            gui.selectedItem = item;
            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            this.currentAmount = 1;
            this.buttonUp.field_73742_g = this.currentAmount < gui.selectedItem.getMaxAmount();
            this.buttonDown.field_73742_g = false;
            this.buttonBuy.field_73742_g = gui.selectedItem.getPrice() * (double)this.currentAmount <= ShopGUI.CURRENT_MONEY;
            break;
        }
    }

    @Override
    public void actionPerformed(GuiButton button, ShopGUI gui, Category category) {
        if (button.field_73741_f == 1) {
            this.currentAmount = Keyboard.isKeyDown((int)42) ? gui.selectedItem.getMaxAmount() : ++this.currentAmount;
        } else if (button.field_73741_f == 2) {
            this.currentAmount = Keyboard.isKeyDown((int)42) ? 1 : --this.currentAmount;
        } else if (button.field_73741_f == 3) {
            if (System.currentTimeMillis() - ShopGUI.lastBuy > 3000L || ClientData.isOp) {
                if (gui.selectedItem.getPrice() > 0.0) {
                    ShopGUI.lastBuy = System.currentTimeMillis();
                }
                ShopGUI.CAN_BUY = false;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new BuyPacket(ArrayUtils.indexOf((Object[])gui.categories, (Object)gui.selectedCategory), gui.selectedItem.getIndex(), this.currentAmount, gui.selectedCategory.getName())));
            }
        } else if (button.field_73741_f == 4) {
            ++this.currentPage;
        } else if (button.field_73741_f == 5) {
            --this.currentPage;
        }
    }
}

