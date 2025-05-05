package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalLargeGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetCreatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseBetCreateGui extends ModalLargeGui {

   private GuiButton yesButton;
   private GuiButton noButton;
   private GuiScreen guiFrom;
   private GuiTextField inputQuestion;
   private GuiTextField inputOption1;
   private GuiTextField inputOption2;
   private GuiTextField inputDuration;
   private GuiTextField inputBetMin;


   public EnterpriseBetCreateGui(GuiScreen guiFrom) {
      super(guiFrom);
      this.guiFrom = guiFrom;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.inputQuestion = new GuiTextField(this.field_73886_k, this.guiLeft + 55, this.guiTop + 45, 245, 10);
      this.inputQuestion.func_73786_a(false);
      this.inputQuestion.func_73804_f(50);
      this.inputOption1 = new GuiTextField(this.field_73886_k, this.guiLeft + 55, this.guiTop + 84, 245, 10);
      this.inputOption1.func_73786_a(false);
      this.inputOption1.func_73804_f(40);
      this.inputOption2 = new GuiTextField(this.field_73886_k, this.guiLeft + 55, this.guiTop + 123, 245, 10);
      this.inputOption2.func_73786_a(false);
      this.inputOption2.func_73804_f(40);
      this.inputDuration = new GuiTextField(this.field_73886_k, this.guiLeft + 55, this.guiTop + 160, 116, 10);
      this.inputDuration.func_73786_a(false);
      this.inputDuration.func_73804_f(5);
      this.inputBetMin = new GuiTextField(this.field_73886_k, this.guiLeft + 184, this.guiTop + 160, 116, 10);
      this.inputBetMin.func_73786_a(false);
      this.inputBetMin.func_73804_f(5);
      this.yesButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 183, 118, 20, I18n.func_135053_a("faction.common.confirm"));
      this.noButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 183, 118, 20, I18n.func_135053_a("faction.common.cancel"));
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      super.func_73863_a(mouseX, mouseY, par3);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.question"), this.guiLeft + 54, this.guiTop + 29, 1644825, 1.0F, false, false);
      ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 38, 249, 20);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.option1"), this.guiLeft + 54, this.guiTop + 68, 1644825, 1.0F, false, false);
      ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 77, 249, 20);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.option2"), this.guiLeft + 54, this.guiTop + 106, 1644825, 1.0F, false, false);
      ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 115, 249, 20);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.duration"), this.guiLeft + 54, this.guiTop + 144, 1644825, 1.0F, false, false);
      ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 153, 120, 20);
      this.drawScaledString(I18n.func_135053_a("enterprise.bet.create.betMin"), this.guiLeft + 183, this.guiTop + 144, 1644825, 1.0F, false, false);
      ModernGui.drawNGBlackSquare(this.guiLeft + 182, this.guiTop + 153, 120, 20);
      this.inputQuestion.func_73795_f();
      this.inputOption1.func_73795_f();
      this.inputOption2.func_73795_f();
      this.inputDuration.func_73795_f();
      this.inputBetMin.func_73795_f();
      this.yesButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.noButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.inputQuestion.func_73781_b().isEmpty() && !this.inputOption1.func_73781_b().isEmpty() && !this.inputOption2.func_73781_b().isEmpty() && this.isNumeric(this.inputDuration.func_73781_b()) && this.isNumeric(this.inputBetMin.func_73781_b()) && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 183 && mouseY < this.guiTop + 183 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBetCreatePacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.inputQuestion.func_73781_b(), this.inputOption1.func_73781_b(), this.inputOption2.func_73781_b(), Integer.valueOf(Integer.parseInt(this.inputDuration.func_73781_b())), Integer.valueOf(Integer.parseInt(this.inputBetMin.func_73781_b())))));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 183 && mouseY < this.guiTop + 183 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(this.guiFrom);
         }

         this.inputQuestion.func_73793_a(mouseX, mouseY, mouseButton);
         this.inputOption1.func_73793_a(mouseX, mouseY, mouseButton);
         this.inputOption2.func_73793_a(mouseX, mouseY, mouseButton);
         this.inputDuration.func_73793_a(mouseX, mouseY, mouseButton);
         this.inputBetMin.func_73793_a(mouseX, mouseY, mouseButton);
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.inputQuestion.func_73802_a(typedChar, keyCode);
      this.inputOption1.func_73802_a(typedChar, keyCode);
      this.inputOption2.func_73802_a(typedChar, keyCode);
      this.inputDuration.func_73802_a(typedChar, keyCode);
      this.inputBetMin.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   public boolean isNumeric(String str) {
      try {
         Double.parseDouble(str);
         return true;
      } catch (NumberFormatException var3) {
         return false;
      }
   }
}
