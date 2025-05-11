/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Back
extends TweenEquation {
    public static final Back IN = new Back(){

        @Override
        public final float compute(float t) {
            float s = this.param_s;
            return t * t * ((s + 1.0f) * t - s);
        }

        public String toString() {
            return "Back.IN";
        }
    };
    public static final Back OUT = new Back(){

        @Override
        public final float compute(float t) {
            float s = this.param_s;
            return (t -= 1.0f) * t * ((s + 1.0f) * t + s) + 1.0f;
        }

        public String toString() {
            return "Back.OUT";
        }
    };
    public static final Back INOUT = new Back(){

        @Override
        public final float compute(float t) {
            float f;
            float s = this.param_s;
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * (t * t * (((s *= 1.525f) + 1.0f) * t - s));
            }
            return 0.5f * ((t -= 2.0f) * t * (((s *= 1.525f) + 1.0f) * t + s) + 2.0f);
        }

        public String toString() {
            return "Back.INOUT";
        }
    };
    protected float param_s = 1.70158f;

    public Back s(float s) {
        this.param_s = s;
        return this;
    }
}

