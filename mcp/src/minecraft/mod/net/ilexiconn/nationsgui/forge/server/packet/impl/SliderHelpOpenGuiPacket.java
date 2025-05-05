package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class SliderHelpOpenGuiPacket implements IPacket, IClientPacket
{
    private String identifier;

    public SliderHelpOpenGuiPacket(String identifier)
    {
        this.identifier = identifier;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.identifier = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new SliderHelpGui(this.identifier, (GuiScreen)null));
    }
}
