/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.core.client.ClientProxyCore
 *  micdoodle8.mods.galacticraft.core.client.gui.GCCoreGuiExtendedInventory
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import micdoodle8.mods.galacticraft.core.client.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.client.gui.GCCoreGuiExtendedInventory;
import net.minecraft.entity.player.EntityPlayer;

public class GalticraftGUI
extends GCCoreGuiExtendedInventory {
    public GalticraftGUI(EntityPlayer entityPlayer) {
        super(entityPlayer, ClientProxyCore.dummyInventory);
    }
}

