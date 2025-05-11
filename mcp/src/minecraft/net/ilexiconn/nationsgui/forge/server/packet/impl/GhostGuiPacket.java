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
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashMap;
import net.ilexiconn.nationsgui.forge.client.gui.ghost.GhostGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GhostGuiPacket
implements IPacket,
IClientPacket {
    NBTTagCompound compound;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.compound = CompressedStreamTools.func_74794_a((DataInput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        try {
            CompressedStreamTools.func_74800_a((NBTTagCompound)this.compound, (DataOutput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        NBTTagList items = this.compound.func_74761_m("Items");
        ItemStack[] itemStacks = new ItemStack[15];
        for (int i = 0; i < items.func_74745_c(); ++i) {
            itemStacks[i] = ItemStack.func_77949_a((NBTTagCompound)((NBTTagCompound)items.func_74743_b(i)));
        }
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        NBTTagList playerRank = this.compound.func_74761_m("PlayerRanks");
        for (int i = 0; i < playerRank.func_74745_c(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound)playerRank.func_74743_b(i);
            map.put(tagCompound.func_74779_i("Pseudo"), tagCompound.func_74762_e("Points"));
        }
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new GhostGUI(itemStacks, map, this.compound.func_74762_e("Points")));
    }
}

