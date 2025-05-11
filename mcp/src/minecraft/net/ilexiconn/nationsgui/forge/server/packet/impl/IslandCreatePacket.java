/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.common.DimensionManager
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandCreateGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

public class IslandCreatePacket
implements IPacket,
IClientPacket,
IServerPacket {
    public String name;
    public String description;
    public String size;
    public String biome;
    public boolean isPrivate;
    public int islandId;

    public IslandCreatePacket(String name, String description, String size, String biome, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.biome = biome;
        this.isPrivate = isPrivate;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.islandId = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.name);
        data.writeUTF(this.description);
        data.writeUTF(this.size);
        data.writeUTF(this.biome);
        data.writeBoolean(this.isPrivate);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        IslandCreateGui.creationIslandId = this.islandId;
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        System.out.println("SAVE DIMENSION DATAMAP");
        DimensionManager.saveDimensionDataMap();
    }
}

