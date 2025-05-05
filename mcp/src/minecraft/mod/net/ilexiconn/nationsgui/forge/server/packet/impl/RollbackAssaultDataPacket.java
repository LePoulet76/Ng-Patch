package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.RollbackAssaultGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RollbackAssaultDataPacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RollbackAssaultDataPacket implements IPacket, IClientPacket
{
    public String timeKey;
    public String enemyName;
    public HashMap<String, Object> rollbackInfos;

    public RollbackAssaultDataPacket(String timeKey, String enemyName)
    {
        this.timeKey = timeKey;
        this.enemyName = enemyName;
        System.out.println("debug: " + timeKey + " " + enemyName);
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.rollbackInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new RollbackAssaultDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.timeKey);
        data.writeUTF(this.enemyName);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new RollbackAssaultGUI());
        RollbackAssaultGUI.data = this.rollbackInfos;
        RollbackAssaultGUI.loaded = true;
    }
}
