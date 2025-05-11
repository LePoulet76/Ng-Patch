/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Sine
extends TweenEquation {
    private static final float PI = (float)Math.PI;
    public static final Sine IN = new Sine(){

        @Override
        public final float compute(float t) {
            return (float)(-Math.cos(t * 1.5707964f)) + 1.0f;
        }

        public String toString() {
            return "Sine.IN";
        }
    };
    public static final Sine OUT = new Sine(){

        @Override
        public final float compute(float t) {
            return (float)Math.sin(t * 1.5707964f);
        }

        public String toString() {
            return "Sine.OUT";
        }
    };
    public static final Sine INOUT = new Sine(){

        @Override
        public final float compute(float t) {
            return -0.5f * ((float)Math.cos((float)Math.PI * t) - 1.0f);
        }

        public String toString() {
            return "Sine.INOUT";
        }
    };
}

