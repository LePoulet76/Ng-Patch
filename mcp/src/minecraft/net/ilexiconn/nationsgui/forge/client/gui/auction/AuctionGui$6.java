package net.ilexiconn.nationsgui.forge.client.gui.auction;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.data.Auction;

final class AuctionGui$6 implements Comparator<Auction>
{
    public int compare(Auction o1, Auction o2)
    {
        return (int)(o2.getStartingTime() + o2.getDuration() - (o1.getStartingTime() + o1.getDuration()));
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((Auction)var1, (Auction)var2);
    }
}
