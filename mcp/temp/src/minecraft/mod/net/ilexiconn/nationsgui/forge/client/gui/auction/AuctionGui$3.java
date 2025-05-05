package net.ilexiconn.nationsgui.forge.client.gui.auction;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.data.Auction;

final class AuctionGui$3 implements Comparator<Auction> {

   public int compare(Auction o1, Auction o2) {
      return o1.getQuantity() - o2.getQuantity();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public int compare(Object var1, Object var2) {
      return this.compare((Auction)var1, (Auction)var2);
   }
}
