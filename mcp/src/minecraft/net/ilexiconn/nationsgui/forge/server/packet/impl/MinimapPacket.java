/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MinimapPacket
implements IPacket,
IClientPacket {
    private byte[] colors;

    public MinimapPacket(byte[] colors) {
        this.colors = colors;
    }

    public MinimapPacket() {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        ClientData.minimapColors = this.colors;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            NBTTagCompound nbtTagCompound = CompressedStreamTools.func_74794_a((DataInput)data);
            this.colors = nbtTagCompound.func_74770_j("colors");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        try {
            nbtTagCompound.func_74773_a("colors", this.colors);
            CompressedStreamTools.func_74800_a((NBTTagCompound)nbtTagCompound, (DataOutput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

