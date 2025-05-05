package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AssistanceUpdateAdminState implements IPacket, IClientPacket {

   private boolean state = false;
   NBTTagList list = new NBTTagList();


   public AssistanceUpdateAdminState(boolean state) {
      this.state = state;
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      for(int i = 0; i < this.list.func_74745_c(); ++i) {
         NBTTagCompound tag = (NBTTagCompound)this.list.func_74743_b(i);
         ClientProxy.playersInAdminMode.put(tag.func_74779_i("pseudo"), Boolean.valueOf(tag.func_74767_n("state")));
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         NBTTagCompound e = CompressedStreamTools.func_74794_a(data);
         this.list = e.func_74761_m("playerStates");
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeBoolean(this.state);
   }
}
