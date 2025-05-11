/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SnackbarGUI
extends Gui {
    private String message;
    private int maxAge;
    private int age;
    private float yOffset = 20.0f;
    private boolean clicked;

    public SnackbarGUI(String message) {
        this(message, true);
    }

    public SnackbarGUI(String message, boolean autoclose) {
        this.message = message;
        this.maxAge = autoclose ? (int)((double)Minecraft.func_71410_x().field_71466_p.func_78256_a(message) * 0.75) : 2147383647;
    }

    public void updateSnackbar(ScaledResolution resolution, int mouseX, int mouseY) {
        ++this.age;
        if (this.age > this.maxAge) {
            ClientEventHandler.getInstance().snackbarGUI = null;
        }
        if (!this.clicked && Mouse.isButtonDown((int)0) && mouseX >= resolution.func_78326_a() - 15 && mouseX <= resolution.func_78326_a() - 15 + 9 && mouseY >= 5 && mouseY <= 15) {
            this.age = this.maxAge - 15;
            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            this.clicked = true;
        }
    }

    public void drawSnackbar() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)500.0f);
        GL11.glTranslatef((float)0.0f, (float)(-this.yOffset), (float)0.0f);
        GL11.glEnable((int)3042);
        ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        Gui.func_73734_a((int)0, (int)20, (int)resolution.func_78326_a(), (int)0, (int)-1342177280);
        Minecraft.func_71410_x().field_71466_p.func_78276_b(this.message, 10, 6, -1);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(ShopGUI.TEXTURE);
        GL11.glBlendFunc((int)770, (int)771);
        this.func_73729_b(resolution.func_78326_a() - 15, 5, 204, 18, 9, 10);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        this.yOffset = GUIUtils.interpolate(this.yOffset, this.age < this.maxAge - 15 ? 0.0f : 20.0f, 0.05f);
    }
}

