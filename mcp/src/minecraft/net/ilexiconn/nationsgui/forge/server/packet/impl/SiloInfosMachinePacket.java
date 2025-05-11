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
 *  fr.nationsglory.client.gui.SiloGUI
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.client.gui.SiloGUI;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SiloInfosMachinePacket
implements IPacket,
IClientPacket {
    private HashMap<String, Double> cerealsPrice;
    private boolean isInFarmingEnterprise;
    private String farmingEnterpriseFlag;
    private boolean canOpen;
    private int posX;
    private int posY;
    private int posZ;

    public SiloInfosMachinePacket(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.cerealsPrice = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Double>>(){}.getType());
        this.isInFarmingEnterprise = data.readBoolean();
        this.farmingEnterpriseFlag = data.readUTF();
        this.canOpen = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        if (this.canOpen) {
            SiloGUI.cerealsPrice = this.cerealsPrice;
            SiloGUI.isInFarmingEnterprise = this.isInFarmingEnterprise;
            SiloGUI.farmingEnterpriseFlag = this.farmingEnterpriseFlag;
            SiloGUI.loaded = true;
        } else {
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }
}

