/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  fr.nationsglory.nationsmap.overlay.OverlayClaim
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.nationsglory.nationsmap.overlay.OverlayClaim;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshClaimMapPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> claimedChunks;
    public boolean remove;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.claimedChunks = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.remove = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.claimedChunks));
        data.writeBoolean(this.remove);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.remove) {
            OverlayClaim.claimedChunks.putAll(this.claimedChunks);
        } else {
            Iterator<Map.Entry<String, String>> it = this.claimedChunks.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pair = it.next();
                OverlayClaim.claimedChunks.remove(pair.getKey());
                it.remove();
            }
        }
    }
}

