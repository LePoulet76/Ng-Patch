/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.GuiNewChatTC;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmoteChatTag
extends AbstractChatTag {
    private String id;
    private ResourceLocation resourceLocation;

    public EmoteChatTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.id = parameters.get("id");
        if (this.id == null) {
            throw new Exception("[ChatEngine] Emote/badge id is missing");
        }
        this.resourceLocation = this.getResourceList().get(this.id);
        if (this.resourceLocation == null) {
            throw new Exception("[ChatEngine] Invalid emote/badge id");
        }
    }

    protected HashMap<String, ResourceLocation> getResourceList() {
        return NationsGUI.EMOTES_RESOURCES;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.resourceLocation != null) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(this.resourceLocation);
            GL11.glPushMatrix();
            GL11.glScalef((float)0.25f, (float)0.25f, (float)0.25f);
            ModernGui.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 0, 0, 32, 32, 32.0f, 32.0f, true);
            GL11.glPopMatrix();
            if (mouseX >= 0 && mouseX <= this.getWidth() && mouseY >= 0 && mouseY <= 9) {
                GuiNewChatTC.me.addTooltipToDisplay(Collections.singletonList(this.id), mouseX, mouseY);
            }
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
    }

    @Override
    public int getWidth() {
        return 8;
    }
}

