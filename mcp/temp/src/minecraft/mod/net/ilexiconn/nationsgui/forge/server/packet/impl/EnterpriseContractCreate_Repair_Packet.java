package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseContractCreate_Repair_Packet implements IPacket {

   public String enterpriseName;
   public String content;
   public Integer price;
   public Integer qte;


   public EnterpriseContractCreate_Repair_Packet(String enterpriseName, String content, Integer price, Integer qte) {
      this.enterpriseName = enterpriseName;
      this.content = content;
      this.price = price;
      this.qte = qte;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF(this.content);
      data.writeInt(this.price.intValue());
      data.writeInt(this.qte.intValue());
   }
}
