/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.Filters;
import org.xiph.speex.Ltp;

public class Ltp3Tap
extends Ltp {
    private float[] gain = new float[3];
    private int[] gain_cdbk;
    private int gain_bits;
    private int pitch_bits;
    private float[][] e;

    public Ltp3Tap(int[] var1, int var2, int var3) {
        this.gain_cdbk = var1;
        this.gain_bits = var2;
        this.pitch_bits = var3;
        this.e = new float[3][128];
    }

    @Override
    public final int quant(float[] var1, float[] var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7, int var8, int var9, int var10, float var11, int var12, int var13, Bits var14, float[] var15, int var16, float[] var17, int var18) {
        int[] var21 = new int[1];
        int var22 = 0;
        int var23 = 0;
        int var25 = 0;
        float var27 = -1.0f;
        int var28 = var18;
        if (var18 > 10) {
            var28 = 10;
        }
        int[] var29 = new int[var28];
        float[] var30 = new float[var28];
        if (var28 != 0 && var10 >= var9) {
            int var19;
            float[] var24 = new float[var13];
            if (var28 > var10 - var9 + 1) {
                var28 = var10 - var9 + 1;
            }
            Ltp3Tap.open_loop_nbest_pitch(var2, var3, var9, var10, var13, var29, var30, var28);
            for (var19 = 0; var19 < var28; ++var19) {
                int var20;
                var22 = var29[var19];
                for (var20 = 0; var20 < var13; ++var20) {
                    var7[var8 + var20] = 0.0f;
                }
                float var26 = this.pitch_gain_search_3tap(var1, var4, var5, var6, var7, var8, var22, var12, var13, var14, var15, var16, var17, var21);
                if (!(var26 < var27) && !(var27 < 0.0f)) continue;
                for (var20 = 0; var20 < var13; ++var20) {
                    var24[var20] = var7[var8 + var20];
                }
                var27 = var26;
                var25 = var22;
                var23 = var21[0];
            }
            var14.pack(var25 - var9, this.pitch_bits);
            var14.pack(var23, this.gain_bits);
            for (var19 = 0; var19 < var13; ++var19) {
                var7[var8 + var19] = var24[var19];
            }
            return var22;
        }
        var14.pack(0, this.pitch_bits);
        var14.pack(0, this.gain_bits);
        for (int var19 = 0; var19 < var13; ++var19) {
            var7[var8 + var19] = 0.0f;
        }
        return var9;
    }

    @Override
    public final int unquant(float[] var1, int var2, int var3, float var4, int var5, float[] var6, Bits var7, int var8, int var9, float var10) {
        int var11;
        int var12 = var7.unpack(this.pitch_bits);
        int var13 = var7.unpack(this.gain_bits);
        this.gain[0] = 0.015625f * (float)this.gain_cdbk[var13 * 3] + 0.5f;
        this.gain[1] = 0.015625f * (float)this.gain_cdbk[var13 * 3 + 1] + 0.5f;
        this.gain[2] = 0.015625f * (float)this.gain_cdbk[var13 * 3 + 2] + 0.5f;
        if (var8 != 0 && (var12 += var3) > var9) {
            float var15;
            float var14 = Math.abs(this.gain[1]);
            float f = var15 = var8 < 4 ? var10 : 0.4f * var10;
            if (var15 > 0.95f) {
                var15 = 0.95f;
            }
            var14 = this.gain[0] > 0.0f ? (var14 += this.gain[0]) : (var14 -= 0.5f * this.gain[0]);
            var14 = this.gain[2] > 0.0f ? (var14 += this.gain[2]) : (var14 -= 0.5f * this.gain[0]);
            if (var14 > var15) {
                float var16 = var15 / var14;
                var11 = 0;
                while (var11 < 3) {
                    int n = var11++;
                    this.gain[n] = this.gain[n] * var16;
                }
            }
        }
        var6[0] = this.gain[0];
        var6[1] = this.gain[1];
        var6[2] = this.gain[2];
        for (var11 = 0; var11 < 3; ++var11) {
            int var20;
            int var17 = var12 + 1 - var11;
            int var18 = var5;
            if (var5 > var17) {
                var18 = var17;
            }
            int var19 = var5;
            if (var5 > var17 + var12) {
                var19 = var17 + var12;
            }
            for (var20 = 0; var20 < var18; ++var20) {
                this.e[var11][var20] = var1[var2 + var20 - var17];
            }
            for (var20 = var18; var20 < var19; ++var20) {
                this.e[var11][var20] = var1[var2 + var20 - var17 - var12];
            }
            for (var20 = var19; var20 < var5; ++var20) {
                this.e[var11][var20] = 0.0f;
            }
        }
        for (var11 = 0; var11 < var5; ++var11) {
            var1[var2 + var11] = this.gain[0] * this.e[2][var11] + this.gain[1] * this.e[1][var11] + this.gain[2] * this.e[0][var11];
        }
        return var12;
    }

    private float pitch_gain_search_3tap(float[] var1, float[] var2, float[] var3, float[] var4, float[] var5, int var6, int var7, int var8, int var9, Bits var10, float[] var11, int var12, float[] var13, int[] var14) {
        int var16;
        int var15;
        float[] var18 = new float[3];
        float[][] var19 = new float[3][3];
        int var20 = 1 << this.gain_bits;
        float[][] var17 = new float[3][var9];
        this.e = new float[3][var9];
        for (var15 = 2; var15 >= 0; --var15) {
            int var23 = var7 + 1 - var15;
            for (var16 = 0; var16 < var9; ++var16) {
                this.e[var15][var16] = var16 - var23 < 0 ? var11[var12 + var16 - var23] : (var16 - var23 - var7 < 0 ? var11[var12 + var16 - var23 - var7] : 0.0f);
            }
            if (var15 == 2) {
                Filters.syn_percep_zero(this.e[var15], 0, var2, var3, var4, var17[var15], var9, var8);
                continue;
            }
            for (var16 = 0; var16 < var9 - 1; ++var16) {
                var17[var15][var16 + 1] = var17[var15 + 1][var16];
            }
            var17[var15][0] = 0.0f;
            for (var16 = 0; var16 < var9; ++var16) {
                float[] fArray = var17[var15];
                int n = var16;
                fArray[n] = fArray[n] + this.e[var15][0] * var13[var16];
            }
        }
        for (var15 = 0; var15 < 3; ++var15) {
            var18[var15] = Ltp3Tap.inner_prod(var17[var15], 0, var1, 0, var9);
        }
        for (var15 = 0; var15 < 3; ++var15) {
            for (var16 = 0; var16 <= var15; ++var16) {
                float f = Ltp3Tap.inner_prod(var17[var15], 0, var17[var16], 0, var9);
                var19[var16][var15] = f;
                var19[var15][var16] = f;
            }
        }
        float[] var31 = new float[9];
        boolean var24 = false;
        int var25 = 0;
        float var26 = 0.0f;
        var31[0] = var18[2];
        var31[1] = var18[1];
        var31[2] = var18[0];
        var31[3] = var19[1][2];
        var31[4] = var19[0][1];
        var31[5] = var19[0][2];
        var31[6] = var19[2][2];
        var31[7] = var19[1][1];
        var31[8] = var19[0][0];
        for (var15 = 0; var15 < var20; ++var15) {
            float var27 = 0.0f;
            int var32 = 3 * var15;
            float var28 = 0.015625f * (float)this.gain_cdbk[var32] + 0.5f;
            float var29 = 0.015625f * (float)this.gain_cdbk[var32 + 1] + 0.5f;
            float var30 = 0.015625f * (float)this.gain_cdbk[var32 + 2] + 0.5f;
            var27 += var31[0] * var28;
            var27 += var31[1] * var29;
            var27 += var31[2] * var30;
            var27 -= var31[3] * var28 * var29;
            var27 -= var31[4] * var30 * var29;
            var27 -= var31[5] * var30 * var28;
            var27 -= 0.5f * var31[6] * var28 * var28;
            var27 -= 0.5f * var31[7] * var29 * var29;
            if (!((var27 -= 0.5f * var31[8] * var30 * var30) > var26) && var15 != 0) continue;
            var26 = var27;
            var25 = var15;
        }
        this.gain[0] = 0.015625f * (float)this.gain_cdbk[var25 * 3] + 0.5f;
        this.gain[1] = 0.015625f * (float)this.gain_cdbk[var25 * 3 + 1] + 0.5f;
        this.gain[2] = 0.015625f * (float)this.gain_cdbk[var25 * 3 + 2] + 0.5f;
        var14[0] = var25;
        for (var15 = 0; var15 < var9; ++var15) {
            var5[var6 + var15] = this.gain[0] * this.e[2][var15] + this.gain[1] * this.e[1][var15] + this.gain[2] * this.e[0][var15];
        }
        float var21 = 0.0f;
        float var22 = 0.0f;
        for (var15 = 0; var15 < var9; ++var15) {
            var21 += var1[var15] * var1[var15];
        }
        for (var15 = 0; var15 < var9; ++var15) {
            var22 += (var1[var15] - this.gain[2] * var17[0][var15] - this.gain[1] * var17[1][var15] - this.gain[0] * var17[2][var15]) * (var1[var15] - this.gain[2] * var17[0][var15] - this.gain[1] * var17[1][var15] - this.gain[0] * var17[2][var15]);
        }
        return var22;
    }
}

