package net.ilexiconn.nationsgui.forge.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class SpeakerModel extends ModelBase {

   public ModelRenderer speaker1;
   public ModelRenderer speaker2;
   public ModelRenderer speaker3;
   public ModelRenderer speaker4;
   public ModelRenderer speaker5;
   public ModelRenderer middle;
   public ModelRenderer speaker6;
   public ModelRenderer speaker7;
   public ModelRenderer speaker8;
   public ModelRenderer speaker10;
   public ModelRenderer speaker9;
   public ModelRenderer speaker11;


   public SpeakerModel() {
      this.field_78090_t = 64;
      this.field_78089_u = 32;
      this.speaker4 = new ModelRenderer(this, 25, 16);
      this.speaker4.func_78793_a(-1.0F, -1.0F, -1.0F);
      this.speaker4.func_78790_a(0.0F, 0.0F, 0.0F, 9, 9, 2, 0.0F);
      this.speaker7 = new ModelRenderer(this, 0, 3);
      this.speaker7.func_78793_a(11.0F, 0.0F, -2.0F);
      this.speaker7.func_78790_a(-2.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
      this.middle = new ModelRenderer(this, 0, 27);
      this.middle.func_78793_a(3.0F, 3.0F, -1.0F);
      this.middle.func_78790_a(0.0F, 0.0F, -1.0F, 4, 4, 1, 0.0F);
      this.speaker10 = new ModelRenderer(this, 0, 0);
      this.speaker10.func_78793_a(-0.5F, 10.5F, -2.0F);
      this.speaker10.func_78790_a(0.0F, -1.0F, 0.0F, 11, 1, 2, 0.0F);
      this.speaker1 = new ModelRenderer(this, 26, 6);
      this.speaker1.func_78793_a(-3.5F, 28.5F, -10.0F);
      this.speaker1.func_78790_a(0.0F, 0.0F, 0.0F, 7, 7, 2, 0.0F);
      this.speaker6 = new ModelRenderer(this, 0, 3);
      this.speaker6.func_78793_a(-1.0F, 0.0F, -2.0F);
      this.speaker6.func_78790_a(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
      this.speaker8 = new ModelRenderer(this, 0, 0);
      this.speaker8.func_78793_a(-0.5F, -0.5F, -2.0F);
      this.speaker8.func_78790_a(0.0F, 0.0F, 0.0F, 11, 1, 2, 0.0F);
      this.speaker9 = new ModelRenderer(this, 0, 0);
      this.speaker9.func_78793_a(0.5F, -0.5F, 0.0F);
      this.speaker9.func_78790_a(0.0F, 0.0F, 0.0F, 10, 1, 2, 0.0F);
      this.speaker2 = new ModelRenderer(this, 11, 4);
      this.speaker2.func_78793_a(1.0F, -0.5F, 0.0F);
      this.speaker2.func_78790_a(0.0F, 0.0F, 0.0F, 5, 8, 2, 0.0F);
      this.speaker3 = new ModelRenderer(this, 43, 0);
      this.speaker3.func_78793_a(-0.4F, 1.0F, 0.0F);
      this.speaker3.func_78790_a(0.0F, 0.0F, 0.0F, 8, 5, 2, 0.0F);
      this.speaker5 = new ModelRenderer(this, 0, 15);
      this.speaker5.func_78793_a(-0.5F, -0.5F, -0.5F);
      this.speaker5.func_78790_a(0.0F, 0.0F, -1.0F, 10, 10, 2, 0.0F);
      this.speaker11 = new ModelRenderer(this, 0, 0);
      this.speaker11.func_78793_a(0.5F, 0.5F, 0.0F);
      this.speaker11.func_78790_a(0.0F, -1.0F, 0.0F, 10, 1, 2, 0.0F);
      this.speaker1.func_78792_a(this.speaker4);
      this.speaker5.func_78792_a(this.speaker7);
      this.speaker5.func_78792_a(this.middle);
      this.speaker5.func_78792_a(this.speaker10);
      this.speaker5.func_78792_a(this.speaker6);
      this.speaker5.func_78792_a(this.speaker8);
      this.speaker8.func_78792_a(this.speaker9);
      this.speaker1.func_78792_a(this.speaker2);
      this.speaker1.func_78792_a(this.speaker3);
      this.speaker4.func_78792_a(this.speaker5);
      this.speaker10.func_78792_a(this.speaker11);
   }

   public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks) {
      this.speaker1.func_78785_a(partialTicks);
   }
}
