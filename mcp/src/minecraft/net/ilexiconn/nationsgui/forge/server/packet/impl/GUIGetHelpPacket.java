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
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GUIGetHelpPacket
implements IPacket,
IClientPacket {
    private String GUIClass;
    private String GUIHelpDialog;
    private boolean forceOpenGUI;

    public GUIGetHelpPacket(String GUIClass) {
        this.GUIClass = GUIClass;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.GUIClass = data.readUTF();
        this.GUIHelpDialog = data.readUTF();
        this.forceOpenGUI = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.GUIClass);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        ClientData.GUIWithHelp.put(this.GUIClass, this.GUIHelpDialog);
        if (this.forceOpenGUI && !ClientProxy.clientConfig.openedHelp.contains(this.GUIHelpDialog)) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new SliderHelpGui(this.GUIHelpDialog, Minecraft.func_71410_x().field_71462_r));
            ClientProxy.clientConfig.openedHelp.add(this.GUIHelpDialog);
            try {
                ClientProxy.saveConfig();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

