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
 *  fr.nationsglory.client.gui.FluidTankGUI
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
import fr.nationsglory.client.gui.FluidTankGUI;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class FluidTankInfosMachinePacket
implements IPacket,
IClientPacket {
    private ArrayList<String> enterprises;
    private boolean isInPetrolEnterprise;
    private String petrolEnterpriseFlag;
    private boolean canOpen;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterprises = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
        this.isInPetrolEnterprise = data.readBoolean();
        this.petrolEnterpriseFlag = data.readUTF();
        this.canOpen = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        if (this.canOpen) {
            FluidTankGUI.enterprises = this.enterprises;
            FluidTankGUI.isInPetrolEnterprise = this.isInPetrolEnterprise;
            FluidTankGUI.petrolEnterpriseFlag = this.petrolEnterpriseFlag;
            FluidTankGUI.loaded = true;
        } else {
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }
}

