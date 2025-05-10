package net.ilexiconn.nationsgui.forge.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RadioModel extends ModelBase
{
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

    public RadioModel()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.switchRight = new ModelRenderer(this, 7, 7);
        this.switchRight.setRotationPoint(10.3F, -7.3F, 0.0F);
        this.switchRight.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
        this.handle10 = new ModelRenderer(this, 0, 0);
        this.handle10.setRotationPoint(0.8F, -0.7F, 0.0F);
        this.handle10.addBox(-1.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.handle6 = new ModelRenderer(this, 0, 0);
        this.handle6.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.handle6.addBox(-1.0F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(this.handle6, 0.0F, 0.0F, -0.18849556F);
        this.handle4 = new ModelRenderer(this, 0, 0);
        this.handle4.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.handle4.addBox(0.0F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(this.handle4, 0.0F, 0.0F, ((float)Math.PI / 10F));
        this.antennaBase = new ModelRenderer(this, 6, 0);
        this.antennaBase.setRotationPoint(1.0F, -9.5F, 4.0F);
        this.antennaBase.addBox(0.0F, 0.0F, 0.0F, 1, 1, 0, 0.0F);
        this.antenna = new ModelRenderer(this, 9, 0);
        this.antenna.setRotationPoint(0.2F, -0.1F, 0.0F);
        this.antenna.addBox(0.0F, 0.0F, 0.0F, 13, 1, 0, 0.0F);
        this.setRotateAngle(this.antenna, 0.0F, 0.0F, -0.50265485F);
        this.handle2 = new ModelRenderer(this, 0, 0);
        this.handle2.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.handle2.addBox(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.handle2, 0.0F, 0.0F, 0.37699112F);
        this.handle1 = new ModelRenderer(this, 0, 0);
        this.handle1.setRotationPoint(-0.8F, -0.7F, 0.0F);
        this.handle1.addBox(0.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.handle5 = new ModelRenderer(this, 0, 0);
        this.handle5.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.handle5.addBox(0.0F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(this.handle5, 0.0F, 0.0F, 0.18849556F);
        this.speaker = new ModelRenderer(this, 0, 14);
        this.speaker.setRotationPoint(-6.0F, 24.0F, -3.0F);
        this.speaker.addBox(0.0F, -9.0F, 0.0F, 12, 9, 5, 0.0F);
        this.handle8 = new ModelRenderer(this, 0, 0);
        this.handle8.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.handle8.addBox(-1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.handle8, 0.0F, 0.0F, -0.69115037F);
        this.handle3 = new ModelRenderer(this, 0, 0);
        this.handle3.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.handle3.addBox(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.handle3, 0.0F, 0.0F, 0.69115037F);
        this.speakerBaseLeft = new ModelRenderer(this, 0, 6);
        this.speakerBaseLeft.setRotationPoint(0.3F, -5.5F, 2.5F);
        this.speakerBaseLeft.addBox(-1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
        this.controlPanel = new ModelRenderer(this, 14, 7);
        this.controlPanel.setRotationPoint(2.9F, -8.3F, 0.7F);
        this.controlPanel.addBox(0.0F, 0.0F, -1.0F, 6, 2, 1, 0.0F);
        this.speakerLeft = new ModelRenderer(this, 35, 15);
        this.speakerLeft.setRotationPoint(-0.3F, -0.5F, 0.0F);
        this.speakerLeft.addBox(0.0F, -8.0F, 0.0F, 1, 8, 5, 0.0F);
        this.handle9 = new ModelRenderer(this, 0, 0);
        this.handle9.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.handle9.addBox(-1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.handle9, 0.0F, 0.0F, -0.37699112F);
        this.handle7 = new ModelRenderer(this, 0, 0);
        this.handle7.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.handle7.addBox(-1.0F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(this.handle7, 0.0F, 0.0F, -((float)Math.PI / 10F));
        this.switchLeft = new ModelRenderer(this, 7, 7);
        this.switchLeft.setRotationPoint(1.6F, -7.3F, 0.0F);
        this.switchLeft.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
        this.speakerRight = new ModelRenderer(this, 35, 15);
        this.speakerRight.mirror = true;
        this.speakerRight.setRotationPoint(12.3F, -0.5F, 0.0F);
        this.speakerRight.addBox(-1.0F, -8.0F, 0.0F, 1, 8, 5, 0.0F);
        this.speakerBaseRight = new ModelRenderer(this, 0, 6);
        this.speakerBaseRight.setRotationPoint(-0.3F, -5.5F, 2.5F);
        this.speakerBaseRight.addBox(0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
        this.speaker.addChild(this.switchRight);
        this.speakerBaseRight.addChild(this.handle10);
        this.handle7.addChild(this.handle6);
        this.handle3.addChild(this.handle4);
        this.speaker.addChild(this.antennaBase);
        this.antennaBase.addChild(this.antenna);
        this.handle1.addChild(this.handle2);
        this.speakerBaseLeft.addChild(this.handle1);
        this.handle4.addChild(this.handle5);
        this.handle9.addChild(this.handle8);
        this.handle2.addChild(this.handle3);
        this.speakerLeft.addChild(this.speakerBaseLeft);
        this.speaker.addChild(this.controlPanel);
        this.speaker.addChild(this.speakerLeft);
        this.handle10.addChild(this.handle9);
        this.handle8.addChild(this.handle7);
        this.speaker.addChild(this.switchLeft);
        this.speaker.addChild(this.speakerRight);
        this.speakerRight.addChild(this.speakerBaseRight);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks)
    {
        this.speaker.render(partialTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
