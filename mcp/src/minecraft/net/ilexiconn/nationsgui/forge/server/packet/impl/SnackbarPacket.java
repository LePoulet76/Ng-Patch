package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class SnackbarPacket implements IPacket, IClientPacket
{
    private String message;
    private String[] extra;

    public SnackbarPacket(String message, String ... extra)
    {
        this.message = message;
        this.extra = extra;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.translateToLocalFormatted(this.message.replace("<player>", Minecraft.getMinecraft().thePlayer.getTranslatedEntityName()), (Object[])this.extra)));
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.message = data.readUTF();
        int extra = data.readInt();
        this.extra = new String[extra];

        for (int i = 0; i < extra; ++i)
        {
            this.extra[i] = data.readUTF();
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.message);
        data.writeInt(this.extra.length);
        String[] var2 = this.extra;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            String extra = var2[var4];
            data.writeUTF(extra);
        }
    }
}
