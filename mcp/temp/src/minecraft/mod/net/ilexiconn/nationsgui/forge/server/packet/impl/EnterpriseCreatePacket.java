package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseCreatePacket implements IPacket, IClientPacket {

   public String enterpriseName;
   public String type;
   public String errorMessage;
   public int price;


   public EnterpriseCreatePacket(String enterpriseName, String type, int price) {
      this.enterpriseName = enterpriseName;
      this.type = type;
      this.price = price;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.errorMessage = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF(this.type);
      data.writeInt(this.price);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.errorMessage != null && !this.errorMessage.isEmpty()) {
         EnterpriseCreateGui.errorMessage = this.errorMessage;
      } else {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }
}
