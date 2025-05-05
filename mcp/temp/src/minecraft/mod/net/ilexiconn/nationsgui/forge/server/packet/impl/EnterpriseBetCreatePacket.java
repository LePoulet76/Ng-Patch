package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseBetCreatePacket implements IPacket {

   public String enterpriseName;
   private String question;
   private String option1;
   private String option2;
   private Integer duration;
   private Integer minBet;


   public EnterpriseBetCreatePacket(String enterpriseName, String question, String option1, String option2, Integer duration, Integer minBet) {
      this.enterpriseName = enterpriseName;
      this.question = question;
      this.option1 = option1;
      this.option2 = option2;
      this.duration = duration;
      this.minBet = minBet;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.enterpriseName = data.readUTF();
      this.question = data.readUTF();
      this.option1 = data.readUTF();
      this.option2 = data.readUTF();
      this.duration = Integer.valueOf(data.readInt());
      this.minBet = Integer.valueOf(data.readInt());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF(this.question);
      data.writeUTF(this.option1);
      data.writeUTF(this.option2);
      data.writeInt(this.duration.intValue());
      data.writeInt(this.minBet.intValue());
   }
}
