/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Keyboard
 */
package acs.tabbychat;

import acs.tabbychat.PrefsButton;
import acs.tabbychat.TCSetting;
import acs.tabbychat.TCSettingTextBox;
import acs.tabbychat.TabbyChat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class TCSettingsGUI
extends GuiScreen {
    protected static TabbyChat tc;
    protected static Minecraft mc;
    protected final int margin = 4;
    protected final int line_height = 14;
    public final int displayWidth = 325;
    public final int displayHeight = 180;
    protected int lastOpened = 0;
    protected String name = "";
    protected int bgcolor = 1722148836;
    protected int id = 9000;
    protected static List<TCSettingsGUI> ScreenList;
    public static File tabbyChatDir;
    protected File settingsFile;
    private static final int saveButton = 8901;
    private static final int cancelButton = 8902;

    public TCSettingsGUI() {
        mc = Minecraft.func_71410_x();
        ScreenList.add(this);
    }

    protected TCSettingsGUI(TabbyChat _tc) {
        this();
        tc = _tc;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_73887_h.clear();
        int effLeft = (this.field_73880_f - this.displayWidth) / 2;
        int absLeft = effLeft - this.margin;
        int effTop = (this.field_73881_g - this.displayHeight) / 2;
        int absTop = effTop - this.margin;
        this.lastOpened = TCSettingsGUI.mc.field_71456_v.func_73834_c();
        int effRight = (this.field_73880_f + this.displayWidth) / 2;
        int bW = 40;
        int bH = this.line_height;
        PrefsButton savePrefs = new PrefsButton(8901, effRight - bW, (this.field_73881_g + this.displayHeight) / 2 - bH, bW, bH, "Save");
        this.field_73887_h.add(savePrefs);
        PrefsButton cancelPrefs = new PrefsButton(8902, effRight - 2 * bW - 2, (this.field_73881_g + this.displayHeight) / 2 - bH, bW, bH, "Cancel");
        this.field_73887_h.add(cancelPrefs);
        for (int i = 0; i < ScreenList.size(); ++i) {
            TCSettingsGUI.ScreenList.get((int)i).id = 9000 + i;
            if (ScreenList.get(i) == this) continue;
            this.field_73887_h.add(new PrefsButton(TCSettingsGUI.ScreenList.get((int)i).id, effLeft, effTop + 30 * i, 45, 20, TCSettingsGUI.mc.field_71466_p.func_78269_a(TCSettingsGUI.ScreenList.get((int)i).name, 35) + "..."));
            ((PrefsButton)((Object)this.field_73887_h.get((int)(this.field_73887_h.size() - 1)))).bgcolor = 0;
        }
    }

    public void func_73864_a(int par1, int par2, int par3) {
        for (int i = 0; i < this.field_73887_h.size(); ++i) {
            if (!TCSetting.class.isInstance(this.field_73887_h.get(i))) continue;
            TCSetting tmp = (TCSetting)((Object)this.field_73887_h.get(i));
            if (tmp.type != "textbox" && tmp.type != "enum" && tmp.type != "slider") continue;
            tmp.mouseClicked(par1, par2, par3);
        }
        super.func_73864_a(par1, par2, par3);
    }

    protected void func_73869_a(char par1, int par2) {
        for (int i = 0; i < this.field_73887_h.size(); ++i) {
            if (!TCSetting.class.isInstance(this.field_73887_h.get(i))) continue;
            TCSetting tmp = (TCSetting)((Object)this.field_73887_h.get(i));
            if (tmp.type != "textbox") continue;
            ((TCSettingTextBox)tmp).keyTyped(par1, par2);
        }
        super.func_73869_a(par1, par2);
    }

    public void func_73875_a(GuiButton button) {
        if (TCSetting.class.isInstance(button) && ((TCSetting)button).type != "textbox") {
            ((TCSetting)button).actionPerformed();
        } else if (button.field_73741_f == 8901) {
            for (TCSettingsGUI screen : ScreenList) {
                screen.storeTempVars();
                screen.saveSettingsFile();
            }
            tc.updateDefaults();
            tc.loadPatterns();
            tc.updateFilters();
            mc.func_71373_a((GuiScreen)null);
            if (TCSettingsGUI.tc.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
                tc.resetDisplayedChat();
            }
        } else if (button.field_73741_f == 8902) {
            for (TCSettingsGUI screen : ScreenList) {
                screen.resetTempVars();
            }
            mc.func_71373_a((GuiScreen)null);
            if (TCSettingsGUI.tc.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
                tc.resetDisplayedChat();
            }
        } else {
            for (int i = 0; i < ScreenList.size(); ++i) {
                if (button.field_73741_f != TCSettingsGUI.ScreenList.get((int)i).id) continue;
                mc.func_71373_a((GuiScreen)ScreenList.get(i));
            }
        }
        this.validateButtonStates();
    }

    public void validateButtonStates() {
    }

    protected boolean loadSettingsFile() {
        return false;
    }

    protected void saveSettingsFile() {
    }

    protected void storeTempVars() {
    }

    protected void resetTempVars() {
    }

    protected int rowY(int rowNum) {
        return (this.field_73881_g - this.displayHeight) / 2 + (rowNum - 1) * (this.line_height + this.margin);
    }

    public void func_73863_a(int x, int y, float f) {
        int i;
        boolean unicodeStore = TCSettingsGUI.mc.field_71466_p.func_82883_a();
        if (TCSettingsGUI.tc.generalSettings.tabbyChatEnable.getValue().booleanValue() && TCSettingsGUI.tc.advancedSettings.forceUnicode.getValue().booleanValue()) {
            TCSettingsGUI.mc.field_71466_p.func_78264_a(true);
        }
        int iMargin = (this.line_height - TCSettingsGUI.mc.field_71466_p.field_78288_b) / 2;
        int effLeft = (this.field_73880_f - this.displayWidth) / 2;
        int absLeft = effLeft - this.margin;
        int effTop = (this.field_73881_g - this.displayHeight) / 2;
        int absTop = effTop - this.margin;
        TCSettingsGUI.func_73734_a((int)absLeft, (int)absTop, (int)(absLeft + this.displayWidth + 2 * this.margin), (int)(absTop + this.displayHeight + 2 * this.margin), (int)-2013265920);
        for (i = 0; i < ScreenList.size(); ++i) {
            if (ScreenList.get(i) == this) {
                int delta = TCSettingsGUI.mc.field_71456_v.func_73834_c() - this.lastOpened;
                int tabDist = TCSettingsGUI.mc.field_71466_p.func_78256_a(TCSettingsGUI.ScreenList.get((int)i).name) + 2 * this.margin - 40;
                int curWidth = delta <= 5 ? 45 + delta * tabDist / 5 : tabDist + 45;
                TCSettingsGUI.func_73734_a((int)absLeft, (int)(effTop + 30 * i), (int)(absLeft + curWidth), (int)(effTop + 30 * i + 20), (int)TCSettingsGUI.ScreenList.get((int)i).bgcolor);
                TCSettingsGUI.func_73734_a((int)(absLeft + 45), (int)absTop, (int)(absLeft + 46), (int)(effTop + 30 * i), (int)0x66FFFFFF);
                TCSettingsGUI.func_73734_a((int)(absLeft + 45), (int)(effTop + 30 * i + 20), (int)(absLeft + 46), (int)(absTop + this.displayHeight), (int)0x66FFFFFF);
                this.func_73731_b(TCSettingsGUI.mc.field_71466_p, TCSettingsGUI.mc.field_71466_p.func_78269_a(TCSettingsGUI.ScreenList.get((int)i).name, curWidth), effLeft, effTop + 6 + 30 * i, 0xFFFFFF);
                continue;
            }
            TCSettingsGUI.func_73734_a((int)absLeft, (int)(effTop + 30 * i), (int)(absLeft + 45), (int)(effTop + 30 * i + 20), (int)TCSettingsGUI.ScreenList.get((int)i).bgcolor);
        }
        for (i = 0; i < this.field_73887_h.size(); ++i) {
            ((GuiButton)this.field_73887_h.get(i)).func_73737_a(mc, x, y);
        }
        TCSettingsGUI.mc.field_71466_p.func_78264_a(unicodeStore);
    }

    static {
        ScreenList = new ArrayList<TCSettingsGUI>();
        tabbyChatDir = new File("config/ngchat");
    }
}

