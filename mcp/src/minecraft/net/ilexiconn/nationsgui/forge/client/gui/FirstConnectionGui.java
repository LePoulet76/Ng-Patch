/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.common.base.Charsets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class FirstConnectionGui
extends AbstractFirstConnectionGui {
    private List<Screen> screenList = null;
    private List<String> textList = new ArrayList<String>();
    private int currentScreen = 0;
    private String serverName;

    public FirstConnectionGui(String serverName) {
        this.serverName = serverName;
        try {
            Type listType = new TypeToken<ArrayList<Screen>>(){}.getType();
            this.screenList = (List)new Gson().fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/json/welcome.json").openStream(), Charsets.UTF_8), listType);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.updateText();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new Button(this.field_73880_f / 2 + (this.screenList.get(this.currentScreen).image.equals("girl") ? 35 : 75), this.field_73881_g / 2 + 15));
    }

    private int getCharacterLimit(int line) {
        if (this.screenList.get(this.currentScreen).image.equals("girl")) {
            if (line <= 1) {
                return 180;
            }
            if (line >= 6) {
                return 117;
            }
            return 147;
        }
        return 180;
    }

    private void updateText() {
        this.textList.clear();
        StringBuilder sub = new StringBuilder();
        List lines = this.screenList.get(this.currentScreen).text;
        int i = 0;
        for (String line : lines) {
            String[] words;
            for (String word : words = line.split(" ")) {
                String temp = (!Objects.equals(words[0], word) ? " " : "") + word;
                if (Minecraft.func_71410_x().field_71466_p.func_78256_a(sub.toString()) + Minecraft.func_71410_x().field_71466_p.func_78256_a(temp) <= this.getCharacterLimit(i)) {
                    sub.append(temp);
                    continue;
                }
                this.textList.add(sub.toString());
                sub = new StringBuilder(word);
                ++i;
            }
            if (!sub.toString().equals("")) {
                this.textList.add(sub.toString());
            }
            if (lines.size() <= 1 || Objects.equals(lines.get(lines.size() - 1), line)) continue;
            sub = new StringBuilder();
            this.textList.add("");
            ++i;
        }
    }

    @Override
    public void func_73863_a(int par1, int par2, float par3) {
        super.func_73863_a(par1, par2, par3);
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        String currentImage = this.screenList.get(this.currentScreen).image;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        if (currentImage.equals("girl")) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.field_73880_f / 2 + 100 - 42 - 2), (float)(this.field_73881_g / 2 + 128 - 144 + 5), (float)0.0f);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)1.0f);
            this.func_73729_b(0, 0, 40, 112, 85, 144);
            GL11.glPopMatrix();
        } else if (currentImage.equals("warrior")) {
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3008);
            GL11.glShadeModel((int)7425);
            GL11.glPushMatrix();
            float scale = 0.6f;
            GL11.glTranslatef((float)((float)(this.field_73880_f / 2) - 194.0f * scale / 2.0f), (float)((float)(this.field_73881_g / 2 + 64) - 109.0f * scale - 2.0f), (float)0.0f);
            GL11.glScalef((float)scale, (float)scale, (float)1.0f);
            this.func_73729_b(0, 0, 62, 0, 194, 109);
            GL11.glPopMatrix();
            GL11.glShadeModel((int)7424);
            GL11.glEnable((int)3008);
            GL11.glDisable((int)3042);
        }
        int i = 0;
        for (String text : this.textList) {
            this.field_73882_e.field_71466_p.func_78276_b(text, this.field_73880_f / 2 - 90, this.field_73881_g / 2 + i * 9 - 35, -1);
            ++i;
        }
    }

    @Override
    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        if (par1GuiButton.field_73741_f == 0) {
            if (this.currentScreen + 1 < this.screenList.size()) {
                ++this.currentScreen;
            } else {
                this.field_73882_e.func_71373_a(null);
            }
            this.updateText();
            this.field_73887_h.clear();
            this.func_73866_w_();
        }
    }

    private class Button
    extends GuiButton {
        public Button(int x, int y) {
            super(0, x, y, 17, 40, ">");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            GL11.glPushMatrix();
            boolean bl = this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            if (this.field_82253_i) {
                float color = 0.7f;
                GL11.glColor3f((float)color, (float)color, (float)color);
            }
            GL11.glTranslatef((float)this.field_73746_c, (float)this.field_73743_d, (float)0.0f);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            FirstConnectionGui.this.field_73882_e.func_110434_K().func_110577_a(AbstractFirstConnectionGui.BACKGROUND);
            this.func_73729_b(0, 0, 0, 12, 22, 53);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(11 - FirstConnectionGui.this.field_73886_k.func_78256_a(this.field_73744_e) + 1), (float)19.0f, (float)0.0f);
            GL11.glScalef((float)2.0f, (float)2.0f, (float)0.0f);
            FirstConnectionGui.this.field_73886_k.func_78276_b(this.field_73744_e, 0, 0, -1);
            GL11.glPopMatrix();
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
    }

    private class Screen {
        private String image = "";
        private List<String> text = new ArrayList<String>();

        private Screen() {
        }

        public String getImage() {
            return this.image;
        }

        public List<String> getText() {
            return this.text;
        }
    }
}

