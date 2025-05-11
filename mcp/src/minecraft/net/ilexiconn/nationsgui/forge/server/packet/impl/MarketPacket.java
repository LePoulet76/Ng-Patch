/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.util.StringCompression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MarketPacket
implements IPacket,
IClientPacket {
    private static List<MarketSale> marketSales = new ArrayList<MarketSale>();
    private static NBTTagCompound itemRegistry = null;
    private boolean last;
    public String search;
    public String order;
    public int offset;
    public boolean mySalesOnly;
    public int totalResults;
    public boolean canRemoveAll;

    public MarketPacket(String search, String order, int offset, boolean mySalesOnly) {
        this.search = search;
        this.order = order;
        this.offset = offset;
        this.mySalesOnly = mySalesOnly;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        if (this.last) {
            GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
            if (guiScreen instanceof MarketGui) {
                MarketGui marketGui = (MarketGui)guiScreen;
                MarketGui.totalResults = this.totalResults;
                MarketGui.canRemoveAll = this.canRemoveAll;
                boolean flag = marketGui.getMarketSales().isEmpty();
                marketGui.setMarketSales(marketSales);
                if (flag) {
                    marketGui.updateList();
                }
                marketGui.func_73866_w_();
            }
            marketSales.clear();
            itemRegistry = null;
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.totalResults = data.readInt();
            this.canRemoveAll = data.readBoolean();
            this.last = data.readBoolean();
            boolean first = data.readBoolean();
            NBTTagCompound nbtTagCompound = CompressedStreamTools.func_74794_a((DataInput)data);
            int size = data.readInt();
            byte[] bytes = new byte[size];
            data.readFully(bytes);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(StringCompression.decompress(bytes)).getAsJsonObject();
            List mS = (List)new Gson().fromJson((JsonElement)jsonObject.getAsJsonArray("sales"), new TypeToken<List<MarketSale>>(){}.getType());
            if (first) {
                marketSales.clear();
                itemRegistry = nbtTagCompound;
            }
            marketSales.addAll(mS);
            for (MarketSale marketSale : marketSales) {
                marketSale.setItemStack(ItemStack.func_77949_a((NBTTagCompound)itemRegistry.func_74775_l(Integer.toString(marketSale.getItemstackId()))));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.search);
        data.writeUTF(this.order);
        data.writeInt(this.offset);
        data.writeBoolean(this.mySalesOnly);
    }
}

