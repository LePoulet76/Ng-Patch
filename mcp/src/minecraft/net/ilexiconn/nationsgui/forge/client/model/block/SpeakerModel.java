/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 */
package net.ilexiconn.nationsgui.forge.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(value=Side.CLIENT)
public class SpeakerModel
extends ModelBase {
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
        this.speaker4 = new ModelRenderer((ModelBase)this, 25, 16);
        this.speaker4.func_78793_a(-1.0f, -1.0f, -1.0f);
        this.speaker4.func_78790_a(0.0f, 0.0f, 0.0f, 9, 9, 2, 0.0f);
        this.speaker7 = new ModelRenderer((ModelBase)this, 0, 3);
        this.speaker7.func_78793_a(11.0f, 0.0f, -2.0f);
        this.speaker7.func_78790_a(-2.0f, 0.0f, 0.0f, 2, 10, 2, 0.0f);
        this.middle = new ModelRenderer((ModelBase)this, 0, 27);
        this.middle.func_78793_a(3.0f, 3.0f, -1.0f);
        this.middle.func_78790_a(0.0f, 0.0f, -1.0f, 4, 4, 1, 0.0f);
        this.speaker10 = new ModelRenderer((ModelBase)this, 0, 0);
        this.speaker10.func_78793_a(-0.5f, 10.5f, -2.0f);
        this.speaker10.func_78790_a(0.0f, -1.0f, 0.0f, 11, 1, 2, 0.0f);
        this.speaker1 = new ModelRenderer((ModelBase)this, 26, 6);
        this.speaker1.func_78793_a(-3.5f, 28.5f, -10.0f);
        this.speaker1.func_78790_a(0.0f, 0.0f, 0.0f, 7, 7, 2, 0.0f);
        this.speaker6 = new ModelRenderer((ModelBase)this, 0, 3);
        this.speaker6.func_78793_a(-1.0f, 0.0f, -2.0f);
        this.speaker6.func_78790_a(0.0f, 0.0f, 0.0f, 2, 10, 2, 0.0f);
        this.speaker8 = new ModelRenderer((ModelBase)this, 0, 0);
        this.speaker8.func_78793_a(-0.5f, -0.5f, -2.0f);
        this.speaker8.func_78790_a(0.0f, 0.0f, 0.0f, 11, 1, 2, 0.0f);
        this.speaker9 = new ModelRenderer((ModelBase)this, 0, 0);
        this.speaker9.func_78793_a(0.5f, -0.5f, 0.0f);
        this.speaker9.func_78790_a(0.0f, 0.0f, 0.0f, 10, 1, 2, 0.0f);
        this.speaker2 = new ModelRenderer((ModelBase)this, 11, 4);
        this.speaker2.func_78793_a(1.0f, -0.5f, 0.0f);
        this.speaker2.func_78790_a(0.0f, 0.0f, 0.0f, 5, 8, 2, 0.0f);
        this.speaker3 = new ModelRenderer((ModelBase)this, 43, 0);
        this.speaker3.func_78793_a(-0.4f, 1.0f, 0.0f);
        this.speaker3.func_78790_a(0.0f, 0.0f, 0.0f, 8, 5, 2, 0.0f);
        this.speaker5 = new ModelRenderer((ModelBase)this, 0, 15);
        this.speaker5.func_78793_a(-0.5f, -0.5f, -0.5f);
        this.speaker5.func_78790_a(0.0f, 0.0f, -1.0f, 10, 10, 2, 0.0f);
        this.speaker11 = new ModelRenderer((ModelBase)this, 0, 0);
        this.speaker11.func_78793_a(0.5f, 0.5f, 0.0f);
        this.speaker11.func_78790_a(0.0f, -1.0f, 0.0f, 10, 1, 2, 0.0f);
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

