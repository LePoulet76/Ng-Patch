/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.StatCollector
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class SnackbarPacket
implements IPacket,
IClientPacket {
    private String message;
    private String[] extra;

    public SnackbarPacket(String message, String ... extra) {
        this.message = message;
        this.extra = extra;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74837_a((String)this.message.replace("<player>", Minecraft.func_71410_x().field_71439_g.func_96090_ax()), (Object[])this.extra)));
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.message = data.readUTF();
        int extra = data.readInt();
        this.extra = new String[extra];
        for (int i = 0; i < extra; ++i) {
            this.extra[i] = data.readUTF();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.message);
        data.writeInt(this.extra.length);
        for (String extra : this.extra) {
            data.writeUTF(extra);
        }
    }
}

