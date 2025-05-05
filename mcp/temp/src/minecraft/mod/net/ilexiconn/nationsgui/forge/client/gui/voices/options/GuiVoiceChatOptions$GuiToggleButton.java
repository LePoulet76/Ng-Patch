package net.ilexiconn.nationsgui.forge.client.gui.voices.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiVoiceChatOptions$GuiToggleButton extends GuiButton {

   public boolean isVoiceActive;


   public GuiVoiceChatOptions$GuiToggleButton(int par1, int par2, int par3, int par4, int par5, boolean isVoiceActive) {
      super(par1, par2, par3, par4, par5, "");
      this.isVoiceActive = isVoiceActive;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      super.func_73737_a(par1Minecraft, par2, par3);
      this.field_73744_e = this.isVoiceActive?I18n.func_135053_a("voice.enabled"):I18n.func_135053_a("voice.disabled");
   }
}
