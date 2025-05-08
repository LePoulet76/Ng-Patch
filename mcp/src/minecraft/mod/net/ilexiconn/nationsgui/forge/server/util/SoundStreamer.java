package net.ilexiconn.nationsgui.forge.server.util;

/**
 * Stub serveur pour SoundStreamer, sans logique audio ni client.
 */
public class SoundStreamer implements Runnable {
    public SoundStreamer(String source) {}
    public void setLooping(boolean looping) {}
    public void setVolume(float volume) {}
    public void stop() {}
    public void softStop() {}
    public void run() {}
}
