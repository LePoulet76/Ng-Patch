/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class CountryFlagChatTag
extends AbstractChatTag {
    String countryName;
    String countryColor;

    public CountryFlagChatTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.countryName = parameters.get("name");
        this.countryColor = parameters.get("color");
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.countryName != null && !this.countryName.contains("Wilderness") && !this.countryName.isEmpty()) {
            ClientProxy.loadCountryFlag(this.countryName);
            if (ClientProxy.flagsTexture.containsKey(this.countryName)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.countryName).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 156, 78, 12, 7, 156.0f, 78.0f, false);
                GL11.glEnable((int)3042);
            }
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (mouseX >= 2 && mouseX <= 14 && mouseY >= 0 && mouseY <= 8) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(this.countryName));
        }
    }

    @Override
    public int getWidth() {
        return this.countryName != null && !this.countryName.contains("Wilderness") && !this.countryName.isEmpty() ? 15 : 0;
    }
}

