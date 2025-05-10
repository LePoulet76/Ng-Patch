package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieWishGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDiplomatieWishDataPacket implements IPacket, IClientPacket
{
    private String targetName;
    public HashMap<String, ArrayList<HashMap<String, String>>> relationWishes = new HashMap();

    public FactionDiplomatieWishDataPacket(String targetName)
    {
        this.targetName = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.relationWishes = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionDiplomatieWishDataPacket$1(this)).getType());
        this.targetName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.relationWishes));
        data.writeUTF(this.targetName);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        DiplomatieWishGUI.loaded = true;
        DiplomatieWishGUI.diplomatieWishInfos = this.relationWishes;
    }
}
