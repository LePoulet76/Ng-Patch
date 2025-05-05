package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseContractCreatePacket implements IPacket {

   public String enterpriseName;
   public String content;
   public Integer price;
   public Long deadlineTime;


   public EnterpriseContractCreatePacket(String enterpriseName, String content, Integer price, Long deadlineTime) {
      this.enterpriseName = enterpriseName;
      this.content = content;
      this.price = price;
      this.deadlineTime = deadlineTime;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF(this.content);
      data.writeInt(this.price.intValue());
      data.writeLong(this.deadlineTime.longValue());
   }
}
