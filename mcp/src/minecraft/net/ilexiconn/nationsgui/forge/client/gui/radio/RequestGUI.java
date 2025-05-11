/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.util.StatCollector
 *  org.lwjgl.input.Keyboard
 */
package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@SideOnly(value=Side.CLIENT)
public class RequestGUI
extends GuiScreen {
    private RadioGUI parent;
    private GuiTextField fieldRequest;
    private GuiButton buttonSend;

    public RequestGUI(RadioGUI parent) {
        this.parent = parent;
    }

    public void func_73866_w_() {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 50;
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_73887_h.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.buttonSend = new GuiButton(1, x + 16, y + 72, StatCollector.func_74838_a((String)"nationsgui.radio.send"));
        this.field_73887_h.add(this.buttonSend);
        this.buttonSend.field_73742_g = false;
        this.fieldRequest = new GuiTextField(this.field_73886_k, x + 77, y + 48, 139, 18);
        this.fieldRequest.func_73804_f(200);
        this.fieldRequest.func_73803_e();
        this.fieldRequest.func_73796_b(true);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 50;
        this.func_73873_v_();
        this.field_73882_e.func_110434_K().func_110577_a(RadioGUI.TEXTURE);
        this.func_73729_b(x, y, 0, 0, 233, 43);
        for (int i = 0; i < 4; ++i) {
            this.func_73729_b(x, y + 43 + 13 * i, 0, 243, 228, 13);
        }
        this.func_73729_b(x, y + 95, 0, 160, 235, 5);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.request"), x + 16, y + 54, 0x6F6F6F);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.request"), x + 16, y + 53, 0xFFFFFF);
        this.fieldRequest.func_73795_f();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 0) {
            this.field_73882_e.func_71373_a((GuiScreen)this.parent);
        } else if (button.field_73741_f == 1) {
            this.buttonSend.field_73742_g = false;
            this.fieldRequest.func_82265_c(false);
            PacketCallbacks.REQUEST_SONG.send(this.fieldRequest.func_73781_b());
        }
    }

    public void func_73874_b() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    public void func_73876_c() {
        this.fieldRequest.func_73780_a();
    }

    protected void func_73869_a(char character, int key) {
        if (!this.fieldRequest.func_73802_a(character, key)) {
            super.func_73869_a(character, key);
        } else {
            this.buttonSend.field_73742_g = !this.fieldRequest.func_73781_b().isEmpty();
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int button) {
        this.fieldRequest.func_73793_a(mouseX, mouseY, button);
        super.func_73864_a(mouseX, mouseY, button);
    }

    public boolean func_73868_f() {
        return false;
    }

    public static void handleReturn(boolean success) {
        GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
        if (screen instanceof RequestGUI) {
            RequestGUI request = (RequestGUI)screen;
            if (success) {
                request.field_73882_e.func_71373_a((GuiScreen)request.parent);
            } else {
                request.buttonSend.field_73742_g = true;
                request.fieldRequest.func_82265_c(true);
            }
        }
    }
}

