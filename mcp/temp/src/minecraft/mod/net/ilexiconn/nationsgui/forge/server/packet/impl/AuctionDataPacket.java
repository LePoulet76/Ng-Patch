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
import net.ilexiconn.nationsgui.forge.client.data.Auction;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.util.StringCompression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class AuctionDataPacket implements IPacket, IClientPacket {

   private static List<Auction> auctions = new ArrayList();
   private boolean last;
   public String search;
   public String order;
   public int offset;
   public boolean myAuctionsOnly;
   public int totalResults;
   public boolean canRemoveAll;


   public AuctionDataPacket(String search, String order, int offset, boolean mySalesOnly) {
      this.search = search;
      this.order = order;
      this.offset = offset;
      this.myAuctionsOnly = mySalesOnly;
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.last) {
         GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
         if(guiScreen instanceof AuctionGui) {
            AuctionGui auctionGui = (AuctionGui)guiScreen;
            AuctionGui.totalResults = this.totalResults;
            AuctionGui.canRemoveAll = this.canRemoveAll;
            boolean flag = auctionGui.getAuctions().isEmpty();
            auctionGui.setAuctions(auctions);
            if(flag) {
               auctionGui.updateList();
            }

            auctionGui.func_73866_w_();
         }

         auctions.clear();
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         this.totalResults = data.readInt();
         this.canRemoveAll = data.readBoolean();
         this.last = data.readBoolean();
         boolean e = data.readBoolean();
         NBTTagCompound nbtTagCompound = CompressedStreamTools.func_74794_a(data);
         int size = data.readInt();
         byte[] bytes = new byte[size];
         data.readFully(bytes);
         JsonParser jsonParser = new JsonParser();
         JsonObject jsonObject = jsonParser.parse(StringCompression.decompress(bytes)).getAsJsonObject();
         List mS = (List)(new Gson()).fromJson(jsonObject.getAsJsonArray("sales"), (new AuctionDataPacket$1(this)).getType());
         if(e) {
            auctions.clear();
         }

         auctions.addAll(mS);
         Iterator var9 = auctions.iterator();

         while(var9.hasNext()) {
            Auction auction = (Auction)var9.next();
            auction.setItemStack();
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.search);
      data.writeUTF(this.order);
      data.writeInt(this.offset);
      data.writeBoolean(this.myAuctionsOnly);
   }

}
