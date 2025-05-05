package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientPotions;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class UpdatePotionsPacket implements IPacket, IClientPacket {

   public int entityID;
   public int potionID;
   public boolean isAdded;


   public UpdatePotionsPacket() {}

   public UpdatePotionsPacket(int entityID, int potion, boolean isAdded) {
      this.entityID = entityID;
      this.potionID = potion;
      this.isAdded = isAdded;
   }

   public void handleClientPacket(EntityPlayer player) {
      Entity entity = player.field_70170_p.func_73045_a(this.entityID);
      if(entity instanceof EntityLivingBase) {
         EntityLivingBase living = (EntityLivingBase)entity;
         if(this.isAdded) {
            ClientPotions.addPotionToEntity(living, Potion.field_76425_a[this.potionID]);
         } else {
            ClientPotions.removePotionFromEntity(living, Potion.field_76425_a[this.potionID]);
         }
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      this.entityID = data.readInt();
      this.potionID = data.readInt();
      this.isAdded = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.entityID);
      data.writeInt(this.potionID);
      data.writeBoolean(this.isAdded);
   }
}
