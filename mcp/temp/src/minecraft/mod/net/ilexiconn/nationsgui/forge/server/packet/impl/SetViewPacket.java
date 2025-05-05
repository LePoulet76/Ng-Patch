package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SetViewPacket implements IClientPacket, IPacket {

   private EntityPlayer target;


   public SetViewPacket(EntityPlayer viewer, EntityPlayer target) {
      this.target = target;
   }

   public void fromBytes(ByteArrayDataInput data) {
      String targetUsername = data.readUTF();
      this.target = ClientHooks.getNearPlayerFromName(targetUsername);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target.field_71092_bJ);
   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.target != null) {
         ClientHooks.setEntityViewRenderer(this.target);
      }

   }
}
