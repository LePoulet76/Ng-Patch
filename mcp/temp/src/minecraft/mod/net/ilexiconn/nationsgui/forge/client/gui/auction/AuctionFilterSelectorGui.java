package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class AuctionFilterSelectorGui<T extends Object> extends Gui {

   private int posX;
   private int posY;
   private int width;
   private Map<String, T> stringComparatorMap;
   private Entry<String, T> currentSelection;
   private boolean open;


   public AuctionFilterSelectorGui(int posX, int posY, int width, Map<String, T> stringComparatorMap) {
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.stringComparatorMap = stringComparatorMap;
      Iterator iterator = stringComparatorMap.entrySet().iterator();
      this.currentSelection = (Entry)iterator.next();
   }

   public void draw(int mouseX, int mouseY) {
      FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
      String text = (String)this.currentSelection.getKey();
      fontRenderer.func_78276_b(text, this.posX + (this.width - fontRenderer.func_78256_a(text)) / 2, this.posY + 4, 16777215);
      if(this.open) {
         int i = 1;
         func_73734_a(this.posX - 1, this.posY + 15, this.posX + this.width + 16, this.posY + (this.stringComparatorMap.size() + 1) * 15 + 1, -13487566);

         for(Iterator var6 = this.stringComparatorMap.entrySet().iterator(); var6.hasNext(); ++i) {
            Entry entry = (Entry)var6.next();
            int y = this.posY + 15 * i;
            int color = -14737633;
            if(mouseX >= this.posX && mouseX <= this.posX + this.width + 15 && mouseY >= y && mouseY <= y + 15) {
               color = -11119018;
            }

            func_73734_a(this.posX, y, this.posX + this.width + 15, y + 15, color);
            fontRenderer.func_78276_b((String)entry.getKey(), this.posX + (this.width - fontRenderer.func_78256_a((String)entry.getKey()) + 15) / 2, y + 2, 16777215);
         }
      }

   }

   public T getSelectedObject() {
      return this.currentSelection.getValue();
   }

   public String getSelectedFilterString() {
      return (String)this.currentSelection.getKey();
   }

   public boolean mousePressed(int mouseX, int mouseY) {
      if(mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + 13) {
         this.open = !this.open;
      } else if(this.open) {
         int i = 1;

         for(Iterator var4 = this.stringComparatorMap.entrySet().iterator(); var4.hasNext(); ++i) {
            Entry entry = (Entry)var4.next();
            int y = this.posY + 15 * i;
            if(mouseX >= this.posX && mouseX <= this.posX + this.width + 15 && mouseY >= y && mouseY <= y + 14) {
               this.currentSelection = entry;
               AuctionGui.currentPage = 1;
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(AuctionGui.search.func_73781_b(), (String)entry.getKey(), AuctionGui.currentPage - 1, AuctionGui.myAuctionsOnly)));
               this.open = false;
               return true;
            }
         }
      }

      return false;
   }

   public Entry<String, T> saveSelection() {
      return this.currentSelection;
   }

   public void restoreSelection(Entry<String, T> s) {
      this.currentSelection = s;
   }
}
