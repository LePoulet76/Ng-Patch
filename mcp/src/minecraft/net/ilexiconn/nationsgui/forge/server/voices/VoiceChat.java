package net.ilexiconn.nationsgui.forge.server.voices;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import java.util.logging.Logger;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat$1;

public class VoiceChat
{
    public static VoiceChat instance;
    public static VoiceChatServer proxy;

    public VoiceChat(Side side)
    {
        instance = this;

        switch (VoiceChat$1.$SwitchMap$cpw$mods$fml$relauncher$Side[side.ordinal()])
        {
            case 1:
                proxy = new VoiceChatClient();
                break;

            case 2:
                proxy = new VoiceChatServer();
        }
    }

    public void init(FMLInitializationEvent event)
    {
        if (event.getSide() == Side.CLIENT)
        {
            proxy.initClient(this, event);
        }
    }

    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInitClient(event);
    }

    public void initServer(FMLServerStartingEvent event)
    {
        proxy.initServer(event);
    }

    public void preInitServer(FMLServerAboutToStartEvent event)
    {
        proxy.preInitServer(event);
    }

    public static synchronized Logger getLogger()
    {
        return VoiceChatServer.getLogger();
    }

    public static VoiceChat getInstance()
    {
        return instance;
    }

    public static synchronized VoiceChat getSynchronizedInstance()
    {
        return instance;
    }

    public static VoiceChatClient getProxyInstance()
    {
        return (VoiceChatClient)((VoiceChatClient)(proxy instanceof VoiceChatClient ? (VoiceChatClient)proxy : proxy));
    }

    public static VoiceChatServer getServerInstance()
    {
        return proxy;
    }

    public static synchronized VoiceChatClient getSynchronizedProxyInstance()
    {
        return (VoiceChatClient)proxy;
    }
}
