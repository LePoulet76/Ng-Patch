package net.ilexiconn.nationsgui.forge.client.voices.networking.game;

import cpw.mods.fml.common.IPlayerTracker;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPlayerTracker implements IPlayerTracker
{
    VoiceChatClient voiceChat;

    public ClientPlayerTracker(VoiceChatClient voiceChatClient)
    {
        this.voiceChat = voiceChatClient;
    }

    public void onPlayerLogin(EntityPlayer player) {}

    public void onPlayerLogout(EntityPlayer player)
    {
        if (player != null && VoiceChatClient.getSoundManager() != null)
        {
            VoiceChatClient.getSoundManager().killStream(player.entityId);
        }
    }

    public void onPlayerChangedDimension(EntityPlayer player) {}

    public void onPlayerRespawn(EntityPlayer player) {}
}
