package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class ChatScrollBar
{
    private static Minecraft mc;
    private static GuiChat gc;
    private float mouseLoc = 0.0F;
    private int scrollBarCenter = 0;
    private int barBottomY = 0;
    private int barTopY = 0;
    private int barX = 326;
    private int barMinY = 0;
    private int barMaxY = 0;
    private int lastY = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private static int barHeight = 8;
    private static int barWidth = 5;
    private boolean scrolling = false;

    public ChatScrollBar(GuiChat _gc)
    {
        mc = Minecraft.getMinecraft();
        gc = _gc;
    }

    public void handleMouse()
    {
        boolean adjX = false;
        boolean adjY = false;

        if (Mouse.getEventButton() == 0 && Mouse.isButtonDown(0))
        {
            int adjX1 = Mouse.getEventX() * gc.width / mc.displayWidth;
            int adjY1 = gc.height - Mouse.getEventY() * gc.height / mc.displayHeight - 1;

            if (Math.abs(adjX1 - this.barX) <= barWidth / 2 && adjY1 <= this.barMaxY && adjY1 >= this.barMinY)
            {
                this.scrolling = true;
            }
            else
            {
                this.scrolling = false;
            }
        }
        else if (!Mouse.isButtonDown(0))
        {
            this.scrolling = false;
        }

        int aY = gc.height - Mouse.getEventY() * gc.height / mc.displayHeight - 1;

        if (Math.abs(aY - this.lastY) > 1 && this.scrolling)
        {
            this.scrollBarMouseDrag(aY);
        }
    }

    private void update()
    {
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int clines = TabbyChat.gnc.GetChatHeight() < maxlines ? TabbyChat.gnc.GetChatHeight() : maxlines;
        int oX = 0;

        if (TabbyChat.instance.generalSettings.timeStampEnable.getValue().booleanValue())
        {
            oX = MathHelper.floor_float((float)mc.fontRenderer.getStringWidth(((TimeStampEnum)TabbyChat.instance.generalSettings.timeStampStyle.getValue()).maxTime) * TabbyChat.gnc.getScaleSetting());
        }

        barHeight = MathHelper.floor_float(8.0F * TabbyChat.gnc.getScaleSetting());
        barWidth = MathHelper.floor_float(5.0F * TabbyChat.gnc.getScaleSetting());
        this.barX = 4 + this.offsetX + oX + (int)((float)TabbyChat.gnc.getWidthSetting() * TabbyChat.gnc.getScaleSetting());
        this.barMaxY = gc.height - 34 + this.offsetY;
        this.barMinY = this.barMaxY + 2 - MathHelper.floor_float((float)((clines - 1) * 9) * TabbyChat.gnc.getScaleSetting());
        this.barTopY = this.barMinY + barHeight / 2;
        this.barBottomY = this.barMaxY - barHeight / 2;
        this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0F - this.mouseLoc) * (float)this.barBottomY);
    }

    public void drawScrollBar()
    {
        this.update();
        int minX = this.barX - (barWidth - 1) / 2;
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        GuiChat var10000;

        if (TabbyChat.gnc.GetChatHeight() > maxlines)
        {
            var10000 = gc;
            GuiChat.drawRect(minX, this.scrollBarCenter - barHeight / 2, minX + barWidth, this.scrollBarCenter + barHeight / 2, 1442840575);
        }

        var10000 = gc;
        GuiChat.drawRect(this.barX, this.barMinY, this.barX + 1, this.barMaxY, -1711276033);
    }

    public void scrollBarMouseWheel()
    {
        this.update();
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int blines = TabbyChat.gnc.GetChatHeight();

        if (blines > maxlines)
        {
            this.mouseLoc = (float)TabbyChat.gnc.chatLinesTraveled() / (float)(blines - maxlines);
        }
        else
        {
            this.mouseLoc = 0.0F;
        }

        this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0F - this.mouseLoc) * (float)this.barBottomY);
    }

    public void scrollBarMouseDrag(int _absY)
    {
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int blines = TabbyChat.gnc.GetChatHeight();

        if (blines <= maxlines)
        {
            this.mouseLoc = 0.0F;
        }
        else
        {
            if (_absY < this.barTopY)
            {
                this.mouseLoc = 1.0F;
            }
            else if (_absY > this.barBottomY)
            {
                this.mouseLoc = 0.0F;
            }
            else
            {
                this.mouseLoc = (float)(this.barBottomY - _absY) / (float)(this.barBottomY - this.barTopY);
            }

            float moveInc = 1.0F / (float)(blines - 19);
            float settleInc = 1.0F / (float)(blines - 20);
            int moveLines = (int)Math.floor((double)(this.mouseLoc / moveInc));

            if (moveLines > blines - maxlines)
            {
                moveLines = blines - maxlines;
            }

            TabbyChat.gnc.setVisChatLines(moveLines);
            this.mouseLoc = settleInc * (float)moveLines;
            this.scrollBarCenter = Math.round(this.mouseLoc * (float)this.barTopY + (1.0F - this.mouseLoc) * (float)this.barBottomY);
            this.lastY = _absY;
        }
    }

    public void setOffset(int _x, int _y)
    {
        this.offsetX = _x;
        this.offsetY = _y;
        int maxlines = TabbyChat.gnc.getHeightSetting() / 9;
        int clines = TabbyChat.gnc.GetChatHeight() < maxlines ? TabbyChat.gnc.GetChatHeight() : maxlines;
        this.barX = 324 + _x;
        this.barMinY = mc.currentScreen.height - ((clines - 1) * 9 + 8) - 35 + _y;
        this.barTopY = this.barMinY + barHeight / 2 + _y;
        this.barMaxY = mc.currentScreen.height - 45 + _y;
        this.barBottomY = this.barMaxY - barHeight / 2 + _y;
    }
}
