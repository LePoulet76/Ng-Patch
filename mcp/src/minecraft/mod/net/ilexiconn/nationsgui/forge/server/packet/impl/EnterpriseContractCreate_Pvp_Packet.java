package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseContractCreate_Pvp_Packet implements IPacket
{
    public String enterpriseName;
    public String content;
    public Integer price;
    public String condition;

    public EnterpriseContractCreate_Pvp_Packet(String enterpriseName, String content, Integer price, String condition)
    {
        this.enterpriseName = enterpriseName;
        this.content = content;
        this.price = price;
        this.condition = condition;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.content);
        data.writeInt(this.price.intValue());
        data.writeUTF(this.condition);
    }
}
