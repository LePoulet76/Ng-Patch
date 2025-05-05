package net.ilexiconn.nationsgui.forge.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RadioModel extends ModelBase {

   public ModelRenderer speaker;
   public ModelRenderer switchLeft;
   public ModelRenderer controlPanel;
   public ModelRenderer antennaBase;
   public ModelRenderer speakerRight;
   public ModelRenderer switchRight;
   public ModelRenderer speakerLeft;
   public ModelRenderer antenna;
   public ModelRenderer speakerBaseRight;
   public ModelRenderer handle10;
   public ModelRenderer handle9;
   public ModelRenderer handle8;
   public ModelRenderer handle7;
   public ModelRenderer handle6;
   public ModelRenderer speakerBaseLeft;
   public ModelRenderer handle1;
   public ModelRenderer handle2;
   public ModelRenderer handle3;
   public ModelRenderer handle4;
   public ModelRenderer handle5;


   public RadioModel() {
      this.field_78090_t = 64;
      this.field_78089_u = 32;
      this.switchRight = new ModelRenderer(this, 7, 7);
      this.switchRight.func_78793_a(10.3F, -7.3F, 0.0F);
      this.switchRight.func_78790_a(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
      this.handle10 = new ModelRenderer(this, 0, 0);
      this.handle10.func_78793_a(0.8F, -0.7F, 0.0F);
      this.handle10.func_78790_a(-1.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.handle6 = new ModelRenderer(this, 0, 0);
      this.handle6.func_78793_a(0.0F, -4.0F, 0.0F);
      this.handle6.func_78790_a(-1.0F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.handle6, 0.0F, 0.0F, -0.18849556F);
      this.handle4 = new ModelRenderer(this, 0, 0);
      this.handle4.func_78793_a(0.0F, -1.0F, 0.0F);
      this.handle4.func_78790_a(0.0F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.handle4, 0.0F, 0.0F, 0.31415927F);
      this.antennaBase = new ModelRenderer(this, 6, 0);
      this.antennaBase.func_78793_a(1.0F, -9.5F, 4.0F);
      this.antennaBase.func_78790_a(0.0F, 0.0F, 0.0F, 1, 1, 0, 0.0F);
      this.antenna = new ModelRenderer(this, 9, 0);
      this.antenna.func_78793_a(0.2F, -0.1F, 0.0F);
      this.antenna.func_78790_a(0.0F, 0.0F, 0.0F, 13, 1, 0, 0.0F);
      this.setRotateAngle(this.antenna, 0.0F, 0.0F, -0.50265485F);
      this.handle2 = new ModelRenderer(this, 0, 0);
      this.handle2.func_78793_a(0.0F, -3.0F, 0.0F);
      this.handle2.func_78790_a(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.handle2, 0.0F, 0.0F, 0.37699112F);
      this.handle1 = new ModelRenderer(this, 0, 0);
      this.handle1.func_78793_a(-0.8F, -0.7F, 0.0F);
      this.handle1.func_78790_a(0.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.handle5 = new ModelRenderer(this, 0, 0);
      this.handle5.func_78793_a(0.0F, -4.0F, 0.0F);
      this.handle5.func_78790_a(0.0F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.handle5, 0.0F, 0.0F, 0.18849556F);
      this.speaker = new ModelRenderer(this, 0, 14);
      this.speaker.func_78793_a(-6.0F, 24.0F, -3.0F);
      this.speaker.func_78790_a(0.0F, -9.0F, 0.0F, 12, 9, 5, 0.0F);
      this.handle8 = new ModelRenderer(this, 0, 0);
      this.handle8.func_78793_a(0.0F, -1.0F, 0.0F);
      this.handle8.func_78790_a(-1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.handle8, 0.0F, 0.0F, -0.69115037F);
      this.handle3 = new ModelRenderer(this, 0, 0);
      this.handle3.func_78793_a(0.0F, -1.0F, 0.0F);
      this.handle3.func_78790_a(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.handle3, 0.0F, 0.0F, 0.69115037F);
      this.speakerBaseLeft = new ModelRenderer(this, 0, 6);
      this.speakerBaseLeft.func_78793_a(0.3F, -5.5F, 2.5F);
      this.speakerBaseLeft.func_78790_a(-1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
      this.controlPanel = new ModelRenderer(this, 14, 7);
      this.controlPanel.func_78793_a(2.9F, -8.3F, 0.7F);
      this.controlPanel.func_78790_a(0.0F, 0.0F, -1.0F, 6, 2, 1, 0.0F);
      this.speakerLeft = new ModelRenderer(this, 35, 15);
      this.speakerLeft.func_78793_a(-0.3F, -0.5F, 0.0F);
      this.speakerLeft.func_78790_a(0.0F, -8.0F, 0.0F, 1, 8, 5, 0.0F);
      this.handle9 = new ModelRenderer(this, 0, 0);
      this.handle9.func_78793_a(0.0F, -3.0F, 0.0F);
      this.handle9.func_78790_a(-1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.handle9, 0.0F, 0.0F, -0.37699112F);
      this.handle7 = new ModelRenderer(this, 0, 0);
      this.handle7.func_78793_a(0.0F, -1.0F, 0.0F);
      this.handle7.func_78790_a(-1.0F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.handle7, 0.0F, 0.0F, -0.31415927F);
      this.switchLeft = new ModelRenderer(this, 7, 7);
      this.switchLeft.func_78793_a(1.6F, -7.3F, 0.0F);
      this.switchLeft.func_78790_a(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
      this.speakerRight = new ModelRenderer(this, 35, 15);
      this.speakerRight.field_78809_i = true;
      this.speakerRight.func_78793_a(12.3F, -0.5F, 0.0F);
      this.speakerRight.func_78790_a(-1.0F, -8.0F, 0.0F, 1, 8, 5, 0.0F);
      this.speakerBaseRight = new ModelRenderer(this, 0, 6);
      this.speakerBaseRight.func_78793_a(-0.3F, -5.5F, 2.5F);
      this.speakerBaseRight.func_78790_a(0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
      this.speaker.func_78792_a(this.switchRight);
      this.speakerBaseRight.func_78792_a(this.handle10);
      this.handle7.func_78792_a(this.handle6);
      this.handle3.func_78792_a(this.handle4);
      this.speaker.func_78792_a(this.antennaBase);
      this.antennaBase.func_78792_a(this.antenna);
      this.handle1.func_78792_a(this.handle2);
      this.speakerBaseLeft.func_78792_a(this.handle1);
      this.handle4.func_78792_a(this.handle5);
      this.handle9.func_78792_a(this.handle8);
      this.handle2.func_78792_a(this.handle3);
      this.speakerLeft.func_78792_a(this.speakerBaseLeft);
      this.speaker.func_78792_a(this.controlPanel);
      this.speaker.func_78792_a(this.speakerLeft);
      this.handle10.func_78792_a(this.handle9);
      this.handle8.func_78792_a(this.handle7);
      this.speaker.func_78792_a(this.switchLeft);
      this.speaker.func_78792_a(this.speakerRight);
      this.speakerRight.func_78792_a(this.speakerBaseRight);
   }

   public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks) {
      this.speaker.func_78785_a(partialTicks);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.field_78795_f = x;
      modelRenderer.field_78796_g = y;
      modelRenderer.field_78808_h = z;
   }
}
