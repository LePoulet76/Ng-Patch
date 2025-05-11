/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 */
package net.ilexiconn.nationsgui.forge.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RadioModel
extends ModelBase {
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
        this.switchRight = new ModelRenderer((ModelBase)this, 7, 7);
        this.switchRight.func_78793_a(10.3f, -7.3f, 0.0f);
        this.switchRight.func_78790_a(-1.0f, -1.0f, -1.0f, 2, 2, 1, 0.0f);
        this.handle10 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle10.func_78793_a(0.8f, -0.7f, 0.0f);
        this.handle10.func_78790_a(-1.0f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        this.handle6 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle6.func_78793_a(0.0f, -4.0f, 0.0f);
        this.handle6.func_78790_a(-1.0f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        this.setRotateAngle(this.handle6, 0.0f, 0.0f, -0.18849556f);
        this.handle4 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle4.func_78793_a(0.0f, -1.0f, 0.0f);
        this.handle4.func_78790_a(0.0f, -4.0f, -0.5f, 1, 4, 1, 0.0f);
        this.setRotateAngle(this.handle4, 0.0f, 0.0f, 0.31415927f);
        this.antennaBase = new ModelRenderer((ModelBase)this, 6, 0);
        this.antennaBase.func_78793_a(1.0f, -9.5f, 4.0f);
        this.antennaBase.func_78790_a(0.0f, 0.0f, 0.0f, 1, 1, 0, 0.0f);
        this.antenna = new ModelRenderer((ModelBase)this, 9, 0);
        this.antenna.func_78793_a(0.2f, -0.1f, 0.0f);
        this.antenna.func_78790_a(0.0f, 0.0f, 0.0f, 13, 1, 0, 0.0f);
        this.setRotateAngle(this.antenna, 0.0f, 0.0f, -0.50265485f);
        this.handle2 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle2.func_78793_a(0.0f, -3.0f, 0.0f);
        this.handle2.func_78790_a(0.0f, -1.0f, -0.5f, 1, 1, 1, 0.0f);
        this.setRotateAngle(this.handle2, 0.0f, 0.0f, 0.37699112f);
        this.handle1 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle1.func_78793_a(-0.8f, -0.7f, 0.0f);
        this.handle1.func_78790_a(0.0f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        this.handle5 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle5.func_78793_a(0.0f, -4.0f, 0.0f);
        this.handle5.func_78790_a(0.0f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        this.setRotateAngle(this.handle5, 0.0f, 0.0f, 0.18849556f);
        this.speaker = new ModelRenderer((ModelBase)this, 0, 14);
        this.speaker.func_78793_a(-6.0f, 24.0f, -3.0f);
        this.speaker.func_78790_a(0.0f, -9.0f, 0.0f, 12, 9, 5, 0.0f);
        this.handle8 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle8.func_78793_a(0.0f, -1.0f, 0.0f);
        this.handle8.func_78790_a(-1.0f, -1.0f, -0.5f, 1, 1, 1, 0.0f);
        this.setRotateAngle(this.handle8, 0.0f, 0.0f, -0.69115037f);
        this.handle3 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle3.func_78793_a(0.0f, -1.0f, 0.0f);
        this.handle3.func_78790_a(0.0f, -1.0f, -0.5f, 1, 1, 1, 0.0f);
        this.setRotateAngle(this.handle3, 0.0f, 0.0f, 0.69115037f);
        this.speakerBaseLeft = new ModelRenderer((ModelBase)this, 0, 6);
        this.speakerBaseLeft.func_78793_a(0.3f, -5.5f, 2.5f);
        this.speakerBaseLeft.func_78790_a(-1.0f, -1.0f, -1.0f, 1, 2, 2, 0.0f);
        this.controlPanel = new ModelRenderer((ModelBase)this, 14, 7);
        this.controlPanel.func_78793_a(2.9f, -8.3f, 0.7f);
        this.controlPanel.func_78790_a(0.0f, 0.0f, -1.0f, 6, 2, 1, 0.0f);
        this.speakerLeft = new ModelRenderer((ModelBase)this, 35, 15);
        this.speakerLeft.func_78793_a(-0.3f, -0.5f, 0.0f);
        this.speakerLeft.func_78790_a(0.0f, -8.0f, 0.0f, 1, 8, 5, 0.0f);
        this.handle9 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle9.func_78793_a(0.0f, -3.0f, 0.0f);
        this.handle9.func_78790_a(-1.0f, -1.0f, -0.5f, 1, 1, 1, 0.0f);
        this.setRotateAngle(this.handle9, 0.0f, 0.0f, -0.37699112f);
        this.handle7 = new ModelRenderer((ModelBase)this, 0, 0);
        this.handle7.func_78793_a(0.0f, -1.0f, 0.0f);
        this.handle7.func_78790_a(-1.0f, -4.0f, -0.5f, 1, 4, 1, 0.0f);
        this.setRotateAngle(this.handle7, 0.0f, 0.0f, -0.31415927f);
        this.switchLeft = new ModelRenderer((ModelBase)this, 7, 7);
        this.switchLeft.func_78793_a(1.6f, -7.3f, 0.0f);
        this.switchLeft.func_78790_a(-1.0f, -1.0f, -1.0f, 2, 2, 1, 0.0f);
        this.speakerRight = new ModelRenderer((ModelBase)this, 35, 15);
        this.speakerRight.field_78809_i = true;
        this.speakerRight.func_78793_a(12.3f, -0.5f, 0.0f);
        this.speakerRight.func_78790_a(-1.0f, -8.0f, 0.0f, 1, 8, 5, 0.0f);
        this.speakerBaseRight = new ModelRenderer((ModelBase)this, 0, 6);
        this.speakerBaseRight.func_78793_a(-0.3f, -5.5f, 2.5f);
        this.speakerBaseRight.func_78790_a(0.0f, -1.0f, -1.0f, 1, 2, 2, 0.0f);
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

