package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiTextMultiLines;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceListedGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceButtonIcon;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceNewTicketGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class AssistancePlayerGUI extends AbstractAssistanceListedGUI {

   private final GuiTextMultiLines createTicketText = new GuiTextMultiLines(I18n.func_135053_a("nationsgui.assistance.createTicketText"), 147, true, 0.9F);
   private final GuiTextMultiLines supportSiteText = new GuiTextMultiLines(I18n.func_135053_a("nationsgui.assistance.supportSiteText"), 147, true, 0.9F);
   public static boolean achievementDone = false;


   public AssistancePlayerGUI() {
      if(!achievementDone) {
         achievementDone = true;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_assistance", 1)));
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.createTicketText.setPosition(this.guiLeft + 202 + 5, this.guiTop + 61 + 5);
      this.supportSiteText.setPosition(this.guiLeft + 202 + 5, this.guiTop + 132 + 5);
      this.addComponent(this.createTicketText);
      this.addComponent(this.supportSiteText);
      this.field_73887_h.add(new AssistanceButton(0, this.guiLeft + 202 + 5, this.guiTop + 102, 147, 20, I18n.func_135053_a("nationsgui.assistance.createTicket")));
      this.field_73887_h.add(new AssistanceButtonIcon(1, this.guiLeft + 202 + 5, this.guiTop + 189, 147, 20, 227, 255, I18n.func_135053_a("nationsgui.assistance.supportSite")));
   }

   protected void drawGui(int mouseX, int mouseY, float partialTick) {
      super.drawGui(mouseX, mouseY, partialTick);
      ModernGui.drawNGBlackSquare(this.guiLeft + 202, this.guiTop + 61, 157, 68);
      ModernGui.drawNGBlackSquare(this.guiLeft + 202, this.guiTop + 132, 157, 84);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      switch(par1GuiButton.field_73741_f) {
      case 0:
         this.field_73882_e.func_71373_a(new AssistanceNewTicketGUI());
         break;
      case 1:
         Desktop desktop = Desktop.getDesktop();
         if(desktop.isSupported(Action.BROWSE)) {
            try {
               desktop.browse(new URI(System.getProperty("java.lang").equals("fr")?"https://nationsglory.fr/support":"https://nationsglory.com/support"));
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }
      }

      super.func_73875_a(par1GuiButton);
   }

}
