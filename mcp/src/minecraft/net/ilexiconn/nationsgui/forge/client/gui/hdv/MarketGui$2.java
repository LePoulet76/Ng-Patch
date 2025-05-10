package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;

final class MarketGui$2 implements Comparator<MarketSale>
{
    public int compare(MarketSale o1, MarketSale o2)
    {
        return o2.getPrice() - o1.getPrice();
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((MarketSale)var1, (MarketSale)var2);
    }
}
