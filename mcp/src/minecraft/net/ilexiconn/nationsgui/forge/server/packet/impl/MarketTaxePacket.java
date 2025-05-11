/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class MarketTaxePacket
implements IPacket,
IClientPacket {
    private int taxe;
    private int pubPrice;

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
        if (guiScreen != null && guiScreen instanceof SellItemGUI) {
            SellItemGUI sellItemGUI = (SellItemGUI)guiScreen;
            sellItemGUI.setTaxe(this.taxe);
            sellItemGUI.setPubPrice(this.pubPrice);
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.taxe = data.readInt();
        this.pubPrice = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

