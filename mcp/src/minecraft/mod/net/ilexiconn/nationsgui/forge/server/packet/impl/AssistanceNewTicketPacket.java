package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class AssistanceNewTicketPacket implements IPacket
{
    private String category;
    private String message;
    private String screenshotUrl;

    public AssistanceNewTicketPacket(String category, String message, String screenshotUrl)
    {
        this.category = category;
        this.message = message;
        this.screenshotUrl = screenshotUrl;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.category);
        data.writeUTF(this.message);
        data.writeUTF(this.screenshotUrl);
    }
}
