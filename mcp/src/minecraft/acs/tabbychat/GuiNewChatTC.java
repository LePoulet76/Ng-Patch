/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatClickData
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiNewChat
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.StringTranslate
 *  net.minecraft.util.StringUtils
 *  org.lwjgl.opengl.GL11
 */
package acs.tabbychat;

import acs.tabbychat.GuiChatTC;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TaggableChatLine;
import acs.tabbychat.TimeStampEnum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickCountryData;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickProfilData;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ChatTagManager;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.DialogOverride;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatClickData;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class GuiNewChatTC
extends GuiNewChat {
    public final Minecraft field_73772_a;
    public ScaledResolution sr;
    public int chatWidth = 320;
    public int chatHeight = 0;
    protected List<String> field_73770_b = new ArrayList<String>();
    protected List<ChatLine> backupLines = new ArrayList<ChatLine>();
    protected List<TaggableChatLine> field_73771_c = new ArrayList<TaggableChatLine>();
    private int scrollOffset = 0;
    private boolean chatScrolled = false;
    public static final GuiNewChatTC me = new GuiNewChatTC();
    public static int mouseXChat = 0;
    public static int mouseYChat = 0;
    private int xOffset = 0;
    private int yOrigin = 0;
    private List<Object[]> tooltipsToDisplay = new ArrayList<Object[]>();
    private List<Object[]> tooltipsBadgeToDisplay = new ArrayList<Object[]>();
    public static final int LINE_SPAWN_ANIMATION_TIME = 100;
    private long spawnAnimationTime = 0L;

    public GuiNewChatTC() {
        this(Minecraft.func_71410_x());
    }

    public GuiNewChatTC(Minecraft par1Minecraft) {
        super(par1Minecraft);
        this.field_73772_a = par1Minecraft;
        this.sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
    }

    public void func_73762_a(int currentTick) {
        boolean unicodeStore = this.field_73772_a.field_71466_p.func_82883_a();
        int lineCounter = 0;
        int visLineCounter = 0;
        if (TabbyChat.instance.generalSettings.tabbyChatEnable.getValue().booleanValue() && ClientProxy.clientConfig.enableUnicode) {
            this.field_73772_a.field_71466_p.func_78264_a(true);
        }
        if (this.field_73772_a.field_71474_y.field_74343_n != 2) {
            this.sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
            this.chatHeight = 0;
            this.chatWidth = 320;
            int maxDisplayedLines = 0;
            boolean chatOpen = false;
            int validLinesDisplayed = 0;
            int numLinesTotal = this.field_73771_c.size();
            float chatOpacity = this.field_73772_a.field_71474_y.field_74357_r * 0.9f + 0.1f;
            float chatScaling = Math.max(0.1f, this.func_96131_h());
            int timeStampOffset = 0;
            int fadeTicks = 200;
            if (numLinesTotal > 0) {
                chatOpen = this.func_73760_d();
            }
            if (TabbyChat.instance.enabled()) {
                if (TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
                    float scaleFactor = chatOpen ? TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0f : TabbyChat.instance.advancedSettings.chatBoxUnfocHeight.getValue().floatValue() / 100.0f;
                    maxDisplayedLines = (int)Math.floor((float)(this.sr.func_78328_b() - 51) * scaleFactor / 9.0f);
                } else {
                    maxDisplayedLines = this.func_96127_i();
                    this.chatWidth = MathHelper.func_76123_f((float)((float)this.func_96126_f() / chatScaling));
                }
                if (ClientProxy.clientConfig.enableTimestamp) {
                    timeStampOffset = this.field_73772_a.field_71466_p.func_78256_a(((TimeStampEnum)TabbyChat.instance.generalSettings.timeStampStyle.getValue()).maxTime);
                }
                if (TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
                    int curWidth = this.sr.func_78326_a() - 14 - timeStampOffset;
                    float screenWidthScale = TabbyChat.instance.advancedSettings.chatBoxWidth.getValue().floatValue() / 100.0f;
                    this.chatWidth = MathHelper.func_76123_f((float)(screenWidthScale * (float)curWidth / chatScaling));
                }
                fadeTicks = TabbyChat.instance.advancedSettings.chatFadeTicks.getValue().intValue();
            } else {
                maxDisplayedLines = this.func_96127_i();
                this.chatWidth = MathHelper.func_76123_f((float)((float)this.func_96126_f() / chatScaling));
            }
            float animationOffset = 0.0f;
            if (this.isAnimating()) {
                float animationStep = Math.min((float)(System.currentTimeMillis() - this.spawnAnimationTime) / 100.0f, 1.0f);
                animationOffset = 9.0f + -9.0f * animationStep;
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)2.0f, (float)20.0f, (float)0.0f);
            GL11.glScalef((float)chatScaling, (float)chatScaling, (float)1.0f);
            int _size = this.field_73771_c.size();
            for (lineCounter = 0; lineCounter + this.scrollOffset < _size && lineCounter < maxDisplayedLines; ++lineCounter) {
                int currentOpacity;
                int lineAge;
                this.chatHeight = lineCounter * 9;
                TaggableChatLine _line = this.field_73771_c.get(lineCounter + this.scrollOffset);
                if (_line == null || (lineAge = currentTick - _line.func_74540_b()) >= fadeTicks && !chatOpen) continue;
                double agePercent = (double)currentTick / (double)fadeTicks;
                agePercent = 10.0 * (1.0 - agePercent);
                agePercent = Math.min(0.0, agePercent);
                agePercent = Math.max(1.0, agePercent);
                agePercent *= agePercent;
                int n = currentOpacity = DialogOverride.displaysDialog() ? 0 : (int)(255.0 * agePercent);
                if (chatOpen) {
                    currentOpacity = 255;
                }
                currentOpacity = (int)((float)currentOpacity * chatOpacity);
                ++validLinesDisplayed;
                if (currentOpacity <= 3) continue;
                ++visLineCounter;
                int xOrigin = 3;
                this.yOrigin = -lineCounter * 9;
                GL11.glPushMatrix();
                if (lineCounter == 0 && this.isAnimating()) {
                    GL11.glTranslated((double)0.0, (double)animationOffset, (double)0.0);
                } else if (ClientProxy.clientConfig.enableChatBackground) {
                    GuiNewChatTC.func_73734_a((int)xOrigin, (int)(this.yOrigin - 9), (int)(xOrigin + this.chatWidth + 4 + timeStampOffset), (int)this.yOrigin, (int)(currentOpacity / 2 << 24));
                }
                GL11.glEnable((int)3042);
                String _chat = _line.func_74538_a();
                if (!this.field_73772_a.field_71474_y.field_74344_o) {
                    _chat = StringUtils.func_76338_a((String)_chat);
                }
                _chat = this.cleanChatLine(_chat);
                this.xOffset = 0;
                int tagI = 0;
                GL11.glPushMatrix();
                GL11.glTranslated((double)xOrigin, (double)(this.yOrigin - 8), (double)0.0);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < _chat.length(); ++i) {
                    char c = _chat.charAt(i);
                    if (c == ChatTagManager.REF_CHAR) {
                        if (stringBuilder.length() > 0) {
                            this.field_73772_a.field_71466_p.func_78261_a(stringBuilder.toString(), this.xOffset, 0, 0xFFFFFF + (currentOpacity << 24));
                            this.xOffset += this.field_73772_a.field_71466_p.func_78256_a(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                        }
                        if (!TabbyChat.instance.channelMap.get((Object)"Global").active || _line.getTags().size() <= tagI) continue;
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)this.xOffset, (double)0.0, (double)0.0);
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        AbstractChatTag abstractChatTag = _line.getTags().get(tagI);
                        abstractChatTag.render(mouseXChat - this.xOffset, -(mouseYChat + this.yOrigin - 8));
                        GL11.glPopMatrix();
                        this.xOffset += abstractChatTag.getWidth();
                        ++tagI;
                        continue;
                    }
                    stringBuilder.append(c);
                }
                if (stringBuilder.length() > 0) {
                    this.field_73772_a.field_71466_p.func_78261_a(stringBuilder.toString(), this.xOffset, 0, 0xFFFFFF + (currentOpacity << 24));
                }
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
            if (chatOpen && !TabbyChat.instance.enabled()) {
                int fontHeight = this.field_73772_a.field_71466_p.field_78288_b;
                GL11.glTranslatef((float)-3.0f, (float)0.0f, (float)0.0f);
                int allLineHeight = numLinesTotal * fontHeight + numLinesTotal;
                int allValidHeight = validLinesDisplayed * fontHeight + validLinesDisplayed;
                if (allLineHeight != allValidHeight) {
                    int scrollPos = this.scrollOffset * allValidHeight / numLinesTotal;
                    int scrollMax = allValidHeight * allValidHeight / allLineHeight;
                    int scrollOpacity = scrollPos > 0 ? 170 : 96;
                    int scrollBarColor = this.chatScrolled ? 0xCC3333 : 0x3333AA;
                    GuiNewChatTC.func_73734_a((int)0, (int)(-scrollPos), (int)2, (int)(-scrollPos - scrollMax), (int)(scrollBarColor + (scrollOpacity << 24)));
                    GuiNewChatTC.func_73734_a((int)2, (int)(-scrollPos), (int)1, (int)(-scrollPos - scrollMax), (int)(0xCCCCCC + (scrollOpacity << 24)));
                }
            }
            for (Object[] objects : this.tooltipsToDisplay) {
                this.drawHoveringText((List)objects[0], (Integer)objects[1], (Integer)objects[2], Minecraft.func_71410_x().field_71466_p);
            }
            for (Object[] objects : this.tooltipsBadgeToDisplay) {
                int badgeCounter = 0;
                ArrayList<String> displayedBadges = new ArrayList<String>();
                if (((List)objects[0]).size() <= 0) continue;
                Gui.func_73734_a((int)((Integer)objects[1]), (int)((Integer)objects[2] - 13), (int)((Integer)objects[1] + ((List)objects[0]).size() * 9 + 7), (int)((Integer)objects[2]), (int)-434562791);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                for (Object badge : (List)objects[0]) {
                    if (badge != null && !((String)badge).isEmpty() && !displayedBadges.contains(badge) && badgeCounter < 3 && NationsGUI.BADGES_RESOURCES.containsKey(badge)) {
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(badge));
                        ModernGui.drawScaledCustomSizeModalRect((Integer)objects[1] + 3 + badgeCounter * 10, (Integer)objects[2] - 13 + 2, 0.0f, 0.0f, 18, 18, 9, 9, 18.0f, 18.0f, false);
                        displayedBadges.add((String)badge);
                    }
                    ++badgeCounter;
                }
            }
            GL11.glPopMatrix();
        }
        if (TabbyChat.instance.enabled() && !this.func_73760_d() && TabbyChat.instance.generalSettings.unreadFlashing.getValue().booleanValue()) {
            TabbyChat.instance.pollForUnread((Gui)this, -visLineCounter * 9, currentTick);
        }
        this.tooltipsToDisplay.clear();
        this.tooltipsBadgeToDisplay.clear();
        this.field_73772_a.field_71466_p.func_78264_a(unicodeStore);
    }

    public String cleanChatLine(String rawChatLine) {
        String result = rawChatLine;
        result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*All[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
        result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*Annonce[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
        result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*RP[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
        result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*Logs[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
        result = result.replaceAll("\\[Ile [0-9]+\\]", "");
        Pattern p = Pattern.compile("((\u00a7.{1})?[^\u00a7]*(\\s|^))" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "((\\s|$))(?!.*\u00bb)", 32);
        Matcher m = p.matcher(result);
        if (m.find()) {
            if (m.groupCount() == 5 && m.group(1) != null && m.group(2) != null && m.group(3) != null && m.group(4) != null && m.group(5) != null) {
                result = m.replaceAll(Matcher.quoteReplacement(m.group(1)) + "\u00a74@" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "\u00a7r" + Matcher.quoteReplacement(m.group(2)) + Matcher.quoteReplacement(m.group(3)));
            } else if (m.groupCount() == 3 && m.group(1) != null && m.group(2) != null && m.group(3) != null) {
                result = m.replaceAll(Matcher.quoteReplacement(m.group(1)) + "\u00a74@" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "\u00a7r" + Matcher.quoteReplacement(m.group(3)));
            }
        }
        return result;
    }

    public void func_73761_a() {
        this.field_73771_c.clear();
        this.backupLines.clear();
        this.field_73770_b.clear();
    }

    public void func_73765_a(String _msg) {
        this.func_73763_a(_msg, 0);
    }

    public void func_73763_a(String _msg, int flag) {
        this.func_96129_a(_msg, flag, this.field_73772_a.field_71456_v.func_73834_c(), false);
        this.field_73772_a.func_98033_al().func_98233_a("[CHAT] " + _msg);
    }

    public void func_96129_a(String _msg, int id, int tick, boolean backupFlag) {
        boolean chatOpen = this.func_73760_d();
        boolean isLineOne = true;
        ArrayList<ChatLine> multiLineChat = new ArrayList<ChatLine>();
        if (id != 0) {
            this.func_73759_c(id);
        }
        int maxWidth = MathHelper.func_76141_d((float)((float)this.func_96126_f() / Math.max(0.1f, this.func_96131_h())));
        if (TabbyChat.instance.enabled()) {
            TabbyChat.instance.checkServer();
            if (TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
                maxWidth = this.chatWidth;
            }
        }
        for (Tuple<String, ArrayList<AbstractChatTag>> next : ChatTagManager.generateMultiLineWithTags(_msg, Math.max(50, maxWidth))) {
            String _line = (String)next.a;
            if (chatOpen && this.scrollOffset > 0) {
                this.chatScrolled = true;
                this.func_73758_b(1);
            }
            if (!isLineOne) {
                _line = " " + _line;
            }
            TaggableChatLine chatLine = new TaggableChatLine(tick, _line, (ArrayList)next.b, id);
            multiLineChat.add(chatLine);
            isLineOne = false;
        }
        if (TabbyChat.instance.enabled()) {
            int next = TabbyChat.instance.processChat(multiLineChat);
        } else {
            int _len = multiLineChat.size();
            for (int i = 0; i < _len; ++i) {
                this.field_73771_c.add(0, (TaggableChatLine)((Object)multiLineChat.get(i)));
                if (backupFlag) continue;
                this.backupLines.add(0, (ChatLine)multiLineChat.get(i));
            }
        }
        int maxChats = TabbyChat.instance.enabled() ? Integer.parseInt(TabbyChat.instance.advancedSettings.chatScrollHistory.getValue()) : 100;
        int chatLineSize = this.field_73771_c.size();
        int cmdLineSize = this.backupLines.size();
        if (chatLineSize >= maxChats + 5) {
            this.field_73771_c.subList(chatLineSize - 11, chatLineSize - 1).clear();
        }
        if (!backupFlag && cmdLineSize >= maxChats + 5) {
            this.backupLines.subList(cmdLineSize - 11, cmdLineSize - 1).clear();
        }
    }

    public void func_96132_b() {
        this.field_73771_c.clear();
        this.func_73764_c();
        for (int i = this.backupLines.size() - 1; i >= 0; --i) {
            ChatLine _cl = this.backupLines.get(i);
            this.func_96129_a(_cl.func_74538_a(), _cl.func_74539_c(), _cl.func_74540_b(), true);
        }
    }

    public List func_73756_b() {
        return this.field_73770_b;
    }

    public void func_73767_b(String _msg) {
        if (this.field_73770_b.isEmpty() || !this.field_73770_b.get(this.field_73770_b.size() - 1).equals(_msg)) {
            this.field_73770_b.add(_msg);
        }
    }

    public void func_73764_c() {
        this.scrollOffset = 0;
        this.chatScrolled = false;
    }

    public void func_73758_b(int _lines) {
        int maxLineDisplay;
        if (TabbyChat.instance.enabled() && TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
            float scaleFactor = this.func_73760_d() ? TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0f : TabbyChat.instance.advancedSettings.chatBoxUnfocHeight.getValue().floatValue() / 100.0f;
            maxLineDisplay = (int)Math.floor((float)(this.sr.func_78328_b() - 51) * scaleFactor / 9.0f);
        } else {
            maxLineDisplay = this.func_96127_i();
        }
        this.scrollOffset += _lines;
        int numLines = this.field_73771_c.size();
        this.scrollOffset = Math.min(this.scrollOffset, numLines - maxLineDisplay);
        if (this.scrollOffset <= 0) {
            this.scrollOffset = 0;
            this.chatScrolled = false;
        }
    }

    public ChatClickCountryData getClickedCountryData(int clickX, int clickY) {
        if (!this.func_73760_d()) {
            return null;
        }
        ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
        int scaleFactor = _sr.func_78325_e();
        float scaleSetting = Math.max(0.1f, this.func_96131_h());
        int clickXRel = clickX / scaleFactor - 3;
        int clickYRel = clickY / scaleFactor - 28;
        clickXRel = MathHelper.func_76141_d((float)((float)clickXRel / scaleSetting));
        clickYRel = MathHelper.func_76141_d((float)((float)clickYRel / scaleSetting));
        if (clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if (clickXRel <= MathHelper.func_76141_d((float)((float)this.chatWidth / scaleSetting)) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
                int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
                if (lineIndex >= displayedLines + this.scrollOffset || this.field_73771_c.get(lineIndex) == null) {
                    return null;
                }
                return new ChatClickCountryData(this.field_73772_a.field_71466_p, this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex);
            }
            return null;
        }
        return null;
    }

    public ChatClickProfilData getClickedProfilData(int clickX, int clickY) {
        if (!this.func_73760_d()) {
            return null;
        }
        ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
        int scaleFactor = _sr.func_78325_e();
        float scaleSetting = Math.max(0.1f, this.func_96131_h());
        int clickXRel = clickX / scaleFactor - 3;
        int clickYRel = clickY / scaleFactor - 28;
        clickXRel = MathHelper.func_76141_d((float)((float)clickXRel / scaleSetting));
        clickYRel = MathHelper.func_76141_d((float)((float)clickYRel / scaleSetting));
        if (clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if (clickXRel <= MathHelper.func_76141_d((float)((float)this.chatWidth / scaleSetting)) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
                int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
                if (lineIndex >= displayedLines + this.scrollOffset || this.field_73771_c.get(lineIndex) == null) {
                    return null;
                }
                return new ChatClickProfilData(this.field_73772_a.field_71466_p, this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex);
            }
            return null;
        }
        return null;
    }

    public ChatClickData func_73766_a(int clickX, int clickY) {
        if (!this.func_73760_d()) {
            return null;
        }
        ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
        int scaleFactor = _sr.func_78325_e();
        float scaleSetting = Math.max(0.1f, this.func_96131_h());
        int clickXRel = clickX / scaleFactor - 2;
        int clickYRel = clickY / scaleFactor - 28;
        clickXRel = MathHelper.func_76141_d((float)((float)clickXRel / scaleSetting));
        clickYRel = MathHelper.func_76141_d((float)((float)clickYRel / scaleSetting));
        if (clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if (clickXRel <= MathHelper.func_76141_d((float)((float)this.chatWidth / scaleSetting)) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
                int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
                if (lineIndex >= displayedLines + this.scrollOffset || this.field_73771_c.size() <= lineIndex || this.field_73771_c.get(lineIndex) == null) {
                    return null;
                }
                int offSetY = 0;
                for (ChatLine chatLine : this.field_73771_c) {
                    if (chatLine instanceof TaggableChatLine) {
                        TaggableChatLine taggableChatLine = (TaggableChatLine)chatLine;
                        String message = taggableChatLine.func_74538_a();
                        StringBuilder stringBuilder = new StringBuilder();
                        int xOffset = 0;
                        int j = 0;
                        for (int i = 0; i < message.length(); ++i) {
                            char c = message.charAt(i);
                            if (c == ChatTagManager.REF_CHAR) {
                                if (stringBuilder.length() > 0) {
                                    xOffset += this.field_73772_a.field_71466_p.func_78256_a(stringBuilder.toString());
                                    stringBuilder = new StringBuilder();
                                }
                                if (taggableChatLine.getTags().size() <= j) continue;
                                AbstractChatTag chatTag = taggableChatLine.getTags().get(j);
                                chatTag.onClick(clickXRel - xOffset, -(clickYRel + this.field_73772_a.field_71466_p.field_78288_b * this.scrollOffset - offSetY - 8));
                                xOffset += chatTag.getWidth();
                                ++j;
                                continue;
                            }
                            stringBuilder.append(c);
                        }
                    }
                    offSetY += 9;
                }
                return new ChatClickData(this.field_73772_a.field_71466_p, (ChatLine)this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex);
            }
            return null;
        }
        return null;
    }

    public void func_73757_a(String par1Str, Object ... par2ArrayOfObj) {
        this.func_73765_a(new StringTranslate().func_74803_a(par1Str, par2ArrayOfObj));
    }

    public boolean func_73760_d() {
        return this.field_73772_a.field_71462_r instanceof GuiChat || this.field_73772_a.field_71462_r instanceof GuiChatTC;
    }

    public void func_73759_c(int _id) {
        ChatLine _cl;
        Iterator<TaggableChatLine> _iter = this.field_73771_c.iterator();
        do {
            if (_iter.hasNext()) continue;
            _iter = this.backupLines.iterator();
            do {
                if (_iter.hasNext()) continue;
                return;
            } while ((_cl = (ChatLine)_iter.next()).func_74539_c() != _id);
            _iter.remove();
            return;
        } while ((_cl = (ChatLine)_iter.next()).func_74539_c() != _id);
        _iter.remove();
    }

    public int getHeightSetting() {
        if (TabbyChat.instance.enabled() && TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
            float scaleFactor = TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0f;
            return (int)Math.floor((float)(this.sr.func_78328_b() - 51) * scaleFactor);
        }
        return GuiNewChatTC.func_96130_b((float)this.field_73772_a.field_71474_y.field_96694_H);
    }

    public int getWidthSetting() {
        return this.chatWidth;
    }

    public float getScaleSetting() {
        return Math.max(0.1f, this.func_96131_h());
    }

    public int GetChatHeight() {
        return this.field_73771_c.size();
    }

    private boolean isAnimating() {
        return ClientProxy.clientConfig.enableChatAnimation && System.currentTimeMillis() - this.spawnAnimationTime < 100L;
    }

    private void startAnimation() {
        this.spawnAnimationTime = System.currentTimeMillis();
    }

    public void addChatLines(List<TaggableChatLine> _add) {
        this.field_73771_c.addAll(_add);
        this.startAnimation();
    }

    public void addChatLines(int _pos, List<TaggableChatLine> _add) {
        this.field_73771_c.addAll(_pos, _add);
        this.startAnimation();
    }

    public void setChatLines(int _pos, List<TaggableChatLine> _add) {
        for (int i = 0; i < _add.size(); ++i) {
            this.field_73771_c.set(_pos + i, new TaggableChatLine(_add.get(i)));
        }
        this.startAnimation();
    }

    public void clearChatLines() {
        this.func_73764_c();
        this.field_73771_c.clear();
    }

    public int chatLinesTraveled() {
        return this.scrollOffset;
    }

    public void setVisChatLines(int _move) {
        this.scrollOffset = _move;
    }

    public int lastUpdate() {
        return ((ChatLine)this.field_73771_c.get(this.field_73771_c.size() - 1)).func_74540_b();
    }

    public void mergeChatLines(List<ChatLine> _new) {
        ArrayList _current = (ArrayList)this.field_73771_c;
        if (_new == null || _new.size() <= 0) {
            return;
        }
        int _c = 0;
        int _n = 0;
        int dt = 0;
        int max = _new.size();
        while (_n < max && _c < _current.size()) {
            dt = _new.get(_n).func_74540_b() - ((TaggableChatLine)((Object)_current.get(_c))).func_74540_b();
            if (dt > 0) {
                _current.add(_c, new TaggableChatLine(_new.get(_n)));
                ++_n;
                continue;
            }
            if (dt == 0) {
                if (((Object)((Object)((TaggableChatLine)((Object)_current.get(_c))))).equals(_new.get(_n))) {
                    ++_c;
                    ++_n;
                    continue;
                }
                ++_c;
                continue;
            }
            ++_c;
        }
        while (_n < max) {
            _current.add(_current.size(), new TaggableChatLine(_new.get(_n)));
            ++_n;
        }
    }

    public static String filterChatLineString(String line) {
        int nbBadges = 0;
        String senderName = GuiNewChatTC.getSenderName(line);
        ArrayList displayedBadges = new ArrayList();
        if (senderName != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty()) {
            for (AbstractSkin badgeSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES)) {
                if (displayedBadges.contains(badgeSkin.getId())) continue;
                ++nbBadges;
            }
        }
        line = nbBadges == 1 ? line.replace("##", "   ") : (nbBadges == 2 ? line.replace("##", "      ") : (nbBadges >= 3 ? line.replace("##", "          ") : line.replace("##", "")));
        Pattern pattern = Pattern.compile(":([a-z0-9]+):");
        Matcher m = pattern.matcher(line);
        while (m.find()) {
            if (!NationsGUI.EMOTES_SYMBOLS.containsKey(m.group(1))) continue;
            line = line.replace(m.group(0), "\u00a70" + NationsGUI.EMOTES_SYMBOLS.get(m.group(1)) + "\u00a7r ");
        }
        return line;
    }

    public static boolean containsRestrictedBadge(String text) {
        for (String badge : Arrays.asList("fondateur", "co-fonda", "respadmin", "admin", "supermodo", "moderateur")) {
            if (!text.contains(badge)) continue;
            return true;
        }
        return false;
    }

    public static String implode(String separator, String ... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; ++i) {
            if (data[i].matches(" *")) continue;
            sb.append(data[i]);
            sb.append(separator);
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }

    public static String getSenderName(String line) {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9_\u00a7]*)[\\s]{1}\u00bb");
        Matcher m = pattern.matcher(line);
        if (m.find()) {
            return m.group(1).replaceAll("\u00a7[0-9a-z]{1}", "");
        }
        return null;
    }

    public void addTooltipToDisplay(List par1List, int par2, int par3) {
        this.tooltipsToDisplay.add(new Object[]{par1List, par2 + this.xOffset, par3 + this.yOrigin - 9});
    }

    public void addTooltipBadgeToDisplay(List par1List, int par2, int par3) {
        this.tooltipsBadgeToDisplay.add(new Object[]{par1List, par2 + this.xOffset, par3 + this.yOrigin - 9});
    }

    public void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (par1List != null && !par1List.isEmpty()) {
            RenderItem itemRenderer = new RenderItem();
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0, (double)0.0, (double)300.0);
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            GL11.glPopMatrix();
            GL11.glDisable((int)2896);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public String drawChatMessagePost(ChatLine chatLine, String formattedText, int x, int y, int alpha) throws IOException {
        Double chatOffsetY;
        String senderName;
        int i = 320;
        int j = 40;
        int chatWidth = MathHelper.func_76141_d((float)(Minecraft.func_71410_x().field_71474_y.field_96692_F * (float)(i - j) + (float)j));
        float chatScale = Math.max(0.1f, Minecraft.func_71410_x().field_71474_y.field_96691_E);
        Float chatHeight = Float.valueOf(Minecraft.func_71410_x().field_71474_y.field_96693_G);
        ArrayList tooltipsToDraw = new ArrayList();
        if (formattedText.contains("\u00bb") && (senderName = GuiNewChatTC.getSenderName(formattedText)) != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int badgeCounter = 0;
            Double scale = 0.5;
            GL11.glPushMatrix();
            GL11.glScaled((double)scale, (double)scale, (double)scale);
            ArrayList<String> arrayList = new ArrayList<String>();
            for (AbstractSkin badgeSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES)) {
                String badgeName = badgeSkin.getId();
                if (!arrayList.contains(badgeName) && badgeCounter < 3) {
                    chatOffsetY = (double)y * (1.0 / scale) - 9.0 * (1.0 / scale);
                    int chatOffsetX = Minecraft.func_71410_x().field_71466_p.func_78256_a(formattedText.split("  ")[0]) * 2 + badgeCounter * 11 * 2 + 10;
                    badgeSkin.renderInGUI(chatOffsetX, chatOffsetY.intValue(), 1.0f, 0.0f);
                    int mouseChatYChanged = mouseYChat - 9;
                    if (NationsGUI.BADGES_TOOLTIPS.containsKey(badgeName) && mouseXChat > chatOffsetX / 2 && mouseXChat <= chatOffsetX / 2 + 9 && mouseChatYChanged > y * -1 - 9 && mouseChatYChanged <= -1 * y) {
                        ArrayList<Object> tooltipInfos = new ArrayList<Object>();
                        tooltipInfos.add(NationsGUI.BADGES_TOOLTIPS.get(badgeName));
                        tooltipInfos.add(mouseXChat);
                        tooltipInfos.add(-1 * mouseYChat + 15);
                        tooltipInfos.add(Minecraft.func_71410_x().field_71466_p);
                        tooltipsToDraw.add(tooltipInfos);
                    }
                    arrayList.add(badgeName);
                }
                ++badgeCounter;
            }
            GL11.glPopMatrix();
        }
        Double scale = 0.6;
        GL11.glPushMatrix();
        GL11.glScaled((double)scale, (double)scale, (double)scale);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        for (Map.Entry<String, String> pair : NationsGUI.EMOTES_SYMBOLS.entrySet()) {
            if (!formattedText.contains(pair.getValue()) || !NationsGUI.EMOTES_RESOURCES.containsKey(pair.getKey())) continue;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.EMOTES_RESOURCES.get(pair.getKey()));
            String[] stringArray = formattedText.split(pair.getValue());
            for (int k = 0; k < stringArray.length - 1; ++k) {
                String textBeforeEmote = "";
                for (int a = 0; a <= k; ++a) {
                    textBeforeEmote = textBeforeEmote + pair.getValue() + stringArray[a];
                }
                Double offsetX = (double)Minecraft.func_71410_x().field_71466_p.func_78256_a(textBeforeEmote) * (1.0 / scale) - (double)Minecraft.func_71410_x().field_71466_p.func_78256_a(pair.getValue()) * (1.0 / scale) - 0.0;
                chatOffsetY = (double)y * (1.0 / scale) - 9.0 * (1.0 / scale) - 1.0;
                int mouseChatYChanged = mouseYChat - 9;
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX.intValue(), chatOffsetY.intValue(), 0, 0, 18, 18, 18.0f, 18.0f, true);
                if (!((double)mouseXChat > offsetX / (1.0 / scale)) || !((double)mouseXChat <= offsetX / (1.0 / scale) + 9.0) || mouseChatYChanged <= y * -1 - 9 || mouseChatYChanged > -1 * y) continue;
                ArrayList<Object> tooltipInfos = new ArrayList<Object>();
                tooltipInfos.add(Collections.singletonList(":" + pair.getKey() + ":"));
                tooltipInfos.add(mouseXChat);
                tooltipInfos.add(-1 * mouseYChat + 15);
                tooltipInfos.add(Minecraft.func_71410_x().field_71466_p);
                tooltipsToDraw.add(tooltipInfos);
            }
            formattedText = GuiNewChatTC.implode(" ", stringArray);
        }
        Collections.reverse(tooltipsToDraw);
        GL11.glScaled((double)(1.0 / scale * 0.9), (double)(1.0 / scale * 0.9), (double)(1.0 / scale * 0.9));
        for (ArrayList arrayList : tooltipsToDraw) {
            this.drawHoveringText((List)arrayList.get(0), (Integer)arrayList.get(1), (Integer)arrayList.get(2), (FontRenderer)arrayList.get(3));
        }
        GL11.glPopMatrix();
        return formattedText;
    }
}

