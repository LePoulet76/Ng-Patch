package net.ilexiconn.nationsgui.forge.client.voices;

import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class UpdatedSoundManager
{
    SoundSystem soundSystem;
    SoundManager soundManager;
    VoiceChatClient voiceChat;

    public UpdatedSoundManager(VoiceChatClient voiceChatClient, SoundManager soundManager)
    {
        this.voiceChat = voiceChatClient;
        this.soundManager = soundManager;
        this.soundSystem = soundManager.sndSystem;
    }

    public void init()
    {
        try
        {
            SoundSystemConfig.removeLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.addLibrary(ovr.paulscode.sound.libraries.LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        }
        catch (Exception var2)
        {
            var2.printStackTrace();
        }

        this.soundManager.sndSystem = new SoundSystem();
        VoiceChatClient.getLogger().info("Replaced SoundSystem with " + this.soundManager.sndSystem + ", libraries got " + SoundSystemConfig.getLibraries());
    }
}
