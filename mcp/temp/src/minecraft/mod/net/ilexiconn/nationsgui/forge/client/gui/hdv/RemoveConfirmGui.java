package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoveSalePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class RemoveConfirmGui extends GuiScreen {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/hdv_accept.png");
   private int posX;
   private int posY;
   private GuiScreen previous;
   private MarketSale marketSale;
   private List<String> textList = new ArrayList();


   public RemoveConfirmGui(GuiScreen previous, MarketSale marketSale) {
      this.previous = previous;
      this.marketSale = marketSale;
      this.textList.clear();
      StringBuilder sub = new StringBuilder();
      String[] words = I18n.func_135053_a("hdv.remove.confirm.text").split(" ");
      String[] var5 = words;
      int var6 = words.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String word = var5[var7];
         String temp = (!Objects.equals(words[0], word)?" ":"") + word;
         if(Minecraft.func_71410_x().field_71466_p.func_78256_a(sub.toString()) + Minecraft.func_71410_x().field_71466_p.func_78256_a(temp) <= 210) {
            sub.append(temp);
         } else {
            this.textList.add(sub.toString());
            sub = new StringBuilder(word);
         }
      }

      if(!sub.toString().equals("")) {
         this.textList.add(sub.toString());
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.posX = this.field_73880_f / 2 - 137;
      this.posY = this.field_73881_g / 2 - 58;
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(0, this.posX + 50, this.posY + 75, 75, 20, I18n.func_135053_a("hdv.remove.confirm.yes")));
      this.field_73887_h.add(new GuiButton(1, this.posX + 260 - 75 - 50, this.posY + 75, 75, 20, I18n.func_135053_a("hdv.remove.confirm.no")));
   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 275, 171, 275.0F, 256.0F, false);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("hdv.remove.confirm.title"), this.posX + 14 + 123 - this.field_73886_k.func_78256_a(I18n.func_135053_a("hdv.remove.confirm.title")) / 2, this.posY + 14, 0);
      int i = 0;

      for(Iterator var5 = this.textList.iterator(); var5.hasNext(); ++i) {
         String line = (String)var5.next();
         this.field_73886_k.func_78276_b(line, this.posX + 14 + 123 - this.field_73886_k.func_78256_a(line) / 2, this.posY + 30 + 10 * i, 0);
      }

      super.func_73863_a(par1, par2, par3);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      switch(par1GuiButton.field_73741_f) {
      case 0:
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoveSalePacket(this.marketSale.getUuid(), true)));
         if(this.previous instanceof MarketGui) {
            MarketGui marketGui = (MarketGui)this.previous;
            marketGui.removeSale(this.marketSale);
         }

         this.field_73882_e.func_71373_a(this.previous);
         break;
      case 1:
         this.field_73882_e.func_71373_a(this.previous);
      }

   }

}
