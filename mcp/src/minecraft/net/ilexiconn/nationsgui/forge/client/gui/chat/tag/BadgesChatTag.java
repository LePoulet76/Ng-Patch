/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import java.util.ArrayList;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import org.lwjgl.opengl.GL11;

public class BadgesChatTag
extends AbstractChatTag {
    String playerName;
    Integer badgeCount = 0;

    public BadgesChatTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.playerName = parameters.get("playerName").split(" ")[parameters.get("playerName").split(" ").length - 1].replaceAll("\u00a70x.{6}", "").replaceAll("\u00a7.{1}", "");
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (!ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(this.playerName, SkinType.BADGES).isEmpty()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int badgeCounter = 0;
            ArrayList<String> displayedBadges = new ArrayList<String>();
            for (AbstractSkin badgeSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(this.playerName, SkinType.BADGES)) {
                if (displayedBadges.contains(badgeSkin.getId()) || badgeCounter >= 2) continue;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                badgeSkin.renderInGUI(0 + badgeCounter * 7, 0, 0.33f, 0.0f);
                displayedBadges.add(badgeSkin.getId());
                if (mouseX >= 2 + badgeCounter * 8 && mouseX <= 2 + badgeCounter * 8 + 8 && mouseY >= 1 && mouseY <= 8) {
                    TabbyChat.gnc.addTooltipToDisplay(NationsGUI.BADGES_TOOLTIPS.get(badgeSkin.getId()), mouseX, mouseY);
                }
                ++badgeCounter;
            }
            this.badgeCount = badgeCounter;
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
    }

    @Override
    public int getWidth() {
        return this.badgeCount * 6;
    }
}

