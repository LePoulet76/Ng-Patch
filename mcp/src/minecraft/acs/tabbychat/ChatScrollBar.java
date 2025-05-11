/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.input.Mouse
 */
package acs.tabbychat;

import acs.tabbychat.TabbyChat;
import acs.tabbychat.TimeStampEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class ChatScrollBar {
    private static Minecraft mc;
    private static GuiChat gc;
    private float mouseLoc = 0.0f;
    private int scrollBarCenter = 0;
    private int barBottomY = 0;
    private int barTopY = 0;
    private int barX = 326;
    private int barMinY = 0;
    private int barMaxY = 0;
    private int lastY = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private static int barHeight;
    private static int barWidth;
    private boolean scrolling = false;

    public ChatScrollBar(GuiChat _gc) {
        mc = Minecraft.func_71410_x();
        gc = _gc;
    }

    public void handleMouse() {
        int adjX = 0;
        int adjY = 0;
        if (Mouse.getEventButton() == 0 && Mouse.isButtonDown((int)0)) {
            adjX = Mouse.getEventX() * ChatScrollBar.gc.field_73880_f / ChatScrollBar.mc.field_71443_c;
            adjY = ChatScrollBar.gc.field_73881_g - Mouse.getEventY() * ChatScrollBar.gc.field_73881_g / ChatScrollBar.mc.field_71440_d - 1;
            this.scrolling = Math.abs(adjX - this.barX) <= barWidth / 2 && adjY <= this.barMaxY && adjY >= this.barMinY;
        } else if (!Mouse.isButtonDown((int)0)) {
            this.scrolling = false;
        }
        int aY = ChatScrollBar.gc.field_73881_g - Mouse.getEventY() * ChatScrollBar.gc.field_73881_g / ChatScrollBar.mc.field_71440_d - 1;
        if (Math.abs(aY - this.lastY) > 1 && this.scrolling) {
            this.scrollBarMouseDrag(aY);
        }
    }

    private void update() {
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int clines = TabbyChat.gnc.GetChatHeight() < maxlines ? TabbyChat.gnc.GetChatHeight() : maxlines;
        int oX = 0;
        if (TabbyChat.instance.generalSettings.timeStampEnable.getValue().booleanValue()) {
            oX = MathHelper.func_76141_d((float)((float)ChatScrollBar.mc.field_71466_p.func_78256_a(((TimeStampEnum)TabbyChat.instance.generalSettings.timeStampStyle.getValue()).maxTime) * TabbyChat.gnc.getScaleSetting()));
        }
        barHeight = MathHelper.func_76141_d((float)(8.0f * TabbyChat.gnc.getScaleSetting()));
        barWidth = MathHelper.func_76141_d((float)(5.0f * TabbyChat.gnc.getScaleSetting()));
        this.barX = 4 + this.offsetX + oX + (int)((float)TabbyChat.gnc.getWidthSetting() * TabbyChat.gnc.getScaleSetting());
        this.barMaxY = ChatScrollBar.gc.field_73881_g - 34 + this.offsetY;
        this.barMinY = this.barMaxY + 2 - MathHelper.func_76141_d((float)((float)((clines - 1) * 9) * TabbyChat.gnc.getScaleSetting()));
        this.barTopY = this.barMinY + barHeight / 2;
        this.barBottomY = this.barMaxY - barHeight / 2;
        this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0f - this.mouseLoc) * (float)this.barBottomY);
    }

    public void drawScrollBar() {
        this.update();
        int minX = this.barX - (barWidth - 1) / 2;
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        if (TabbyChat.gnc.GetChatHeight() > maxlines) {
            GuiChat.func_73734_a((int)minX, (int)(this.scrollBarCenter - barHeight / 2), (int)(minX + barWidth), (int)(this.scrollBarCenter + barHeight / 2), (int)0x55FFFFFF);
        }
        GuiChat.func_73734_a((int)this.barX, (int)this.barMinY, (int)(this.barX + 1), (int)this.barMaxY, (int)-1711276033);
    }

    public void scrollBarMouseWheel() {
        this.update();
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int blines = TabbyChat.gnc.GetChatHeight();
        this.mouseLoc = blines > maxlines ? (float)TabbyChat.gnc.chatLinesTraveled() / (float)(blines - maxlines) : 0.0f;
        this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0f - this.mouseLoc) * (float)this.barBottomY);
    }

    public void scrollBarMouseDrag(int _absY) {
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int blines = TabbyChat.gnc.GetChatHeight();
        if (blines <= maxlines) {
            this.mouseLoc = 0.0f;
            return;
        }
        this.mouseLoc = _absY < this.barTopY ? 1.0f : (_absY > this.barBottomY ? 0.0f : (float)(this.barBottomY - _absY) / (float)(this.barBottomY - this.barTopY));
        float moveInc = 1.0f / (float)(blines - 19);
        float settleInc = 1.0f / (float)(blines - 20);
        int moveLines = (int)Math.floor(this.mouseLoc / moveInc);
        if (moveLines > blines - maxlines) {
            moveLines = blines - maxlines;
        }
        TabbyChat.gnc.setVisChatLines(moveLines);
        this.mouseLoc = settleInc * (float)moveLines;
        this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0f - this.mouseLoc) * (float)this.barBottomY);
        this.lastY = _absY;
    }

    public void setOffset(int _x, int _y) {
        this.offsetX = _x;
        this.offsetY = _y;
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int clines = TabbyChat.gnc.GetChatHeight() < maxlines ? TabbyChat.gnc.GetChatHeight() : maxlines;
        this.barX = 324 + _x;
        this.barMinY = ChatScrollBar.mc.field_71462_r.field_73881_g - ((clines - 1) * 9 + 8) - 35 + _y;
        this.barTopY = this.barMinY + barHeight / 2 + _y;
        this.barMaxY = ChatScrollBar.mc.field_71462_r.field_73881_g - 45 + _y;
        this.barBottomY = this.barMaxY - barHeight / 2 + _y;
    }

    static {
        barHeight = 8;
        barWidth = 5;
    }
}

