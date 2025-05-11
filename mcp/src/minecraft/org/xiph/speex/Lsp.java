/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Lsp {
    private float[] pw = new float[42];

    public static final float cheb_poly_eva(float[] var0, float var1, int var2) {
        int var6 = var2 >> 1;
        float[] var5 = new float[var6 + 1];
        var5[0] = 1.0f;
        var5[1] = var1;
        float var4 = var0[var6] + var0[var6 - 1] * var1;
        var1 *= 2.0f;
        for (int var3 = 2; var3 <= var6; ++var3) {
            var5[var3] = var1 * var5[var3 - 1] - var5[var3 - 2];
            var4 += var0[var6 - var3] * var5[var3];
        }
        return var4;
    }

    public static int lpc2lsp(float[] var0, int var1, float[] var2, int var3, float var4) {
        int var13;
        float var11 = 0.0f;
        int var25 = 0;
        boolean var16 = true;
        int var15 = var1 / 2;
        float[] var18 = new float[var15 + 1];
        float[] var19 = new float[var15 + 1];
        int var20 = 0;
        int var21 = 0;
        int var22 = var20;
        int var23 = var21;
        int var28 = var20 + 1;
        var19[var20] = 1.0f;
        int var29 = var21 + 1;
        var18[var21] = 1.0f;
        for (var13 = 1; var13 <= var15; ++var13) {
            var19[var28++] = var0[var13] + var0[var1 + 1 - var13] - var19[var22++];
            var18[var29++] = var0[var13] - var0[var1 + 1 - var13] + var18[var23++];
        }
        var28 = 0;
        var29 = 0;
        for (var13 = 0; var13 < var15; ++var13) {
            var19[var28] = 2.0f * var19[var28];
            var18[var29] = 2.0f * var18[var29];
            ++var28;
            ++var29;
        }
        boolean var30 = false;
        boolean var31 = false;
        float var10 = 0.0f;
        float var9 = 1.0f;
        for (int var14 = 0; var14 < var1; ++var14) {
            float[] var24 = var14 % 2 != 0 ? var18 : var19;
            float var5 = Lsp.cheb_poly_eva(var24, var9, var1);
            var16 = true;
            while (var16 && (double)var10 >= -1.0) {
                float var6;
                float var26 = (float)((double)var4 * (1.0 - 0.9 * (double)var9 * (double)var9));
                if ((double)Math.abs(var5) < 0.2) {
                    var26 = (float)((double)var26 * 0.5);
                }
                if ((double)((var6 = Lsp.cheb_poly_eva(var24, var10 = var9 - var26, var1)) * var5) < 0.0) {
                    ++var25;
                    for (int var17 = 0; var17 <= var3; ++var17) {
                        var11 = (var9 + var10) / 2.0f;
                        float var7 = Lsp.cheb_poly_eva(var24, var11, var1);
                        if ((double)(var7 * var5) > 0.0) {
                            var5 = var7;
                            var9 = var11;
                            continue;
                        }
                        var10 = var11;
                    }
                    var2[var14] = var11;
                    var9 = var11;
                    var16 = false;
                    continue;
                }
                var5 = var6;
                var9 = var10;
            }
        }
        return var25;
    }

    public void lsp2lpc(float[] var1, float[] var2, int var3) {
        int var4;
        int var13 = 0;
        int var14 = var3 / 2;
        for (var4 = 0; var4 < 4 * var14 + 2; ++var4) {
            this.pw[var4] = 0.0f;
        }
        float var8 = 1.0f;
        float var9 = 1.0f;
        for (int var5 = 0; var5 <= var3; ++var5) {
            float var7;
            float var6;
            int var15 = 0;
            var4 = 0;
            while (var4 < var14) {
                int var10 = var4 * 4;
                int var11 = var10 + 1;
                int var12 = var11 + 1;
                var13 = var12 + 1;
                var6 = var8 - 2.0f * var1[var15] * this.pw[var10] + this.pw[var11];
                var7 = var9 - 2.0f * var1[var15 + 1] * this.pw[var12] + this.pw[var13];
                this.pw[var11] = this.pw[var10];
                this.pw[var13] = this.pw[var12];
                this.pw[var10] = var8;
                this.pw[var12] = var9;
                var8 = var6;
                var9 = var7;
                ++var4;
                var15 += 2;
            }
            var6 = var8 + this.pw[var13 + 1];
            var7 = var9 - this.pw[var13 + 2];
            var2[var5] = (var6 + var7) * 0.5f;
            this.pw[var13 + 1] = var8;
            this.pw[var13 + 2] = var9;
            var8 = 0.0f;
            var9 = 0.0f;
        }
    }

    public static void enforce_margin(float[] var0, int var1, float var2) {
        if (var0[0] < var2) {
            var0[0] = var2;
        }
        if (var0[var1 - 1] > (float)Math.PI - var2) {
            var0[var1 - 1] = (float)Math.PI - var2;
        }
        for (int var3 = 1; var3 < var1 - 1; ++var3) {
            if (var0[var3] < var0[var3 - 1] + var2) {
                var0[var3] = var0[var3 - 1] + var2;
            }
            if (!(var0[var3] > var0[var3 + 1] - var2)) continue;
            var0[var3] = 0.5f * (var0[var3] + var0[var3 + 1] - var2);
        }
    }
}

