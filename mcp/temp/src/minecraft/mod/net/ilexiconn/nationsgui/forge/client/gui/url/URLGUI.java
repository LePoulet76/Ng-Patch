package net.ilexiconn.nationsgui.forge.client.gui.url;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.URLSavePacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class URLGUI extends GuiScreen {

   public static String savedClientURL = "";
   public static Long savedClientTime = Long.valueOf(0L);
   private TileEntity blockEntity;
   private GuiTextField urlField;


   public URLGUI(TileEntity blockEntity) {
      this.blockEntity = blockEntity;
   }

   public void func_73866_w_() {
      int x = this.field_73880_f / 2 - 200;
      int y = this.field_73881_g / 2 - 9;
      this.urlField = new GuiTextField(this.field_73886_k, x, y, 400, 18);
      this.urlField.func_73804_f(2000);
      if(this.blockEntity instanceof URLBlockEntity) {
         this.urlField.func_73782_a(((URLBlockEntity)this.blockEntity).url);
      } else if(this.blockEntity instanceof GenericGeckoTileEntity) {
         this.urlField.func_73782_a(((GenericGeckoTileEntity)this.blockEntity).url);
      }

      this.urlField.func_73803_e();
      this.urlField.func_73796_b(true);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.urlField.func_73795_f();
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   public void func_73876_c() {
      this.urlField.func_73780_a();
   }

   protected void func_73869_a(char character, int key) {
      if(!this.urlField.func_73802_a(character, key)) {
         super.func_73869_a(character, key);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int button) {
      this.urlField.func_73793_a(mouseX, mouseY, button);
      super.func_73864_a(mouseX, mouseY, button);
   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
      String actualUrl = "";
      if(this.blockEntity instanceof URLBlockEntity) {
         actualUrl = ((URLBlockEntity)this.blockEntity).url;
      } else if(this.blockEntity instanceof GenericGeckoTileEntity) {
         actualUrl = ((GenericGeckoTileEntity)this.blockEntity).url;
      }

      if(!actualUrl.equals(this.urlField.func_73781_b())) {
         if(this.blockEntity instanceof URLBlockEntity) {
            ((URLBlockEntity)this.blockEntity).url = this.urlField.func_73781_b();
         } else if(this.blockEntity instanceof GenericGeckoTileEntity) {
            ((GenericGeckoTileEntity)this.blockEntity).url = this.urlField.func_73781_b();
         }

         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new URLSavePacket(this.blockEntity)));
         savedClientURL = this.urlField.func_73781_b();
         savedClientTime = Long.valueOf(System.currentTimeMillis());
      }

   }

   public boolean func_73868_f() {
      return false;
   }

}
