/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class EnterpriseBankSalaryPacket
implements IPacket,
IClientPacket {
    public String enterpriseName;
    public HashMap<String, Integer> salaries;

    public EnterpriseBankSalaryPacket(String enterpriseName, HashMap<String, Integer> salaries) {
        this.enterpriseName = enterpriseName;
        this.salaries = salaries;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterpriseName = data.readUTF();
        this.salaries = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Integer>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(new Gson().toJson(this.salaries));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankDataPacket(this.enterpriseName)));
    }
}

