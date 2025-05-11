/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.VQ;

public class Stereo {
    public static final int SPEEX_INBAND_STEREO = 9;
    public static final float[] e_ratio_quant = new float[]{0.25f, 0.315f, 0.397f, 0.5f};
    private float balance = 1.0f;
    private float e_ratio = 0.5f;
    private float smooth_left = 1.0f;
    private float smooth_right = 1.0f;

    public static void encode(Bits var0, float[] var1, int var2) {
        float var5 = 0.0f;
        float var6 = 0.0f;
        float var7 = 0.0f;
        for (int var3 = 0; var3 < var2; ++var3) {
            var5 += var1[2 * var3] * var1[2 * var3];
            var6 += var1[2 * var3 + 1] * var1[2 * var3 + 1];
            var1[var3] = 0.5f * (var1[2 * var3] + var1[2 * var3 + 1]);
            var7 += var1[var3] * var1[var3];
        }
        float var8 = (var5 + 1.0f) / (var6 + 1.0f);
        float var9 = var7 / (1.0f + var5 + var6);
        var0.pack(14, 5);
        var0.pack(9, 4);
        var8 = (float)(4.0 * Math.log(var8));
        if (var8 > 0.0f) {
            var0.pack(0, 1);
        } else {
            var0.pack(1, 1);
        }
        var8 = (float)Math.floor(0.5f + Math.abs(var8));
        if (var8 > 30.0f) {
            var8 = 31.0f;
        }
        var0.pack((int)var8, 5);
        int var4 = VQ.index(var9, e_ratio_quant, 4);
        var0.pack(var4, 2);
    }

    public void decode(float[] var1, int var2) {
        int var3;
        float var4 = 0.0f;
        for (var3 = var2 - 1; var3 >= 0; --var3) {
            var4 += var1[var3] * var1[var3];
        }
        float var7 = var4 / this.e_ratio;
        float var5 = var7 * this.balance / (1.0f + this.balance);
        float var6 = var7 - var5;
        var5 = (float)Math.sqrt(var5 / (var4 + 0.01f));
        var6 = (float)Math.sqrt(var6 / (var4 + 0.01f));
        for (var3 = var2 - 1; var3 >= 0; --var3) {
            float var8 = var1[var3];
            this.smooth_left = 0.98f * this.smooth_left + 0.02f * var5;
            this.smooth_right = 0.98f * this.smooth_right + 0.02f * var6;
            var1[2 * var3] = this.smooth_left * var8;
            var1[2 * var3 + 1] = this.smooth_right * var8;
        }
    }

    public void init(Bits var1) {
        float var2 = 1.0f;
        if (var1.unpack(1) != 0) {
            var2 = -1.0f;
        }
        int var3 = var1.unpack(5);
        this.balance = (float)Math.exp((double)var2 * 0.25 * (double)var3);
        var3 = var1.unpack(2);
        this.e_ratio = e_ratio_quant[var3];
    }
}

