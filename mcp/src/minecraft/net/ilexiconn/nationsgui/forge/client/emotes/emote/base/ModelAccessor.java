/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 */
package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import aurelienribon.tweenengine.TweenAccessor;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelAccessor
implements TweenAccessor<ModelBiped> {
    public static final ModelAccessor INSTANCE = new ModelAccessor();
    private static final int ROT_X = 0;
    private static final int ROT_Y = 1;
    private static final int ROT_Z = 2;
    private static final int OFF_X = 3;
    private static final int OFF_Y = 4;
    private static final int OFF_Z = 5;
    protected static final int MODEL_PROPS = 6;
    protected static final int BODY_PARTS = 7;
    protected static final int STATE_COUNT = 42;
    public static final int HEAD = 0;
    public static final int BODY = 6;
    public static final int RIGHT_ARM = 12;
    public static final int LEFT_ARM = 18;
    public static final int RIGHT_LEG = 24;
    public static final int LEFT_LEG = 30;
    public static final int MODEL = 36;
    public static final int HEAD_X = 0;
    public static final int HEAD_Y = 1;
    public static final int HEAD_Z = 2;
    public static final int BODY_X = 6;
    public static final int BODY_Y = 7;
    public static final int BODY_Z = 8;
    public static final int RIGHT_ARM_X = 12;
    public static final int RIGHT_ARM_Y = 13;
    public static final int RIGHT_ARM_Z = 14;
    public static final int LEFT_ARM_X = 18;
    public static final int LEFT_ARM_Y = 19;
    public static final int LEFT_ARM_Z = 20;
    public static final int RIGHT_LEG_X = 24;
    public static final int RIGHT_LEG_Y = 25;
    public static final int RIGHT_LEG_Z = 26;
    public static final int LEFT_LEG_X = 30;
    public static final int LEFT_LEG_Y = 31;
    public static final int LEFT_LEG_Z = 32;
    public static final int MODEL_X = 36;
    public static final int MODEL_Y = 37;
    public static final int MODEL_Z = 38;
    public static final int HEAD_OFF_X = 3;
    public static final int HEAD_OFF_Y = 4;
    public static final int HEAD_OFF_Z = 5;
    public static final int BODY_OFF_X = 9;
    public static final int BODY_OFF_Y = 10;
    public static final int BODY_OFF_Z = 11;
    public static final int RIGHT_ARM_OFF_X = 15;
    public static final int RIGHT_ARM_OFF_Y = 16;
    public static final int RIGHT_ARM_OFF_Z = 17;
    public static final int LEFT_ARM_OFF_X = 21;
    public static final int LEFT_ARM_OFF_Y = 22;
    public static final int LEFT_ARM_OFF_Z = 23;
    public static final int RIGHT_LEG_OFF_X = 27;
    public static final int RIGHT_LEG_OFF_Y = 28;
    public static final int RIGHT_LEG_OFF_Z = 29;
    public static final int LEFT_LEG_OFF_X = 33;
    public static final int LEFT_LEG_OFF_Y = 34;
    public static final int LEFT_LEG_OFF_Z = 35;
    public static final int MODEL_OFF_X = 39;
    public static final int MODEL_OFF_Y = 40;
    public static final int MODEL_OFF_Z = 41;
    private final Map<ModelBiped, float[]> MODEL_VALUES = new WeakHashMap<ModelBiped, float[]>();

    public static ModelRenderer getEarsModel(ModelBiped model) {
        return model.field_78121_j;
    }

    public void resetModel(ModelBiped model) {
        this.MODEL_VALUES.remove(model);
    }

    @Override
    public int getValues(ModelBiped target, int tweenType, float[] returnValues) {
        int axis = tweenType % 6;
        int bodyPart = tweenType - axis;
        if (bodyPart == 36) {
            if (!this.MODEL_VALUES.containsKey(target)) {
                returnValues[0] = 0.0f;
                return 1;
            }
            float[] values = this.MODEL_VALUES.get(target);
            returnValues[0] = values[axis];
            return 1;
        }
        ModelRenderer model = this.getBodyPart(target, bodyPart);
        if (model == null) {
            return 0;
        }
        switch (axis) {
            case 0: {
                returnValues[0] = model.field_78795_f;
                break;
            }
            case 1: {
                returnValues[0] = model.field_78796_g;
                break;
            }
            case 2: {
                returnValues[0] = model.field_78808_h;
                break;
            }
            case 3: {
                returnValues[0] = model.field_82906_o;
                break;
            }
            case 4: {
                returnValues[0] = model.field_82908_p;
                break;
            }
            case 5: {
                returnValues[0] = model.field_82907_q;
            }
        }
        return 1;
    }

    private ModelRenderer getBodyPart(ModelBiped model, int part) {
        switch (part) {
            case 0: {
                return model.field_78116_c;
            }
            case 6: {
                return model.field_78115_e;
            }
            case 12: {
                return model.field_78112_f;
            }
            case 18: {
                return model.field_78113_g;
            }
            case 24: {
                return model.field_78123_h;
            }
            case 30: {
                return model.field_78124_i;
            }
        }
        return null;
    }

    @Override
    public void setValues(ModelBiped target, int tweenType, float[] newValues) {
        int axis = tweenType % 6;
        int bodyPart = tweenType - axis;
        if (bodyPart == 36) {
            float[] values = this.MODEL_VALUES.get(target);
            if (values == null) {
                values = new float[6];
                this.MODEL_VALUES.put(target, values);
            }
            values[axis] = newValues[0];
            return;
        }
        ModelRenderer model = this.getBodyPart(target, bodyPart);
        this.messWithModel(target, model, axis, newValues[0]);
    }

    private void messWithModel(ModelBiped biped, ModelRenderer part, int axis, float val) {
        this.setPartAxis(part, axis, val);
        if (biped instanceof ModelBiped && part == biped.field_78116_c) {
            this.setPartAxis(biped.field_78114_d, axis, val);
            this.setPartOffset(ModelAccessor.getEarsModel(biped), axis, val);
        }
    }

    private void setPartOffset(ModelRenderer part, int axis, float val) {
        if (part == null) {
            return;
        }
        switch (axis) {
            case 3: {
                part.field_82906_o = val;
                break;
            }
            case 4: {
                part.field_82908_p = val;
                break;
            }
            case 5: {
                part.field_82907_q = val;
            }
        }
    }

    private void setPartAxis(ModelRenderer part, int axis, float val) {
        if (part == null) {
            return;
        }
        switch (axis) {
            case 0: {
                part.field_78795_f = val;
                break;
            }
            case 1: {
                part.field_78796_g = val;
                break;
            }
            case 2: {
                part.field_78808_h = val;
                break;
            }
            case 3: {
                part.field_82906_o = val;
                break;
            }
            case 4: {
                part.field_82908_p = val;
                break;
            }
            case 5: {
                part.field_82907_q = val;
            }
        }
    }
}

