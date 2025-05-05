package acs.tabbychat;

import acs.tabbychat.GuiChatTC;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

class GuiChatTC$EmoteButton extends GuiButton {

   private String emoteName;
   // $FF: synthetic field
   final GuiChatTC this$0;


   public GuiChatTC$EmoteButton(GuiChatTC var1, int id, int posX, int posY, int width, int height, String emoteName) {
      super(id, posX, posY, width, height, "");
      this.this$0 = var1;
      this.emoteName = emoteName;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(GuiChatTC.access$100(this.this$0)) {
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         float _mult = GuiChatTC.access$200(this.this$0).field_71474_y.field_74357_r * 0.9F + 0.1F;
         if(this.field_82253_i) {
            _mult *= 0.5F;
         }

         int _opacity = (int)(255.0F * _mult);
         func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, _opacity / 2 << 24);
         Double scale = Double.valueOf(0.6D);
         GL11.glPushMatrix();
         GL11.glScaled(scale.doubleValue(), scale.doubleValue(), scale.doubleValue());
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         if(NationsGUI.EMOTES_RESOURCES.containsKey(this.emoteName)) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.EMOTES_RESOURCES.get(this.emoteName));
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c * (1.0F / scale.floatValue()) + 8.0F, (float)this.field_73743_d * (1.0F / scale.floatValue()) + 7.0F, 0, 0, 18, 18, 18.0F, 18.0F, true);
         }

         GL11.glPopMatrix();
      }

   }
}
