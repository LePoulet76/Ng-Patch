package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.entity.player.EntityPlayer;

public class VoiceMutePlayerPacket implements IPacket, IClientPacket
{
    private int entityId;
    private String username;

    public VoiceMutePlayerPacket(int entityId, String username)
    {
        this.entityId = entityId;
        this.username = username;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.entityId = data.readInt();
        this.username = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.entityId);
        data.writeUTF(this.username);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (!VoiceChat.getProxyInstance().getSettings().isPlayerMuted(this.username))
        {
            VoiceChat.getProxyInstance().getSettings().addMutedPlayer(this.username);
        }
        else
        {
            VoiceChat.getProxyInstance().getSettings().removeMutedPlayer(this.username);
        }

        if (VoiceChat.getProxyInstance().getSettings().isPlayerMuted(this.username) && VoiceChatClient.getSoundManager() != null && this.entityId != -1)
        {
            VoiceChatClient.getSoundManager().killStream(this.entityId);
        }
    }
}
