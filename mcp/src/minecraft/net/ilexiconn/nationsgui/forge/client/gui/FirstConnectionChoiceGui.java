/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.gson.Gson
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FactionSelectGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class FirstConnectionChoiceGui
extends AbstractFirstConnectionGui {
    private Data data;

    public FirstConnectionChoiceGui(String serverName) {
        try {
            this.data = (Data)new Gson().fromJson((Reader)new InputStreamReader(new URL("http://api.nationsglory.fr/factions_list.php?server=" + serverName).openStream(), Charsets.UTF_8), Data.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canBeOpen() {
        return !this.data.availableFactions.isEmpty() || !this.data.openFactions.isEmpty();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new Button(0, this.field_73880_f / 2 - 75, this.field_73881_g / 2 - 30, "Rejoindre un pays existant"));
        Button button = new Button(1, this.field_73880_f / 2 - 75, this.field_73881_g / 2 - 10, "Cr\u00e9er mon propre pays");
        button.field_73742_g = !this.data.availableFactions.isEmpty();
        this.field_73887_h.add(button);
    }

    @Override
    public void func_73863_a(int par1, int par2, float par3) {
        super.func_73863_a(par1, par2, par3);
        String text = "Il est temps de faire ton choix :";
        this.field_73882_e.field_71466_p.func_78276_b(text, this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 40, -1);
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        GL11.glPushMatrix();
        float scale = 0.375f;
        GL11.glTranslatef((float)((float)(this.field_73880_f / 2) - 112.0f * scale / 2.0f), (float)((float)(this.field_73881_g / 2 + 64) - 144.0f * scale - 2.0f), (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)1.0f);
        this.func_73729_b(0, 0, 40, 112, 216, 144);
        GL11.glPopMatrix();
    }

    @Override
    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        if (par1GuiButton.field_73741_f == 0 || par1GuiButton.field_73741_f == 1) {
            this.field_73882_e.func_71373_a((GuiScreen)new FactionSelectGui(par1GuiButton.field_73741_f == 1, this.data));
        }
    }

    public class Data {
        private List<String> availableFactions = new ArrayList<String>();
        private List<String> openFactions = new ArrayList<String>();

        public List<String> getAvailableFactions() {
            return this.availableFactions;
        }

        public List<String> getOpenFactions() {
            return this.openFactions;
        }
    }

    private class Button
    extends GuiButton {
        public Button(int id, int x, int y, String text) {
            super(id, x, y, 150, 15, text);
        }

        public void func_73737_a(Minecraft par1Minecraft, int mouseX, int mouseY) {
            boolean bl = this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
            if (this.field_82253_i) {
                float color = 0.5f;
                GL11.glColor3f((float)color, (float)color, (float)color);
            }
            FirstConnectionChoiceGui.this.drawGreyRectangle(this.field_73746_c, this.field_73743_d, this.field_73747_a, this.field_73745_b);
            String finale = this.field_73744_e + (this.field_73742_g ? "" : " (Indisponible)");
            ((FirstConnectionChoiceGui)FirstConnectionChoiceGui.this).field_73882_e.field_71466_p.func_78276_b(finale, this.field_73746_c + this.field_73747_a / 2 - ((FirstConnectionChoiceGui)FirstConnectionChoiceGui.this).field_73882_e.field_71466_p.func_78256_a(finale) / 2, this.field_73743_d + this.field_73745_b / 2 - 4, -1);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        }
    }
}

