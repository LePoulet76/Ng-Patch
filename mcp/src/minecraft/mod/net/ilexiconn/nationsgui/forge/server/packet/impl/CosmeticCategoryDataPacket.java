package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticCategoryDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class CosmeticCategoryDataPacket implements IPacket, IClientPacket
{
    public LinkedHashMap<String, ArrayList<HashMap<String, String>>> data = new LinkedHashMap();
    private String categoryTarget;
    private String playerTarget;
    private int packetCounter = 0;

    public CosmeticCategoryDataPacket(String categoryTarget, String playerTarget)
    {
        this.categoryTarget = categoryTarget;
        this.playerTarget = playerTarget;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.categoryTarget = data.readUTF();
        this.data = (LinkedHashMap)(new Gson()).fromJson(data.readUTF(), (new CosmeticCategoryDataPacket$1(this)).getType());
        this.packetCounter = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.categoryTarget);
        data.writeUTF(this.playerTarget);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (this.packetCounter == 0 && CosmeticCategoryGUI.categoriesData.containsKey(this.categoryTarget))
        {
            ((LinkedHashMap)CosmeticCategoryGUI.categoriesData.get(this.categoryTarget)).clear();
            CosmeticCategoryGUI.activeBadges.clear();
            CosmeticCategoryGUI.activeEmotes.clear();
            CosmeticCategoryGUI.cachedSelectedSkin = null;
        }
        else if (this.packetCounter == 0)
        {
            CosmeticCategoryGUI.categoriesData.put(this.categoryTarget, new LinkedHashMap());
        }

        ((LinkedHashMap)CosmeticCategoryGUI.categoriesData.get(this.categoryTarget)).putAll(this.data);
        Iterator var2;
        Entry entry;
        HashMap skinData;

        if (this.categoryTarget.equals("emotes"))
        {
            var2 = this.data.entrySet().iterator();

            while (var2.hasNext())
            {
                entry = (Entry)var2.next();
                skinData = (HashMap)((ArrayList)entry.getValue()).get(0);

                if (((String)skinData.get("active")).equals("1"))
                {
                    CosmeticCategoryGUI.activeEmotes.add(skinData.get("skin_name"));
                }
            }
        }
        else if (this.categoryTarget.equals("badges"))
        {
            var2 = this.data.entrySet().iterator();

            while (var2.hasNext())
            {
                entry = (Entry)var2.next();
                skinData = (HashMap)((ArrayList)entry.getValue()).get(0);

                if (((String)skinData.get("active")).equals("1"))
                {
                    CosmeticCategoryGUI.activeBadges.add(skinData.get("skin_name"));
                }
            }
        }

        CosmeticCategoryGUI.loaded = true;
    }
}
