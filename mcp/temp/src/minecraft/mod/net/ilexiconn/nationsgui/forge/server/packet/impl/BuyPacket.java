package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class BuyPacket implements IPacket, IClientPacket {

   private int category;
   private int item;
   private int amount;
   private String categoryName;
   private double newMoney;


   public BuyPacket(int category, int item, int amount, String categoryName) {
      this.category = category;
      this.item = item;
      this.amount = amount;
      this.categoryName = categoryName;
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.newMoney != ShopGUI.CURRENT_MONEY) {
         ShopGUI.CURRENT_MONEY = this.newMoney;
         Minecraft.func_71410_x().field_71416_A.func_77366_a("nationsgui:buy", 1.0F, 1.0F);
         ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a("nationsgui.shop.successful")));
      }

      ShopGUI.CAN_BUY = true;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.newMoney = data.readDouble();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.category);
      data.writeInt(this.item);
      data.writeInt(this.amount);
      data.writeUTF(this.categoryName);
   }
}
