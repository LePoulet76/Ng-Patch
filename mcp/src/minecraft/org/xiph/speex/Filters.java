/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Filters {
    private int last_pitch;
    private float[] last_pitch_gain = new float[3];
    private float smooth_gain;
    private float[] xx = new float[1024];

    public void init() {
        this.last_pitch = 0;
        this.last_pitch_gain[2] = 0.0f;
        this.last_pitch_gain[1] = 0.0f;
        this.last_pitch_gain[0] = 0.0f;
        this.smooth_gain = 1.0f;
    }

    public static final void bw_lpc(float var0, float[] var1, float[] var2, int var3) {
        float var4 = 1.0f;
        for (int var5 = 0; var5 < var3 + 1; ++var5) {
            var2[var5] = var4 * var1[var5];
            var4 *= var0;
        }
    }

    public static final void filter_mem2(float[] var0, int var1, float[] var2, float[] var3, int var4, int var5, float[] var6, int var7) {
        for (int var8 = 0; var8 < var4; ++var8) {
            float var10 = var0[var1 + var8];
            var0[var1 + var8] = var2[0] * var10 + var6[var7 + 0];
            float var11 = var0[var1 + var8];
            for (int var9 = 0; var9 < var5 - 1; ++var9) {
                var6[var7 + var9] = var6[var7 + var9 + 1] + var2[var9 + 1] * var10 - var3[var9 + 1] * var11;
            }
            var6[var7 + var5 - 1] = var2[var5] * var10 - var3[var5] * var11;
        }
    }

    public static final void filter_mem2(float[] var0, int var1, float[] var2, float[] var3, float[] var4, int var5, int var6, int var7, float[] var8, int var9) {
        for (int var10 = 0; var10 < var6; ++var10) {
            float var12 = var0[var1 + var10];
            var4[var5 + var10] = var2[0] * var12 + var8[0];
            float var13 = var4[var5 + var10];
            for (int var11 = 0; var11 < var7 - 1; ++var11) {
                var8[var9 + var11] = var8[var9 + var11 + 1] + var2[var11 + 1] * var12 - var3[var11 + 1] * var13;
            }
            var8[var9 + var7 - 1] = var2[var7] * var12 - var3[var7] * var13;
        }
    }

    public static final void iir_mem2(float[] var0, int var1, float[] var2, float[] var3, int var4, int var5, int var6, float[] var7) {
        for (int var8 = 0; var8 < var5; ++var8) {
            var3[var4 + var8] = var0[var1 + var8] + var7[0];
            for (int var9 = 0; var9 < var6 - 1; ++var9) {
                var7[var9] = var7[var9 + 1] - var2[var9 + 1] * var3[var4 + var8];
            }
            var7[var6 - 1] = -var2[var6] * var3[var4 + var8];
        }
    }

    public static final void fir_mem2(float[] var0, int var1, float[] var2, float[] var3, int var4, int var5, int var6, float[] var7) {
        for (int var8 = 0; var8 < var5; ++var8) {
            float var10 = var0[var1 + var8];
            var3[var4 + var8] = var2[0] * var10 + var7[0];
            for (int var9 = 0; var9 < var6 - 1; ++var9) {
                var7[var9] = var7[var9 + 1] + var2[var9 + 1] * var10;
            }
            var7[var6 - 1] = var2[var6] * var10;
        }
    }

    public static final void syn_percep_zero(float[] var0, int var1, float[] var2, float[] var3, float[] var4, float[] var5, int var6, int var7) {
        float[] var9 = new float[var7];
        Filters.filter_mem2(var0, var1, var3, var2, var5, 0, var6, var7, var9, 0);
        for (int var8 = 0; var8 < var7; ++var8) {
            var9[var8] = 0.0f;
        }
        Filters.iir_mem2(var5, 0, var4, var5, 0, var6, var7, var9);
    }

    public static final void residue_percep_zero(float[] var0, int var1, float[] var2, float[] var3, float[] var4, float[] var5, int var6, int var7) {
        float[] var9 = new float[var7];
        Filters.filter_mem2(var0, var1, var2, var3, var5, 0, var6, var7, var9, 0);
        for (int var8 = 0; var8 < var7; ++var8) {
            var9[var8] = 0.0f;
        }
        Filters.fir_mem2(var5, 0, var4, var5, 0, var6, var7, var9);
    }

    public void fir_mem_up(float[] var1, float[] var2, float[] var3, int var4, int var5, float[] var6) {
        int var7;
        for (var7 = 0; var7 < var4 / 2; ++var7) {
            this.xx[2 * var7] = var1[var4 / 2 - 1 - var7];
        }
        for (var7 = 0; var7 < var5 - 1; var7 += 2) {
            this.xx[var4 + var7] = var6[var7 + 1];
        }
        for (var7 = 0; var7 < var4; var7 += 4) {
            float var12 = 0.0f;
            float var11 = 0.0f;
            float var10 = 0.0f;
            float var9 = 0.0f;
            float var13 = this.xx[var4 - 4 - var7];
            for (int var8 = 0; var8 < var5; var8 += 4) {
                float var15 = var2[var8];
                float var16 = var2[var8 + 1];
                float var14 = this.xx[var4 - 2 + var8 - var7];
                var9 += var15 * var14;
                var10 += var16 * var14;
                var11 += var15 * var13;
                var12 += var16 * var13;
                var15 = var2[var8 + 2];
                var16 = var2[var8 + 3];
                var13 = this.xx[var4 + var8 - var7];
                var9 += var15 * var13;
                var10 += var16 * var13;
                var11 += var15 * var14;
                var12 += var16 * var14;
            }
            var3[var7] = var9;
            var3[var7 + 1] = var10;
            var3[var7 + 2] = var11;
            var3[var7 + 3] = var12;
        }
        for (var7 = 0; var7 < var5 - 1; var7 += 2) {
            var6[var7 + 1] = this.xx[var7];
        }
    }

    public void comb_filter(float[] var1, int var2, float[] var3, int var4, int var5, int var6, float[] var7, float var8) {
        int var9;
        float var11 = 0.0f;
        float var12 = 0.0f;
        float var16 = 0.0f;
        for (var9 = var2; var9 < var2 + var5; ++var9) {
            var11 += var1[var9] * var1[var9];
        }
        var16 = 0.5f * Math.abs(var7[0] + var7[1] + var7[2] + this.last_pitch_gain[0] + this.last_pitch_gain[1] + this.last_pitch_gain[2]);
        if (var16 > 1.3f) {
            var8 *= 1.3f / var16;
        }
        if (var16 < 0.5f) {
            var8 *= 2.0f * var16;
        }
        float var14 = 1.0f / (float)var5;
        float var15 = 0.0f;
        var9 = 0;
        int var10 = var2;
        while (var9 < var5) {
            var3[var4 + var9] = var1[var10] + var8 * (var15 += var14) * (var7[0] * var1[var10 - var6 + 1] + var7[1] * var1[var10 - var6] + var7[2] * var1[var10 - var6 - 1]) + var8 * (1.0f - var15) * (this.last_pitch_gain[0] * var1[var10 - this.last_pitch + 1] + this.last_pitch_gain[1] * var1[var10 - this.last_pitch] + this.last_pitch_gain[2] * var1[var10 - this.last_pitch - 1]);
            ++var9;
            ++var10;
        }
        this.last_pitch_gain[0] = var7[0];
        this.last_pitch_gain[1] = var7[1];
        this.last_pitch_gain[2] = var7[2];
        this.last_pitch = var6;
        for (var9 = var4; var9 < var4 + var5; ++var9) {
            var12 += var3[var9] * var3[var9];
        }
        float var13 = (float)Math.sqrt(var11 / (0.1f + var12));
        if (var13 < 0.5f) {
            var13 = 0.5f;
        }
        if (var13 > 1.0f) {
            var13 = 1.0f;
        }
        var9 = var4;
        while (var9 < var4 + var5) {
            this.smooth_gain = 0.96f * this.smooth_gain + 0.04f * var13;
            int n = var9++;
            var3[n] = var3[n] * this.smooth_gain;
        }
    }

    public static final void qmf_decomp(float[] var0, float[] var1, float[] var2, float[] var3, int var4, int var5, float[] var6) {
        int var7;
        float[] var11 = new float[var5];
        float[] var12 = new float[var4 + var5 - 1];
        int var13 = var5 - 1;
        int var10 = var5 >> 1;
        for (var7 = 0; var7 < var5; ++var7) {
            var11[var5 - var7 - 1] = var1[var7];
        }
        for (var7 = 0; var7 < var5 - 1; ++var7) {
            var12[var7] = var6[var5 - var7 - 2];
        }
        for (var7 = 0; var7 < var4; ++var7) {
            var12[var7 + var5 - 1] = var0[var7];
        }
        var7 = 0;
        int var9 = 0;
        while (var7 < var4) {
            var2[var9] = 0.0f;
            var3[var9] = 0.0f;
            for (int var8 = 0; var8 < var10; ++var8) {
                int n = var9;
                var2[n] = var2[n] + var11[var8] * (var12[var7 + var8] + var12[var13 + var7 - var8]);
                int n2 = var9;
                var3[n2] = var3[n2] - var11[var8] * (var12[var7 + var8] - var12[var13 + var7 - var8]);
                int n3 = var9;
                var2[n3] = var2[n3] + var11[++var8] * (var12[var7 + var8] + var12[var13 + var7 - var8]);
                int n4 = var9;
                var3[n4] = var3[n4] + var11[var8] * (var12[var7 + var8] - var12[var13 + var7 - var8]);
            }
            var7 += 2;
            ++var9;
        }
        for (var7 = 0; var7 < var5 - 1; ++var7) {
            var6[var7] = var0[var4 - var7 - 1];
        }
    }
}

