/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieWishGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDiplomatieWishDataPacket
implements IPacket,
IClientPacket {
    private String targetName;
    public HashMap<String, ArrayList<HashMap<String, String>>> relationWishes = new HashMap();

    public FactionDiplomatieWishDataPacket(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.relationWishes = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, ArrayList<HashMap<String, String>>>>(){}.getType());
        this.targetName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.relationWishes));
        data.writeUTF(this.targetName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        DiplomatieWishGUI.loaded = true;
        DiplomatieWishGUI.diplomatieWishInfos = this.relationWishes;
    }
}

