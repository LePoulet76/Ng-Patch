/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.primitives;

import aurelienribon.tweenengine.TweenAccessor;

public class MutableInteger
extends Number
implements TweenAccessor<MutableInteger> {
    private int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public int getValues(MutableInteger target, int tweenType, float[] returnValues) {
        returnValues[0] = target.value;
        return 1;
    }

    @Override
    public void setValues(MutableInteger target, int tweenType, float[] newValues) {
        target.value = (int)newValues[0];
    }
}

