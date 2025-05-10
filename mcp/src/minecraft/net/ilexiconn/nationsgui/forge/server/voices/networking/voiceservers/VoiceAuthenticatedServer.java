package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.voices.networking.EntityInfo;

public abstract class VoiceAuthenticatedServer extends VoiceServer
{
    protected HashMap authHash = new HashMap();

    public VoiceAuthenticatedServer(EnumVoiceNetworkType enumVoiceServer)
    {
        super(enumVoiceServer);
    }

    public void addHash(EntityInfo id, String hash)
    {
        this.authHash.put(hash, id);
    }

    public void removeHash(String hash)
    {
        this.authHash.remove(hash);
    }

    public String getHashByEntity(int entityID)
    {
        Iterator it = this.authHash.entrySet().iterator();

        while (it.hasNext())
        {
            Entry e = (Entry)it.next();
            int id = ((EntityInfo)e.getValue()).entityID;

            if (id == entityID)
            {
                return (String)e.getKey();
            }
        }

        return null;
    }
}
