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

public class RemoteMarketSellPacket implements IPacket, IClientPacket {

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      InventoryPlayer inventoryPlayer = Minecraft.func_71410_x().field_71439_g.field_71071_by;
      ItemStack itemStack = inventoryPlayer.field_70462_a[inventoryPlayer.field_70461_c];
      if(itemStack != null) {
         Minecraft.func_71410_x().func_71373_a(new SellItemGUI(itemStack, inventoryPlayer.field_70461_c));
      }

   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {}
}
