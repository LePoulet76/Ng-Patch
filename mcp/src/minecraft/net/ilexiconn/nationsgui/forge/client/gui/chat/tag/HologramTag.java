/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractActionTag;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

public class HologramTag
extends AbstractActionTag {
    private String url;
    private URL actionUrl = null;

    public HologramTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.url = parameters.get("src");
        if (this.url == null) {
            throw new Exception("[ChatEngin] No URL defined to image");
        }
        if (parameters.get("action") != null && (parameters.get("action").matches("https://glor\\.cc/.*") || parameters.get("action").matches("https://nationsglory.fr/.*"))) {
            try {
                this.actionUrl = new URL(parameters.get("action"));
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.url.matches("https://static\\.nationsglory\\.fr/.*\\.png.*") || this.url.matches("http://localhost/.*\\.png") || this.url.matches("https://apiv2.nationsglory.fr/.*")) {
            int width = 320;
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(-width / 4 + 8, 0, 0, 0, width, 32, width, 32, width, 32.0f, false, this.url);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
    }

    @Override
    public int getLineHeight() {
        return 4;
    }

    @Override
    public int getWidth() {
        return 0;
    }
}

