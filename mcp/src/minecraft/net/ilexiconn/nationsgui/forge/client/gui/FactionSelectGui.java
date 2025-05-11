/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernScrollBar;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionTeleportPacket;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class FactionSelectGui
extends AbstractFirstConnectionGui {
    private boolean createFaction;
    private FirstConnectionChoiceGui.Data data;
    private List<FactionButton> factionButtonList = new ArrayList<FactionButton>();
    private ModernScrollBar modernScrollBar;
    private List<String> textList = new ArrayList<String>();
    private String infoText;

    public FactionSelectGui(boolean createFaction, FirstConnectionChoiceGui.Data data) {
        this.createFaction = createFaction;
        this.data = data;
        this.infoText = createFaction ? "Astuce : Une fois ton pays cr\u00e9\u00e9, tu seras t\u00e9l\u00e9port\u00e9 sur ta terre promise !" : "Astuce : Tu sera automatiquement t\u00e9l\u00e9port\u00e9 au spawn de ton pays.";
        this.updateText();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.factionButtonList.clear();
        List<String> list = this.createFaction ? this.data.getAvailableFactions() : this.data.getOpenFactions();
        int i = 0;
        for (String factionName : list) {
            this.factionButtonList.add(new FactionButton(factionName, this.field_73880_f / 2 - 89, this.field_73881_g / 2 - 26 + i * 12));
            ++i;
        }
        this.modernScrollBar = this.factionButtonList.size() > 6 ? new ModernScrollBar(this.field_73880_f / 2 + 22, this.field_73881_g / 2 - 40, 3, 95, 3) : null;
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float truc) {
        super.func_73863_a(mouseX, mouseY, truc);
        this.drawGreyRectangle(this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 40, 110, 95);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPushMatrix();
        float scale = 0.35f;
        GL11.glTranslatef((float)((float)(this.field_73880_f / 2 + 100) - scale * 155.0f - 2.0f), (float)((float)(this.field_73881_g / 2 + 64) - 144.0f * scale - 2.0f), (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)1.0f);
        this.func_73729_b(0, 0, 40, 112, 155, 144);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        String text = this.createFaction ? "Cr\u00e9er mon propre pays" : "Rejoindre un pays";
        GL11.glTranslatef((float)((float)(this.field_73880_f / 2 - 90 + 55) - (float)this.field_73882_e.field_71466_p.func_78256_a(text) / 2.0f * 0.75f), (float)(this.field_73881_g / 2 - 37), (float)0.0f);
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        this.field_73882_e.field_71466_p.func_78276_b(text, 0, 0, -1);
        GL11.glPopMatrix();
        int x = this.field_73880_f / 2 - 90;
        int y = this.field_73881_g / 2 - 28;
        FactionSelectGui.func_73734_a((int)(x + 4), (int)y, (int)(x + 106), (int)(y + 1), (int)-1);
        int decal = 0;
        if (this.modernScrollBar != null) {
            this.modernScrollBar.draw(mouseX, mouseY);
            decal = this.getDecal();
        }
        GUIUtils.startGLScissor(this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 26, 109, 80);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)(-decal), (float)0.0f);
        for (FactionButton factionButton : this.factionButtonList) {
            factionButton.draw(mouseX, mouseY + decal);
        }
        GL11.glPopMatrix();
        GUIUtils.endGLScissor();
        int i = 0;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.field_73880_f / 2 + 23), (float)(this.field_73881_g / 2 - 40), (float)0.0f);
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        for (String mText : this.textList) {
            this.field_73882_e.field_71466_p.func_78276_b(mText, 0, i * 8, -1);
            ++i;
        }
        GL11.glPopMatrix();
    }

    private void updateText() {
        String[] words;
        this.textList.clear();
        StringBuilder sub = new StringBuilder();
        for (String word : words = this.infoText.split(" ")) {
            if (sub.length() + word.length() <= 18) {
                if (!Objects.equals(words[0], word)) {
                    sub.append(' ');
                }
                sub.append(word);
                continue;
            }
            this.textList.add(sub.toString());
            sub = new StringBuilder(word);
        }
        if (sub.length() > 0) {
            this.textList.add(sub.toString());
        }
    }

    private int getDecal() {
        return (int)(this.modernScrollBar.getSliderValue() * (float)(this.factionButtonList.size() - 6) * 12.0f);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mode) {
        super.func_73864_a(mouseX, mouseY, mode);
        for (FactionButton factionButton : this.factionButtonList) {
            factionButton.click(mouseX, mouseY + (this.modernScrollBar != null ? this.getDecal() : 0));
        }
    }

    private class FactionButton
    extends Gui {
        private String factionName;
        private final int posX;
        private final int posY;
        private int width = 108;
        private int height = 12;

        public FactionButton(String factionName, int posX, int posY) {
            this.factionName = factionName;
            this.posX = posX;
            this.posY = posY;
        }

        public void draw(int mouseX, int mouseY) {
            if (mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height) {
                FactionButton.func_73734_a((int)this.posX, (int)this.posY, (int)(this.posX + this.width), (int)(this.posY + this.height), (int)-13948117);
                FactionSelectGui.this.field_73882_e.func_110434_K().func_110577_a(AbstractFirstConnectionGui.BACKGROUND);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                if (FactionSelectGui.this.createFaction) {
                    this.func_73729_b(this.posX + this.width - 10, this.posY + this.height / 2 - 3, 35, 45, 6, 7);
                } else {
                    this.func_73729_b(this.posX + this.width - 10, this.posY + this.height / 2 - 2, 26, 45, 5, 5);
                }
            }
            ((FactionSelectGui)FactionSelectGui.this).field_73882_e.field_71466_p.func_78276_b(this.factionName, this.posX + 5, this.posY + this.height / 2 - 4, -1);
        }

        public void click(int mouseX, int mouseY) {
            if (mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height) {
                FactionTeleportPacket factionTeleportPacket = new FactionTeleportPacket();
                factionTeleportPacket.createFaction = FactionSelectGui.this.createFaction;
                FactionSelectGui.this.field_73882_e.func_71373_a(null);
                if (FactionSelectGui.this.createFaction) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
                    ((FactionSelectGui)FactionSelectGui.this).field_73882_e.field_71439_g.func_71165_d("/f create " + this.factionName);
                    ((FactionSelectGui)FactionSelectGui.this).field_73882_e.field_71439_g.func_71165_d("/f claim");
                } else {
                    ((FactionSelectGui)FactionSelectGui.this).field_73882_e.field_71439_g.func_71165_d("/f join " + this.factionName);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
                }
            }
        }
    }
}

