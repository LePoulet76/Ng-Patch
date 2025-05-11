/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.MathUtility;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import org.xiph.speex.SpeexEncoder;

public class Recorder
implements Runnable {
    private boolean recording;
    private Thread thread;
    private VoiceChatClient voiceChat;

    public Recorder(VoiceChatClient voiceChat) {
        this.voiceChat = voiceChat;
    }

    public void set(boolean toggle) {
        if (toggle) {
            this.start();
        } else {
            this.stop();
        }
    }

    public void start() {
        this.thread = new Thread((Runnable)this, "Input Capture Thread");
        this.recording = true;
        this.thread.start();
    }

    public void stop() {
        this.recording = false;
        this.thread = null;
    }

    @Override
    public void run() {
        int minQualty = this.voiceChat.getSettings().minQuality;
        int maxQuality = this.voiceChat.getSettings().maxQuality;
        AudioFormat format = SoundManager.getUniversalAudioFormat();
        TargetDataLine recordingLine = this.voiceChat.getSettings().getInputDevice().getLine();
        if (recordingLine == null) {
            VoiceChat.getLogger().severe("Attempted to record input device, but failed! Java Sound System hasn't found any microphones, check your input devices and restart Minecraft.");
        } else {
            SpeexEncoder encoder = new SpeexEncoder();
            encoder.init(0, (int)MathUtility.clamp(MathUtility.clamp((int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0f), 1.0f, 9.0f), minQualty, maxQuality), (int)format.getSampleRate(), format.getChannels());
            int blockSize = encoder.getFrameSize() * format.getChannels() * 2;
            byte[] normBuffer = new byte[blockSize * 2];
            if (this.startLine(recordingLine)) {
                byte[] boostedBuffer;
                int read;
                while (this.recording && (read = recordingLine.read(normBuffer, 0, blockSize)) != -1 && encoder.processData(boostedBuffer = this.boostVolume(normBuffer), 0, blockSize)) {
                    int encoded = encoder.getProcessedData(boostedBuffer, 0);
                    byte[] encoded_data = new byte[encoded];
                    System.arraycopy(boostedBuffer, 0, encoded_data, 0, encoded);
                    this.voiceChat.getClientNetwork().sendSamples(encoded_data, false);
                }
                this.voiceChat.getClientNetwork().sendSamples(null, true);
                recordingLine.stop();
                recordingLine.close();
            }
        }
    }

    private boolean startLine(TargetDataLine recordingLine) {
        try {
            recordingLine.open();
        }
        catch (LineUnavailableException var3) {
            VoiceChat.getLogger().severe("Failed to open recording line!");
            return false;
        }
        recordingLine.start();
        return true;
    }

    private byte[] boostVolume(byte[] data) {
        int USHORT_MASK = 65535;
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer newBuf = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        while (buf.hasRemaining()) {
            int sample = buf.getShort() & USHORT_MASK;
            newBuf.putShort((short)(sample & USHORT_MASK));
        }
        return newBuf.array();
    }
}

