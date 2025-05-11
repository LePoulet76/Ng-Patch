/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class OggCrc {
    private static int[] crc_lookup = new int[256];

    public static int checksum(int var0, byte[] var1, int var2, int var3) {
        int var4 = var2 + var3;
        while (var2 < var4) {
            var0 = var0 << 8 ^ crc_lookup[var0 >>> 24 & 0xFF ^ var1[var2] & 0xFF];
            ++var2;
        }
        return var0;
    }

    static {
        for (int var0 = 0; var0 < crc_lookup.length; ++var0) {
            int var1 = var0 << 24;
            for (int var2 = 0; var2 < 8; ++var2) {
                if ((var1 & Integer.MIN_VALUE) != 0) {
                    var1 = var1 << 1 ^ 0x4C11DB7;
                    continue;
                }
                var1 <<= 1;
            }
            OggCrc.crc_lookup[var0] = var1 & 0xFFFFFFFF;
        }
    }
}

