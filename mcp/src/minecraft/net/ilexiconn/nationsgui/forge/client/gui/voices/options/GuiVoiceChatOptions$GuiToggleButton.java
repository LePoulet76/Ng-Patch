package net.ilexiconn.nationsgui.forge.client.gui.voices.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiVoiceChatOptions$GuiToggleButton extends GuiButton
{
    public boolean isVoiceActive;

    public GuiVoiceChatOptions$GuiToggleButton(int par1, int par2, int par3, int par4, int par5, boolean isVoiceActive)
    {
        super(par1, par2, par3, par4, par5, "");
        this.isVoiceActive = isVoiceActive;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        super.drawButton(par1Minecraft, par2, par3);
        this.displayString = this.isVoiceActive ? I18n.getString("voice.enabled") : I18n.getString("voice.disabled");
    }
}
