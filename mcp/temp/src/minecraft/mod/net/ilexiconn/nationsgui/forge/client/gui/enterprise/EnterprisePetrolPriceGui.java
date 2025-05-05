package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterprisePetrolGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalLargeGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterprisePetrolPricePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterprisePetrolPriceGui extends ModalLargeGui {

   private GuiButton cancelButton;
   private GuiButton validButton;
   private GuiTextField priceInput;


   public EnterprisePetrolPriceGui(GuiScreen guiFrom) {
      super(guiFrom);
   }

   public void func_73876_c() {
      this.priceInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 184, 118, 20, I18n.func_135053_a("enterprise.bank.modal.action.cancel"));
      this.validButton = new GuiButton(1, this.guiLeft + 184, this.guiTop + 184, 118, 20, I18n.func_135053_a("enterprise.bank.modal.action.valid"));
      if(!EnterpriseGui.hasPermission("petrol")) {
         this.validButton.field_73742_g = false;
      }

      this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 57, this.guiTop + 76, 97, 10);
      this.priceInput.func_73786_a(false);
      this.priceInput.func_73804_f(6);
      this.priceInput.func_73782_a(((Double)EnterprisePetrolGUI.enterprisePetrolInfos.get("price")).intValue() + "");
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      super.func_73863_a(mouseX, mouseY, par3);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.text_3"), this.guiLeft + 53, this.guiTop + 55, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_modal_large");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 70), 0, 242, 122, 20, 512.0F, 512.0F, false);
      this.drawScaledString("\u00a7a$", this.guiLeft + 167, this.guiTop + 76, 16777215, 1.3F, true, false);
      this.priceInput.func_73795_f();
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.avg") + " \u00a72" + String.format("%.1f", new Object[]{(Double)EnterprisePetrolGUI.enterprisePetrolInfos.get("avgPrice")}) + "$/100L", this.guiLeft + 180, this.guiTop + 76, 0, 1.0F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.restrict"), this.guiLeft + 53, this.guiTop + 100, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_modal_large");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 115), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAll")).booleanValue()?0:10, 262, 10, 10, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.all"), this.guiLeft + 66, this.guiTop + 117, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_modal_large");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 130), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowCountry")).booleanValue()?0:10, 262, 10, 10, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.country").replace("<country>", (String)EnterprisePetrolGUI.enterprisePetrolInfos.get("associatedCountry")), this.guiLeft + 66, this.guiTop + 132, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_modal_large");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 145), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAlly")).booleanValue()?0:10, 262, 10, 10, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.petrol.modal.price.ally").replace("<country>", (String)EnterprisePetrolGUI.enterprisePetrolInfos.get("associatedCountry")), this.guiLeft + 66, this.guiTop + 147, 1644825, 1.0F, false, false);
      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.priceInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 184 && mouseY < this.guiTop + 184 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new EnterprisePetrolGUI());
         }

         if(mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 115 && mouseY < this.guiTop + 115 + 10) {
            EnterprisePetrolGUI.enterprisePetrolInfos.put("allowAll", Boolean.valueOf(!((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAll")).booleanValue()));
         } else if(mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 130 && mouseY < this.guiTop + 130 + 10) {
            EnterprisePetrolGUI.enterprisePetrolInfos.put("allowCountry", Boolean.valueOf(!((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowCountry")).booleanValue()));
         } else if(mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 145 && mouseY < this.guiTop + 145 + 10) {
            EnterprisePetrolGUI.enterprisePetrolInfos.put("allowAlly", Boolean.valueOf(!((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAlly")).booleanValue()));
         }

         if(this.validButton.field_73742_g && this.isNumeric(this.priceInput.func_73781_b()) && EnterpriseGui.hasPermission("capital") && mouseX > this.guiLeft + 184 && mouseX < this.guiLeft + 184 + 118 && mouseY > this.guiTop + 184 && mouseY < this.guiTop + 184 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterprisePetrolPricePacket((String)EnterpriseGui.enterpriseInfos.get("name"), Integer.valueOf(Integer.parseInt(this.priceInput.func_73781_b())), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAll")).booleanValue(), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowCountry")).booleanValue(), ((Boolean)EnterprisePetrolGUI.enterprisePetrolInfos.get("allowAlly")).booleanValue())));
            Minecraft.func_71410_x().func_71373_a(new EnterprisePetrolGUI());
         }
      }

      this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
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

         if(Integer.parseInt(str) < 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }
}
