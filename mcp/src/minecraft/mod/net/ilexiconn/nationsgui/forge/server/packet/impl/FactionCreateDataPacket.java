package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionCreateDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> countries = new ArrayList();
    public String serverName;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.serverName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        try
        {
            Type e = (new FactionCreateDataPacket$1(this)).getType();

            if (this.serverName.equals("dev"))
            {
                this.countries = (ArrayList)(new Gson()).fromJson("[{\"name\":\"AfriqueDuSud\",\"x\":\"100\",\"z\":\"2250\"},{\"name\":\"Altai\",\"x\":\"2122\",\"z\":\"-2692\"},{\"name\":\"Crete\",\"x\":\"-81\",\"z\":\"-1574\"},{\"name\":\"Guyana\",\"x\":\"-4320\",\"z\":\"120\"},{\"name\":\"Koweit\",\"x\":\"1033\",\"z\":\"-1224\"},{\"name\":\"Maroc\",\"x\":\"-1530\",\"z\":\"-1420\"},{\"name\":\"Tchad\",\"x\":\"-290\",\"z\":\"-370\"},{\"name\":\"TerreBooth\",\"x\":\"2100\",\"z\":\"4580\"},{\"name\":\"TerreLiard\",\"x\":\"820\",\"z\":\"4320\"},{\"name\":\"Tunisie\",\"x\":\"-830\",\"z\":\"-1560\"}]", e);
            }
            else
            {
                this.countries = (ArrayList)(new Gson()).fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/mods/country_list/" + this.serverName)).openStream(), Charsets.UTF_8), e);
            }
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }

        FactionCreateGUI.availableCountries = this.countries;

        if (this.countries != null && this.countries.size() > 0)
        {
            FactionCreateGUI.selectedCountry = (HashMap)this.countries.get(0);
        }

        FactionCreateGUI.loaded = true;
    }
}
