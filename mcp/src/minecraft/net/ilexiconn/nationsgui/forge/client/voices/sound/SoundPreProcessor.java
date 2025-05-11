/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.debug.Statistics;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import org.xiph.speex.SpeexDecoder;

public class SoundPreProcessor {
    VoiceChatClient voiceChat;
    Statistics stats;
    SpeexDecoder decoder;

    public SoundPreProcessor(VoiceChatClient voiceChat, Minecraft mc) {
        this.voiceChat = voiceChat;
        this.stats = VoiceChatClient.getStatistics();
    }

    public boolean process(int id, byte[] encodedSamples, int chunkSize, int global) {
        byte[] var17;
        if (!VoiceChat.getProxyInstance().getSettings().isVoiceEnable() || KeyManager.getInstance().isKeyMuted()) {
            return false;
        }
        if (chunkSize == 0) {
            return false;
        }
        if (chunkSize == -1) {
            VoiceChatClient.getLogger().severe("Sound Pre-Processor has been given incorrect data from network. " + (Object)((Object)this.voiceChat.getSettings().getVoiceServerType()));
            return false;
        }
        if (this.decoder == null) {
            this.decoder = new SpeexDecoder();
            this.decoder.init(0, (int)SoundManager.getUniversalAudioFormat().getSampleRate(), SoundManager.getUniversalAudioFormat().getChannels(), this.voiceChat.getSettings().isPerceptualEnchantmentAllowed());
        }
        Object decodedData = null;
        if (encodedSamples.length <= chunkSize) {
            try {
                this.decoder.processData(encodedSamples, 0, encodedSamples.length);
            }
            catch (StreamCorruptedException var16) {
                var16.printStackTrace();
                return false;
            }
            var17 = new byte[this.decoder.getProcessedDataByteSize()];
            this.decoder.getProcessedData(var17, 0);
        } else {
            List samplesList = SoundPreProcessor.divideArray(encodedSamples, chunkSize);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int e = 0; e < samplesList.size(); ++e) {
                byte[] sample = (byte[])samplesList.get(e);
                SpeexDecoder tempDecoder = new SpeexDecoder();
                tempDecoder.init(0, (int)SoundManager.getUniversalAudioFormat().getSampleRate(), SoundManager.getUniversalAudioFormat().getChannels(), this.voiceChat.getSettings().isPerceptualEnchantmentAllowed());
                try {
                    this.decoder.processData(sample, 0, sample.length);
                }
                catch (StreamCorruptedException var15) {
                    var15.printStackTrace();
                    return false;
                }
                byte[] sampleBuffer = new byte[this.decoder.getProcessedDataByteSize()];
                this.decoder.getProcessedData(sampleBuffer, 0);
                try {
                    baos.write(sampleBuffer);
                    continue;
                }
                catch (IOException var14) {
                    var14.printStackTrace();
                }
            }
            var17 = baos.toByteArray();
            try {
                baos.flush();
                baos.close();
            }
            catch (IOException var13) {
                var13.printStackTrace();
            }
        }
        if (var17 != null) {
            VoiceChatClient.getSoundManager().addQueue(var17, global, id);
            if (this.stats != null) {
                this.stats.addEncodedSamples(encodedSamples.length);
                this.stats.addDecodedSamples(var17.length);
            }
            return true;
        }
        return false;
    }

    public static List divideArray(byte[] source, int chunksize) {
        ArrayList<byte[]> result = new ArrayList<byte[]>();
        for (int start = 0; start < source.length; start += chunksize) {
            int end = Math.min(source.length, start + chunksize);
            result.add(Arrays.copyOfRange(source, start, end));
        }
        return result;
    }
}

