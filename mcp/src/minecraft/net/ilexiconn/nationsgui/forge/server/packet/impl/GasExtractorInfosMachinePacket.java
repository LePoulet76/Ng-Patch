/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.edora.client.gui.GCEdoraGuiGasExtractor
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.edora.client.gui.GCEdoraGuiGasExtractor;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GasExtractorInfosMachinePacket
implements IPacket,
IClientPacket {
    private int posX;
    private int posZ;
    private String zone;
    private float gasPercent;
    private boolean canOpen;

    public GasExtractorInfosMachinePacket(int posX, int posZ) {
        this.posX = posX;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.zone = data.readUTF();
        this.gasPercent = data.readFloat();
        this.canOpen = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.posX);
        data.writeInt(this.posZ);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        if (this.canOpen) {
            GCEdoraGuiGasExtractor.zoneName = this.zone;
            GCEdoraGuiGasExtractor.zonePercent = this.gasPercent;
        } else {
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }
}

