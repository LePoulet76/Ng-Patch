package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RepairMachineGUI extends GuiContainer {

   private static final ResourceLocation REPAIR_MACHINE_GUI = new ResourceLocation("nationsgui", "textures/gui/repair_machine.png");
   private RepairMachineBlockEntity blockEntity;


   public RepairMachineGUI(RepairMachineContainer container) {
      super(container);
      this.blockEntity = container.getBlockEntity();
   }

   protected void func_74189_g(int par1, int par2) {
      String name = this.blockEntity.func_94042_c()?this.blockEntity.func_70303_b():I18n.func_135053_a(this.blockEntity.func_70303_b());
      this.field_73886_k.func_78276_b(name, this.field_74194_b / 2 - this.field_73886_k.func_78256_a(name) / 2, 6, 4210752);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("container.inventory"), 8, this.field_74195_c - 96 + 2, 4210752);
   }

   protected void func_74185_a(float partialTicks, int mouseX, int mouseY) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(REPAIR_MACHINE_GUI);
      int x = (this.field_73880_f - this.field_74194_b) / 2;
      int y = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(x, y, 0, 0, this.field_74194_b, this.field_74195_c);
      if(this.blockEntity.isActive()) {
         this.func_73729_b(x + 117, y + 22, 176, 0, 14, 14);
      }

   }

}
