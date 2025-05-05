package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandPasswordPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class IslandPasswordGui extends ModalGui {

   private GuiButton cancelButton;
   private GuiButton confirmButton;
   private GuiTextField passwordInput;
   private EntityPlayer entityPlayer;
   private String islandId;
   private String passwordTyped = "";
   private String passwordValue = "";
   private String serverNumber = "";
   public static boolean hasError = false;


   public IslandPasswordGui(EntityPlayer entityPlayer, GuiScreen guiFrom, String islandId, String passwordValue, String serverNumber) {
      super(guiFrom);
      this.entityPlayer = entityPlayer;
      this.islandId = islandId;
      this.passwordValue = passwordValue;
      this.serverNumber = serverNumber;
      hasError = false;
   }

   public void func_73876_c() {
      this.passwordInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a("island.password.cancel"));
      this.confirmButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.func_135053_a("island.password.confirm"));
      this.passwordInput = new GuiTextField(this.field_73886_k, this.guiLeft + 56, this.guiTop + 68, 247, 10);
      this.passwordInput.func_73786_a(false);
      this.passwordInput.func_73804_f(20);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      super.func_73863_a(mouseX, mouseY, par3);
      this.drawScaledString(I18n.func_135053_a("island.password.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
      if(hasError) {
         this.drawScaledString(I18n.func_135053_a("island.password.error"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
      } else {
         this.drawScaledString(I18n.func_135053_a("island.password.description_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("island.password.description_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
      }

      ClientEventHandler.STYLE.bindTexture("faction_modal");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 158, 249, 20, 512.0F, 512.0F, false);
      this.passwordInput.func_73795_f();
      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.confirmButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.passwordInput.func_73782_a(this.passwordTyped);
      this.passwordInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
      this.passwordTyped = this.passwordInput.func_73781_b();
      this.passwordInput.func_73782_a(this.passwordInput.func_73781_b().replaceAll(".", "*"));
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         Minecraft.func_71410_x().func_71373_a(this.guiFrom);
      }

      if(!this.passwordInput.func_73781_b().isEmpty() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         hasError = false;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandPasswordPacket(this.islandId, this.passwordTyped, this.passwordValue, this.serverNumber)));
      }

      this.passwordInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
