/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;

public class MicrophoneTester
implements Runnable {
    private AudioInputStream audioInputStream;
    private AudioFormat format = SoundManager.getUniversalAudioFormat();
    public byte[] byteStream;
    public TargetDataLine line;
    public Thread thread;
    private double duration;
    public boolean recording;
    private VoiceChatClient voiceChat;
    public float currentAmplitude;
    private static final int USHORT_MASK = 65535;

    public MicrophoneTester(VoiceChatClient voiceChat) {
        this.voiceChat = voiceChat;
    }

    public void start() {
        this.thread = new Thread((Runnable)this, "Input Device Tester");
        this.thread.setName("Capture");
        this.recording = true;
        this.thread.start();
    }

    public void stop() {
        this.recording = false;
        this.thread = null;
    }

    public void toggle() {
        if (this.recording) {
            this.start();
        } else {
            this.stop();
        }
    }

    @Override
    public void run() {
        this.duration = 0.0;
        this.line = this.voiceChat.getSettings().getInputDevice().getLine();
        if (this.line == null) {
            VoiceChatClient.getLogger().severe("No line in found, cannot test input device.");
        } else {
            DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, this.format);
            try {
                int numBytesRead;
                TargetDataLine e = this.line;
                e.open(this.format);
                e.start();
                SourceDataLine sourceLine = (SourceDataLine)AudioSystem.getLine(sourceInfo);
                sourceLine.open(this.format);
                sourceLine.start();
                byte[] targetData = new byte[e.getBufferSize() / 5];
                while (this.recording && (numBytesRead = e.read(targetData, 0, targetData.length)) != -1) {
                    byte[] boostedTargetData = this.boostVolume(targetData);
                    sourceLine.write(boostedTargetData, 0, numBytesRead);
                    double sum = 0.0;
                    for (int i = 0; i < numBytesRead; ++i) {
                        sum += (double)(boostedTargetData[i] * boostedTargetData[i]);
                    }
                    if (numBytesRead <= 0) continue;
                    this.currentAmplitude = (int)Math.sqrt(sum / (double)numBytesRead);
                }
                sourceLine.flush();
                sourceLine.close();
                this.line.flush();
                this.line.close();
            }
            catch (Exception var10) {
                System.err.println(var10);
            }
        }
    }

    private byte[] boostVolume(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer newBuf = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        while (buf.hasRemaining()) {
            int sample = buf.getShort() & 0xFFFF;
            newBuf.putShort((short)(sample & 0xFFFF));
        }
        return newBuf.array();
    }

    public AudioFormat getFormat() {
        return this.format;
    }

    public void setFormat(AudioFormat format) {
        this.format = format;
    }

    public Thread getThread() {
        return this.thread;
    }

    public double getDuration() {
        return this.duration;
    }
}

