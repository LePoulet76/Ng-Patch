package net.ilexiconn.nationsgui.forge.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class SkullModel extends ModelBase
{
    private ModelRenderer bipedHead;
    private ModelRenderer bipedHeadwear;

    public SkullModel()
    {
        this(64, 32);
    }

    public SkullModel(int textureWidth, int textureHeight)
    {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.3F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks, entity);
        this.bipedHead.render(partialTicks);
        this.bipedHeadwear.render(partialTicks);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity entity)
    {
        this.bipedHead.rotateAngleY = this.bipedHeadwear.rotateAngleY = (float)((double)rotationYaw / (180D / Math.PI));
        this.bipedHead.rotateAngleX = this.bipedHeadwear.rotateAngleX = (float)((double)rotationPitch / (180D / Math.PI));
    }
}
