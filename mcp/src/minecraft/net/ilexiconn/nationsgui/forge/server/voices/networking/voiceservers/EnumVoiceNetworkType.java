package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

public enum EnumVoiceNetworkType
{
    MINECRAFT("MINECRAFT", 0, "Minecraft", false);
    public boolean authRequired;
    public String name;

    private EnumVoiceNetworkType(String var11, int var21, String name, boolean authRequired)
    {
        this.name = name;
        this.authRequired = authRequired;
    }
}
