package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetViewPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;

public class ViewCommand$Manager implements ITickHandler
{
    public Map<String, String> currentViews = new HashMap();

    public ViewCommand$Manager()
    {
        System.out.println("Register");
        TickRegistry.registerTickHandler(this, Side.SERVER);
    }

    public boolean isViewer(EntityPlayerMP viewer)
    {
        return this.currentViews.containsKey(viewer.getUniqueID().toString());
    }

    public void startView(EntityPlayerMP viewer, EntityPlayerMP target)
    {
        this.currentViews.put(viewer.getUniqueID().toString(), target.getUniqueID().toString());
        viewer.setPositionAndRotation(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
        viewer.capabilities.isFlying = true;
        viewer.setGameType(EnumGameType.CREATIVE);
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new SetViewPacket(viewer, target)), (Player)viewer);
    }

    public void stopView(EntityPlayerMP viewer)
    {
        this.currentViews.remove(viewer.getUniqueID().toString());
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new SetViewPacket(viewer, viewer)), (Player)viewer);
    }

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {}

    public void tickEnd(EnumSet<TickType> type, Object ... tickData)
    {
        Iterator var3 = this.currentViews.keySet().iterator();

        while (var3.hasNext())
        {
            String player = (String)var3.next();
            Iterator var5 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

            while (var5.hasNext())
            {
                EntityPlayerMP viewer = (EntityPlayerMP)var5.next();

                if (viewer.getUniqueID().toString().equalsIgnoreCase(player))
                {
                    Iterator var7 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

                    while (var7.hasNext())
                    {
                        EntityPlayerMP target = (EntityPlayerMP)var7.next();

                        if (target.getUniqueID().toString().equals(this.currentViews.get(player)))
                        {
                            if (!viewer.isSneaking() && target.isEntityAlive() && viewer.getEntityWorld().provider.dimensionId == target.getEntityWorld().provider.dimensionId)
                            {
                                viewer.setPositionAndRotation(target.posX, target.posY + 15.0D, target.posZ, target.rotationYaw, target.rotationPitch);
                            }
                            else
                            {
                                this.stopView(viewer);
                            }
                        }
                    }
                }
            }
        }
    }

    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    public String getLabel()
    {
        return "SpectateViewTickHandler";
    }
}
