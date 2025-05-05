package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface IPacket {

   void fromBytes(ByteArrayDataInput var1);

   void toBytes(ByteArrayDataOutput var1);
}
