/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  micdoodle8.mods.galacticraft.api.tile.IDisableableMachine
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SolarPanelActiveMachinePacket
implements IPacket,
IServerPacket {
    private int posX;
    private int posY;
    private int posZ;
    private boolean active;

    public SolarPanelActiveMachinePacket(int posX, int posY, int posZ, boolean active) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.active = active;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.active = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeBoolean(this.active);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        Plugin pl;
        TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ);
        if (tileEntity instanceof IDisableableMachine && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerInteractBlock", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                boolean res = (Boolean)m.invoke(pl, player.field_71092_bJ, this.posX, this.posY, this.posZ);
                if (res) {
                    IDisableableMachine machine;
                    machine.setDisabled(0, !(machine = (IDisableableMachine)tileEntity).getDisabled(0));
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

