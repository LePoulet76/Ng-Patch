package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenEnterpriseListPacket implements IPacket, IClientPacket {

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      Minecraft.func_71410_x().func_71373_a(new EnterpriseListGui());
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {}
}
