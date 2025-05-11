/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Quart
extends TweenEquation {
    public static final Quart IN = new Quart(){

        @Override
        public final float compute(float t) {
            return t * t * t * t;
        }

        public String toString() {
            return "Quart.IN";
        }
    };
    public static final Quart OUT = new Quart(){

        @Override
        public final float compute(float t) {
            return -((t -= 1.0f) * t * t * t - 1.0f);
        }

        public String toString() {
            return "Quart.OUT";
        }
    };
    public static final Quart INOUT = new Quart(){

        @Override
        public final float compute(float t) {
            float f;
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * t * t * t * t;
            }
            return -0.5f * ((t -= 2.0f) * t * t * t - 2.0f);
        }

        public String toString() {
            return "Quart.INOUT";
        }
    };
}

