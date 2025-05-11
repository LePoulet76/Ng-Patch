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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPortalGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenIslandPortalPacket
implements IPacket,
IClientPacket {
    public int posX;
    public int posY;
    public int posZ;

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandPortalGui(this.posX, this.posY, this.posZ));
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

