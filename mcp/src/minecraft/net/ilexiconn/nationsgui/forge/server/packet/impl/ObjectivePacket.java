/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.DataInput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ObjectivePacket
implements IPacket,
IClientPacket {
    private NBTTagCompound compound;

    @Override
    public void handleClientPacket(EntityPlayer player) {
        NBTTagList nbtTagList = this.compound.func_74761_m("objectives");
        Objective selectedObjective = null;
        if (!ClientData.objectives.isEmpty() && ClientData.objectives.size() - 1 < ClientData.currentObjectiveIndex) {
            selectedObjective = ClientData.objectives.get(ClientData.currentObjectiveIndex);
        }
        ClientData.objectives.clear();
        for (int i = 0; i < nbtTagList.func_74745_c(); ++i) {
            ClientData.objectives.add(new Objective((NBTTagCompound)nbtTagList.func_74743_b(i)));
        }
        if (selectedObjective == null || !ClientData.objectives.get(ClientData.currentObjectiveIndex).equals(selectedObjective)) {
            ClientData.currentObjectiveIndex = 0;
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.compound = CompressedStreamTools.func_74794_a((DataInput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

