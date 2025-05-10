package net.ilexiconn.nationsgui.forge.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class SpeakerModel extends ModelBase
{
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

    public SpeakerModel()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.speaker4 = new ModelRenderer(this, 25, 16);
        this.speaker4.setRotationPoint(-1.0F, -1.0F, -1.0F);
        this.speaker4.addBox(0.0F, 0.0F, 0.0F, 9, 9, 2, 0.0F);
        this.speaker7 = new ModelRenderer(this, 0, 3);
        this.speaker7.setRotationPoint(11.0F, 0.0F, -2.0F);
        this.speaker7.addBox(-2.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.middle = new ModelRenderer(this, 0, 27);
        this.middle.setRotationPoint(3.0F, 3.0F, -1.0F);
        this.middle.addBox(0.0F, 0.0F, -1.0F, 4, 4, 1, 0.0F);
        this.speaker10 = new ModelRenderer(this, 0, 0);
        this.speaker10.setRotationPoint(-0.5F, 10.5F, -2.0F);
        this.speaker10.addBox(0.0F, -1.0F, 0.0F, 11, 1, 2, 0.0F);
        this.speaker1 = new ModelRenderer(this, 26, 6);
        this.speaker1.setRotationPoint(-3.5F, 28.5F, -10.0F);
        this.speaker1.addBox(0.0F, 0.0F, 0.0F, 7, 7, 2, 0.0F);
        this.speaker6 = new ModelRenderer(this, 0, 3);
        this.speaker6.setRotationPoint(-1.0F, 0.0F, -2.0F);
        this.speaker6.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.speaker8 = new ModelRenderer(this, 0, 0);
        this.speaker8.setRotationPoint(-0.5F, -0.5F, -2.0F);
        this.speaker8.addBox(0.0F, 0.0F, 0.0F, 11, 1, 2, 0.0F);
        this.speaker9 = new ModelRenderer(this, 0, 0);
        this.speaker9.setRotationPoint(0.5F, -0.5F, 0.0F);
        this.speaker9.addBox(0.0F, 0.0F, 0.0F, 10, 1, 2, 0.0F);
        this.speaker2 = new ModelRenderer(this, 11, 4);
        this.speaker2.setRotationPoint(1.0F, -0.5F, 0.0F);
        this.speaker2.addBox(0.0F, 0.0F, 0.0F, 5, 8, 2, 0.0F);
        this.speaker3 = new ModelRenderer(this, 43, 0);
        this.speaker3.setRotationPoint(-0.4F, 1.0F, 0.0F);
        this.speaker3.addBox(0.0F, 0.0F, 0.0F, 8, 5, 2, 0.0F);
        this.speaker5 = new ModelRenderer(this, 0, 15);
        this.speaker5.setRotationPoint(-0.5F, -0.5F, -0.5F);
        this.speaker5.addBox(0.0F, 0.0F, -1.0F, 10, 10, 2, 0.0F);
        this.speaker11 = new ModelRenderer(this, 0, 0);
        this.speaker11.setRotationPoint(0.5F, 0.5F, 0.0F);
        this.speaker11.addBox(0.0F, -1.0F, 0.0F, 10, 1, 2, 0.0F);
        this.speaker1.addChild(this.speaker4);
        this.speaker5.addChild(this.speaker7);
        this.speaker5.addChild(this.middle);
        this.speaker5.addChild(this.speaker10);
        this.speaker5.addChild(this.speaker6);
        this.speaker5.addChild(this.speaker8);
        this.speaker8.addChild(this.speaker9);
        this.speaker1.addChild(this.speaker2);
        this.speaker1.addChild(this.speaker3);
        this.speaker4.addChild(this.speaker5);
        this.speaker10.addChild(this.speaker11);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks)
    {
        this.speaker1.render(partialTicks);
    }
}
