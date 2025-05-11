/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.voices.networking.EntityInfo;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.VoiceServer;

public abstract class VoiceAuthenticatedServer
extends VoiceServer {
    protected HashMap authHash = new HashMap();

    public VoiceAuthenticatedServer(EnumVoiceNetworkType enumVoiceServer) {
        super(enumVoiceServer);
    }

    public void addHash(EntityInfo id, String hash) {
        this.authHash.put(hash, id);
    }

    public void removeHash(String hash) {
        this.authHash.remove(hash);
    }

    public String getHashByEntity(int entityID) {
        Map.Entry e;
        int id;
        Iterator it = this.authHash.entrySet().iterator();
        do {
            if (!it.hasNext()) {
                return null;
            }
            e = it.next();
        } while ((id = ((EntityInfo)e.getValue()).entityID) != entityID);
        return (String)e.getKey();
    }
}

