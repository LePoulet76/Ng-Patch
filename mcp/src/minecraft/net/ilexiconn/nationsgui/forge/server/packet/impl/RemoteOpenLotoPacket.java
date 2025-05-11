/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.LotoAdminGui;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenLotoPacket
implements IPacket,
IClientPacket {
    private boolean admin;

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.admin) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new LotoAdminGui());
        } else {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new LotoGui());
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.admin = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

