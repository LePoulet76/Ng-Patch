package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.network.Player;

public class DataStream
{
    public final String identifier;
    public final int id;
    public long lastUpdated;
    public Player player;

    public DataStream(Player player, int id, String identifier)
    {
        this.id = id;
        this.identifier = identifier;
        this.player = player;
        this.lastUpdated = System.currentTimeMillis();
    }

    public String getIdentifer()
    {
        return this.identifier;
    }
}
