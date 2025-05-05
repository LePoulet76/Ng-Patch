package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class RollbackAssaultStartPacket implements IPacket {

   public HashMap<String, Object> rollbackInfos;


   public RollbackAssaultStartPacket(HashMap<String, Object> data) {
      this.rollbackInfos = data;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.rollbackInfos));
   }
}
