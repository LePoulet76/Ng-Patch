/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestNuclearPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;

public class FactionChestSaveDataPacket
implements IPacket,
IClientPacket,
IServerPacket {
    public HashMap<String, Object> logs;

    public FactionChestSaveDataPacket(HashMap<String, Object> logs) {
        this.logs = logs;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.logs = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.logs));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        String factionName = NationsGUI.getFactionNameFromId((String)this.logs.get("factionName"));
        System.out.println("LOG TRANSACTION CHEST " + factionName);
        System.out.println(this.logs.get("logs").toString());
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("FactionChest");
        boolean hasT4 = false;
        boolean hasRed = false;
        boolean hasFusee = false;
        boolean hasT5 = false;
        NBTTagList itemsTag = compound.func_74761_m((String)this.logs.get("factionName"));
        for (int i = 0; i < itemsTag.func_74745_c(); ++i) {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.func_74743_b(i);
            byte slot = itemTag.func_74771_c("Slot");
            ItemStack item = ItemStack.func_77949_a((NBTTagCompound)itemTag);
            if (item.field_77993_c == 19483 && item.func_77960_j() == 22) {
                hasT4 = true;
                continue;
            }
            if (item.field_77993_c == 19483 && item.func_77960_j() == 23) {
                hasRed = true;
                continue;
            }
            if (item.field_77993_c == 19483 && item.func_77960_j() == 32) {
                hasT5 = true;
                continue;
            }
            if (item.field_77993_c != 10111 && item.field_77993_c != 10162) continue;
            hasFusee = true;
        }
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionChestNuclearPacket((String)this.logs.get("factionName"), hasT4, hasRed, hasFusee, hasT5)), (Player)((Player)player));
    }
}

