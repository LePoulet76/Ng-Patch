/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.FrameGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class FrameOpenGuiPacket
implements IPacket,
IClientPacket {
    private String url;
    private String title;
    private String musicUrl;

    public FrameOpenGuiPacket(String url, String title, String musicUrl) {
        this.url = url;
        this.title = title;
        this.musicUrl = musicUrl;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.url = data.readUTF();
        this.title = data.readUTF();
        this.musicUrl = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.url);
        data.writeUTF(this.title);
        data.writeUTF(this.musicUrl);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new FrameGui(this.url, this.title, this.musicUrl));
    }
}

