package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CheckForbiddenShopCategoriesPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class CheckForbiddenShopCategoriesPacket implements IPacket, IClientPacket {

   private List<String> forbiddenCategories;


   public void handleClientPacket(EntityPlayer player) {
      ShopGUI.forbiddenCategories = this.forbiddenCategories;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.forbiddenCategories = (List)(new Gson()).fromJson(data.readUTF(), (new CheckForbiddenShopCategoriesPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}
}
