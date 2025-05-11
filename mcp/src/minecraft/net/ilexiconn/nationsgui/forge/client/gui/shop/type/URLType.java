/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.net.URL;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.shop.Category;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ICategoryType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class URLType
implements ICategoryType {
    @Override
    public void init(int x, int y, ShopGUI gui, Category category, List<GuiButton> buttonList) {
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
        ResourceLocation image = null;
        if (mouseX > x + 121 && mouseX < x + 286 && mouseY > y + 34 && mouseY < y + 217) {
            if (category.isImageHoverLoaded()) {
                image = category.getImageHover();
            }
        } else if (category.isImageLoaded()) {
            image = category.getImage();
        }
        if (image != null) {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(image);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78382_b();
            int categoryX = x + 122;
            int categoryY = y + 35;
            tessellator.func_78374_a((double)categoryX, (double)(categoryY + 192), 0.0, 0.0, 1.0);
            tessellator.func_78374_a((double)(categoryX + 162), (double)(categoryY + 192), 0.0, 1.0, 1.0);
            tessellator.func_78374_a((double)(categoryX + 162), (double)categoryY, 0.0, 1.0, 0.0);
            tessellator.func_78374_a((double)categoryX, (double)categoryY, 0.0, 0.0, 0.0);
            tessellator.func_78381_a();
        }
    }

    @Override
    public void renderPost(int x, int y, int mouseX, int mouseY, ShopGUI gui, Category category, FontRenderer fontRenderer) {
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, ShopGUI gui, Category category) {
        Desktop desktop;
        if (mouseX > x + 121 && mouseX < x + 286 && mouseY > y + 34 && mouseY < y + 217 && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URL(category.getURL()).toURI());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button, ShopGUI gui, Category category) {
    }
}

