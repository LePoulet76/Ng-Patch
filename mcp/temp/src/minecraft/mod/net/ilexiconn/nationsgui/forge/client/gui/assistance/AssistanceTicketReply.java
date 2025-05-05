package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.TextAreaComponent;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceMessage;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceSimpleButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceClosePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTeleportPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTicketReplyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class AssistanceTicketReply extends AbstractAssistanceGUI {

   private final int id;
   private final String title;
   private final String screenshotUrl;
   private final GuiScreen previous;
   private boolean locked = false;
   private TextAreaComponent textAreaComponent;
   private final GuiScroller guiScroller;
   private AssistanceButton sendButton;
   private boolean admin = false;


   public AssistanceTicketReply(AssistanceTicketGUI previous, int id, AssistanceMessage message, String title, String screenshotUrl) {
      if(ClientProxy.playersInAdminMode.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
         this.admin = ((Boolean)ClientProxy.playersInAdminMode.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)).booleanValue();
      }

      this.id = id;
      this.previous = previous;
      this.title = title;
      this.screenshotUrl = screenshotUrl;
      this.guiScroller = new GuiScroller(157, this.admin?132:156);
      this.guiScroller.addElement(message);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.textAreaComponent = new TextAreaComponent(this.guiLeft + 202, this.guiTop + 61, 157, 13);
      this.guiScroller.init(this.guiLeft + 13, this.guiTop + 61);
      this.addComponent(this.textAreaComponent);
      this.addComponent(this.guiScroller);
      this.field_73887_h.add(new AssistanceSimpleButton(1, this.guiLeft + 13, this.guiTop + 42, 181, 256));
      this.sendButton = new AssistanceButton(0, this.guiLeft + 202, this.guiTop + 197, 157, 20, I18n.func_135053_a("nationsgui.assistance.sendreply"));
      this.sendButton.field_73742_g = this.canSend();
      this.field_73887_h.add(this.sendButton);
      if(this.screenshotUrl != null && !this.screenshotUrl.isEmpty()) {
         this.field_73887_h.add(new AssistanceSimpleButton(2, this.guiLeft + 142, this.guiTop + (this.admin?174:198), 181, 272));
      }

      if(this.admin) {
         this.field_73887_h.add(new GuiButton(3, this.guiLeft + 13, this.guiTop + 197, 77, 20, I18n.func_135053_a("nationsgui.assistance.teleport")));
         AssistanceButton closeButton = new AssistanceButton(4, this.guiLeft + 94, this.guiTop + 197, 77, 20, I18n.func_135053_a("nationsgui.assistance.close"));
         closeButton.setUVMap(249, 406, 256);
         this.field_73887_h.add(closeButton);
      }

   }

   protected void drawGui(int mouseX, int mouseY, float partialTick) {
      Minecraft.func_71410_x().field_71466_p.func_78276_b(I18n.func_135052_a("nationsgui.assistance.replytitle", new Object[0]), this.guiLeft + 202, this.guiTop + 51, 0);
      int var10002 = this.guiLeft + 13 + 25;
      int var10003 = this.guiTop + 47;
      Minecraft.func_71410_x().field_71466_p.func_78276_b(this.title, var10002, var10003, 0);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      switch(par1GuiButton.field_73741_f) {
      case 0:
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTicketReplyPacket(this.id, this.textAreaComponent.getText())));
         this.locked = true;
         this.sendButton.field_73742_g = false;
         break;
      case 1:
         this.field_73882_e.func_71373_a(this.previous);
         break;
      case 2:
         Desktop desktop = Desktop.getDesktop();
         if(desktop.isSupported(Action.BROWSE)) {
            try {
               desktop.browse(new URI(this.screenshotUrl));
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }
         break;
      case 3:
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTeleportPacket(this.id)));
         this.field_73882_e.func_71373_a((GuiScreen)null);
         break;
      case 4:
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceClosePacket(this.id)));
         this.locked = true;
      }

   }

   public boolean canSend() {
      return !this.locked && !this.textAreaComponent.getText().equals("");
   }

   public void actionPerformed(GuiComponent guiComponent) {
      this.sendButton.field_73742_g = this.canSend();
   }
}
