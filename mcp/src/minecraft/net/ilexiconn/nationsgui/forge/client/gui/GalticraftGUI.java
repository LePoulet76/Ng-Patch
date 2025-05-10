package net.ilexiconn.nationsgui.forge.client.gui;

import micdoodle8.mods.galacticraft.core.client.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.client.gui.GCCoreGuiExtendedInventory;
import net.minecraft.entity.player.EntityPlayer;

public class GalticraftGUI extends GCCoreGuiExtendedInventory
{
    public GalticraftGUI(EntityPlayer entityPlayer)
    {
        super(entityPlayer, ClientProxyCore.dummyInventory);
    }
}
