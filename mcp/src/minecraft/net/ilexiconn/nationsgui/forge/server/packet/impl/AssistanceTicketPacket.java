/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class AssistanceTicketPacket
implements IPacket,
IClientPacket {
    private NBTTagCompound tagCompound = null;
    private int id;

    public AssistanceTicketPacket(int id) {
        this.id = id;
    }

    public AssistanceTicketPacket() {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        AssistanceTicketButton.locked = false;
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new AssistanceTicketGUI(this.tagCompound));
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.tagCompound = CompressedStreamTools.func_74794_a((DataInput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.id);
    }
}

