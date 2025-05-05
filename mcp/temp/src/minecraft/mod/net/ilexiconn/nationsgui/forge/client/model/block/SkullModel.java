package net.ilexiconn.nationsgui.forge.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class SkullModel extends ModelBase {

   private ModelRenderer bipedHead;
   private ModelRenderer bipedHeadwear;


   public SkullModel() {
      this(64, 32);
   }

   public SkullModel(int textureWidth, int textureHeight) {
      this.field_78090_t = textureWidth;
      this.field_78089_u = textureHeight;
      this.bipedHead = new ModelRenderer(this, 0, 0);
      this.bipedHead.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
      this.bipedHeadwear = new ModelRenderer(this, 32, 0);
      this.bipedHeadwear.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.3F);
   }

   public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks) {
      this.func_78087_a(limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks, entity);
      this.bipedHead.func_78785_a(partialTicks);
      this.bipedHeadwear.func_78785_a(partialTicks);
   }

   public void func_78087_a(float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity entity) {
      this.bipedHead.field_78796_g = this.bipedHeadwear.field_78796_g = (float)((double)rotationYaw / 57.29577951308232D);
      this.bipedHead.field_78795_f = this.bipedHeadwear.field_78795_f = (float)((double)rotationPitch / 57.29577951308232D);
   }
}
