package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ObjectivePacket implements IPacket, IClientPacket
{
    private NBTTagCompound compound;

    public void handleClientPacket(EntityPlayer player)
    {
        NBTTagList nbtTagList = this.compound.getTagList("objectives");
        Objective selectedObjective = null;

        if (!ClientData.objectives.isEmpty() && ClientData.objectives.size() - 1 < ClientData.currentObjectiveIndex)
        {
            selectedObjective = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
        }

        ClientData.objectives.clear();

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            ClientData.objectives.add(new Objective((NBTTagCompound)nbtTagList.tagAt(i)));
        }

        if (selectedObjective == null || !((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).equals(selectedObjective))
        {
            ClientData.currentObjectiveIndex = 0;
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            this.compound = CompressedStreamTools.read(data);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void toBytes(ByteArrayDataOutput data) {}
}
