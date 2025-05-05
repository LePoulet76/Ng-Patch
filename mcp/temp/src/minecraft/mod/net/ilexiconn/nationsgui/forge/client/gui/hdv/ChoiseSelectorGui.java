package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class ChoiseSelectorGui<T extends Object> extends Gui {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
   private int posX;
   private int posY;
   private int width;
   private Map<String, T> stringComparatorMap;
   private Entry<String, T> currentSelection;
   private boolean open;


   public ChoiseSelectorGui(int posX, int posY, int width, Map<String, T> stringComparatorMap) {
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.stringComparatorMap = stringComparatorMap;
      Iterator iterator = stringComparatorMap.entrySet().iterator();
      this.currentSelection = (Entry)iterator.next();
   }

   public void draw(int mouseX, int mouseY) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 373, 14, 16, 372.0F, 400.0F, false);
      func_73734_a(this.posX + 14, this.posY, this.posX + this.width - 1, this.posY + 16, -13487566);
      func_73734_a(this.posX + this.width - 1, this.posY + 1, this.posX + this.width, this.posY + 15, -13487566);
      FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
      String text = (String)this.currentSelection.getKey();
      fontRenderer.func_78276_b(text, this.posX + 14 + (this.width - 14) / 2 - fontRenderer.func_78256_a(text) / 2, this.posY + 5, 16777215);
      if(this.open) {
         int i = 1;

         for(Iterator var6 = this.stringComparatorMap.entrySet().iterator(); var6.hasNext(); ++i) {
            Entry entry = (Entry)var6.next();
            int y = this.posY - 14 * i;
            int color = -14737633;
            if(mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= y && mouseY <= y + 14) {
               color = -11119018;
            }

            func_73734_a(this.posX, y, this.posX + this.width, y + 14, color);
            fontRenderer.func_78276_b((String)entry.getKey(), this.posX + this.width / 2 - fontRenderer.func_78256_a((String)entry.getKey()) / 2, y + 2, 16777215);
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
            int y = this.posY - 14 * i;
            if(mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= y && mouseY <= y + 14) {
               this.currentSelection = entry;
               MarketGui.currentPage = 1;
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(MarketGui.search.func_73781_b(), (String)entry.getKey(), MarketGui.currentPage - 1, MarketGui.mySalesOnly)));
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
