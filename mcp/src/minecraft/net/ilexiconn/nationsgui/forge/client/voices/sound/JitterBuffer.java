/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound;

import javax.sound.sampled.AudioFormat;

public class JitterBuffer {
    private byte[] buffer;
    private AudioFormat format;
    private int jitter;
    private int threshold;

    public JitterBuffer(AudioFormat format, int jitter) {
        this.format = format;
        this.updateJitter(jitter);
    }

    public void updateJitter(int size) {
        this.jitter = size;
        this.threshold = this.getSizeInBytes(this.format, size);
        if (this.buffer == null) {
            this.buffer = this.threshold != 0 ? new byte[3 * this.threshold] : new byte[320];
        }
    }

    private int getSizeInBytes(AudioFormat fmt, int size) {
        int s = (int)(fmt.getSampleRate() / 1000.0f);
        int sampleSize = (int)((float)(fmt.getSampleSizeInBits() / 8) * 0.5f);
        return sampleSize != 0 ? s * size / sampleSize : 0;
    }

    public void push(byte[] data) {
        this.write(data);
    }

    public byte[] get() {
        return this.buffer;
    }

    public boolean isReady() {
        return this.buffer.length > this.threshold;
    }

    private void write(byte[] write) {
        byte[] result = new byte[this.buffer.length + write.length];
        System.arraycopy(this.buffer, 0, result, 0, this.buffer.length);
        System.arraycopy(write, 0, result, this.buffer.length, write.length);
        this.buffer = result;
    }

    public void clearBuffer(int jitterSize) {
        this.buffer = new byte[0];
        this.updateJitter(jitterSize);
    }
}

