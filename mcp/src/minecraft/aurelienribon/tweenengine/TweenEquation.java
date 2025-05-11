/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine;

public abstract class TweenEquation {
    public abstract float compute(float var1);

    public boolean isValueOf(String str) {
        return str.equals(this.toString());
    }
}

