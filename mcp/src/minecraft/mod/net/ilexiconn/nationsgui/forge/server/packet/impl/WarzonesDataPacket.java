package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket$3;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket$4;
import net.minecraft.entity.player.EntityPlayer;

public class WarzonesDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, String> bateauInfos = new HashMap();
    public HashMap<String, String> petrolInfos = new HashMap();
    public HashMap<String, String> mineInfos = new HashMap();
    public HashMap<String, String> scoreInfos = new HashMap();
    public int dollarsDailyLimit;
    public int maxPowerboost;
    public int maxSkillboost;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.bateauInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzonesDataPacket$1(this)).getType());
        this.petrolInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzonesDataPacket$2(this)).getType());
        this.mineInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzonesDataPacket$3(this)).getType());
        this.scoreInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzonesDataPacket$4(this)).getType());
        this.dollarsDailyLimit = data.readInt();
        this.maxPowerboost = data.readInt();
        this.maxSkillboost = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        WarzonesGui.bateauInfos = this.bateauInfos;
        WarzonesGui.petrolInfos = this.petrolInfos;
        WarzonesGui.mineInfos = this.mineInfos;
        WarzonesGui.scoreInfos = this.scoreInfos;
        WarzonesGui.dollarsDailyLimit = this.dollarsDailyLimit;
        WarzonesGui.maxPowerboost = this.maxPowerboost;
        WarzonesGui.maxSkillboost = this.maxSkillboost;
        WarzonesGui.loaded = true;
    }
}
