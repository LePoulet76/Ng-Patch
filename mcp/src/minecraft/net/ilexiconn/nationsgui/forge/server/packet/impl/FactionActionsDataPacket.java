/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.util.StringCompression;
import net.minecraft.entity.player.EntityPlayer;

public class FactionActionsDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> actionsInfos = new HashMap();
    public String target;

    public FactionActionsDataPacket(String targetName) {
        this.target = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        int size = data.readInt();
        byte[] bytes = new byte[size];
        data.readFully(bytes);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = jsonParser.parse(StringCompression.decompress(bytes)).getAsJsonObject();
            this.actionsInfos = (HashMap)new Gson().fromJson((JsonElement)jsonObject.getAsJsonObject("result"), new TypeToken<HashMap<String, Object>>(){}.getType());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        System.out.println(this.actionsInfos);
        BankGUI.factionActionsInfos = this.actionsInfos;
    }
}

