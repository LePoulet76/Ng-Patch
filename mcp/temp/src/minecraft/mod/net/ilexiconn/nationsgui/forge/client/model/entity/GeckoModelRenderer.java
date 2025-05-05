package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoModelRenderer extends ModelRenderer {

   private final GeoSimpleRenderer<IAnimatable> renderer;
   private float scale;


   public GeckoModelRenderer(ModelBase par1ModelBase, GeoSimpleRenderer<IAnimatable> renderer) {
      super(par1ModelBase);
      this.renderer = renderer;
   }

   public void func_78785_a(float par1) {
      GL11.glPushMatrix();
      GL11.glScalef(this.scale, this.scale, this.scale);
      GL11.glRotatef(this.field_78795_f, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(this.field_78796_g, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(this.field_78808_h, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(this.field_82906_o, this.field_82908_p, this.field_82907_q);
      this.renderer.doRender(par1);
      GL11.glPopMatrix();
   }

   public float getScale() {
      return this.scale;
   }

   public void setScale(float scale) {
      this.scale = scale;
   }
}
