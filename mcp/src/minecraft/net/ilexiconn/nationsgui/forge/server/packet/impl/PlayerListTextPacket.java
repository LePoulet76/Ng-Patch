/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListTextPacket
implements IPacket,
IClientPacket {
    public String up;
    public String bottom;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.up = data.readUTF();
        this.bottom = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.up);
        data.writeUTF(this.bottom);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PlayerListGUI.topText = this.up.replace("%player%", Minecraft.func_71410_x().func_110432_I().func_111285_a());
        PlayerListGUI.bottomText = this.bottom.replace("%player%", Minecraft.func_71410_x().func_110432_I().func_111285_a());
    }
}

