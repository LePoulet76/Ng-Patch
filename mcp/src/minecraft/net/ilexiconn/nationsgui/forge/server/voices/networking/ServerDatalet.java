/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.Player
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.network.Player;

public class ServerDatalet {
    public final Player player;
    public final int id;
    public final byte[] data;
    public boolean end;

    public ServerDatalet(Player player, int id, byte[] data, boolean end) {
        this.player = player;
        this.id = id;
        this.data = data;
        this.end = end;
    }
}

