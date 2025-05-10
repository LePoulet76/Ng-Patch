package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket$1;
import net.ilexiconn.nationsgui.forge.server.util.StringCompression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MarketPacket implements IPacket, IClientPacket
{
    private static List<MarketSale> marketSales = new ArrayList();
    private static NBTTagCompound itemRegistry = null;
    private boolean last;
    public String search;
    public String order;
    public int offset;
    public boolean mySalesOnly;
    public int totalResults;
    public boolean canRemoveAll;

    public MarketPacket(String search, String order, int offset, boolean mySalesOnly)
    {
        this.search = search;
        this.order = order;
        this.offset = offset;
        this.mySalesOnly = mySalesOnly;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        if (this.last)
        {
            GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;

            if (guiScreen instanceof MarketGui)
            {
                MarketGui marketGui = (MarketGui)guiScreen;
                MarketGui.totalResults = this.totalResults;
                MarketGui.canRemoveAll = this.canRemoveAll;
                boolean flag = marketGui.getMarketSales().isEmpty();
                marketGui.setMarketSales(marketSales);

                if (flag)
                {
                    marketGui.updateList();
                }

                marketGui.initGui();
            }

            marketSales.clear();
            itemRegistry = null;
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            this.totalResults = data.readInt();
            this.canRemoveAll = data.readBoolean();
            this.last = data.readBoolean();
            boolean e = data.readBoolean();
            NBTTagCompound nbtTagCompound = CompressedStreamTools.read(data);
            int size = data.readInt();
            byte[] bytes = new byte[size];
            data.readFully(bytes);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(StringCompression.decompress(bytes)).getAsJsonObject();
            List mS = (List)(new Gson()).fromJson(jsonObject.getAsJsonArray("sales"), (new MarketPacket$1(this)).getType());

            if (e)
            {
                marketSales.clear();
                itemRegistry = nbtTagCompound;
            }

            marketSales.addAll(mS);
            Iterator var9 = marketSales.iterator();

            while (var9.hasNext())
            {
                MarketSale marketSale = (MarketSale)var9.next();
                marketSale.setItemStack(ItemStack.loadItemStackFromNBT(itemRegistry.getCompoundTag(Integer.toString(marketSale.getItemstackId()))));
            }
        }
        catch (Exception var11)
        {
            var11.printStackTrace();
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.search);
        data.writeUTF(this.order);
        data.writeInt(this.offset);
        data.writeBoolean(this.mySalesOnly);
    }
}
