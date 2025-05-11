/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.event.FMLServerAboutToStartEvent
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 *  cpw.mods.fml.common.event.FMLStateEvent
 *  cpw.mods.fml.relauncher.Side
 */
package net.ilexiconn.nationsgui.forge.server.voices;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.relauncher.Side;
import java.util.logging.Logger;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;

public class VoiceChat {
    public static VoiceChat instance;
    public static VoiceChatServer proxy;

    public VoiceChat(Side side) {
        instance = this;
        switch (side) {
            case CLIENT: {
                proxy = new VoiceChatClient();
                break;
            }
            case SERVER: {
                proxy = new VoiceChatServer();
            }
        }
    }

    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            proxy.initClient(this, (FMLStateEvent)event);
        }
    }

    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInitClient(event);
    }

    public void initServer(FMLServerStartingEvent event) {
        proxy.initServer(event);
    }

    public void preInitServer(FMLServerAboutToStartEvent event) {
        proxy.preInitServer(event);
    }

    public static synchronized Logger getLogger() {
        return VoiceChatServer.getLogger();
    }

    public static VoiceChat getInstance() {
        return instance;
    }

    public static synchronized VoiceChat getSynchronizedInstance() {
        return instance;
    }

    public static VoiceChatClient getProxyInstance() {
        return (VoiceChatClient)(proxy instanceof VoiceChatClient ? (VoiceChatClient)proxy : proxy);
    }

    public static VoiceChatServer getServerInstance() {
        return proxy;
    }

    public static synchronized VoiceChatClient getSynchronizedProxyInstance() {
        return (VoiceChatClient)proxy;
    }
}

