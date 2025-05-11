/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.xiph.speex.AudioFileWriter;

public class RawWriter
extends AudioFileWriter {
    private OutputStream out;

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    @Override
    public void open(File var1) throws IOException {
        var1.delete();
        this.out = new FileOutputStream(var1);
    }

    @Override
    public void open(String var1) throws IOException {
        this.open(new File(var1));
    }

    @Override
    public void writeHeader(String var1) throws IOException {
    }

    @Override
    public void writePacket(byte[] var1, int var2, int var3) throws IOException {
        this.out.write(var1, var2, var3);
    }
}

