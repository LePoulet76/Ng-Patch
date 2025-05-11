/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  fr.nationsglory.server.block.entity.GCTraderBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.server.block.entity.GCTraderBlockEntity;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class UpdateTraderMachinePacket
implements IPacket,
IServerPacket {
    public static HashMap<String, Long> lastUpdateMachine = new HashMap();
    public int posX;
    public int posY;
    public int posZ;
    public int machineValue;

    public UpdateTraderMachinePacket(int posX, int posY, int posZ, int machineValue) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.machineValue = machineValue;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.machineValue = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeInt(this.machineValue);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ);
        if (tileEntity instanceof GCTraderBlockEntity) {
            String key;
            if (!(((GCTraderBlockEntity)tileEntity).machineValue == this.machineValue || lastUpdateMachine.containsKey(key = this.posX + "#" + this.posY + "#" + this.posZ) && System.currentTimeMillis() - lastUpdateMachine.get(key) <= 300000L || this.machineValue != GCTraderBlockEntity.serverValue)) {
                lastUpdateMachine.put(key, System.currentTimeMillis());
                NationsGUI.addPlayerSkill(player.field_71092_bJ, "engineer", 1);
            }
            ((GCTraderBlockEntity)tileEntity).machineValue = this.machineValue;
        }
    }
}

