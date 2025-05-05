package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BonusDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BonusDataPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class BonusDataPacket implements IPacket, IClientPacket
{
    private HashMap<String, Float> bonuses = new HashMap();
    private Long bonusStartTime = Long.valueOf(0L);
    private Long bonusEndTime = Long.valueOf(0L);

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.bonuses = this.bonuses;
        ClientData.bonusStartTime = this.bonusStartTime;
        ClientData.bonusEndTime = this.bonusEndTime;

        if (this.bonusStartTime.longValue() != 0L && !ClientProxy.clientConfig.lastBonusStartTime.equals(this.bonusStartTime) && this.bonusStartTime.longValue() < System.currentTimeMillis() && this.bonusEndTime.longValue() > System.currentTimeMillis())
        {
            ClientProxy.clientConfig.lastBonusStartTime = this.bonusStartTime;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }

            (new Timer()).schedule(new BonusDataPacket$1(this), 2000L);
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.bonuses = (HashMap)(new Gson()).fromJson(data.readUTF(), (new BonusDataPacket$2(this)).getType());
        this.bonusStartTime = Long.valueOf(data.readLong());
        this.bonusEndTime = Long.valueOf(data.readLong());
    }

    public void toBytes(ByteArrayDataOutput data) {}
}
