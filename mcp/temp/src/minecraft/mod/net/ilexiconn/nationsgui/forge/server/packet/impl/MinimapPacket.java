package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MinimapPacket implements IPacket, IClientPacket {

   private byte[] colors;


   public MinimapPacket(byte[] colors) {
      this.colors = colors;
   }

   public MinimapPacket() {}

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      ClientData.minimapColors = this.colors;
   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         NBTTagCompound e = CompressedStreamTools.func_74794_a(data);
         this.colors = e.func_74770_j("colors");
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();

      try {
         nbtTagCompound.func_74773_a("colors", this.colors);
         CompressedStreamTools.func_74800_a(nbtTagCompound, data);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }
}
