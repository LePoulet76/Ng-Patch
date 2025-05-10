package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import aurelienribon.tweenengine.TweenAccessor;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelAccessor implements TweenAccessor<ModelBiped>
{
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
    private final Map<ModelBiped, float[]> MODEL_VALUES = new WeakHashMap();

    public static ModelRenderer getEarsModel(ModelBiped model)
    {
        return model.bipedEars;
    }

    public void resetModel(ModelBiped model)
    {
        this.MODEL_VALUES.remove(model);
    }

    public int getValues(ModelBiped target, int tweenType, float[] returnValues)
    {
        int axis = tweenType % 6;
        int bodyPart = tweenType - axis;

        if (bodyPart == 36)
        {
            if (!this.MODEL_VALUES.containsKey(target))
            {
                returnValues[0] = 0.0F;
                return 1;
            }
            else
            {
                float[] model1 = (float[])((float[])this.MODEL_VALUES.get(target));
                returnValues[0] = model1[axis];
                return 1;
            }
        }
        else
        {
            ModelRenderer model = this.getBodyPart(target, bodyPart);

            if (model == null)
            {
                return 0;
            }
            else
            {
                switch (axis)
                {
                    case 0:
                        returnValues[0] = model.rotateAngleX;
                        break;

                    case 1:
                        returnValues[0] = model.rotateAngleY;
                        break;

                    case 2:
                        returnValues[0] = model.rotateAngleZ;
                        break;

                    case 3:
                        returnValues[0] = model.offsetX;
                        break;

                    case 4:
                        returnValues[0] = model.offsetY;
                        break;

                    case 5:
                        returnValues[0] = model.offsetZ;
                }

                return 1;
            }
        }
    }

    private ModelRenderer getBodyPart(ModelBiped model, int part)
    {
        switch (part)
        {
            case 0:
                return model.bipedHead;

            case 6:
                return model.bipedBody;

            case 12:
                return model.bipedRightArm;

            case 18:
                return model.bipedLeftArm;

            case 24:
                return model.bipedRightLeg;

            case 30:
                return model.bipedLeftLeg;

            default:
                return null;
        }
    }

    public void setValues(ModelBiped target, int tweenType, float[] newValues)
    {
        int axis = tweenType % 6;
        int bodyPart = tweenType - axis;

        if (bodyPart == 36)
        {
            float[] model1 = (float[])((float[])this.MODEL_VALUES.get(target));

            if (model1 == null)
            {
                this.MODEL_VALUES.put(target, model1 = new float[6]);
            }

            model1[axis] = newValues[0];
        }
        else
        {
            ModelRenderer model = this.getBodyPart(target, bodyPart);
            this.messWithModel(target, model, axis, newValues[0]);
        }
    }

    private void messWithModel(ModelBiped biped, ModelRenderer part, int axis, float val)
    {
        this.setPartAxis(part, axis, val);

        if (biped instanceof ModelBiped && part == biped.bipedHead)
        {
            this.setPartAxis(biped.bipedHeadwear, axis, val);
            this.setPartOffset(getEarsModel(biped), axis, val);
        }
    }

    private void setPartOffset(ModelRenderer part, int axis, float val)
    {
        if (part != null)
        {
            switch (axis)
            {
                case 3:
                    part.offsetX = val;
                    break;

                case 4:
                    part.offsetY = val;
                    break;

                case 5:
                    part.offsetZ = val;
            }
        }
    }

    private void setPartAxis(ModelRenderer part, int axis, float val)
    {
        if (part != null)
        {
            switch (axis)
            {
                case 0:
                    part.rotateAngleX = val;
                    break;

                case 1:
                    part.rotateAngleY = val;
                    break;

                case 2:
                    part.rotateAngleZ = val;
                    break;

                case 3:
                    part.offsetX = val;
                    break;

                case 4:
                    part.offsetY = val;
                    break;

                case 5:
                    part.offsetZ = val;
            }
        }
    }

    public void setValues(Object var1, int var2, float[] var3)
    {
        this.setValues((ModelBiped)var1, var2, var3);
    }

    public int getValues(Object var1, int var2, float[] var3)
    {
        return this.getValues((ModelBiped)var1, var2, var3);
    }
}
