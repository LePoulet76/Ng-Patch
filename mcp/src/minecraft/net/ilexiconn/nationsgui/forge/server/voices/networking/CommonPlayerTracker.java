package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.IPlayerTracker;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.minecraft.entity.player.EntityPlayer;

public class CommonPlayerTracker implements IPlayerTracker
{
    VoiceChatServer voiceChat;

    public CommonPlayerTracker(VoiceChatServer vc)
    {
        this.voiceChat = vc;
    }

    public void onPlayerLogout(EntityPlayer player)
    {
        this.voiceChat.getServerNetwork().getDataManager().playerDisconnect(player);
    }

    public void onPlayerLogin(EntityPlayer player) {}

    public void onPlayerChangedDimension(EntityPlayer player) {}

    public void onPlayerRespawn(EntityPlayer player) {}
}
