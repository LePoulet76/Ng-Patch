/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Vbr {
    public static final int VBR_MEMORY_SIZE = 5;
    public static final int MIN_ENERGY = 6000;
    public static final float NOISE_POW = 0.3f;
    public static final float[][] nb_thresh = new float[][]{{-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f}, {3.5f, 2.5f, 2.0f, 1.2f, 0.5f, 0.0f, -0.5f, -0.7f, -0.8f, -0.9f, -1.0f}, {10.0f, 6.5f, 5.2f, 4.5f, 3.9f, 3.5f, 3.0f, 2.5f, 2.3f, 1.8f, 1.0f}, {11.0f, 8.8f, 7.5f, 6.5f, 5.0f, 3.9f, 3.9f, 3.9f, 3.5f, 3.0f, 1.0f}, {11.0f, 11.0f, 9.9f, 9.0f, 8.0f, 7.0f, 6.5f, 6.0f, 5.0f, 4.0f, 2.0f}, {11.0f, 11.0f, 11.0f, 11.0f, 9.5f, 9.0f, 8.0f, 7.0f, 6.5f, 5.0f, 3.0f}, {11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 9.5f, 8.5f, 8.0f, 6.5f, 4.0f}, {11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 9.8f, 7.5f, 5.5f}, {8.0f, 5.0f, 3.7f, 3.0f, 2.5f, 2.0f, 1.8f, 1.5f, 1.0f, 0.0f, 0.0f}};
    public static final float[][] hb_thresh = new float[][]{{-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f}, {-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f}, {11.0f, 11.0f, 9.5f, 8.5f, 7.5f, 6.0f, 5.0f, 3.9f, 3.0f, 2.0f, 1.0f}, {11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 9.5f, 8.7f, 7.8f, 7.0f, 6.5f, 4.0f}, {11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 11.0f, 9.8f, 7.5f, 5.5f}};
    public static final float[][] uhb_thresh = new float[][]{{-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f}, {3.9f, 2.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f}};
    private float energy_alpha = 0.1f;
    private float average_energy = 0.0f;
    private float last_energy = 1.0f;
    private float[] last_log_energy;
    private float accum_sum = 0.0f;
    private float last_pitch_coef = 0.0f;
    private float soft_pitch = 0.0f;
    private float last_quality = 0.0f;
    private float noise_level;
    private float noise_accum = (float)(0.05 * Math.pow(6000.0, 0.3f));
    private float noise_accum_count = 0.05f;
    private int consec_noise = 0;

    public Vbr() {
        this.noise_level = this.noise_accum / this.noise_accum_count;
        this.last_log_energy = new float[5];
        for (int var1 = 0; var1 < 5; ++var1) {
            this.last_log_energy[var1] = (float)Math.log(6000.0);
        }
    }

    public float analysis(float[] var1, int var2, int var3, float var4) {
        float var15;
        boolean var10;
        int var5;
        float var6 = 0.0f;
        float var7 = 0.0f;
        float var8 = 0.0f;
        float var9 = 7.0f;
        float var12 = 0.0f;
        for (var5 = 0; var5 < var2 >> 1; ++var5) {
            var7 += var1[var5] * var1[var5];
        }
        for (var5 = var2 >> 1; var5 < var2; ++var5) {
            var8 += var1[var5] * var1[var5];
        }
        var6 = var7 + var8;
        float var11 = (float)Math.log(var6 + 6000.0f);
        for (var5 = 0; var5 < 5; ++var5) {
            var12 += (var11 - this.last_log_energy[var5]) * (var11 - this.last_log_energy[var5]);
        }
        if ((var12 /= 150.0f) > 1.0f) {
            var12 = 1.0f;
        }
        float var13 = 3.0f * (var4 - 0.4f) * Math.abs(var4 - 0.4f);
        this.average_energy = (1.0f - this.energy_alpha) * this.average_energy + this.energy_alpha * var6;
        this.noise_level = this.noise_accum / this.noise_accum_count;
        float var14 = (float)Math.pow(var6, 0.3f);
        if (this.noise_accum_count < 0.06f && var6 > 6000.0f) {
            this.noise_accum = 0.05f * var14;
        }
        if ((var13 >= 0.3f || var12 >= 0.2f || var14 >= 1.2f * this.noise_level) && (var13 >= 0.3f || var12 >= 0.05f || var14 >= 1.5f * this.noise_level) && (var13 >= 0.4f || var12 >= 0.05f || var14 >= 1.2f * this.noise_level) && (var13 >= 0.0f || var12 >= 0.05f)) {
            var10 = true;
            this.consec_noise = 0;
        } else {
            var10 = false;
            ++this.consec_noise;
            var15 = var14 > 3.0f * this.noise_level ? 3.0f * this.noise_level : var14;
            if (this.consec_noise >= 4) {
                this.noise_accum = 0.95f * this.noise_accum + 0.05f * var15;
                this.noise_accum_count = 0.95f * this.noise_accum_count + 0.05f;
            }
        }
        if (var14 < this.noise_level && var6 > 6000.0f) {
            this.noise_accum = 0.95f * this.noise_accum + 0.05f * var14;
            this.noise_accum_count = 0.95f * this.noise_accum_count + 0.05f;
        }
        if (var6 < 30000.0f) {
            var9 -= 0.7f;
            if (var6 < 10000.0f) {
                var9 -= 0.7f;
            }
            if (var6 < 3000.0f) {
                var9 -= 0.7f;
            }
        } else {
            var15 = (float)Math.log((var6 + 1.0f) / (1.0f + this.last_energy));
            float var16 = (float)Math.log((var6 + 1.0f) / (1.0f + this.average_energy));
            if (var16 < -5.0f) {
                var16 = -5.0f;
            }
            if (var16 > 2.0f) {
                var16 = 2.0f;
            }
            if (var16 > 0.0f) {
                var9 += 0.6f * var16;
            }
            if (var16 < 0.0f) {
                var9 += 0.5f * var16;
            }
            if (var15 > 0.0f) {
                if (var15 > 5.0f) {
                    var15 = 5.0f;
                }
                var9 += 0.5f * var15;
            }
            if (var8 > 1.6f * var7) {
                var9 += 0.5f;
            }
        }
        this.last_energy = var6;
        this.soft_pitch = 0.6f * this.soft_pitch + 0.4f * var4;
        if ((var9 = (float)((double)var9 + (double)2.2f * ((double)var4 - 0.4 + ((double)this.soft_pitch - 0.4)))) < this.last_quality) {
            var9 = 0.5f * var9 + 0.5f * this.last_quality;
        }
        if (var9 < 4.0f) {
            var9 = 4.0f;
        }
        if (var9 > 10.0f) {
            var9 = 10.0f;
        }
        if (this.consec_noise >= 3) {
            var9 = 4.0f;
        }
        if (this.consec_noise != 0) {
            var9 -= (float)(1.0 * (Math.log(3.0 + (double)this.consec_noise) - Math.log(3.0)));
        }
        if (var9 < 0.0f) {
            var9 = 0.0f;
        }
        if (var6 < 60000.0f) {
            if (this.consec_noise > 2) {
                var9 -= (float)(0.5 * (Math.log(3.0 + (double)this.consec_noise) - Math.log(3.0)));
            }
            if (var6 < 10000.0f && this.consec_noise > 2) {
                var9 -= (float)(0.5 * (Math.log(3.0 + (double)this.consec_noise) - Math.log(3.0)));
            }
            if (var9 < 0.0f) {
                var9 = 0.0f;
            }
            var9 += (float)(0.3 * Math.log((double)var6 / 60000.0));
        }
        if (var9 < -1.0f) {
            var9 = -1.0f;
        }
        this.last_pitch_coef = var4;
        this.last_quality = var9;
        for (var5 = 4; var5 > 0; --var5) {
            this.last_log_energy[var5] = this.last_log_energy[var5 - 1];
        }
        this.last_log_energy[0] = var11;
        return var9;
    }
}

