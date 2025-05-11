/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SpawnPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class CustomGuiGameOver
extends GuiScreen {
    private int cooldownTimer;

    public void func_73866_w_() {
        this.field_73887_h.clear();
        if (this.field_73882_e.field_71441_e.func_72912_H().func_76093_s()) {
            if (this.field_73882_e.func_71387_A()) {
                this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a((String)"deathScreen.deleteWorld")));
            } else {
                this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a((String)"deathScreen.leaveServer")));
            }
        } else {
            if (this.field_73882_e.func_71387_A()) {
                this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 72, I18n.func_135053_a((String)"deathScreen.respawn")));
            } else {
                this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 72, 98, 20, I18n.func_135053_a((String)"deathScreen.respawn.spawn")));
                this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 + 2, this.field_73881_g / 4 + 72, 98, 20, I18n.func_135053_a((String)"deathScreen.respawn.faction")));
            }
            this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 96, I18n.func_135053_a((String)"deathScreen.titleScreen")));
            if (this.field_73882_e.func_110432_I() == null) {
                ((GuiButton)this.field_73887_h.get((int)1)).field_73742_g = false;
            }
        }
        for (GuiButton guibutton : this.field_73887_h) {
            guibutton.field_73742_g = false;
        }
    }

    protected void func_73869_a(char par1, int par2) {
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        SpawnPacket spawnPacket = new SpawnPacket();
        switch (par1GuiButton.field_73741_f) {
            case 0: {
                spawnPacket.factionSpawn = true;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(spawnPacket));
                this.field_73882_e.field_71439_g.func_71004_bE();
                this.field_73882_e.func_71373_a(null);
                break;
            }
            case 1: {
                if (!this.field_73882_e.func_71387_A()) {
                    spawnPacket.factionSpawn = false;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(spawnPacket));
                }
                this.field_73882_e.field_71439_g.func_71004_bE();
                this.field_73882_e.func_71373_a(null);
                break;
            }
            case 2: {
                if (this.field_73882_e.field_71441_e != null) {
                    this.field_73882_e.field_71441_e.func_72882_A();
                }
                this.field_73882_e.func_71403_a(null);
                this.field_73882_e.func_71373_a((GuiScreen)new GuiMainMenu());
            }
        }
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.func_73733_a(0, 0, this.field_73880_f, this.field_73881_g, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        boolean flag = this.field_73882_e.field_71441_e.func_72912_H().func_76093_s();
        String s = flag ? I18n.func_135053_a((String)"deathScreen.title.hardcore") : I18n.func_135053_a((String)"deathScreen.title");
        this.func_73732_a(this.field_73886_k, s, this.field_73880_f / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        if (flag) {
            this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"deathScreen.hardcoreInfo"), this.field_73880_f / 2, 144, 0xFFFFFF);
        }
        this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"deathScreen.score") + ": " + EnumChatFormatting.YELLOW + this.field_73882_e.field_71439_g.func_71037_bA(), this.field_73880_f / 2, 100, 0xFFFFFF);
        super.func_73863_a(par1, par2, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73876_c() {
        super.func_73876_c();
        ++this.cooldownTimer;
        if (this.cooldownTimer == 20) {
            for (GuiButton guibutton : this.field_73887_h) {
                guibutton.field_73742_g = true;
            }
        }
    }
}

