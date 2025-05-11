/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractActionTag;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;
import net.minecraft.network.packet.Packet;

public class ImageTag
extends AbstractActionTag {
    private String url;
    private URL actionUrl = null;
    private String cmd = null;

    public ImageTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.url = parameters.get("src");
        if (this.url == null) {
            throw new Exception("[ChatEngin] No URL defined to image");
        }
        if (parameters.get("action") != null) {
            if (parameters.get("action").matches("https://glor\\.cc/.*") || parameters.get("action").matches("https://nationsglory.fr/.*")) {
                try {
                    this.actionUrl = new URL(parameters.get("action"));
                }
                catch (MalformedURLException malformedURLException) {}
            } else if (parameters.get("action").equals("cmd")) {
                this.cmd = parameters.get("value");
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.url.matches("https://static\\.nationsglory\\.fr/.*\\.png.*") || this.url.matches("http://localhost/.*\\.png") || this.url.matches("https://apiv2.nationsglory.fr/.*")) {
            int offsetX = 320 - TabbyChat.gnc.chatWidth;
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(0 - (offsetX /= 2), 0, 0, 0, 1280, 128, 320, 32, 1280.0f, 128.0f, false, this.url);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        int width = TabbyChat.gnc.chatWidth;
        if (mouseX >= 0 && mouseX <= width && mouseY >= 0 && mouseY <= this.getLineHeight() * 8) {
            if (this.actionUrl != null) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, this.actionUrl.toURI());
                }
                catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (this.cmd != null) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket(this.cmd, 0)));
            } else {
                this.doAction();
            }
        }
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

