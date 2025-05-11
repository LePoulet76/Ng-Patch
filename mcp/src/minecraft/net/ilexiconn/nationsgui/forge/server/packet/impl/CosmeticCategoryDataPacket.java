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
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class CosmeticCategoryDataPacket
implements IPacket,
IClientPacket {
    public LinkedHashMap<String, ArrayList<HashMap<String, String>>> data = new LinkedHashMap();
    private String categoryTarget;
    private String playerTarget;
    private int packetCounter = 0;

    public CosmeticCategoryDataPacket(String categoryTarget, String playerTarget) {
        this.categoryTarget = categoryTarget;
        this.playerTarget = playerTarget;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.categoryTarget = data.readUTF();
        this.data = (LinkedHashMap)new Gson().fromJson(data.readUTF(), new TypeToken<LinkedHashMap<String, ArrayList<HashMap<String, String>>>>(){}.getType());
        this.packetCounter = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.categoryTarget);
        data.writeUTF(this.playerTarget);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.packetCounter == 0 && CosmeticCategoryGUI.categoriesData.containsKey(this.categoryTarget)) {
            CosmeticCategoryGUI.categoriesData.get(this.categoryTarget).clear();
            CosmeticCategoryGUI.activeBadges.clear();
            CosmeticCategoryGUI.activeEmotes.clear();
            CosmeticCategoryGUI.cachedSelectedSkin = null;
        } else if (this.packetCounter == 0) {
            CosmeticCategoryGUI.categoriesData.put(this.categoryTarget, new LinkedHashMap());
        }
        CosmeticCategoryGUI.categoriesData.get(this.categoryTarget).putAll(this.data);
        if (this.categoryTarget.equals("emotes")) {
            for (Map.Entry<String, ArrayList<HashMap<String, String>>> entry : this.data.entrySet()) {
                HashMap<String, String> skinData = entry.getValue().get(0);
                if (!skinData.get("active").equals("1")) continue;
                CosmeticCategoryGUI.activeEmotes.add(skinData.get("skin_name"));
            }
        } else if (this.categoryTarget.equals("badges")) {
            for (Map.Entry<String, ArrayList<HashMap<String, String>>> entry : this.data.entrySet()) {
                HashMap<String, String> skinData = entry.getValue().get(0);
                if (!skinData.get("active").equals("1")) continue;
                CosmeticCategoryGUI.activeBadges.add(skinData.get("skin_name"));
            }
        }
        CosmeticCategoryGUI.loaded = true;
    }
}

