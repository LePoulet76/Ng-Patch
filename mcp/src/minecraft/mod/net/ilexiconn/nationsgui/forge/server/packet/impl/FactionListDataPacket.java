package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionListDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> countries = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.countries = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionListDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        FactionListGUI.countriesData.addAll(this.countries);

        if (!FactionListGUI.loaded && this.countries.size() > 0)
        {
            FactionListGUI.selectedCountry = (HashMap)this.countries.get(0);
        }

        FactionListGUI.loaded = true;
    }
}
