package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestNuclearPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestSaveDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FactionChestSaveDataPacket implements IPacket, IClientPacket, IServerPacket {

   public HashMap<String, Object> logs;


   public FactionChestSaveDataPacket(HashMap<String, Object> logs) {
      this.logs = logs;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.logs = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionChestSaveDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.logs));
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }

   public void handleServerPacket(EntityPlayer player) {
      String factionName = NationsGUI.getFactionNameFromId((String)this.logs.get("factionName"));
      System.out.println("LOG TRANSACTION CHEST " + factionName);
      System.out.println(this.logs.get("logs").toString());
      NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("FactionChest");
      boolean hasT4 = false;
      boolean hasRed = false;
      boolean hasFusee = false;
      boolean hasT5 = false;
      NBTTagList itemsTag = compound.func_74761_m((String)this.logs.get("factionName"));

      for(int i = 0; i < itemsTag.func_74745_c(); ++i) {
         NBTTagCompound itemTag = (NBTTagCompound)itemsTag.func_74743_b(i);
         byte slot = itemTag.func_74771_c("Slot");
         ItemStack item = ItemStack.func_77949_a(itemTag);
         if(item.field_77993_c == 19483 && item.func_77960_j() == 22) {
            hasT4 = true;
         } else if(item.field_77993_c == 19483 && item.func_77960_j() == 23) {
            hasRed = true;
         } else if(item.field_77993_c == 19483 && item.func_77960_j() == 32) {
            hasT5 = true;
         } else if(item.field_77993_c == 10111 || item.field_77993_c == 10162) {
            hasFusee = true;
         }
      }

      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new FactionChestNuclearPacket((String)this.logs.get("factionName"), hasT4, hasRed, hasFusee, hasT5)), (Player)player);
   }
}
