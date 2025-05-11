/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SkinPacket
implements IPacket,
IClientPacket {
    private String pseudo;
    private List<String> activesSkins;

    public SkinPacket(String pseudo, List<String> activesSkins) {
        this.pseudo = pseudo;
        this.activesSkins = activesSkins;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            NBTTagCompound tagCompound = CompressedStreamTools.func_74794_a((DataInput)data);
            this.pseudo = tagCompound.func_74779_i("pseudo");
            this.activesSkins = new ArrayList<String>();
            NBTTagList tagList = tagCompound.func_74761_m("activeSkins");
            for (int i = 0; i < tagList.func_74745_c(); ++i) {
                this.activesSkins.add(((NBTTagCompound)tagList.func_74743_b(i)).func_74779_i("skinID"));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.func_74778_a("pseudo", this.pseudo);
        NBTTagList tagList = new NBTTagList();
        for (String activeSkin : this.activesSkins) {
            NBTTagCompound obj = new NBTTagCompound();
            obj.func_74778_a("skinID", activeSkin);
            tagList.func_74742_a((NBTBase)obj);
        }
        tagCompound.func_74782_a("activeSkins", (NBTBase)tagList);
        try {
            CompressedStreamTools.func_74800_a((NBTTagCompound)tagCompound, (DataOutput)data);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.SKIN_MANAGER.setPlayerSkins(this.pseudo, this.activesSkins);
    }
}

