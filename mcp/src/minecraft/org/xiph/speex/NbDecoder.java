/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import java.io.StreamCorruptedException;
import java.util.Random;
import org.xiph.speex.Bits;
import org.xiph.speex.Decoder;
import org.xiph.speex.Filters;
import org.xiph.speex.Inband;
import org.xiph.speex.Lsp;
import org.xiph.speex.NbCodec;
import org.xiph.speex.SbCodec;
import org.xiph.speex.Stereo;

public class NbDecoder
extends NbCodec
implements Decoder {
    private float[] innov2;
    private int count_lost;
    private int last_pitch;
    private float last_pitch_gain;
    private float[] pitch_gain_buf;
    private int pitch_gain_buf_idx;
    private float last_ol_gain;
    protected Random random = new Random();
    protected Stereo stereo = new Stereo();
    protected Inband inband = new Inband(this.stereo);
    protected boolean enhanced = true;

    @Override
    public void init(int var1, int var2, int var3, int var4) {
        super.init(var1, var2, var3, var4);
        this.filters.init();
        this.innov2 = new float[40];
        this.count_lost = 0;
        this.last_pitch = 40;
        this.last_pitch_gain = 0.0f;
        this.pitch_gain_buf = new float[3];
        this.pitch_gain_buf_idx = 0;
        this.last_ol_gain = 0.0f;
    }

    @Override
    public int decode(Bits var1, float[] var2) throws StreamCorruptedException {
        int var3;
        int var14;
        int var6 = 0;
        float[] var8 = new float[3];
        float var9 = 0.0f;
        float var10 = 0.0f;
        int var11 = 40;
        float var12 = 0.0f;
        float var13 = 0.0f;
        if (var1 == null && this.dtx_enabled != 0) {
            this.submodeID = 0;
        } else {
            int var7;
            if (var1 == null) {
                this.decodeLost(var2);
                return 0;
            }
            do {
                if (var1.unpack(1) != 0) {
                    var7 = var1.unpack(3);
                    var14 = SbCodec.SB_FRAME_SIZE[var7];
                    if (var14 < 0) {
                        throw new StreamCorruptedException("Invalid sideband mode encountered (1st sideband): " + var7);
                    }
                    var1.advance(var14 -= 4);
                    if (var1.unpack(1) != 0) {
                        var7 = var1.unpack(3);
                        var14 = SbCodec.SB_FRAME_SIZE[var7];
                        if (var14 < 0) {
                            throw new StreamCorruptedException("Invalid sideband mode encountered. (2nd sideband): " + var7);
                        }
                        var1.advance(var14 -= 4);
                        if (var1.unpack(1) != 0) {
                            throw new StreamCorruptedException("More than two sideband layers found");
                        }
                    }
                }
                if ((var7 = var1.unpack(4)) == 15) {
                    return 1;
                }
                if (var7 == 14) {
                    this.inband.speexInbandRequest(var1);
                    continue;
                }
                if (var7 == 13) {
                    this.inband.userInbandRequest(var1);
                    continue;
                }
                if (var7 <= 8) continue;
                throw new StreamCorruptedException("Invalid mode encountered: " + var7);
            } while (var7 > 8);
            this.submodeID = var7;
        }
        System.arraycopy(this.frmBuf, this.frameSize, this.frmBuf, 0, this.bufSize - this.frameSize);
        System.arraycopy(this.excBuf, this.frameSize, this.excBuf, 0, this.bufSize - this.frameSize);
        if (this.submodes[this.submodeID] == null) {
            Filters.bw_lpc(0.93f, this.interp_qlpc, this.lpc, 10);
            float var26 = 0.0f;
            for (var3 = 0; var3 < this.frameSize; ++var3) {
                var26 += this.innov[var3] * this.innov[var3];
            }
            var26 = (float)Math.sqrt(var26 / (float)this.frameSize);
            for (var3 = this.excIdx; var3 < this.excIdx + this.frameSize; ++var3) {
                this.excBuf[var3] = 3.0f * var26 * (this.random.nextFloat() - 0.5f);
            }
            this.first = 1;
            Filters.iir_mem2(this.excBuf, this.excIdx, this.lpc, this.frmBuf, this.frmIdx, this.frameSize, this.lpcSize, this.mem_sp);
            var2[0] = this.frmBuf[this.frmIdx] + this.preemph * this.pre_mem;
            for (var3 = 1; var3 < this.frameSize; ++var3) {
                var2[var3] = this.frmBuf[this.frmIdx + var3] + this.preemph * var2[var3 - 1];
            }
            this.pre_mem = var2[this.frameSize - 1];
            this.count_lost = 0;
            return 0;
        }
        this.submodes[this.submodeID].lsqQuant.unquant(this.qlsp, this.lpcSize, var1);
        if (this.count_lost != 0) {
            float var26 = 0.0f;
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                var26 += Math.abs(this.old_qlsp[var3] - this.qlsp[var3]);
            }
            float var15 = (float)(0.6 * Math.exp(-0.2 * (double)var26));
            var3 = 0;
            while (var3 < 2 * this.lpcSize) {
                int n = var3++;
                this.mem_sp[n] = this.mem_sp[n] * var15;
            }
        }
        if (this.first != 0 || this.count_lost != 0) {
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.old_qlsp[var3] = this.qlsp[var3];
            }
        }
        if (this.submodes[this.submodeID].lbr_pitch != -1) {
            var6 = this.min_pitch + var1.unpack(7);
        }
        if (this.submodes[this.submodeID].forced_pitch_gain != 0) {
            var14 = var1.unpack(4);
            var10 = 0.066667f * (float)var14;
        }
        var14 = var1.unpack(5);
        var9 = (float)Math.exp((double)var14 / 3.5);
        if (this.submodeID == 1) {
            int var27 = var1.unpack(4);
            this.dtx_enabled = var27 == 15 ? 1 : 0;
        }
        if (this.submodeID > 1) {
            this.dtx_enabled = 0;
        }
        for (int var4 = 0; var4 < this.nbSubframes; ++var4) {
            float var23;
            int var31;
            int var28;
            float var21;
            int var27 = this.subframeSize * var4;
            int var16 = this.frmIdx + var27;
            int var17 = this.excIdx + var27;
            float var18 = (1.0f + (float)var4) / (float)this.nbSubframes;
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.interp_qlsp[var3] = (1.0f - var18) * this.old_qlsp[var3] + var18 * this.qlsp[var3];
            }
            Lsp.enforce_margin(this.interp_qlsp, this.lpcSize, 0.002f);
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.interp_qlsp[var3] = (float)Math.cos(this.interp_qlsp[var3]);
            }
            this.m_lsp.lsp2lpc(this.interp_qlsp, this.interp_qlpc, this.lpcSize);
            if (this.enhanced) {
                float var19 = 0.9f;
                float var20 = this.submodes[this.submodeID].lpc_enh_k1;
                var21 = this.submodes[this.submodeID].lpc_enh_k2;
                float var22 = (1.0f - (1.0f - var19 * var20) / (1.0f - var19 * var21)) / var19;
                Filters.bw_lpc(var20, this.interp_qlpc, this.awk1, this.lpcSize);
                Filters.bw_lpc(var21, this.interp_qlpc, this.awk2, this.lpcSize);
                Filters.bw_lpc(var22, this.interp_qlpc, this.awk3, this.lpcSize);
            }
            var18 = 1.0f;
            this.pi_gain[var4] = 0.0f;
            for (var3 = 0; var3 <= this.lpcSize; ++var3) {
                int n = var4;
                this.pi_gain[n] = this.pi_gain[n] + var18 * this.interp_qlpc[var3];
                var18 = -var18;
            }
            for (var3 = 0; var3 < this.subframeSize; ++var3) {
                this.excBuf[var17 + var3] = 0.0f;
            }
            if (this.submodes[this.submodeID].lbr_pitch != -1) {
                int var30 = this.submodes[this.submodeID].lbr_pitch;
                if (var30 != 0) {
                    int var29;
                    var28 = var6 - var30 + 1;
                    if (var28 < this.min_pitch) {
                        var28 = this.min_pitch;
                    }
                    if ((var29 = var6 + var30) > this.max_pitch) {
                        var29 = this.max_pitch;
                    }
                } else {
                    var28 = var6;
                }
            } else {
                var28 = this.min_pitch;
                int n = this.max_pitch;
            }
            int var5 = this.submodes[this.submodeID].ltp.unquant(this.excBuf, var17, var28, var10, this.subframeSize, var8, var1, this.count_lost, var27, this.last_pitch_gain);
            if (this.count_lost != 0 && var9 < this.last_ol_gain) {
                var21 = var9 / (this.last_ol_gain + 1.0f);
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    int n = this.excIdx + var3;
                    this.excBuf[n] = this.excBuf[n] * var21;
                }
            }
            Math.abs(var8[0] + var8[1] + var8[2]);
            var18 = Math.abs(var8[1]);
            var18 = var8[0] > 0.0f ? (var18 += var8[0]) : (float)((double)var18 - 0.5 * (double)var8[0]);
            var18 = var8[2] > 0.0f ? (var18 += var8[2]) : (float)((double)var18 - 0.5 * (double)var8[0]);
            var13 += var18;
            if (var18 > var12) {
                var11 = var5;
                var12 = var18;
            }
            for (var3 = var31 = var4 * this.subframeSize; var3 < var31 + this.subframeSize; ++var3) {
                this.innov[var3] = 0.0f;
            }
            if (this.submodes[this.submodeID].have_subframe_gain == 3) {
                int var30 = var1.unpack(3);
                var23 = (float)((double)var9 * Math.exp(exc_gain_quant_scal3[var30]));
            } else if (this.submodes[this.submodeID].have_subframe_gain == 1) {
                int var30 = var1.unpack(1);
                var23 = (float)((double)var9 * Math.exp(exc_gain_quant_scal1[var30]));
            } else {
                var23 = var9;
            }
            if (this.submodes[this.submodeID].innovation != null) {
                this.submodes[this.submodeID].innovation.unquant(this.innov, var31, this.subframeSize, var1);
            }
            var3 = var31;
            while (var3 < var31 + this.subframeSize) {
                int n = var3++;
                this.innov[n] = this.innov[n] * var23;
            }
            if (this.submodeID == 1) {
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    this.excBuf[var17 + var3] = 0.0f;
                }
                while (this.voc_offset < this.subframeSize) {
                    if (this.voc_offset >= 0) {
                        this.excBuf[var17 + this.voc_offset] = (float)Math.sqrt(1.0f * (float)var6);
                    }
                    this.voc_offset += var6;
                }
                this.voc_offset -= this.subframeSize;
                float var24 = 0.5f + 2.0f * (var10 - 0.6f);
                if (var24 < 0.0f) {
                    var24 = 0.0f;
                }
                if (var24 > 1.0f) {
                    var24 = 1.0f;
                }
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    float var25 = this.excBuf[var17 + var3];
                    this.excBuf[var17 + var3] = 0.8f * var24 * this.excBuf[var17 + var3] * var9 + 0.6f * var24 * this.voc_m1 * var9 + 0.5f * var24 * this.innov[var31 + var3] - 0.5f * var24 * this.voc_m2 + (1.0f - var24) * this.innov[var31 + var3];
                    this.voc_m1 = var25;
                    this.voc_m2 = this.innov[var31 + var3];
                    this.voc_mean = 0.95f * this.voc_mean + 0.05f * this.excBuf[var17 + var3];
                    int n = var17 + var3;
                    this.excBuf[n] = this.excBuf[n] - this.voc_mean;
                }
            } else {
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    int n = var17 + var3;
                    this.excBuf[n] = this.excBuf[n] + this.innov[var31 + var3];
                }
            }
            if (this.submodes[this.submodeID].double_codebook != 0) {
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    this.innov2[var3] = 0.0f;
                }
                this.submodes[this.submodeID].innovation.unquant(this.innov2, 0, this.subframeSize, var1);
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    this.innov2[var3] = (float)((double)this.innov2[var3] * (double)var23 * 0.45454545454545453);
                }
                for (var3 = 0; var3 < this.subframeSize; ++var3) {
                    int n = var17 + var3;
                    this.excBuf[n] = this.excBuf[n] + this.innov2[var3];
                }
            }
            for (var3 = 0; var3 < this.subframeSize; ++var3) {
                this.frmBuf[var16 + var3] = this.excBuf[var17 + var3];
            }
            if (this.enhanced && this.submodes[this.submodeID].comb_gain > 0.0f) {
                this.filters.comb_filter(this.excBuf, var17, this.frmBuf, var16, this.subframeSize, var5, var8, this.submodes[this.submodeID].comb_gain);
            }
            if (this.enhanced) {
                Filters.filter_mem2(this.frmBuf, var16, this.awk2, this.awk1, this.subframeSize, this.lpcSize, this.mem_sp, this.lpcSize);
                Filters.filter_mem2(this.frmBuf, var16, this.awk3, this.interp_qlpc, this.subframeSize, this.lpcSize, this.mem_sp, 0);
                continue;
            }
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.mem_sp[this.lpcSize + var3] = 0.0f;
            }
            Filters.iir_mem2(this.frmBuf, var16, this.interp_qlpc, this.frmBuf, var16, this.subframeSize, this.lpcSize, this.mem_sp);
        }
        var2[0] = this.frmBuf[this.frmIdx] + this.preemph * this.pre_mem;
        for (var3 = 1; var3 < this.frameSize; ++var3) {
            var2[var3] = this.frmBuf[this.frmIdx + var3] + this.preemph * var2[var3 - 1];
        }
        this.pre_mem = var2[this.frameSize - 1];
        for (var3 = 0; var3 < this.lpcSize; ++var3) {
            this.old_qlsp[var3] = this.qlsp[var3];
        }
        this.first = 0;
        this.count_lost = 0;
        this.last_pitch = var11;
        this.last_pitch_gain = 0.25f * var13;
        this.pitch_gain_buf[this.pitch_gain_buf_idx++] = this.last_pitch_gain;
        if (this.pitch_gain_buf_idx > 2) {
            this.pitch_gain_buf_idx = 0;
        }
        this.last_ol_gain = var9;
        return 0;
    }

    public int decodeLost(float[] var1) {
        int var2;
        float var3;
        float var5;
        float var4 = (float)Math.exp(-0.04 * (double)this.count_lost * (double)this.count_lost);
        float f = this.pitch_gain_buf[0] < this.pitch_gain_buf[1] ? (this.pitch_gain_buf[1] < this.pitch_gain_buf[2] ? this.pitch_gain_buf[1] : (this.pitch_gain_buf[0] < this.pitch_gain_buf[2] ? this.pitch_gain_buf[2] : this.pitch_gain_buf[0])) : (this.pitch_gain_buf[2] < this.pitch_gain_buf[1] ? this.pitch_gain_buf[1] : (var5 = this.pitch_gain_buf[2] < this.pitch_gain_buf[0] ? this.pitch_gain_buf[2] : this.pitch_gain_buf[0]));
        if (var5 < this.last_pitch_gain) {
            this.last_pitch_gain = var5;
        }
        if ((var3 = this.last_pitch_gain) > 0.95f) {
            var3 = 0.95f;
        }
        var3 *= var4;
        System.arraycopy(this.frmBuf, this.frameSize, this.frmBuf, 0, this.bufSize - this.frameSize);
        System.arraycopy(this.excBuf, this.frameSize, this.excBuf, 0, this.bufSize - this.frameSize);
        for (int var6 = 0; var6 < this.nbSubframes; ++var6) {
            float var10;
            int var7 = this.subframeSize * var6;
            int var8 = this.frmIdx + var7;
            int var9 = this.excIdx + var7;
            if (this.enhanced) {
                float var12;
                float var11;
                var10 = 0.9f;
                if (this.submodes[this.submodeID] != null) {
                    var11 = this.submodes[this.submodeID].lpc_enh_k1;
                    var12 = this.submodes[this.submodeID].lpc_enh_k2;
                } else {
                    var12 = 0.7f;
                    var11 = 0.7f;
                }
                float var13 = (1.0f - (1.0f - var10 * var11) / (1.0f - var10 * var12)) / var10;
                Filters.bw_lpc(var11, this.interp_qlpc, this.awk1, this.lpcSize);
                Filters.bw_lpc(var12, this.interp_qlpc, this.awk2, this.lpcSize);
                Filters.bw_lpc(var13, this.interp_qlpc, this.awk3, this.lpcSize);
            }
            var10 = 0.0f;
            for (var2 = 0; var2 < this.frameSize; ++var2) {
                var10 += this.innov[var2] * this.innov[var2];
            }
            var10 = (float)Math.sqrt(var10 / (float)this.frameSize);
            for (var2 = 0; var2 < this.subframeSize; ++var2) {
                this.excBuf[var9 + var2] = var3 * this.excBuf[var9 + var2 - this.last_pitch] + var4 * (float)Math.sqrt(1.0f - var3) * 3.0f * var10 * (this.random.nextFloat() - 0.5f);
            }
            for (var2 = 0; var2 < this.subframeSize; ++var2) {
                this.frmBuf[var8 + var2] = this.excBuf[var9 + var2];
            }
            if (this.enhanced) {
                Filters.filter_mem2(this.frmBuf, var8, this.awk2, this.awk1, this.subframeSize, this.lpcSize, this.mem_sp, this.lpcSize);
                Filters.filter_mem2(this.frmBuf, var8, this.awk3, this.interp_qlpc, this.subframeSize, this.lpcSize, this.mem_sp, 0);
                continue;
            }
            for (var2 = 0; var2 < this.lpcSize; ++var2) {
                this.mem_sp[this.lpcSize + var2] = 0.0f;
            }
            Filters.iir_mem2(this.frmBuf, var8, this.interp_qlpc, this.frmBuf, var8, this.subframeSize, this.lpcSize, this.mem_sp);
        }
        var1[0] = this.frmBuf[0] + this.preemph * this.pre_mem;
        for (var2 = 1; var2 < this.frameSize; ++var2) {
            var1[var2] = this.frmBuf[var2] + this.preemph * var1[var2 - 1];
        }
        this.pre_mem = var1[this.frameSize - 1];
        this.first = 0;
        ++this.count_lost;
        this.pitch_gain_buf[this.pitch_gain_buf_idx++] = var3;
        if (this.pitch_gain_buf_idx > 2) {
            this.pitch_gain_buf_idx = 0;
        }
        return 0;
    }

    @Override
    public void decodeStereo(float[] var1, int var2) {
        this.stereo.decode(var1, var2);
    }

    @Override
    public void setPerceptualEnhancement(boolean var1) {
        this.enhanced = var1;
    }

    @Override
    public boolean getPerceptualEnhancement() {
        return this.enhanced;
    }
}

