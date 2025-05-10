package net.ilexiconn.nationsgui.forge.client.voices.networking;

import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;

public abstract class VoiceClient implements Runnable
{
    protected EnumVoiceNetworkType type;

    public VoiceClient(EnumVoiceNetworkType enumVoiceServer)
    {
        this.type = enumVoiceServer;
    }

    public final void run()
    {
        this.start();
    }

    public abstract void start();

    public abstract void stop();

    public abstract void handleEnd(int var1);

    public abstract void handlePacket(int var1, byte[] var2, int var3, int var4);

    public abstract void sendVoiceData(byte[] var1, boolean var2);

    public final EnumVoiceNetworkType getType()
    {
        return this.type;
    }

    public abstract void handleEntityData(int var1, String var2, double var3, double var5, double var7, double var9, double var11, double var13);

    public abstract void handleEntityPosition(int var1, double var2, double var4, double var6, double var8, double var10, double var12);
}
