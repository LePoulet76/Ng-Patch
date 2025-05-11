/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.ThreadDownloadImageData
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.shop;

import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.Category;
import net.ilexiconn.nationsgui.forge.client.gui.shop.CategoryItem;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.json.CategoryJSON;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CheckForbiddenShopCategoriesPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.util.ReleaseType;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ShopGUI
extends GuiScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/shop.png");
    public static final String API_URL = "https://apiv2.nationsglory.fr/mods/shop_api";
    public static final String API_DEV_URL = "https://apiv2.nationsglory.fr/json/shop_api.json";
    public static final Gson GSON = new Gson();
    public static boolean CAN_BUY = true;
    public static double CURRENT_MONEY = -1.0;
    public static long lastBuy = 0L;
    public Category selectedCategory;
    public CategoryItem selectedItem;
    public Category hoverCategory;
    public Category[] categories;
    public static List<String> forbiddenCategories = new ArrayList<String>();
    public Map<Category, Tuple<Float, Float>> categoryPositions = new HashMap<Category, Tuple<Float, Float>>();
    public RenderItem itemRenderer = new RenderItem();
    public float currentScroll;
    public float currentOffset;
    public boolean isScrolling;
    public boolean wasClicking;
    public int guiLeft;
    public int guiTop;
    public static CategoryJSON[] containers;
    public static boolean achievementDone;
    public List<String> tooltipToDraw = new ArrayList<String>();

    public ShopGUI() {
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_catalog", 1)));
        }
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CheckForbiddenShopCategoriesPacket()));
        CAN_BUY = true;
    }

    public void func_73866_w_() {
        PacketCallbacks.MONEY.send(new String[0]);
        PermissionCache.INSTANCE.clearCache();
        if (containers == null) {
            try {
                containers = (CategoryJSON[])GSON.fromJson((Reader)new BufferedReader(new InputStreamReader(new URL(NationsGUI.RELEASE_TYPE == ReleaseType.DEVELOP ? API_DEV_URL : "https://apiv2.nationsglory.fr/mods/shop_api?lang=" + System.getProperty("java.lang")).openStream(), "UTF-8")), CategoryJSON[].class);
            }
            catch (Exception e) {
                e.printStackTrace();
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        this.categories = new Category[containers.length + 1];
        for (int i = 0; i < containers.length; ++i) {
            this.categories[i] = new Category(this, containers[i]);
        }
        this.guiLeft = this.field_73880_f / 2 - 147;
        this.guiTop = this.field_73881_g / 2 - 119;
        this.field_73887_h.add(new CloseButtonGUI(0, this.guiLeft + 274, this.guiTop + 9));
        this.selectCategory(this.categories[0]);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int i;
        this.func_73873_v_();
        this.tooltipToDraw = new ArrayList<String>();
        this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
        int x = this.field_73880_f / 2 - 147;
        int y = this.field_73881_g / 2 - 119;
        this.func_73729_b(x + 113, y, 0, 196, 181, 29);
        for (i = 0; i < 10; ++i) {
            this.func_73729_b(x + 113, y + 29 + i * 19, 0, 225, 181, 19);
        }
        this.func_73729_b(x + 113, y + 29 + 190, 0, 225, 181, 12);
        this.func_73729_b(x + 113, y + 231, 0, 249, 181, 7);
        this.func_73729_b(x, y + 26, 0, 0, 113, 7);
        this.func_73729_b(x, y + 215, 0, 34, 113, 7);
        for (i = 0; i < 7; ++i) {
            this.func_73729_b(x, y + 33 + 26 * i, 0, 7, 113, 26);
        }
        this.func_73729_b(x + 6, y + 3, 149, 173, 107, 23);
        if (this.field_73882_e.field_71474_y.field_74363_ab.startsWith("fr_")) {
            this.func_73729_b(x + 148, y + 7, 0, 58, 113, 17);
        } else {
            this.func_73729_b(x + 148, y + 7, 0, 41, 113, 17);
        }
        if (!this.hasScrollbar()) {
            GL11.glColor4f((float)0.75f, (float)0.75f, (float)0.75f, (float)1.0f);
        }
        this.func_73729_b(x + 101, y + 33 + (int)(167.0f * this.currentOffset), 204, 0, 9, 15);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        String money = (int)CURRENT_MONEY + "$";
        this.field_73886_k.func_78276_b(money, x + 107 - this.field_73886_k.func_78256_a(money), y + 14, -1);
        float scrollOffset = -(this.currentOffset * ((float)this.categories.length / 182.0f) * 19.0f) * 182.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)scrollOffset, (float)0.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int categoryOffset = 0;
        GUIUtils.startGLScissor(0, y + 33, x + 98, 182);
        boolean flag = false;
        for (Category category : this.categories) {
            if (category == null || !category.isEnabled()) continue;
            this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
            int categoryX = x + 7;
            int categoryY = y + 33 + categoryOffset * 19;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(categoryX + 75, categoryY, 188, this.selectedCategory == category ? 0 : 19, 16, 19);
            GL11.glPushMatrix();
            if (!this.categoryPositions.containsKey(category)) {
                this.categoryPositions.put(category, new Tuple<Float, Float>(Float.valueOf(0.0f), Float.valueOf(0.0f)));
            }
            GL11.glTranslatef((float)((Float)this.categoryPositions.get((Object)category).a).floatValue(), (float)0.0f, (float)0.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (!this.isScrolling && mouseX > x + 7 && mouseX < x + 98 && mouseY > y + 33 && mouseY < y + 215) {
                if (mouseX > categoryX && mouseX < categoryX + 91 && (float)mouseY - scrollOffset > (float)categoryY && (float)mouseY - scrollOffset < (float)(categoryY + 19)) {
                    this.hoverCategory = category;
                    flag = true;
                }
            } else {
                this.hoverCategory = null;
            }
            Tuple<Float, Float> position = this.categoryPositions.get(category);
            position.b = this.hoverCategory == category ? Float.valueOf(-12.0f) : Float.valueOf(0.0f);
            position.a = Float.valueOf(GUIUtils.interpolate(((Float)position.a).floatValue(), ((Float)position.b).floatValue(), 0.15f));
            this.categoryPositions.put(category, position);
            this.func_73729_b(categoryX, categoryY, 113, this.selectedCategory == category ? 0 : 19, 19, 19);
            this.drawStripTexturedModalRect(categoryX + 19, categoryY, 128, this.selectedCategory == category ? 0 : 19, 60 - (int)((Float)position.a).floatValue(), 19);
            this.field_73886_k.func_78276_b(I18n.func_135053_a((String)("shop.category." + category.getName().toLowerCase().replace(" ", "_"))), categoryX + 23, categoryY + 5, this.selectedCategory == category || this.hoverCategory == category ? 0x3D3D3D : 0x747474);
            if (category.isIconLoaded()) {
                this.field_73882_e.field_71446_o.func_110577_a(category.getIcon());
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78382_b();
                tessellator.func_78374_a((double)(categoryX + 1), (double)(categoryY + 17), 0.0, 0.0, 1.0);
                tessellator.func_78374_a((double)(categoryX + 17), (double)(categoryY + 17), 0.0, 1.0, 1.0);
                tessellator.func_78374_a((double)(categoryX + 17), (double)(categoryY + 1), 0.0, 1.0, 0.0);
                tessellator.func_78374_a((double)(categoryX + 1), (double)(categoryY + 1), 0.0, 0.0, 0.0);
                tessellator.func_78381_a();
            }
            ++categoryOffset;
            GL11.glPopMatrix();
        }
        if (!flag) {
            this.hoverCategory = null;
        }
        GUIUtils.endGLScissor();
        GL11.glPopMatrix();
        this.selectedCategory.renderCategory(this, mouseX, mouseY);
        if (this.hasScrollbar() && !this.wasClicking && Mouse.isButtonDown((int)0) && mouseX >= x + 101 && mouseY >= y + 33 && mouseX < x + 101 + 9 && mouseY < y + 33 + 182) {
            this.isScrolling = true;
        } else if (!Mouse.isButtonDown((int)0)) {
            this.isScrolling = false;
        }
        this.wasClicking = Mouse.isButtonDown((int)0);
        if (this.isScrolling) {
            this.currentScroll = ((float)(mouseY - y) - 33.0f - 9.0f) / 156.0f;
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            } else if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
        }
        this.currentOffset = GUIUtils.interpolate(this.currentOffset, this.currentScroll, 0.15f);
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.selectedCategory.renderCategoryPost(this, mouseX, mouseY);
        if (!this.selectedCategory.getName().equalsIgnoreCase("accueil") && mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 208 + 21 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 109 + 10) {
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"nationsgui.shop.stack_tooltip")), mouseX, mouseY, this.field_73886_k);
        }
        if (this.tooltipToDraw != null && !this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
    }

    public void func_73867_d() {
        super.func_73867_d();
        int scroll = Mouse.getEventDWheel();
        if (this.hasScrollbar() && scroll != 0) {
            this.currentScroll += (float)scroll / ((float)this.categories.length * 19.0f - 182.0f) / 19.0f;
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            } else if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int button) {
        if (this.hoverCategory != null) {
            this.selectCategory(this.hoverCategory);
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
        } else {
            this.selectedCategory.mouseClicked(this, mouseX, mouseY, button);
            super.func_73864_a(mouseX, mouseY, button);
        }
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(null);
        }
        this.selectedCategory.actionPerformed(this, button);
    }

    public void selectCategory(Category category) {
        ArrayList<GuiButton> remove = new ArrayList<GuiButton>();
        for (GuiButton button : this.field_73887_h) {
            if (button.field_73741_f == 0) continue;
            remove.add(button);
        }
        this.field_73887_h.removeAll(remove);
        this.selectedCategory = category;
        category.initCategory(this, this.field_73887_h);
    }

    public ThreadDownloadImageData getDownloadImage(ResourceLocation location, String url) {
        if (url.equals("https://static.nationsglory.fr/N23665_G5_.png") && !Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a().equalsIgnoreCase("fr_fr")) {
            url = "https://static.nationsglory.fr/N362_6G_22.png";
        } else if (url.equals("https://static.nationsglory.fr/N23665_2Ny.png") && !Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a().equalsIgnoreCase("fr_fr")) {
            url = "https://static.nationsglory.fr/n236652nyen.png";
        }
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        TextureObject texture = textureManager.func_110581_b(location);
        if (texture == null) {
            texture = new ThreadDownloadImageData(url, null, null);
            textureManager.func_110579_a(location, texture);
        }
        return (ThreadDownloadImageData)texture;
    }

    public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer) {
        if (!text.isEmpty()) {
            GL11.glDisable((int)32826);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int width = 0;
            for (String line : text) {
                int lineWidth = fontRenderer.func_78256_a(line);
                width = Math.max(width, lineWidth);
            }
            int posX = mouseX + 12;
            int posY = mouseY - 12;
            int offsetY = 8;
            if (text.size() > 1) {
                offsetY += 2 + (text.size() - 1) * 10;
            }
            if (posX + width > this.field_73880_f) {
                posX -= 28 + width;
            }
            if (posY + offsetY + 6 > this.field_73881_g) {
                posY = this.field_73881_g - offsetY - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int color1 = -267386864;
            this.func_73733_a(posX - 3, posY - 4, posX + width + 3, posY - 3, color1, color1);
            this.func_73733_a(posX - 3, posY + offsetY + 3, posX + width + 3, posY + offsetY + 4, color1, color1);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX - 4, posY - 3, posX - 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX + width + 3, posY - 3, posX + width + 4, posY + offsetY + 3, color1, color1);
            int color2 = 0x505000FF;
            int color3 = (color2 & 0xFEFEFE) >> 1 | color2 & 0xFF000000;
            this.func_73733_a(posX - 3, posY - 3 + 1, posX - 3 + 1, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX + width + 2, posY - 3 + 1, posX + width + 3, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY - 3 + 1, color2, color2);
            this.func_73733_a(posX - 3, posY + offsetY + 2, posX + width + 3, posY + offsetY + 3, color3, color3);
            for (int i = 0; i < text.size(); ++i) {
                String line = text.get(i);
                if (i == 0) {
                    fontRenderer.func_78261_a(line, posX, posY, -1);
                    posY += 2;
                } else {
                    fontRenderer.func_78261_a(EnumChatFormatting.GOLD + line, posX + width - fontRenderer.func_78256_a(line), posY, 0xFFFFFF);
                }
                posY += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void drawStripTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float size = 0.00390625f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)x, (double)(y + height), (double)this.field_73735_i, (double)((float)u * size), (double)((float)(v + height) * size));
        tessellator.func_78374_a((double)(x + width), (double)(y + height), (double)this.field_73735_i, (double)((float)(u + 1) * size), (double)((float)(v + height) * size));
        tessellator.func_78374_a((double)(x + width), (double)y, (double)this.field_73735_i, (double)((float)(u + 1) * size), (double)((float)v * size));
        tessellator.func_78374_a((double)x, (double)y, (double)this.field_73735_i, (double)((float)u * size), (double)((float)v * size));
        tessellator.func_78381_a();
    }

    public boolean hasScrollbar() {
        return this.categories.length > 8;
    }

    public void setZLevel(float zLevel) {
        this.field_73735_i = zLevel;
    }

    static {
        achievementDone = false;
    }
}

