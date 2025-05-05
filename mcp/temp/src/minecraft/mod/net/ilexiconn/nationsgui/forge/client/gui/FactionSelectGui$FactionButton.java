package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FactionSelectGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionTeleportPacket;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

class FactionSelectGui$FactionButton extends Gui {

   private String factionName;
   private final int posX;
   private final int posY;
   private int width;
   private int height;
   // $FF: synthetic field
   final FactionSelectGui this$0;


   public FactionSelectGui$FactionButton(FactionSelectGui var1, String factionName, int posX, int posY) {
      this.this$0 = var1;
      this.width = 108;
      this.height = 12;
      this.factionName = factionName;
      this.posX = posX;
      this.posY = posY;
   }

   public void draw(int mouseX, int mouseY) {
      if(mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height) {
         func_73734_a(this.posX, this.posY, this.posX + this.width, this.posY + this.height, -13948117);
         FactionSelectGui.access$000(this.this$0).func_110434_K().func_110577_a(AbstractFirstConnectionGui.BACKGROUND);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         if(FactionSelectGui.access$100(this.this$0)) {
            this.func_73729_b(this.posX + this.width - 10, this.posY + this.height / 2 - 3, 35, 45, 6, 7);
         } else {
            this.func_73729_b(this.posX + this.width - 10, this.posY + this.height / 2 - 2, 26, 45, 5, 5);
         }
      }

      int var10002 = this.posX + 5;
      FactionSelectGui.access$200(this.this$0).field_71466_p.func_78276_b(this.factionName, var10002, this.posY + this.height / 2 - 4, -1);
   }

   public void click(int mouseX, int mouseY) {
      if(mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height) {
         FactionTeleportPacket factionTeleportPacket = new FactionTeleportPacket();
         factionTeleportPacket.createFaction = FactionSelectGui.access$100(this.this$0);
         FactionSelectGui.access$300(this.this$0).func_71373_a((GuiScreen)null);
         if(FactionSelectGui.access$100(this.this$0)) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
            FactionSelectGui.access$400(this.this$0).field_71439_g.func_71165_d("/f create " + this.factionName);
            FactionSelectGui.access$500(this.this$0).field_71439_g.func_71165_d("/f claim");
         } else {
            FactionSelectGui.access$600(this.this$0).field_71439_g.func_71165_d("/f join " + this.factionName);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
         }
      }

   }
}
