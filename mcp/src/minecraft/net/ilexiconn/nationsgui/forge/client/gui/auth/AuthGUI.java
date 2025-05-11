/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 */
package net.ilexiconn.nationsgui.forge.client.gui.auth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.AuthTypes;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

@SideOnly(value=Side.CLIENT)
public class AuthGUI
extends GuiScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/auth.png");
    private int ordinal;
    private IAuthType type;

    public AuthGUI(AuthTypes type) {
        this.ordinal = type.ordinal();
        this.type = type.getType();
    }

    public void func_73866_w_() {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 67;
        Keyboard.enableRepeatEvents((boolean)true);
        this.type.init(x, y, this, this.field_73887_h);
    }

    public void func_73874_b() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 67;
        this.func_73873_v_();
        this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
        this.func_73729_b(x, y, 0, 0, 233, 37);
        for (int i = 0; i < 4; ++i) {
            this.func_73729_b(x, y + 37 + i * 24, 0, 37, 233, 24);
        }
        this.func_73729_b(x, y + 133, 0, 61, 233, 5);
        int v = 66 + this.ordinal * 36;
        if (this.field_73882_e.field_71474_y.field_74363_ab.startsWith("fr_")) {
            v += 18;
        }
        this.func_73729_b(x + 11, y + 11, 0, v, 150, 18);
        this.type.render(x, y, mouseX, mouseY, this);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public void func_73876_c() {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 67;
        this.type.update(x, y, this);
    }

    protected void func_73869_a(char character, int key) {
        if (key == 1) {
            for (Object button : this.field_73887_h) {
                if (!(button instanceof CloseButtonGUI)) continue;
                return;
            }
            this.field_73887_h.add(new CloseButtonGUI(2, 5, 5));
        } else {
            this.type.onKeyPressed(character, key);
        }
    }

    public void func_73867_d() {
        this.type.handleMouseInput();
        super.func_73867_d();
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 2) {
            if (this.type.equals(AuthTypes.REGISTER.getType()) && ClientProxy.openFirstConnectionGuiAfterAuthMe) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FirstConnectionGui(ClientProxy.currentServerName));
                ClientProxy.openFirstConnectionGuiAfterAuthMe = false;
            } else {
                Minecraft.func_71410_x().func_71373_a(null);
            }
        } else {
            this.type.actionPerformed(button, this);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int button) {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 67;
        this.type.mouseClicked(x, y, mouseX, mouseY, button, this);
        super.func_73864_a(mouseX, mouseY, button);
    }

    public AuthTypes getType() {
        return AuthTypes.values()[this.ordinal];
    }
}

