package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.ToggleGUI$ISliderCallback;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ToggleGUI extends GuiButton {

   private ToggleGUI$ISliderCallback callback;
   private float sliderOffset = 10.0F;
   private boolean active;


   public ToggleGUI(int x, int y, ToggleGUI$ISliderCallback callback, boolean active) {
      super(-1, x, y, 27, 14, "");
      this.active = active;
      this.sliderOffset = active?0.0F:10.0F;
      this.callback = callback;
   }

   public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
      mc.func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, 384, 21, this.field_73747_a, this.field_73745_b, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef(this.sliderOffset, 0.0F, 0.0F);
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.field_73746_c + 1), (float)(this.field_73743_d - 1), 381, 0, 15, 17, 512.0F, 512.0F, false);
      GL11.glPopMatrix();
      GL11.glDisable(3042);
      this.sliderOffset = GUIUtils.interpolate(this.sliderOffset, this.active?0.0F:10.0F, 0.15F);
   }

   public boolean func_73736_c(Minecraft mc, int mouseX, int mouseY) {
      if(super.func_73736_c(mc, mouseX, mouseY)) {
         this.active = !this.active;
         if(this.callback != null) {
            this.callback.call(this.active);
         }

         return true;
      } else {
         return false;
      }
   }
}
