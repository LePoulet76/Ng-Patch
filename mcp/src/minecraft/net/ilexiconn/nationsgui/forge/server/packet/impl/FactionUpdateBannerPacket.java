package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class FactionUpdateBannerPacket implements IPacket
{
    private String bannerName;
    public String imageLink;

    public FactionUpdateBannerPacket(String bannerName, String imageLink)
    {
        this.bannerName = bannerName;
        this.imageLink = imageLink;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.bannerName = data.readUTF();
        this.imageLink = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.bannerName);
        data.writeUTF(this.imageLink);
    }
}
