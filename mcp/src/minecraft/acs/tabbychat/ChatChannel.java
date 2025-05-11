/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiNewChat
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package acs.tabbychat;

import acs.tabbychat.ChatButton;
import acs.tabbychat.TabbyChat;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class ChatChannel {
    private static int nextID = 3600;
    public String title;
    public ChatButton tab;
    public ArrayList<ChatLine> chatLog;
    protected int chanID = nextID++;
    public boolean unread = false;
    public boolean active = false;
    protected boolean hasFilter = false;
    protected boolean hasSpam = false;
    protected int spamCount = 1;

    public ChatChannel() {
        this.chatLog = new ArrayList(100);
    }

    public ChatChannel(int _x, int _y, int _w, int _h, String _title) {
        this();
        this.tab = new ChatButton(this.chanID, _x, _y, _w, _h, _title);
        this.title = _title;
        this.tab.channel = this;
    }

    public ChatChannel(String _title) {
        this(3, 3, Minecraft.func_71410_x().field_71466_p.func_78256_a("<" + _title + ">") + 8, 14, _title);
    }

    public boolean doesButtonEqual(GuiButton btnObj) {
        return this.tab.field_73741_f == btnObj.field_73741_f;
    }

    public int getButtonEnd() {
        return this.tab.field_73746_c + this.tab.width();
    }

    public int getID() {
        return this.chanID;
    }

    public String getDisplayTitle() {
        String label = this.title;
        if (Arrays.asList("Mon pays", "MODO", "Journal", "Avocat").contains(this.title)) {
            label = I18n.func_135053_a((String)("chat.tab." + this.title.toLowerCase().replace(" ", "_")));
        }
        if (this.active) {
            return "[" + label + "]";
        }
        if (this.unread) {
            return "<" + label + ">";
        }
        return label;
    }

    public void setButtonObj(ChatButton btnObj) {
        this.tab = btnObj;
        this.tab.channel = this;
    }

    public String toString() {
        return this.getDisplayTitle();
    }

    public void clear() {
        this.chatLog.clear();
        this.tab.clear();
        this.tab = null;
    }

    public void setButtonLoc(int _x, int _y) {
        this.tab.field_73746_c = _x;
        this.tab.field_73743_d = _y;
    }

    public void trimLog() {
        if (TabbyChat.instance != null && this.chatLog.size() >= Integer.parseInt(TabbyChat.instance.advancedSettings.chatScrollHistory.getValue()) + 5) {
            this.chatLog.subList(this.chatLog.size() - 11, this.chatLog.size() - 1).clear();
        }
    }

    public void unreadNotify(Gui _gui, int _y, int _opacity) {
        float scaleSetting = TabbyChat.gnc.getScaleSetting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)20.0f, (float)0.0f);
        GL11.glScalef((float)scaleSetting, (float)scaleSetting, (float)1.0f);
        TabbyChat.mc.field_71456_v.func_73827_b();
        GuiNewChat.func_73734_a((int)this.tab.field_73746_c, (int)(-this.tab.height() + _y), (int)(this.tab.field_73746_c + this.tab.width()), (int)_y, (int)(0x720000 + (_opacity / 2 << 24)));
        GL11.glEnable((int)3042);
        TabbyChat.mc.field_71456_v.func_73827_b().func_73732_a(TabbyChat.mc.field_71466_p, this.getDisplayTitle(), this.tab.field_73746_c + this.tab.width() / 2, -(this.tab.height() + 8) / 2 + _y, 0xFF0000 + (_opacity << 24));
        GL11.glPopMatrix();
    }
}

