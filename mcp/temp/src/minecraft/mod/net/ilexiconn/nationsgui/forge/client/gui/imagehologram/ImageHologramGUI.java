package net.ilexiconn.nationsgui.forge.client.gui.imagehologram;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ImageHologramSavePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ImageHologramGUI extends GuiScreen {

   private ImageHologramBlockEntity blockEntity;
   private GuiTextField urlField;
   private GuiTextField imgWidth;
   private GuiTextField imgHeight;
   private GuiTextField size;


   public ImageHologramGUI(ImageHologramBlockEntity blockEntity) {
      this.blockEntity = blockEntity;
   }

   public static boolean isNumeric(String str, boolean allowZero) {
      if(str != null && str.length() != 0) {
         char[] var2 = str.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if(!Character.isDigit(c)) {
               return false;
            }
         }

         if(Integer.parseInt(str) == 0 && !allowZero) {
            return false;
         } else if(Integer.parseInt(str) < 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void func_73866_w_() {
      int x = this.field_73880_f / 2 - 70;
      int y = this.field_73881_g / 2 - 9;
      this.urlField = new GuiTextField(this.field_73886_k, x, y, 140, 18);
      this.urlField.func_73804_f(200);
      this.urlField.func_73782_a(this.blockEntity.url);
      this.urlField.func_73803_e();
      this.urlField.func_73796_b(true);
      this.imgWidth = new GuiTextField(this.field_73886_k, x, y + 22, 140, 18);
      this.imgWidth.func_73804_f(4);
      this.imgWidth.func_73782_a(this.blockEntity.imgWidth + "");
      this.imgWidth.func_73803_e();
      this.imgWidth.func_73796_b(false);
      this.imgHeight = new GuiTextField(this.field_73886_k, x, y + 44, 140, 18);
      this.imgHeight.func_73804_f(4);
      this.imgHeight.func_73782_a(this.blockEntity.imgHeight + "");
      this.imgHeight.func_73803_e();
      this.imgHeight.func_73796_b(false);
      this.size = new GuiTextField(this.field_73886_k, x, y + 66, 140, 18);
      this.size.func_73804_f(4);
      this.size.func_73782_a(this.blockEntity.size + "");
      this.size.func_73803_e();
      this.size.func_73796_b(false);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      ModernGui.drawScaledString("URL", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("URL") - 5, this.field_73881_g / 2 - 9 + 4, 16777215, 1.0F, false, true);
      this.urlField.func_73795_f();
      ModernGui.drawScaledString("Image Width", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Image Width") - 5, this.field_73881_g / 2 - 9 + 26, 16777215, 1.0F, false, true);
      this.imgWidth.func_73795_f();
      ModernGui.drawScaledString("Image Height", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Image Height") - 5, this.field_73881_g / 2 - 9 + 48, 16777215, 1.0F, false, true);
      this.imgHeight.func_73795_f();
      ModernGui.drawScaledString("Size (%)", this.field_73880_f / 2 - 70 - this.field_73886_k.func_78256_a("Size (%)") - 5, this.field_73881_g / 2 - 9 + 70, 16777215, 1.0F, false, true);
      this.size.func_73795_f();
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   public void func_73876_c() {
      this.urlField.func_73780_a();
      this.imgWidth.func_73780_a();
      this.imgHeight.func_73780_a();
      this.size.func_73780_a();
   }

   protected void func_73869_a(char character, int key) {
      if(!this.urlField.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

      if(!this.imgWidth.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

      if(!this.imgHeight.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

      if(!this.size.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int button) {
      this.urlField.func_73793_a(mouseX, mouseY, button);
      this.imgWidth.func_73793_a(mouseX, mouseY, button);
      this.imgHeight.func_73793_a(mouseX, mouseY, button);
      this.size.func_73793_a(mouseX, mouseY, button);
      super.func_73864_a(mouseX, mouseY, button);
   }

   public boolean func_73868_f() {
      return false;
   }

   public ImageHologramBlockEntity getBlockEntity() {
      return this.blockEntity;
   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
      if(this.urlField.func_73781_b().startsWith("https://static.nationsglory.fr/")) {
         this.blockEntity.url = this.urlField.func_73781_b();
      } else if(!this.urlField.func_73781_b().isEmpty()) {
         Minecraft.func_71410_x().field_71439_g.func_71035_c("\u00a7cThe URL must start with https://static.nationsglory.fr/");
      }

      if(!this.imgWidth.func_73781_b().isEmpty() && isNumeric(this.imgWidth.func_73781_b(), false)) {
         this.blockEntity.imgWidth = Integer.parseInt(this.imgWidth.func_73781_b());
      }

      if(!this.imgHeight.func_73781_b().isEmpty() && isNumeric(this.imgHeight.func_73781_b(), false)) {
         this.blockEntity.imgHeight = Integer.parseInt(this.imgHeight.func_73781_b());
      }

      if(!this.size.func_73781_b().isEmpty() && isNumeric(this.size.func_73781_b(), false)) {
         this.blockEntity.size = Integer.parseInt(this.size.func_73781_b());
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ImageHologramSavePacket(this.blockEntity)));
   }
}
