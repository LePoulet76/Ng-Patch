/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Misc {
    public static float[] window(int var0, int var1) {
        int var2;
        int var3 = var1 * 7 / 2;
        int var4 = var1 * 5 / 2;
        float[] var5 = new float[var0];
        for (var2 = 0; var2 < var3; ++var2) {
            var5[var2] = (float)(0.54 - 0.46 * Math.cos(Math.PI * (double)var2 / (double)var3));
        }
        for (var2 = 0; var2 < var4; ++var2) {
            var5[var3 + var2] = (float)(0.54 + 0.46 * Math.cos(Math.PI * (double)var2 / (double)var4));
        }
        return var5;
    }

    public static float[] lagWindow(int var0, float var1) {
        float[] var2 = new float[var0 + 1];
        for (int var3 = 0; var3 < var0 + 1; ++var3) {
            var2[var3] = (float)Math.exp(-Math.PI * (double)var1 * (double)var3 * (Math.PI * 2) * (double)var1 * (double)var3);
        }
        return var2;
    }
}

