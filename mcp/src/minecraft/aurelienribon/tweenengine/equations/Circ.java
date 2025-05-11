/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Circ
extends TweenEquation {
    public static final Circ IN = new Circ(){

        @Override
        public final float compute(float t) {
            return (float)(-Math.sqrt(1.0f - t * t)) - 1.0f;
        }

        public String toString() {
            return "Circ.IN";
        }
    };
    public static final Circ OUT = new Circ(){

        @Override
        public final float compute(float t) {
            return (float)Math.sqrt(1.0f - (t -= 1.0f) * t);
        }

        public String toString() {
            return "Circ.OUT";
        }
    };
    public static final Circ INOUT = new Circ(){

        @Override
        public final float compute(float t) {
            float f;
            t *= 2.0f;
            if (f < 1.0f) {
                return -0.5f * ((float)Math.sqrt(1.0f - t * t) - 1.0f);
            }
            return 0.5f * ((float)Math.sqrt(1.0f - (t -= 2.0f) * t) + 1.0f);
        }

        public String toString() {
            return "Circ.INOUT";
        }
    };
}

