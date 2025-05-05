package acs.tabbychat;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class TCSettingsGUI extends GuiScreen
{
    protected static TabbyChat tc;
    protected static Minecraft mc;
    protected final int margin;
    protected final int line_height;
    public final int displayWidth;
    public final int displayHeight;
    protected int lastOpened;
    protected String name;
    protected int bgcolor;
    protected int id;
    protected static List<TCSettingsGUI> ScreenList = new ArrayList();
    public static File tabbyChatDir = new File("config/ngchat");
    protected File settingsFile;
    private static final int saveButton = 8901;
    private static final int cancelButton = 8902;

    public TCSettingsGUI()
    {
        this.margin = 4;
        this.line_height = 14;
        this.displayWidth = 325;
        this.displayHeight = 180;
        this.lastOpened = 0;
        this.name = "";
        this.bgcolor = 1722148836;
        this.id = 9000;
        mc = Minecraft.getMinecraft();
        ScreenList.add(this);
    }

    protected TCSettingsGUI(TabbyChat _tc)
    {
        this();
        tc = _tc;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        int var10000 = this.width;
        this.getClass();
        int effLeft = (var10000 - 325) / 2;
        this.getClass();
        var10000 = effLeft - 4;
        var10000 = this.height;
        this.getClass();
        int effTop = (var10000 - 180) / 2;
        this.getClass();
        var10000 = effTop - 4;
        this.lastOpened = mc.ingameGUI.getUpdateCounter();
        var10000 = this.width;
        this.getClass();
        int effRight = (var10000 + 325) / 2;
        byte bW = 40;
        this.getClass();
        byte bH = 14;
        PrefsButton var11 = new PrefsButton;
        int var10003 = effRight - bW;
        int var10004 = this.height;
        this.getClass();
        var11.<init>(8901, var10003, (var10004 + 180) / 2 - bH, bW, bH, "Save");
        PrefsButton savePrefs = var11;
        this.buttonList.add(savePrefs);
        var11 = new PrefsButton;
        var10003 = effRight - 2 * bW - 2;
        var10004 = this.height;
        this.getClass();
        var11.<init>(8902, var10003, (var10004 + 180) / 2 - bH, bW, bH, "Cancel");
        PrefsButton cancelPrefs = var11;
        this.buttonList.add(cancelPrefs);

        for (int i = 0; i < ScreenList.size(); ++i)
        {
            ((TCSettingsGUI)ScreenList.get(i)).id = 9000 + i;

            if (ScreenList.get(i) != this)
            {
                this.buttonList.add(new PrefsButton(((TCSettingsGUI)ScreenList.get(i)).id, effLeft, effTop + 30 * i, 45, 20, mc.fontRenderer.trimStringToWidth(((TCSettingsGUI)ScreenList.get(i)).name, 35) + "..."));
                ((PrefsButton)this.buttonList.get(this.buttonList.size() - 1)).bgcolor = 0;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int par1, int par2, int par3)
    {
        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            if (TCSetting.class.isInstance(this.buttonList.get(i)))
            {
                TCSetting tmp = (TCSetting)this.buttonList.get(i);

                if (tmp.type == "textbox" || tmp.type == "enum" || tmp.type == "slider")
                {
                    tmp.mouseClicked(par1, par2, par3);
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            if (TCSetting.class.isInstance(this.buttonList.get(i)))
            {
                TCSetting tmp = (TCSetting)this.buttonList.get(i);

                if (tmp.type == "textbox")
                {
                    ((TCSettingTextBox)tmp).keyTyped(par1, par2);
                }
            }
        }

        super.keyTyped(par1, par2);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton button)
    {
        if (TCSetting.class.isInstance(button) && ((TCSetting)button).type != "textbox")
        {
            ((TCSetting)button).actionPerformed();
        }
        else
        {
            Iterator i;
            TCSettingsGUI screen;

            if (button.id == 8901)
            {
                i = ScreenList.iterator();

                while (i.hasNext())
                {
                    screen = (TCSettingsGUI)i.next();
                    screen.storeTempVars();
                    screen.saveSettingsFile();
                }

                tc.updateDefaults();
                tc.loadPatterns();
                tc.updateFilters();
                mc.displayGuiScreen((GuiScreen)null);

                if (tc.generalSettings.tabbyChatEnable.getValue().booleanValue())
                {
                    tc.resetDisplayedChat();
                }
            }
            else if (button.id == 8902)
            {
                i = ScreenList.iterator();

                while (i.hasNext())
                {
                    screen = (TCSettingsGUI)i.next();
                    screen.resetTempVars();
                }

                mc.displayGuiScreen((GuiScreen)null);

                if (tc.generalSettings.tabbyChatEnable.getValue().booleanValue())
                {
                    tc.resetDisplayedChat();
                }
            }
            else
            {
                for (int var4 = 0; var4 < ScreenList.size(); ++var4)
                {
                    if (button.id == ((TCSettingsGUI)ScreenList.get(var4)).id)
                    {
                        mc.displayGuiScreen((GuiScreen)ScreenList.get(var4));
                    }
                }
            }
        }

        this.validateButtonStates();
    }

    public void validateButtonStates() {}

    protected boolean loadSettingsFile()
    {
        return false;
    }

    protected void saveSettingsFile() {}

    protected void storeTempVars() {}

    protected void resetTempVars() {}

    protected int rowY(int rowNum)
    {
        int var10000 = this.height;
        this.getClass();
        var10000 = (var10000 - 180) / 2;
        int var10001 = rowNum - 1;
        this.getClass();
        this.getClass();
        return var10000 + var10001 * (14 + 4);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int x, int y, float f)
    {
        boolean unicodeStore = mc.fontRenderer.getUnicodeFlag();

        if (tc.generalSettings.tabbyChatEnable.getValue().booleanValue() && tc.advancedSettings.forceUnicode.getValue().booleanValue())
        {
            mc.fontRenderer.setUnicodeFlag(true);
        }

        this.getClass();
        int iMargin = (14 - mc.fontRenderer.FONT_HEIGHT) / 2;
        int var10000 = this.width;
        this.getClass();
        int effLeft = (var10000 - 325) / 2;
        this.getClass();
        int absLeft = effLeft - 4;
        var10000 = this.height;
        this.getClass();
        int effTop = (var10000 - 180) / 2;
        this.getClass();
        int absTop = effTop - 4;
        this.getClass();
        int var10002 = absLeft + 325;
        this.getClass();
        var10002 += 2 * 4;
        this.getClass();
        int var10003 = absTop + 180;
        this.getClass();
        drawRect(absLeft, absTop, var10002, var10003 + 2 * 4, -2013265920);
        int i;

        for (i = 0; i < ScreenList.size(); ++i)
        {
            if (ScreenList.get(i) == this)
            {
                int delta = mc.ingameGUI.getUpdateCounter() - this.lastOpened;
                var10000 = mc.fontRenderer.getStringWidth(((TCSettingsGUI)ScreenList.get(i)).name);
                this.getClass();
                int tabDist = var10000 + 2 * 4 - 40;
                int curWidth;

                if (delta <= 5)
                {
                    curWidth = 45 + delta * tabDist / 5;
                }
                else
                {
                    curWidth = tabDist + 45;
                }

                drawRect(absLeft, effTop + 30 * i, absLeft + curWidth, effTop + 30 * i + 20, ((TCSettingsGUI)ScreenList.get(i)).bgcolor);
                drawRect(absLeft + 45, absTop, absLeft + 46, effTop + 30 * i, 1728053247);
                var10000 = absLeft + 45;
                int var10001 = effTop + 30 * i + 20;
                var10002 = absLeft + 46;
                this.getClass();
                drawRect(var10000, var10001, var10002, absTop + 180, 1728053247);
                this.drawString(mc.fontRenderer, mc.fontRenderer.trimStringToWidth(((TCSettingsGUI)ScreenList.get(i)).name, curWidth), effLeft, effTop + 6 + 30 * i, 16777215);
            }
            else
            {
                drawRect(absLeft, effTop + 30 * i, absLeft + 45, effTop + 30 * i + 20, ((TCSettingsGUI)ScreenList.get(i)).bgcolor);
            }
        }

        for (i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(mc, x, y);
        }

        mc.fontRenderer.setUnicodeFlag(unicodeStore);
    }
}
