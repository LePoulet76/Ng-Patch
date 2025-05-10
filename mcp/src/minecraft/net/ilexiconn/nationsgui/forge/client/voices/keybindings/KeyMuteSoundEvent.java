package net.ilexiconn.nationsgui.forge.client.voices.keybindings;

import net.minecraft.client.settings.KeyBinding;

public class KeyMuteSoundEvent extends KeyEvent
{
    private boolean muted;

    public KeyMuteSoundEvent(EnumBinding sound, int i, boolean b)
    {
        super(sound, i, b);
    }

    public void keyDown(KeyBinding var1, boolean var2, boolean var3)
    {
        this.muted = !this.muted;
    }

    public boolean isMuted()
    {
        return this.muted;
    }

    public void keyUp(KeyBinding var1, boolean var2) {}
}
