/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import java.io.StreamCorruptedException;
import org.xiph.speex.Bits;
import org.xiph.speex.Decoder;
import org.xiph.speex.NbDecoder;
import org.xiph.speex.SbDecoder;

public class SpeexDecoder {
    public static final String VERSION = "Java Speex Decoder v0.9.7 ($Revision: 1.4 $)";
    private int sampleRate = 0;
    private int channels = 0;
    private float[] decodedData;
    private short[] outputData;
    private int outputSize;
    private Bits bits = new Bits();
    private Decoder decoder;
    private int frameSize;

    public boolean init(int var1, int var2, int var3, boolean var4) {
        switch (var1) {
            case 0: {
                this.decoder = new NbDecoder();
                ((NbDecoder)this.decoder).nbinit();
                break;
            }
            case 1: {
                this.decoder = new SbDecoder();
                ((SbDecoder)this.decoder).wbinit();
                break;
            }
            case 2: {
                this.decoder = new SbDecoder();
                ((SbDecoder)this.decoder).uwbinit();
                break;
            }
            default: {
                return false;
            }
        }
        this.decoder.setPerceptualEnhancement(var4);
        this.frameSize = this.decoder.getFrameSize();
        this.sampleRate = var2;
        this.channels = var3;
        int var5 = var2 * var3;
        this.decodedData = new float[var5 * 2];
        this.outputData = new short[var5 * 2];
        this.outputSize = 0;
        this.bits.init();
        return true;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public int getChannels() {
        return this.channels;
    }

    public int getProcessedData(byte[] var1, int var2) {
        int var3;
        if (this.outputSize <= 0) {
            return this.outputSize;
        }
        for (var3 = 0; var3 < this.outputSize; ++var3) {
            int var4 = var2 + (var3 << 1);
            var1[var4] = (byte)(this.outputData[var3] & 0xFF);
            var1[var4 + 1] = (byte)(this.outputData[var3] >> 8 & 0xFF);
        }
        var3 = this.outputSize * 2;
        this.outputSize = 0;
        return var3;
    }

    public int getProcessedData(short[] var1, int var2) {
        if (this.outputSize <= 0) {
            return this.outputSize;
        }
        System.arraycopy(this.outputData, 0, var1, var2, this.outputSize);
        int var3 = this.outputSize;
        this.outputSize = 0;
        return var3;
    }

    public int getProcessedDataByteSize() {
        return this.outputSize * 2;
    }

    public void processData(byte[] var1, int var2, int var3) throws StreamCorruptedException {
        if (var1 == null) {
            this.processData(true);
        } else {
            this.bits.read_from(var1, var2, var3);
            this.processData(false);
        }
    }

    public void processData(boolean var1) throws StreamCorruptedException {
        int var2;
        if (var1) {
            this.decoder.decode(null, this.decodedData);
        } else {
            this.decoder.decode(this.bits, this.decodedData);
        }
        if (this.channels == 2) {
            this.decoder.decodeStereo(this.decodedData, this.frameSize);
        }
        for (var2 = 0; var2 < this.frameSize * this.channels; ++var2) {
            if (this.decodedData[var2] > 32767.0f) {
                this.decodedData[var2] = 32767.0f;
                continue;
            }
            if (!(this.decodedData[var2] < -32768.0f)) continue;
            this.decodedData[var2] = -32768.0f;
        }
        var2 = 0;
        while (var2 < this.frameSize * this.channels) {
            this.outputData[this.outputSize] = this.decodedData[var2] > 0.0f ? (short)((double)this.decodedData[var2] + 0.5) : (short)((double)this.decodedData[var2] - 0.5);
            ++var2;
            ++this.outputSize;
        }
    }
}

