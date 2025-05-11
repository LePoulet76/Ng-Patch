/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class PlayerChatTag
extends AbstractChatTag {
    String playerName;
    int customColor = 0;

    public PlayerChatTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.playerName = parameters.get("name");
        if (!this.playerName.startsWith("\u00a7")) {
            this.customColor = 16113331;
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.customColor != 0) {
            Minecraft.func_71410_x().field_71466_p.func_78261_a(this.playerName, 1, 0, this.customColor);
        } else {
            Minecraft.func_71410_x().field_71466_p.func_78261_a(this.playerName, 1, 0, 0xFFFFFF);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (!this.playerName.isEmpty() && mouseX >= 2 && mouseX <= Minecraft.func_71410_x().field_71466_p.func_78256_a(this.playerName) && mouseY >= 0 && mouseY <= 8) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.playerName, ""));
        }
    }

    @Override
    public int getWidth() {
        return Minecraft.func_71410_x().field_71466_p.func_78256_a(this.playerName);
    }
}

