package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.DropdownComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceStaffRankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTicketPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

public class AssistanceStaffGUI extends AbstractAssistanceGUI {

   private final GuiScroller ranksScroller = new GuiScroller(157, 93);
   private final GuiScroller ticketsScroller = new GuiScroller(157, 156);
   private DropdownComponent dropdownComponent;
   private final int ticketOpened;
   private final int ticketClosed;


   public AssistanceStaffGUI(NBTTagCompound tagCompound) {
      NBTTagList ranks = tagCompound.func_74761_m("ranks");

      for(int i = 0; i < ranks.func_74745_c(); ++i) {
         NBTTagCompound compound = (NBTTagCompound)ranks.func_74743_b(i);
         this.ranksScroller.addElement(new AssistanceStaffRankGUI(compound.func_74779_i("pseudo"), compound.func_74762_e("score")));
         if(i < ranks.func_74745_c() - 1) {
            this.ranksScroller.addElement(new ScrollerSeparator());
         }
      }

      this.ticketOpened = tagCompound.func_74762_e("openedTickets");
      this.ticketClosed = tagCompound.func_74762_e("closedTickets");
   }

   protected void drawGui(int mouseX, int mouseY, float partialTick) {
      this.field_73882_e.field_71466_p.func_78276_b(I18n.func_135053_a("nationsgui.assistance.statsTitle"), this.guiLeft + 202, this.guiTop + 60, 0);
      ModernGui.drawNGBlackSquare(this.guiLeft + 202, this.guiTop + 70, 157, 34);
      this.func_73731_b(this.field_73886_k, I18n.func_135052_a("nationsgui.assistance.openTicketStat", new Object[]{Integer.valueOf(this.ticketOpened)}), this.guiLeft + 207, this.guiTop + 76, 16777215);
      this.func_73731_b(this.field_73886_k, I18n.func_135052_a("nationsgui.assistance.closedTicketStat", new Object[]{Integer.valueOf(this.ticketClosed)}), this.guiLeft + 207, this.guiTop + 90, 16777215);
      this.field_73882_e.field_71466_p.func_78276_b(I18n.func_135053_a("nationsgui.assistance.staffTopTitle"), this.guiLeft + 215, this.guiTop + 112, 0);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
      this.func_73729_b(this.guiLeft + 202, this.guiTop + 110, 230, 275, 10, 12);
   }

   private void populate() {
      this.ticketsScroller.clearElement();

      for(int i = 0; i < AssistanceListingPacket.ticketList.func_74745_c(); ++i) {
         NBTTagCompound ticketData = (NBTTagCompound)AssistanceListingPacket.ticketList.func_74743_b(i);
         if(ticketData.func_74767_n("closed") == (this.dropdownComponent != null && this.dropdownComponent.getSelectionIndex() == 1)) {
            this.ticketsScroller.addElement(new AssistanceTicketButton(ticketData.func_74762_e("id"), I18n.func_135053_a(ticketData.func_74779_i("category")), new Date(ticketData.func_74763_f("date")), ticketData.func_74767_n("closed"), ticketData.func_74779_i("player")));
            if(i < AssistanceListingPacket.ticketList.func_74745_c() - 1) {
               this.ticketsScroller.addElement(new ScrollerSeparator());
            }
         }
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.populate();
      this.ranksScroller.init(this.guiLeft + 202, this.guiTop + 124);
      this.ticketsScroller.init(this.guiLeft + 13, this.guiTop + 61);
      this.dropdownComponent = new DropdownComponent(this.guiLeft + 90, this.guiTop + 40, 80);
      this.dropdownComponent.getChoices().add(I18n.func_135053_a("nationsgui.assistance.open"));
      this.dropdownComponent.getChoices().add(I18n.func_135053_a("nationsgui.assistance.closed"));
      this.addComponent(this.ranksScroller);
      this.addComponent(this.ticketsScroller);
      this.addComponent(this.dropdownComponent);
   }

   public void actionPerformed(GuiComponent guiComponent) {
      if(guiComponent instanceof DropdownComponent) {
         this.populate();
         this.ticketsScroller.init(this.guiLeft + 13, this.guiTop + 61);
         this.addComponent(this.ticketsScroller);
      } else if(guiComponent instanceof AssistanceTicketButton) {
         AssistanceTicketButton ticketButton = (AssistanceTicketButton)guiComponent;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTicketPacket(ticketButton.getId())));
         AssistanceTicketButton.locked = true;
      }

   }
}
