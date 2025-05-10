package aurelienribon.tweenengine.primitives;

import aurelienribon.tweenengine.TweenAccessor;

public class MutableInteger extends Number implements TweenAccessor<MutableInteger>
{
    private int value;

    public MutableInteger(int value)
    {
        this.value = value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int intValue()
    {
        return this.value;
    }

    public long longValue()
    {
        return (long)this.value;
    }

    public float floatValue()
    {
        return (float)this.value;
    }

    public double doubleValue()
    {
        return (double)this.value;
    }

    public int getValues(MutableInteger target, int tweenType, float[] returnValues)
    {
        returnValues[0] = (float)target.value;
        return 1;
    }

    public void setValues(MutableInteger target, int tweenType, float[] newValues)
    {
        target.value = (int)newValues[0];
    }

    public void setValues(Object var1, int var2, float[] var3)
    {
        this.setValues((MutableInteger)var1, var2, var3);
    }

    public int getValues(Object var1, int var2, float[] var3)
    {
        return this.getValues((MutableInteger)var1, var2, var3);
    }
}
