/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  org.lwjgl.input.Keyboard
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat;

import acs.tabbychat.GuiChatTC;
import java.net.URI;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiChat
extends GuiScreen {
    protected GuiTextField inputField;
    protected GuiChatTC wrapper;
    public String defaultInputFieldText = "";

    public GuiChat() {
    }

    public GuiChat(String par1Str) {
        this.defaultInputFieldText = par1Str;
    }

    public void func_73866_w_() {
        this.wrapper = new GuiChatTC();
        this.wrapper.field_73900_q = this.defaultInputFieldText;
        this.wrapper.func_73866_w_();
        this.inputField = this.wrapper.field_73901_a;
    }

    public void func_73874_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        this.field_73882_e.field_71456_v.func_73827_b().func_73764_c();
    }

    public void func_73876_c() {
        this.wrapper.func_73876_c();
    }

    protected void func_73869_a(char par1, int par2) {
        this.wrapper.func_73869_a(par1, par2);
    }

    public void func_73867_d() {
        if (this.wrapper != null) {
            this.wrapper.func_73867_d();
        }
        super.func_73867_d();
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        this.wrapper.func_73864_a(par1, par2, par3);
    }

    public void func_73878_a(boolean par1, int par2) {
        this.wrapper.func_73878_a(par1, par2);
    }

    private void func_73896_a(URI par1URI) {
        this.wrapper.func_73896_a(par1URI);
    }

    public void completePlayerName() {
        this.wrapper.func_73895_u_();
    }

    private void func_73893_a(String par1Str, String par2Str) {
        this.wrapper.func_73893_a(par1Str, par2Str);
    }

    public void getSentHistory(int par1) {
        this.wrapper.func_73892_a(par1);
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.wrapper.func_73863_a(par1, par2, par3);
    }

    public void func_73894_a(String[] par1ArrayOfStr) {
        this.wrapper.func_73894_a(par1ArrayOfStr);
    }

    public boolean func_73868_f() {
        return false;
    }
}

