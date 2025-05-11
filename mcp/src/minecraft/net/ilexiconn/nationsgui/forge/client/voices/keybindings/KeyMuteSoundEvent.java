/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package net.ilexiconn.nationsgui.forge.client.voices.keybindings;

import net.ilexiconn.nationsgui.forge.client.voices.keybindings.EnumBinding;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyEvent;
import net.minecraft.client.settings.KeyBinding;

public class KeyMuteSoundEvent
extends KeyEvent {
    private boolean muted;

    public KeyMuteSoundEvent(EnumBinding sound, int i, boolean b) {
        super(sound, i, b);
    }

    @Override
    public void keyDown(KeyBinding var1, boolean var2, boolean var3) {
        this.muted = !this.muted;
    }

    public boolean isMuted() {
        return this.muted;
    }

    @Override
    public void keyUp(KeyBinding var1, boolean var2) {
    }
}

