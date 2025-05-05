package net.ilexiconn.nationsgui.forge.client.gui.gecko;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GeckoEntityDialogSavePacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GeckoEntityGUI extends GuiScreen {

   private GenericGeckoEntity geckoEntity;
   private GuiTextField dialogInteractionField;
   private GuiTextField dialogWalkByField;
   private GuiTextField radiusWalkByField;


   public GeckoEntityGUI(GenericGeckoEntity geckoEntity) {
      this.geckoEntity = geckoEntity;
   }

   public void func_73866_w_() {
      int x = this.field_73880_f / 2 - 70;
      int y = this.field_73881_g / 2 - 9;
      this.dialogInteractionField = new GuiTextField(this.field_73886_k, x, y, 140, 18);
      this.dialogInteractionField.func_73804_f(200);
      this.dialogInteractionField.func_73782_a(this.geckoEntity.getDialogInteraction());
      this.dialogInteractionField.func_73803_e();
      this.dialogInteractionField.func_73796_b(true);
      this.dialogWalkByField = new GuiTextField(this.field_73886_k, x, y + 22, 140, 18);
      this.dialogWalkByField.func_73804_f(200);
      this.dialogWalkByField.func_73782_a(this.geckoEntity.getDialogWalkBy());
      this.dialogWalkByField.func_73803_e();
      this.dialogWalkByField.func_73796_b(false);
      this.radiusWalkByField = new GuiTextField(this.field_73886_k, x, y + 44, 140, 18);
      this.radiusWalkByField.func_73804_f(2);
      this.radiusWalkByField.func_73782_a(this.geckoEntity.getRadiusWalkBy() + "");
      this.radiusWalkByField.func_73803_e();
      this.radiusWalkByField.func_73796_b(false);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      ModernGui.drawScaledString("Dialog interaction", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Dialog interaction") - 5, this.field_73881_g / 2 - 9 + 4, 16777215, 1.0F, false, true);
      this.dialogInteractionField.func_73795_f();
      ModernGui.drawScaledString("Dialog walk by", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Dialog walk by") - 5, this.field_73881_g / 2 - 9 + 4 + 22, 16777215, 1.0F, false, true);
      this.dialogWalkByField.func_73795_f();
      ModernGui.drawScaledString("Radius walk by", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Radius walk by") - 5, this.field_73881_g / 2 - 9 + 4 + 44, 16777215, 1.0F, false, true);
      this.radiusWalkByField.func_73795_f();
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   public void func_73876_c() {
      this.dialogInteractionField.func_73780_a();
      this.dialogWalkByField.func_73780_a();
      this.radiusWalkByField.func_73780_a();
   }

   protected void func_73869_a(char character, int key) {
      if(!this.dialogInteractionField.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

      if(!this.dialogWalkByField.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

      if(!this.radiusWalkByField.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int button) {
      this.dialogInteractionField.func_73793_a(mouseX, mouseY, button);
      this.dialogWalkByField.func_73793_a(mouseX, mouseY, button);
      this.radiusWalkByField.func_73793_a(mouseX, mouseY, button);
      super.func_73864_a(mouseX, mouseY, button);
   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
      if(!this.geckoEntity.getDialogInteraction().equals(this.dialogInteractionField.func_73781_b()) || !this.geckoEntity.getDialogWalkBy().equals(this.dialogWalkByField.func_73781_b()) || !this.geckoEntity.getDialogWalkBy().equals(this.radiusWalkByField.func_73781_b())) {
         this.geckoEntity.dialogInteraction = this.dialogInteractionField.func_73781_b();
         this.geckoEntity.dialogWalkBy = this.dialogWalkByField.func_73781_b();
         this.geckoEntity.radiusWalkBy = Integer.parseInt(this.radiusWalkByField.func_73781_b());
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GeckoEntityDialogSavePacket(this.geckoEntity)));
      }

   }

   public boolean func_73868_f() {
      return false;
   }
}
