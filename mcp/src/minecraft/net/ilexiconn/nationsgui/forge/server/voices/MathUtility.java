/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.voices;

public class MathUtility {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}

