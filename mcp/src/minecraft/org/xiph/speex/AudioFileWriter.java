/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public abstract class AudioFileWriter {
    public abstract void close() throws IOException;

    public abstract void open(File var1) throws IOException;

    public abstract void open(String var1) throws IOException;

    public abstract void writeHeader(String var1) throws IOException;

    public abstract void writePacket(byte[] var1, int var2, int var3) throws IOException;

    public static int writeOggPageHeader(byte[] var0, int var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
        AudioFileWriter.writeString(var0, var1, "OggS");
        var0[var1 + 4] = 0;
        var0[var1 + 5] = (byte)var2;
        AudioFileWriter.writeLong(var0, var1 + 6, var3);
        AudioFileWriter.writeInt(var0, var1 + 14, var5);
        AudioFileWriter.writeInt(var0, var1 + 18, var6);
        AudioFileWriter.writeInt(var0, var1 + 22, 0);
        var0[var1 + 26] = (byte)var7;
        System.arraycopy(var8, 0, var0, var1 + 27, var7);
        return var7 + 27;
    }

    public static byte[] buildOggPageHeader(int var0, long var1, int var3, int var4, int var5, byte[] var6) {
        byte[] var7 = new byte[var5 + 27];
        AudioFileWriter.writeOggPageHeader(var7, 0, var0, var1, var3, var4, var5, var6);
        return var7;
    }

    public static int writeSpeexHeader(byte[] var0, int var1, int var2, int var3, int var4, boolean var5, int var6) {
        AudioFileWriter.writeString(var0, var1, "Speex   ");
        AudioFileWriter.writeString(var0, var1 + 8, "speex-1.0");
        System.arraycopy(new byte[11], 0, var0, var1 + 17, 11);
        AudioFileWriter.writeInt(var0, var1 + 28, 1);
        AudioFileWriter.writeInt(var0, var1 + 32, 80);
        AudioFileWriter.writeInt(var0, var1 + 36, var2);
        AudioFileWriter.writeInt(var0, var1 + 40, var3);
        AudioFileWriter.writeInt(var0, var1 + 44, 4);
        AudioFileWriter.writeInt(var0, var1 + 48, var4);
        AudioFileWriter.writeInt(var0, var1 + 52, -1);
        AudioFileWriter.writeInt(var0, var1 + 56, 160 << var3);
        AudioFileWriter.writeInt(var0, var1 + 60, var5 ? 1 : 0);
        AudioFileWriter.writeInt(var0, var1 + 64, var6);
        AudioFileWriter.writeInt(var0, var1 + 68, 0);
        AudioFileWriter.writeInt(var0, var1 + 72, 0);
        AudioFileWriter.writeInt(var0, var1 + 76, 0);
        return 80;
    }

    public static byte[] buildSpeexHeader(int var0, int var1, int var2, boolean var3, int var4) {
        byte[] var5 = new byte[80];
        AudioFileWriter.writeSpeexHeader(var5, 0, var0, var1, var2, var3, var4);
        return var5;
    }

    public static int writeSpeexComment(byte[] var0, int var1, String var2) {
        int var3 = var2.length();
        AudioFileWriter.writeInt(var0, var1, var3);
        AudioFileWriter.writeString(var0, var1 + 4, var2);
        AudioFileWriter.writeInt(var0, var1 + var3 + 4, 0);
        return var3 + 8;
    }

    public static byte[] buildSpeexComment(String var0) {
        byte[] var1 = new byte[var0.length() + 8];
        AudioFileWriter.writeSpeexComment(var1, 0, var0);
        return var1;
    }

    public static void writeShort(DataOutput var0, short var1) throws IOException {
        var0.writeByte(0xFF & var1);
        var0.writeByte(0xFF & var1 >>> 8);
    }

    public static void writeInt(DataOutput var0, int var1) throws IOException {
        var0.writeByte(0xFF & var1);
        var0.writeByte(0xFF & var1 >>> 8);
        var0.writeByte(0xFF & var1 >>> 16);
        var0.writeByte(0xFF & var1 >>> 24);
    }

    public static void writeShort(OutputStream var0, short var1) throws IOException {
        var0.write(0xFF & var1);
        var0.write(0xFF & var1 >>> 8);
    }

    public static void writeInt(OutputStream var0, int var1) throws IOException {
        var0.write(0xFF & var1);
        var0.write(0xFF & var1 >>> 8);
        var0.write(0xFF & var1 >>> 16);
        var0.write(0xFF & var1 >>> 24);
    }

    public static void writeLong(OutputStream var0, long var1) throws IOException {
        var0.write((int)(0xFFL & var1));
        var0.write((int)(0xFFL & var1 >>> 8));
        var0.write((int)(0xFFL & var1 >>> 16));
        var0.write((int)(0xFFL & var1 >>> 24));
        var0.write((int)(0xFFL & var1 >>> 32));
        var0.write((int)(0xFFL & var1 >>> 40));
        var0.write((int)(0xFFL & var1 >>> 48));
        var0.write((int)(0xFFL & var1 >>> 56));
    }

    public static void writeShort(byte[] var0, int var1, int var2) {
        var0[var1] = (byte)(0xFF & var2);
        var0[var1 + 1] = (byte)(0xFF & var2 >>> 8);
    }

    public static void writeInt(byte[] var0, int var1, int var2) {
        var0[var1] = (byte)(0xFF & var2);
        var0[var1 + 1] = (byte)(0xFF & var2 >>> 8);
        var0[var1 + 2] = (byte)(0xFF & var2 >>> 16);
        var0[var1 + 3] = (byte)(0xFF & var2 >>> 24);
    }

    public static void writeLong(byte[] var0, int var1, long var2) {
        var0[var1] = (byte)(0xFFL & var2);
        var0[var1 + 1] = (byte)(0xFFL & var2 >>> 8);
        var0[var1 + 2] = (byte)(0xFFL & var2 >>> 16);
        var0[var1 + 3] = (byte)(0xFFL & var2 >>> 24);
        var0[var1 + 4] = (byte)(0xFFL & var2 >>> 32);
        var0[var1 + 5] = (byte)(0xFFL & var2 >>> 40);
        var0[var1 + 6] = (byte)(0xFFL & var2 >>> 48);
        var0[var1 + 7] = (byte)(0xFFL & var2 >>> 56);
    }

    public static void writeString(byte[] var0, int var1, String var2) {
        byte[] var3 = var2.getBytes();
        System.arraycopy(var3, 0, var0, var1, var3.length);
    }
}

