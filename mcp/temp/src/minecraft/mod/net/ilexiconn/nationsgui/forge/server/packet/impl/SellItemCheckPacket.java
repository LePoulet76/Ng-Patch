package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class SellItemCheckPacket implements IPacket, IClientPacket {

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {}

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      InventoryPlayer inventoryPlayer = player.field_71071_by;
      ItemStack itemStack = inventoryPlayer.field_70462_a[inventoryPlayer.field_70461_c];
      if(itemStack != null) {
         Minecraft.func_71410_x().func_71373_a(new SellItemGUI(itemStack, inventoryPlayer.field_70461_c));
      }

   }
}
