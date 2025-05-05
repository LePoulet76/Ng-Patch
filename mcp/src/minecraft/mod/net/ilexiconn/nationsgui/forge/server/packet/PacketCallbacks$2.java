package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.minecraft.entity.player.EntityPlayer;

enum PacketCallbacks$2
{
    public void handleCallback(EntityPlayer player, ByteArrayDataInput data)
    {
        ShopGUI.CURRENT_MONEY = data.readDouble();
    }
}
