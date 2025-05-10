package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.util.Comparator;

public class PlayableStream$PlayableStreamComparator implements Comparator<PlayableStream>
{
    public int compare(PlayableStream a, PlayableStream b)
    {
        int f = a.id < b.id ? -1 : (a.id > b.id ? 1 : 0);
        return f;
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((PlayableStream)var1, (PlayableStream)var2);
    }
}
