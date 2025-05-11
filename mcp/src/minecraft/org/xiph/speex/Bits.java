/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Bits {
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private byte[] bytes;
    private int bytePtr;
    private int bitPtr;

    public void init() {
        this.bytes = new byte[1024];
        this.bytePtr = 0;
        this.bitPtr = 0;
    }

    public void advance(int var1) {
        this.bytePtr += var1 >> 3;
        this.bitPtr += var1 & 7;
        if (this.bitPtr > 7) {
            this.bitPtr -= 8;
            ++this.bytePtr;
        }
    }

    protected void setBuffer(byte[] var1) {
        this.bytes = var1;
    }

    public int peek() {
        return (this.bytes[this.bytePtr] & 0xFF) >> 7 - this.bitPtr & 1;
    }

    public void read_from(byte[] var1, int var2, int var3) {
        for (int var4 = 0; var4 < var3; ++var4) {
            this.bytes[var4] = var1[var2 + var4];
        }
        this.bytePtr = 0;
        this.bitPtr = 0;
    }

    public int unpack(int var1) {
        int var2 = 0;
        while (var1 != 0) {
            var2 <<= 1;
            var2 |= (this.bytes[this.bytePtr] & 0xFF) >> 7 - this.bitPtr & 1;
            ++this.bitPtr;
            if (this.bitPtr == 8) {
                this.bitPtr = 0;
                ++this.bytePtr;
            }
            --var1;
        }
        return var2;
    }

    public void pack(int var1, int var2) {
        int var4;
        int var3 = var1;
        while (this.bytePtr + (var2 + this.bitPtr >> 3) >= this.bytes.length) {
            var4 = this.bytes.length * 2;
            byte[] var5 = new byte[var4];
            System.arraycopy(this.bytes, 0, var5, 0, this.bytes.length);
            this.bytes = var5;
        }
        while (var2 > 0) {
            var4 = var3 >> var2 - 1 & 1;
            this.bytes[this.bytePtr] = (byte)(this.bytes[this.bytePtr] | var4 << 7 - this.bitPtr);
            ++this.bitPtr;
            if (this.bitPtr == 8) {
                this.bitPtr = 0;
                ++this.bytePtr;
            }
            --var2;
        }
    }

    public byte[] getBuffer() {
        return this.bytes;
    }

    public int getBufferSize() {
        return this.bytePtr + (this.bitPtr > 0 ? 1 : 0);
    }
}

