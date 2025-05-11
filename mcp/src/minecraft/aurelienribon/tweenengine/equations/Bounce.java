/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Bounce
extends TweenEquation {
    public static final Bounce IN = new Bounce(){

        @Override
        public final float compute(float t) {
            return 1.0f - OUT.compute(1.0f - t);
        }

        public String toString() {
            return "Bounce.IN";
        }
    };
    public static final Bounce OUT = new Bounce(){

        @Override
        public final float compute(float t) {
            if ((double)t < 0.36363636363636365) {
                return 7.5625f * t * t;
            }
            if ((double)t < 0.7272727272727273) {
                return 7.5625f * (t -= 0.54545456f) * t + 0.75f;
            }
            if ((double)t < 0.9090909090909091) {
                return 7.5625f * (t -= 0.8181818f) * t + 0.9375f;
            }
            return 7.5625f * (t -= 0.95454544f) * t + 0.984375f;
        }

        public String toString() {
            return "Bounce.OUT";
        }
    };
    public static final Bounce INOUT = new Bounce(){

        @Override
        public final float compute(float t) {
            if (t < 0.5f) {
                return IN.compute(t * 2.0f) * 0.5f;
            }
            return OUT.compute(t * 2.0f - 1.0f) * 0.5f + 0.5f;
        }

        public String toString() {
            return "Bounce.INOUT";
        }
    };
}

