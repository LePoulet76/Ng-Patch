/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class ChatGUI
extends GuiChat {
    private boolean displayText = false;
    private List<String> lines = new ArrayList<String>();
    private ScrollBar scrollBar;
    public static final int BOX_WIDTH = 400;
    public static final int BOX_HEIGHT = 250;
    private int heightModifier = 0;
    private RenderItem itemRenderer = new RenderItem();

    public ChatGUI() {
    }

    public ChatGUI(String par1Str) {
        super(par1Str);
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.clear();
        int y = 15;
        if (ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
            if (!this.displayText) {
                this.field_73887_h.add(new Button(0, 118, y, 16, 16));
                this.field_73887_h.add(new Button(1, 134, y, 16, 16));
                if (!ClientData.objectives.isEmpty()) {
                    this.field_73887_h.add(new Button(2, 134, y + 19, 16, 15));
                }
                this.scrollBar = null;
            } else {
                this.heightModifier = ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() != null ? 10 : 0;
                this.scrollBar = new ScrollBar(this.field_73880_f / 2 + 200 - 15, this.field_73881_g / 2 - 125 + 30, 185);
                if (ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() == null) {
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                } else {
                    this.field_73887_h.add(new SimpleButton(4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a((String)"objectives.validate")));
                }
            }
        }
    }

    public void func_73871_c(int par1) {
    }

    public void func_73863_a(int par1, int par2, float par3) {
        if (this.displayText) {
            Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
            ChatGUI.func_73734_a((int)(this.field_73880_f / 2 - 200), (int)(this.field_73881_g / 2 - 125 + 25), (int)(this.field_73880_f / 2 + 200), (int)(this.field_73881_g / 2 + 125), (int)-301989888);
            ChatGUI.func_73734_a((int)(this.field_73880_f / 2 - 200), (int)(this.field_73881_g / 2 - 125 + 5), (int)(this.field_73880_f / 2 + 200), (int)(this.field_73881_g / 2 - 125 + 25), (int)-1728053248);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(this.field_73880_f / 2 - 200 + 5, this.field_73881_g / 2 - 125 + 8, 2, 20, 13, 13);
            this.func_73731_b(this.field_73886_k, current.getTitle(), this.field_73880_f / 2 - 200 + 20, this.field_73881_g / 2 - 125 + 11, 0xFFFFFF);
            GUIUtils.startGLScissor(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5 + 30, 380, 185);
            GL11.glPushMatrix();
            if (this.lines.size() > 11) {
                GL11.glTranslatef((float)0.0f, (float)(-this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier)), (float)0.0f);
            }
            int i = 0;
            for (String line : this.lines) {
                this.func_73731_b(this.field_73886_k, line, this.field_73880_f / 2 - 200 + 10, this.field_73881_g / 2 - 125 + 35 + i * 12, 0xFFFFFF);
                ++i;
            }
            ItemStack itemStack = ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack();
            boolean dr = false;
            if (itemStack != null) {
                int itemY = this.lines.size() * 12 + 2;
                int tWidth = this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"objectives.collect"));
                int w = tWidth + 16 + 4;
                this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"objectives.collect"), this.field_73880_f / 2 - 10 - w / 2, this.field_73881_g / 2 - 125 + 35 + itemY + 4, 0xFFFFFF);
                this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
                this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
                int pX = this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4;
                int pY = this.field_73881_g / 2 - 125 + 35 + itemY - (int)(this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
                dr = par1 >= pX && par1 <= pX + 18 && par2 >= pY && par2 <= pY + 18;
                GL11.glDisable((int)2896);
            }
            GL11.glPopMatrix();
            GUIUtils.endGLScissor();
            if (dr) {
                NationsGUIClientHooks.drawItemStackTooltip(itemStack, par1, par2);
                GL11.glDisable((int)2896);
            }
            this.scrollBar.draw(par1, par2);
        }
        if (ClientProxy.clientConfig.specialEnabled && ClientProxy.clientConfig.displayArmorInInfo) {
            int xPos = this.field_73880_f - 146;
            int yPos = this.field_73881_g - 100;
            EntityClientPlayerMP entityPlayer = Minecraft.func_71410_x().field_71439_g;
            for (int i = 0; i < 4; ++i) {
                ItemStack itemStack = entityPlayer.func_82169_q(3 - i);
                if (itemStack == null) continue;
                int x = xPos + 75 + 17 * i;
                int y = yPos + 5;
                if (par1 < x || par1 > x + 16 || par2 < y || par2 > y + 16) continue;
                NationsGUIClientHooks.drawItemStackTooltip(itemStack, par1, par2);
            }
            ItemStack handItem = Minecraft.func_71410_x().field_71439_g.field_71071_by.func_70448_g();
            if (handItem != null) {
                int x = xPos + 100;
                int y = yPos + 30;
                if (par1 >= x && par1 <= x + 16 && par2 >= y && par2 <= y + 16) {
                    NationsGUIClientHooks.drawItemStackTooltip(handItem, par1, par2);
                }
            }
        }
        if (ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty()) {
            int posYHelperAttacker;
            if (!ClientData.currentAssault.get("attackerHelpersCount").equals("0")) {
                int posXHelperAttacker = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a(ClientData.currentAssault.get("attackerFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.field_73881_g * 0.4) + 26;
                if (par1 >= posXHelperAttacker && par1 <= posXHelperAttacker + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3) {
                    this.drawHoveringText(Arrays.asList(ClientData.currentAssault.get("attackerHelpersName").split(",")), par1, par2, this.field_73886_k);
                }
            }
            if (!ClientData.currentAssault.get("defenderHelpersCount").equals("0")) {
                int posXHelperAttacker = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a(ClientData.currentAssault.get("defenderFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.field_73881_g * 0.4) + 26;
                if (par1 >= posXHelperAttacker && par1 <= posXHelperAttacker + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3) {
                    this.drawHoveringText(Arrays.asList(ClientData.currentAssault.get("defenderHelpersName").split(",")), par1, par2, this.field_73886_k);
                }
            }
        }
        super.func_73863_a(par1, par2, par3);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        List<Objective> objectives = ClientData.objectives;
        switch (par1GuiButton.field_73741_f) {
            case 0: {
                if (ClientData.currentObjectiveIndex - 1 >= 0) {
                    --ClientData.currentObjectiveIndex;
                    break;
                }
                ClientData.currentObjectiveIndex = ClientData.objectives.size() - 1;
                break;
            }
            case 1: {
                if (ClientData.currentObjectiveIndex + 1 < objectives.size()) {
                    ++ClientData.currentObjectiveIndex;
                    break;
                }
                ClientData.currentObjectiveIndex = 0;
                break;
            }
            case 2: {
                this.displayText = true;
                this.generateTextLines();
                this.func_73866_w_();
                break;
            }
            case 3: {
                Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
                if (current != null && current.getId().split("-").length == 3) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
                }
                this.displayText = false;
                this.func_73866_w_();
                break;
            }
            case 4: {
                this.displayText = false;
                this.func_73866_w_();
            }
        }
    }

    private void generateTextLines() {
        String[] l;
        this.lines.clear();
        int lineWidth = 360;
        int spaceWidth = this.field_73886_k.func_78256_a(" ");
        Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
        for (String line : l = current.getText().split("\n")) {
            int spaceLeft = lineWidth;
            String[] words = line.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String word : words) {
                int wordWidth = this.field_73886_k.func_78256_a(word);
                if (wordWidth + spaceWidth > spaceLeft) {
                    this.lines.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    spaceLeft = lineWidth - wordWidth;
                } else {
                    spaceLeft -= wordWidth + spaceWidth;
                }
                stringBuilder.append(word);
                stringBuilder.append(' ');
            }
            this.lines.add(stringBuilder.toString());
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        super.func_73864_a(par1, par2, par3);
    }

    public boolean isDisplayingText() {
        return this.displayText;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    private class ScrollBar
    extends GuiScrollBar {
        public ScrollBar(float x, float y, int height) {
            super(x, y, height);
        }

        @Override
        protected void drawScroller() {
            ScrollBar.func_73734_a((int)((int)this.x), (int)((int)this.y), (int)((int)(this.x + 9.0f)), (int)((int)(this.y + (float)this.height)), (int)0x22FFFFFF);
            int yP = (int)(this.y + (float)(this.height - 20) * this.sliderValue);
            ScrollBar.func_73734_a((int)((int)this.x), (int)yP, (int)((int)(this.x + 9.0f)), (int)(yP + 20), (int)0x55FFFFFF);
        }
    }

    private class SimpleButton
    extends GuiButton {
        public SimpleButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
            super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            SimpleButton.func_73734_a((int)this.field_73746_c, (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b), (int)(this.field_82253_i ? 0x77FFFFFF : 0x55FFFFFF));
            ChatGUI.this.field_73886_k.func_78276_b(this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - ChatGUI.this.field_73886_k.func_78256_a(this.field_73744_e) / 2, this.field_73743_d + 4, 0xFFFFFF);
        }
    }

    private class Button
    extends GuiButton {
        public Button(int id, int posX, int posY, int width, int height) {
            super(id, posX, posY, width, height, "");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            boolean bl = this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            if (this.field_82253_i) {
                Button.func_73734_a((int)this.field_73746_c, (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b), (int)0x66FFFFFF);
            }
        }
    }
}

