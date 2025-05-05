package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.IncubatorGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RepairMachineGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ChestGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.imagehologram.ImageHologramGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.client.gui.url.URLGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.IncubatorBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.FactionChestContainer;
import net.ilexiconn.nationsgui.forge.server.container.IncubatorContainer;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer;
import net.ilexiconn.nationsgui.forge.server.inventory.FactionChestInventory;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public enum ServerGUIHandler implements IGuiHandler
{
    INSTANCE;
    public static HashMap<String, FactionChestInventory> chestInventories = new HashMap();

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == NationsGUI.CONFIG.incubatorID)
        {
            return new IncubatorContainer(player.inventory, (IncubatorBlockEntity)world.getBlockTileEntity(x, y, z));
        }
        else if (id == NationsGUI.CONFIG.repairMachineID)
        {
            return new RepairMachineContainer(player.inventory, (RepairMachineBlockEntity)world.getBlockTileEntity(x, y, z));
        }
        else if (id == 666)
        {
            FactionChestInventory chestInventory;

            if (chestInventories.containsKey(RemoteOpenFactionChestPacket.playersTargetGui.get(player.getDisplayName())))
            {
                chestInventory = (FactionChestInventory)chestInventories.get(RemoteOpenFactionChestPacket.playersTargetGui.get(player.getDisplayName()));
            }
            else
            {
                chestInventory = new FactionChestInventory((String)RemoteOpenFactionChestPacket.playersTargetGui.get(player.getDisplayName()), ((Integer)RemoteOpenFactionChestPacket.playersChestLevel.get(player.getDisplayName())).intValue());
                chestInventories.put(RemoteOpenFactionChestPacket.playersTargetGui.get(player.getDisplayName()), chestInventory);
            }

            return new FactionChestContainer(player.inventory, chestInventory, ((Boolean)RemoteOpenFactionChestPacket.playersCanTake.get(player.getDisplayName())).booleanValue(), ((Boolean)RemoteOpenFactionChestPacket.playersCanDeposit.get(player.getDisplayName())).booleanValue(), (String)RemoteOpenFactionChestPacket.playersTargetGui.get(player.getDisplayName()));
        }
        else if (id == 777)
        {
            return new ContainerTrade(player);
        }
        else
        {
            System.out.println(player.username + "a tenter d\'ouvrir un container qui n\'existe pas");
            return null;
        }
    }

    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == NationsGUI.CONFIG.incubatorID)
        {
            return new IncubatorGUI(new IncubatorContainer(player.inventory, (IncubatorBlockEntity)world.getBlockTileEntity(x, y, z)));
        }
        else if (id == NationsGUI.CONFIG.repairMachineID)
        {
            return new RepairMachineGUI(new RepairMachineContainer(player.inventory, (RepairMachineBlockEntity)world.getBlockTileEntity(x, y, z)));
        }
        else if (id == 666)
        {
            return new ChestGui(player.inventory, new FactionChestInventory((String)FactionGUI.factionInfos.get("id"), Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel"))));
        }
        else if (id == 777)
        {
            return new GuiTrade((Container)this.getServerGuiElement(id, player, world, x, y, z));
        }
        else if (id == 888)
        {
            return new RadioGUI((RadioBlockEntity)world.getBlockTileEntity(x, y, z));
        }
        else if (id == 889)
        {
            return new URLGUI(world.getBlockTileEntity(x, y, z));
        }
        else if (id == 890)
        {
            return new ImageHologramGUI((ImageHologramBlockEntity)world.getBlockTileEntity(x, y, z));
        }
        else
        {
            System.out.println(player.username + "a tenter d\'ouvrir un container qui n\'existe pas");
            return null;
        }
    }
}
