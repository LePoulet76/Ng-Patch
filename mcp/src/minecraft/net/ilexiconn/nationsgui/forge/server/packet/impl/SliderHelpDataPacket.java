/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SliderHelpDataPacket
implements IPacket,
IClientPacket {
    private String identifier;
    private ArrayList<String> images = new ArrayList();
    private String wikiURL;

    public SliderHelpDataPacket(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.identifier = data.readUTF();
        this.images = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
        this.wikiURL = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.identifier);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.images.isEmpty()) {
            Minecraft.func_71410_x().func_71373_a(null);
            return;
        }
        SliderHelpGui.images = this.images;
        SliderHelpGui.wikiURL = this.wikiURL;
        ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G434N.mp3", 1.0f);
        for (String image : this.images) {
            ClientProxy.getRemoteResource(image);
        }
    }
}

