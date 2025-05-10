package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;

final class MarketGui$5 implements Comparator<MarketSale>
{
    public int compare(MarketSale o1, MarketSale o2)
    {
        return (int)(o1.getExpiry() - o2.getExpiry());
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((MarketSale)var1, (MarketSale)var2);
    }
}
