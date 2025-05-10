package net.ilexiconn.nationsgui.forge.client.voices.networking;

import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;

public abstract class VoiceAuthenticatedClient extends VoiceClient
{
    private boolean connected;
    private boolean authed;
    protected final String ipAddress;
    protected final String hash;

    public VoiceAuthenticatedClient(EnumVoiceNetworkType enumVoiceServer, String ipAddress, String hash)
    {
        super(enumVoiceServer);
        this.ipAddress = ipAddress;
        this.hash = hash;
    }

    public final String getHash()
    {
        return this.hash;
    }

    public final boolean isConnected()
    {
        return this.connected;
    }

    protected void setConnected(boolean connected)
    {
        this.connected = connected;
    }

    public boolean isAuthed()
    {
        return this.authed;
    }

    protected void setAuthed(boolean authed)
    {
        this.authed = authed;
    }
}
