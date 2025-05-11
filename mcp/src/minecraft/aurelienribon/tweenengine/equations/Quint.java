/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Quint
extends TweenEquation {
    public static final Quint IN = new Quint(){

        @Override
        public final float compute(float t) {
            return t * t * t * t * t;
        }

        public String toString() {
            return "Quint.IN";
        }
    };
    public static final Quint OUT = new Quint(){

        @Override
        public final float compute(float t) {
            return (t -= 1.0f) * t * t * t * t + 1.0f;
        }

        public String toString() {
            return "Quint.OUT";
        }
    };
    public static final Quint INOUT = new Quint(){

        @Override
        public final float compute(float t) {
            float f;
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * t * t * t * t * t;
            }
            return 0.5f * ((t -= 2.0f) * t * t * t * t + 2.0f);
        }

        public String toString() {
            return "Quint.INOUT";
        }
    };
}

