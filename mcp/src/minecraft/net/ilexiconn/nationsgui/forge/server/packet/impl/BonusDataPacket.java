/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.BonusesGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class BonusDataPacket
implements IPacket,
IClientPacket {
    private HashMap<String, Float> bonuses = new HashMap();
    private Long bonusStartTime = 0L;
    private Long bonusEndTime = 0L;

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        ClientData.bonuses = this.bonuses;
        ClientData.bonusStartTime = this.bonusStartTime;
        ClientData.bonusEndTime = this.bonusEndTime;
        if (this.bonusStartTime != 0L && !ClientProxy.clientConfig.lastBonusStartTime.equals(this.bonusStartTime) && this.bonusStartTime < System.currentTimeMillis() && this.bonusEndTime > System.currentTimeMillis()) {
            ClientProxy.clientConfig.lastBonusStartTime = this.bonusStartTime;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            new Timer().schedule(new TimerTask(){

                @Override
                public void run() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new BonusesGui());
                }
            }, 2000L);
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.bonuses = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Float>>(){}.getType());
        this.bonusStartTime = data.readLong();
        this.bonusEndTime = data.readLong();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

