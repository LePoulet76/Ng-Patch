package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MoonConfirmDonationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class ToTheMoonGUI$ToTheMoonModalGui extends ModalGui {

   private GuiButton confirmButton;
   private GuiButton cancelButton;
   private GuiTextField amountInput;


   public ToTheMoonGUI$ToTheMoonModalGui(GuiScreen guiFrom) {
      super(guiFrom);
   }

   public void func_73876_c() {
      this.amountInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.confirmButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a("moon.modal.confirm"));
      this.cancelButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.func_135053_a("moon.modal.cancel"));
      this.amountInput = new GuiTextField(this.field_73886_k, this.guiLeft + 56, this.guiTop + 68, 247, 10);
      this.amountInput.func_73786_a(false);
      this.amountInput.func_73804_f(7);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      super.func_73863_a(mouseX, mouseY, par3);
      this.drawScaledString(I18n.func_135053_a("moon.modal.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
      this.drawScaledString(I18n.func_135053_a("moon.modal.desc_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
      this.drawScaledString(I18n.func_135053_a("moon.modal.desc_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_modal");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 158, 249, 20, 512.0F, 512.0F, false);
      this.amountInput.func_73795_f();
      this.confirmButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.amountInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.amountInput.func_73781_b().isEmpty() && this.isNumeric(this.amountInput.func_73781_b()) && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MoonConfirmDonationPacket(Double.parseDouble(this.amountInput.func_73781_b()))));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }
      }

      this.amountInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean isNumeric(String str) {
      if(str != null && str.length() != 0) {
         char[] var2 = str.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if(!Character.isDigit(c)) {
               return false;
            }
         }

         if(Integer.parseInt(str) <= 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }
}
