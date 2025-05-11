/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Cubic
extends TweenEquation {
    public static final Cubic IN = new Cubic(){

        @Override
        public final float compute(float t) {
            return t * t * t;
        }

        public String toString() {
            return "Cubic.IN";
        }
    };
    public static final Cubic OUT = new Cubic(){

        @Override
        public final float compute(float t) {
            return (t -= 1.0f) * t * t + 1.0f;
        }

        public String toString() {
            return "Cubic.OUT";
        }
    };
    public static final Cubic INOUT = new Cubic(){

        @Override
        public final float compute(float t) {
            float f;
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * t * t * t;
            }
            return 0.5f * ((t -= 2.0f) * t * t + 2.0f);
        }

        public String toString() {
            return "Cubic.INOUT";
        }
    };
}

