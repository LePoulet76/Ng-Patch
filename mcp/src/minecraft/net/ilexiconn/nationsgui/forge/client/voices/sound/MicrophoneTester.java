package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine.Info;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;

public class MicrophoneTester implements Runnable
{
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

    public MicrophoneTester(VoiceChatClient voiceChat)
    {
        this.voiceChat = voiceChat;
    }

    public void start()
    {
        this.thread = new Thread(this, "Input Device Tester");
        this.thread.setName("Capture");
        this.recording = true;
        this.thread.start();
    }

    public void stop()
    {
        this.recording = false;
        this.thread = null;
    }

    public void toggle()
    {
        if (this.recording)
        {
            this.start();
        }
        else
        {
            this.stop();
        }
    }

    public void run()
    {
        this.duration = 0.0D;
        this.line = this.voiceChat.getSettings().getInputDevice().getLine();

        if (this.line == null)
        {
            VoiceChatClient.getLogger().severe("No line in found, cannot test input device.");
        }
        else
        {
            Info sourceInfo = new Info(SourceDataLine.class, this.format);

            try
            {
                TargetDataLine var10 = this.line;
                var10.open(this.format);
                var10.start();
                SourceDataLine sourceLine = (SourceDataLine)AudioSystem.getLine(sourceInfo);
                sourceLine.open(this.format);
                sourceLine.start();
                byte[] targetData = new byte[var10.getBufferSize() / 5];

                while (this.recording)
                {
                    int numBytesRead = var10.read(targetData, 0, targetData.length);

                    if (numBytesRead == -1)
                    {
                        break;
                    }

                    byte[] boostedTargetData = this.boostVolume(targetData);
                    sourceLine.write(boostedTargetData, 0, numBytesRead);
                    double sum = 0.0D;

                    for (int i = 0; i < numBytesRead; ++i)
                    {
                        sum += (double)(boostedTargetData[i] * boostedTargetData[i]);
                    }

                    if (numBytesRead > 0)
                    {
                        this.currentAmplitude = (float)((int)Math.sqrt(sum / (double)numBytesRead));
                    }
                }

                sourceLine.flush();
                sourceLine.close();
                this.line.flush();
                this.line.close();
            }
            catch (Exception var101)
            {
                System.err.println(var101);
            }
        }
    }

    private byte[] boostVolume(byte[] data)
    {
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer newBuf = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);

        while (buf.hasRemaining())
        {
            int sample = buf.getShort() & 65535;
            newBuf.putShort((short)(sample & 65535));
        }

        return newBuf.array();
    }

    public AudioFormat getFormat()
    {
        return this.format;
    }

    public void setFormat(AudioFormat format)
    {
        this.format = format;
    }

    public Thread getThread()
    {
        return this.thread;
    }

    public double getDuration()
    {
        return this.duration;
    }
}
