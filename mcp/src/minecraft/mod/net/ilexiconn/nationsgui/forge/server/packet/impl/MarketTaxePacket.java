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

public class MarketTaxePacket implements IPacket, IClientPacket
{
    private int taxe;
    private int pubPrice;

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;

        if (guiScreen != null && guiScreen instanceof SellItemGUI)
        {
            SellItemGUI sellItemGUI = (SellItemGUI)guiScreen;
            sellItemGUI.setTaxe(this.taxe);
            sellItemGUI.setPubPrice(this.pubPrice);
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.taxe = data.readInt();
        this.pubPrice = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data) {}
}
