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
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.achievements.AchievementsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetAchievementsDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class AchievementsDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> achievements = new ArrayList();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.achievements = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.achievements));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (ClientData.getAchievements() != null) {
            ClientData.getAchievements().clear();
        }
        if (this.achievements != null) {
            ClientData.setAchievements(this.achievements);
            AchievementsGUI.loaded = true;
            SetAchievementsDataPacket packet = new SetAchievementsDataPacket();
            packet.achievements = this.achievements;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(packet));
        }
    }
}

