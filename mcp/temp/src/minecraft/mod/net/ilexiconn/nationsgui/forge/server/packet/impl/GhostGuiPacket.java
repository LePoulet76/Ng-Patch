package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.LinkedHashMap;
import net.ilexiconn.nationsgui.forge.client.gui.ghost.GhostGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GhostGuiPacket implements IPacket, IClientPacket {

   NBTTagCompound compound;


   public void fromBytes(ByteArrayDataInput data) {
      try {
         this.compound = CompressedStreamTools.func_74794_a(data);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      try {
         CompressedStreamTools.func_74800_a(this.compound, data);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      NBTTagList items = this.compound.func_74761_m("Items");
      ItemStack[] itemStacks = new ItemStack[15];

      for(int map = 0; map < items.func_74745_c(); ++map) {
         itemStacks[map] = ItemStack.func_77949_a((NBTTagCompound)items.func_74743_b(map));
      }

      LinkedHashMap var8 = new LinkedHashMap();
      NBTTagList playerRank = this.compound.func_74761_m("PlayerRanks");

      for(int i = 0; i < playerRank.func_74745_c(); ++i) {
         NBTTagCompound tagCompound = (NBTTagCompound)playerRank.func_74743_b(i);
         var8.put(tagCompound.func_74779_i("Pseudo"), Integer.valueOf(tagCompound.func_74762_e("Points")));
      }

      Minecraft.func_71410_x().func_71373_a(new GhostGUI(itemStacks, var8, this.compound.func_74762_e("Points")));
   }
}
