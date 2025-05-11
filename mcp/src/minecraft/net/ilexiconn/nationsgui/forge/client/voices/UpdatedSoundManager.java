/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.audio.SoundManager
 *  paulscode.sound.SoundSystem
 *  paulscode.sound.SoundSystemConfig
 *  paulscode.sound.codecs.CodecJOrbis
 *  paulscode.sound.codecs.CodecWav
 *  paulscode.sound.libraries.LibraryLWJGLOpenAL
 */
package net.ilexiconn.nationsgui.forge.client.voices;

import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.minecraft.client.audio.SoundManager;
import ovr.paulscode.sound.libraries.LibraryLWJGLOpenAL;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;

public class UpdatedSoundManager {
    SoundSystem soundSystem;
    SoundManager soundManager;
    VoiceChatClient voiceChat;

    public UpdatedSoundManager(VoiceChatClient voiceChatClient, SoundManager soundManager) {
        this.voiceChat = voiceChatClient;
        this.soundManager = soundManager;
        this.soundSystem = soundManager.field_77381_a;
    }

    public void init() {
        try {
            SoundSystemConfig.removeLibrary(paulscode.sound.libraries.LibraryLWJGLOpenAL.class);
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec((String)"ogg", CodecJOrbis.class);
            SoundSystemConfig.setCodec((String)"wav", CodecWav.class);
        }
        catch (Exception var2) {
            var2.printStackTrace();
        }
        this.soundManager.field_77381_a = new SoundSystem();
        VoiceChatClient.getLogger().info("Replaced SoundSystem with " + this.soundManager.field_77381_a + ", libraries got " + SoundSystemConfig.getLibraries());
    }
}

