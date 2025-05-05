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

public class SolarPanelActiveMachinePacket implements IPacket, IServerPacket
{
    private int posX;
    private int posY;
    private int posZ;
    private boolean active;

    public SolarPanelActiveMachinePacket(int posX, int posY, int posZ, boolean active)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.active = active;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.active = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeBoolean(this.active);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TileEntity tileEntity = player.getEntityWorld().getBlockTileEntity(this.posX, this.posY, this.posZ);

        if (tileEntity instanceof IDisableableMachine)
        {
            Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

            if (pl != null)
            {
                try
                {
                    Method e = pl.getClass().getDeclaredMethod("canPlayerInteractBlock", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                    boolean res = ((Boolean)e.invoke(pl, new Object[] {player.username, Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ)})).booleanValue();

                    if (res)
                    {
                        IDisableableMachine machine = (IDisableableMachine)tileEntity;
                        machine.setDisabled(0, !machine.getDisabled(0));
                    }
                }
                catch (InvocationTargetException var7)
                {
                    var7.printStackTrace();
                }
            }
        }
    }
}
