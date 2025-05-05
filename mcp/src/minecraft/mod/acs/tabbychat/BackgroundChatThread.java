package acs.tabbychat;

import net.minecraft.client.Minecraft;

public class BackgroundChatThread extends Thread
{
    String sendChat = "";

    BackgroundChatThread(String _send)
    {
        this.sendChat = _send;
    }

    public synchronized void run()
    {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(this.sendChat);

        if (!this.sendChat.toLowerCase().matches("^/msg \\*\\*.*") && !this.sendChat.toLowerCase().matches("^/m \\*\\*.*"))
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(this.sendChat);
        }
    }
}
