/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Quad
extends TweenEquation {
    public static final Quad IN = new Quad(){

        @Override
        public final float compute(float t) {
            return t * t;
        }

        public String toString() {
            return "Quad.IN";
        }
    };
    public static final Quad OUT = new Quad(){

        @Override
        public final float compute(float t) {
            return -t * (t - 2.0f);
        }

        public String toString() {
            return "Quad.OUT";
        }
    };
    public static final Quad INOUT = new Quad(){

        @Override
        public final float compute(float t) {
            float f;
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * t * t;
            }
            return -0.5f * ((t -= 1.0f) * (t - 2.0f) - 1.0f);
        }

        public String toString() {
            return "Quad.INOUT";
        }
    };
}

