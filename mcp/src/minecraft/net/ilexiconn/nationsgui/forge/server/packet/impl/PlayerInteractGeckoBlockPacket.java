/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PlayerInteractGeckoBlockPacket
implements IPacket,
IClientPacket {
    private int x;
    private int y;
    private int z;
    private String worldName;

    public PlayerInteractGeckoBlockPacket(int x, int y, int z, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.worldName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeUTF(this.worldName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        System.out.println("receive packet PlayerInteractGeckoPacket");
        TileEntity tileEntity = player.field_70170_p.func_72796_p(this.x, this.y, this.z);
        if (tileEntity instanceof GenericGeckoTileEntity) {
            GenericGeckoTileEntity geckoTileEntity = (GenericGeckoTileEntity)tileEntity;
            geckoTileEntity.onPlayerInteract();
        }
    }
}

