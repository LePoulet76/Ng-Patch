package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONDrop;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class DropMessagePacket implements IPacket, IClientPacket {

   private int blockID;
   private int dropID;


   public DropMessagePacket(int blockID, int dropID) {
      this.blockID = blockID;
      this.dropID = dropID;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.blockID = data.readInt();
      this.dropID = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.blockID);
      data.writeInt(this.dropID);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      JSONBlock block = (JSONBlock)Block.field_71973_m[this.blockID];
      JSONDrop drop = (JSONDrop)block.drops.get(this.dropID);
      if(!drop.message.containsKey("en_US")) {
         drop.message.put("en_US", (new ArrayList(drop.message.values())).get(0));
      }

      if(!drop.message.containsKey(FMLCommonHandler.instance().getCurrentLanguage())) {
         drop.message.put(FMLCommonHandler.instance().getCurrentLanguage(), drop.message.get("en_US"));
      }

      player.func_71035_c(((String)drop.message.get(FMLCommonHandler.instance().getCurrentLanguage())).replace('&', '\u00a7'));
   }
}
