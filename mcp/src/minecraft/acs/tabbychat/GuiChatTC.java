/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatClickData
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiConfirmOpenLink
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet203AutoComplete
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package acs.tabbychat;

import acs.tabbychat.ChatButton;
import acs.tabbychat.ChatChannel;
import acs.tabbychat.ChatScrollBar;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickCountryData;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickProfilData;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChatTabSetChatPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatClickData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet203AutoComplete;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiChatTC
extends GuiChat {
    public String historyBuffer = "";
    public String field_73900_q = "";
    public int field_73899_c = -1;
    private boolean playerNamesFound = false;
    private boolean waitingOnPlayerNames = false;
    private int playerNameIndex = 0;
    private List foundPlayerNames = new ArrayList();
    private URI clickedURI = null;
    public GuiTextField field_73901_a;
    public List<GuiTextField> inputList = new ArrayList<GuiTextField>(3);
    public ChatScrollBar scrollBar;
    public GuiButton field_73883_a = null;
    public int field_85042_b = 0;
    public long field_85043_c = 0L;
    public int field_92018_d = 0;
    public float field_73735_i = 0.0f;
    public ScaledResolution sr;
    public static GuiChatTC me;
    public static final TabbyChat tc;
    private int heightModifier = 0;
    private boolean displayText = false;
    public static final int BOX_WIDTH = 400;
    public static final int BOX_HEIGHT = 250;
    private ScrollBar scrollBarObj;
    private List<String> lines = new ArrayList<String>();
    private RenderItem itemRenderer = new RenderItem();
    private HashMap<Integer, String> mappingButtonIdEmote = new HashMap();
    private HashMap<Integer, String> mappingButtonIdAnim = new HashMap();
    private boolean displayEmotesGui = false;

    public GuiChatTC() {
        this.field_73882_e = Minecraft.func_71410_x();
        this.field_73886_k = this.field_73882_e.field_71466_p;
        me = this;
        this.sr = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
    }

    public GuiChatTC(String par1Str) {
        this.field_73900_q = par1Str;
    }

    public void func_73866_w_() {
        Double offsetY;
        Keyboard.enableRepeatEvents((boolean)true);
        this.sr = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
        this.field_73887_h.clear();
        this.mappingButtonIdEmote.clear();
        this.inputList.clear();
        this.field_73880_f = this.sr.func_78326_a();
        this.field_73881_g = this.sr.func_78328_b();
        tc.checkServer();
        if (tc.enabled()) {
            this.drawChatTabs();
            if (this.scrollBar == null) {
                this.scrollBar = new ChatScrollBar(this);
            }
            this.scrollBar.drawScrollBar();
        } else if (!Minecraft.func_71410_x().func_71356_B()) {
            tc.updateButtonLocations();
            this.field_73887_h.add(GuiChatTC.tc.channelMap.get((Object)"Global").tab);
        }
        this.field_73899_c = TabbyChat.gnc.func_73756_b().size();
        this.field_73901_a = new GuiTextField(this.field_73886_k, 4, this.field_73881_g - 12, this.field_73880_f - 4, 12);
        this.field_73901_a.func_73804_f(500);
        this.field_73901_a.func_73786_a(false);
        this.field_73901_a.func_73796_b(true);
        this.field_73901_a.func_73782_a(this.field_73900_q);
        this.field_73901_a.func_73805_d(true);
        this.inputList.add(0, this.field_73901_a);
        if (!tc.enabled()) {
            return;
        }
        for (int i = 1; i < 3; ++i) {
            GuiTextField placeholder = new GuiTextField(this.field_73886_k, 4, this.field_73881_g - 12 * (i + 1), this.field_73880_f, 12);
            placeholder.func_73804_f(500);
            placeholder.func_73786_a(false);
            placeholder.func_73796_b(false);
            placeholder.func_73782_a("");
            placeholder.func_73805_d(true);
            placeholder.func_73790_e(false);
            this.inputList.add(i, placeholder);
        }
        int y = 30;
        if (ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
            if (!this.displayText) {
                if (!ClientData.objectives.isEmpty()) {
                    int btnIndex = 10;
                    for (Objective objective : ClientData.objectives) {
                        this.field_73887_h.add(new Button(btnIndex, 4, y, 112, 29));
                        y += 34;
                        ++btnIndex;
                    }
                }
                this.scrollBarObj = null;
            } else {
                this.heightModifier = ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() != null ? 10 : 0;
                this.scrollBarObj = new ScrollBar(this.field_73880_f / 2 + 200 - 15, this.field_73881_g / 2 - 125 + 30, 185);
                if (ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() == null) {
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                } else {
                    this.field_73887_h.add(new SimpleButton(4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a((String)"objectives.validate")));
                }
            }
        }
        this.field_73887_h.add(new EmoteOpenButton(5, this.field_73880_f - 34, this.field_73881_g - 14, 12, 12));
        int buttonIndex = 0;
        int emoteWidth = this.field_73880_f - 131 + buttonIndex % 5 * 22;
        int emoteHeight = this.field_73881_g - 134 + buttonIndex / 5 * 20;
        for (Map.Entry<String, String> pair : NationsGUI.EMOTES_SYMBOLS.entrySet()) {
            if (!NationsGUI.EMOTES_RESOURCES.containsKey(pair.getKey())) continue;
            this.field_73887_h.add(new EmoteButton(100 + buttonIndex, emoteWidth, emoteHeight, 22, 20, pair.getKey()));
            this.mappingButtonIdEmote.put(100 + buttonIndex, pair.getKey());
            emoteWidth = this.field_73880_f - 131 + ++buttonIndex % 5 * 22;
            emoteHeight = this.field_73881_g - 134 + buttonIndex / 5 * 20;
        }
        if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
            offsetY = (double)this.field_73881_g * 0.4 + 78.0;
            this.field_73887_h.add(new VoidButton(6, this.field_73880_f - 120 + 13, offsetY.intValue(), 94, 15));
            this.field_73887_h.add(new VoidButton(7, this.field_73880_f - 120 + 13, offsetY.intValue() + 20, 94, 15));
        } else if (!ClientData.currentJumpLocation.isEmpty()) {
            offsetY = (double)this.field_73881_g * 0.4 + 80.0;
            this.field_73887_h.add(new VoidButton(6, this.field_73880_f - 140, offsetY.intValue(), 140, 16));
        }
    }

    public void func_73876_c() {
        this.field_73901_a.func_73780_a();
    }

    public void func_73869_a(char _char, int _code) {
        this.waitingOnPlayerNames = false;
        if (_code == 15) {
            this.func_73895_u_();
            this.autocompleteTextEmote(this.field_73901_a);
        } else {
            this.playerNamesFound = false;
        }
        if (_code == 1) {
            this.field_73882_e.func_71373_a((GuiScreen)null);
        } else if (_code == 28) {
            StringBuilder _msg = new StringBuilder(1500);
            for (int i = this.inputList.size() - 1; i >= 0; --i) {
                _msg.append(this.inputList.get(i).func_73781_b());
            }
            if (_msg.toString().length() > 0 && _msg.toString().length() < 100 && !_msg.toString().trim().equals("")) {
                String msgToWrite = _msg.toString();
                if (this.field_73882_e.func_71409_c(msgToWrite)) {
                    Minecraft.func_71410_x().field_71456_v.func_73827_b().func_73767_b(msgToWrite);
                    for (int j = 1; j < this.inputList.size(); ++j) {
                        this.inputList.get(j).func_73782_a("");
                        this.inputList.get(j).func_73790_e(false);
                    }
                    this.field_73882_e.func_71373_a((GuiScreen)null);
                    return;
                }
                if (TabbyChat.instance.getActive().size() > 0 && (this.field_73901_a.func_73781_b().matches("/.*") || !TabbyChat.instance.getActive().get(0).equals("Global")) && this.field_73901_a.func_73781_b().length() >= 80) {
                    return;
                }
                if (this.field_73901_a.func_73781_b().startsWith("/") && this.field_73901_a.func_73781_b().length() >= 80) {
                    return;
                }
                if (TabbyChat.instance.getActive().size() > 0) {
                    if (ClientProxy.serverType.equals("build") && !_msg.toString().matches("^/.*") && TabbyChat.instance.getActive().get(0).matches("Ile [0-9]+")) {
                        msgToWrite = "/i msg [" + TabbyChat.instance.getActive().get(0) + "]" + msgToWrite;
                    } else if (!(_msg.toString().matches("^/.*") || TabbyChat.instance.getActive().get(0).equals("Global") || TabbyChat.instance.getActive().get(0).equals("Mon pays") || TabbyChat.instance.getActive().get(0).equals("ALL") || TabbyChat.instance.getActive().get(0).equals("ENE") || TabbyChat.instance.getActive().get(0).equals("ADMIN") || TabbyChat.instance.getActive().get(0).equals("MODO") || TabbyChat.instance.getActive().get(0).equals("Police") || TabbyChat.instance.getActive().get(0).equals("Mafia") || TabbyChat.instance.getActive().get(0).equals("Journal") || TabbyChat.instance.getActive().get(0).equals("Guide") || TabbyChat.instance.getActive().get(0).equals("Avocat") || TabbyChat.instance.getActive().get(0).equals("RP") || TabbyChat.instance.getActive().get(0).equals("Logs"))) {
                        msgToWrite = msgToWrite.substring(0, Math.min(80, msgToWrite.length()));
                        msgToWrite = "/m " + TabbyChat.instance.getActive().get(0) + " " + msgToWrite;
                    }
                }
                TabbyChatUtils.writeLargeChat(msgToWrite);
                for (int j = 1; j < this.inputList.size(); ++j) {
                    this.inputList.get(j).func_73782_a("");
                    this.inputList.get(j).func_73790_e(false);
                }
                this.field_73882_e.func_71373_a((GuiScreen)null);
            }
        } else if (_code == 200) {
            if (GuiScreen.func_73861_o()) {
                this.func_73892_a(-1);
            } else {
                int foc = this.getFocusedFieldInd();
                if (foc + 1 < this.inputList.size() && this.inputList.get(foc + 1).func_73778_q()) {
                    int gcp = this.inputList.get(foc).func_73799_h();
                    int lng = this.inputList.get(foc + 1).func_73781_b().length();
                    int newPos = Math.min(gcp, lng);
                    this.inputList.get(foc).func_73796_b(false);
                    this.inputList.get(foc + 1).func_73796_b(true);
                    this.inputList.get(foc + 1).func_73791_e(newPos);
                } else {
                    this.func_73892_a(-1);
                }
            }
        } else if (_code == 208) {
            if (GuiScreen.func_73861_o()) {
                this.func_73892_a(1);
            } else {
                int foc = this.getFocusedFieldInd();
                if (foc - 1 >= 0 && this.inputList.get(foc - 1).func_73778_q()) {
                    int gcp = this.inputList.get(foc).func_73799_h();
                    int lng = this.inputList.get(foc - 1).func_73781_b().length();
                    int newPos = Math.min(gcp, lng);
                    this.inputList.get(foc).func_73796_b(false);
                    this.inputList.get(foc - 1).func_73796_b(true);
                    this.inputList.get(foc - 1).func_73791_e(newPos);
                } else {
                    this.func_73892_a(1);
                }
            }
        } else if (_code == 201) {
            TabbyChat.gnc.func_73758_b(19);
            if (tc.enabled()) {
                this.scrollBar.scrollBarMouseWheel();
            }
        } else if (_code == 209) {
            TabbyChat.gnc.func_73758_b(-19);
            if (tc.enabled()) {
                this.scrollBar.scrollBarMouseWheel();
            }
        } else if (_code == 14) {
            if (this.field_73901_a.func_73806_l() && this.field_73901_a.func_73799_h() > 0) {
                this.field_73901_a.func_73802_a(_char, _code);
            } else {
                this.removeCharsAtCursor(-1);
            }
        } else if (_code == 211) {
            if (this.field_73901_a.func_73806_l()) {
                this.field_73901_a.func_73802_a(_char, _code);
            } else {
                this.removeCharsAtCursor(1);
            }
        } else if (_code == 203 || _code == 205) {
            this.inputList.get(this.getFocusedFieldInd()).func_73802_a(_char, _code);
        } else if (this.field_73901_a.func_73806_l() && this.field_73886_k.func_78256_a(this.field_73901_a.func_73781_b()) < this.sr.func_78326_a() - 20) {
            if (TabbyChat.instance.getActive().size() > 0 && (this.field_73901_a.func_73781_b().matches("/.*") || !TabbyChat.instance.getActive().get(0).equals("Global")) && this.field_73901_a.func_73781_b().length() >= 80) {
                return;
            }
            if ((this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/")) && this.field_73901_a.func_73781_b().length() >= 80) {
                return;
            }
            if (this.field_73901_a.func_73781_b().length() < 99) {
                this.field_73901_a.func_73802_a(_char, _code);
            }
        } else {
            this.insertCharsAtCursor(Character.toString(_char));
        }
    }

    public void func_73867_d() {
        int wheelDelta = Mouse.getEventDWheel();
        if (wheelDelta != 0) {
            wheelDelta = Math.min(1, wheelDelta);
            wheelDelta = Math.max(-1, wheelDelta);
            if (!GuiChatTC.func_73877_p()) {
                wheelDelta *= 7;
            }
            TabbyChat.gnc.func_73758_b(wheelDelta);
            if (tc.enabled()) {
                this.scrollBar.scrollBarMouseWheel();
            }
        } else if (tc.enabled()) {
            this.scrollBar.handleMouse();
        }
        if (this.field_73882_e.field_71462_r.getClass() != GuiChat.class) {
            super.func_73867_d();
        }
    }

    public void func_73879_b(int _x, int _y, int _button) {
        if (this.field_73883_a != null && _button == 0) {
            this.field_73883_a.func_73740_a(_x, _y);
            this.field_73883_a = null;
        }
    }

    public void func_73864_a(int _x, int _y, int _button) {
        if (_button == 0 && this.field_73882_e.field_71474_y.field_74359_p) {
            String clickedProfil;
            String clickedCountry;
            URI url;
            ChatClickData ccd = TabbyChat.gnc.func_73766_a(Mouse.getX(), Mouse.getY());
            if (ccd != null && (url = ccd.func_78308_g()) != null) {
                if (this.field_73882_e.field_71474_y.field_74358_q) {
                    this.clickedURI = url;
                    this.field_73882_e.func_71373_a((GuiScreen)new GuiConfirmOpenLink((GuiScreen)this, ccd.func_78309_f(), 0, false));
                } else {
                    this.func_73896_a(url);
                }
                return;
            }
            ChatClickCountryData clickCountryData = TabbyChat.gnc.getClickedCountryData(Mouse.getX(), Mouse.getY());
            if (clickCountryData != null && (clickedCountry = clickCountryData.getCountry()) != null) {
                this.field_73882_e.func_71373_a((GuiScreen)new FactionGUI(clickCountryData.getCountry()));
                return;
            }
            ChatClickProfilData clickProfilData = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());
            if (clickProfilData != null && (clickedProfil = clickProfilData.getProfil()) != null) {
                FactionGUI.factionInfos = null;
                this.field_73882_e.func_71373_a((GuiScreen)new ProfilGui(clickProfilData.getProfil(), ""));
                return;
            }
        } else if (_button == 1) {
            Object clickedProfil;
            ChatClickProfilData clickProfilData = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());
            if (clickProfilData != null && (clickedProfil = clickProfilData.getProfil()) != null) {
                if (!GuiChatTC.tc.channelMap.containsKey(clickedProfil)) {
                    GuiChatTC.tc.channelMap.put((String)clickedProfil, new ChatChannel((String)clickedProfil));
                }
                for (ChatChannel chan : GuiChatTC.tc.channelMap.values()) {
                    chan.active = false;
                }
                GuiChatTC.tc.channelMap.get((Object)clickedProfil).active = true;
                tc.resetDisplayedChat();
                return;
            }
        }
        for (int i = 0; i < this.inputList.size(); ++i) {
            if (_y < this.field_73881_g - 12 * (i + 1) || !this.inputList.get(i).func_73778_q()) continue;
            this.inputList.get(i).func_73796_b(true);
            for (GuiTextField field : this.inputList) {
                if (field == this.inputList.get(i)) continue;
                field.func_73796_b(false);
            }
            this.inputList.get(i).func_73793_a(_x, _y, _button);
            break;
        }
        if (_button == 0) {
            for (GuiButton _guibutton : this.field_73887_h) {
                if (!_guibutton.func_73736_c(this.field_73882_e, _x, _y)) continue;
                this.field_73883_a = _guibutton;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.func_73875_a(_guibutton);
            }
        }
    }

    public void func_73878_a(boolean zeroId, int worldNum) {
        if (worldNum == 0) {
            if (zeroId) {
                this.func_73896_a(this.clickedURI);
            }
            this.clickedURI = null;
            this.field_73882_e.func_71373_a((GuiScreen)this);
        }
    }

    public void func_73896_a(URI _uri) {
        try {
            Class<?> desktop = Class.forName("java.awt.Desktop");
            Object theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            desktop.getMethod("browse", URI.class).invoke(theDesktop, _uri);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void func_73895_u_() {
        if (this.playerNamesFound) {
            this.field_73901_a.func_73777_b(this.field_73901_a.func_73798_a(-1, this.field_73901_a.func_73799_h(), false) - this.field_73901_a.func_73799_h());
            if (this.playerNameIndex >= this.foundPlayerNames.size()) {
                this.playerNameIndex = 0;
            }
        } else {
            int prevWordIndex = this.field_73901_a.func_73798_a(-1, this.field_73901_a.func_73799_h(), false);
            this.foundPlayerNames.clear();
            this.playerNameIndex = 0;
            String nameStart = this.field_73901_a.func_73781_b().substring(prevWordIndex).toLowerCase();
            String textBuffer = this.field_73901_a.func_73781_b().substring(0, this.field_73901_a.func_73799_h());
            this.func_73893_a(textBuffer, nameStart);
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = true;
            this.field_73901_a.func_73777_b(prevWordIndex - this.field_73901_a.func_73799_h());
        }
        if (this.foundPlayerNames.size() > 1) {
            StringBuilder _sb = new StringBuilder();
            for (String textBuffer : this.foundPlayerNames) {
                if (_sb.length() > 0) {
                    _sb.append(", ");
                }
                _sb.append(textBuffer);
            }
            this.field_73882_e.field_71456_v.func_73827_b().func_73763_a(_sb.toString(), 1);
        }
        if (this.foundPlayerNames.size() > this.playerNameIndex) {
            this.field_73901_a.func_73792_b((String)this.foundPlayerNames.get(this.playerNameIndex));
            ++this.playerNameIndex;
        }
    }

    public void func_73893_a(String nameStart, String buffer) {
        if (nameStart.length() >= 1) {
            this.field_73882_e.field_71439_g.field_71174_a.func_72552_c((Packet)new Packet203AutoComplete(nameStart));
            this.waitingOnPlayerNames = true;
        }
    }

    public void func_73892_a(int _dir) {
        int loc = this.field_73899_c + _dir;
        int historyLength = TabbyChat.gnc.func_73756_b().size();
        loc = Math.max(0, loc);
        if ((loc = Math.min(historyLength, loc)) == this.field_73899_c) {
            return;
        }
        if (loc == historyLength) {
            this.field_73899_c = historyLength;
            this.setText(new StringBuilder(""), 1);
        } else {
            if (this.field_73899_c == historyLength) {
                this.historyBuffer = this.field_73901_a.func_73781_b();
            }
            StringBuilder _sb = new StringBuilder((String)TabbyChat.gnc.func_73756_b().get(loc));
            this.setText(_sb, _sb.length());
            this.field_73899_c = loc;
        }
    }

    public void func_73863_a(int cursorX, int cursorY, float pointless) {
        if (this.displayText && this.scrollBarObj != null) {
            Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
            GuiChatTC.func_73734_a((int)(this.field_73880_f / 2 - 200), (int)(this.field_73881_g / 2 - 125 + 25), (int)(this.field_73880_f / 2 + 200), (int)(this.field_73881_g / 2 + 125), (int)-301989888);
            GuiChatTC.func_73734_a((int)(this.field_73880_f / 2 - 200), (int)(this.field_73881_g / 2 - 125 + 5), (int)(this.field_73880_f / 2 + 200), (int)(this.field_73881_g / 2 - 125 + 25), (int)-1728053248);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(this.field_73880_f / 2 - 200 + 5, this.field_73881_g / 2 - 125 + 8, 2, 20, 13, 13);
            this.func_73731_b(this.field_73886_k, current.getTitle(), this.field_73880_f / 2 - 200 + 20, this.field_73881_g / 2 - 125 + 11, 0xFFFFFF);
            GUIUtils.startGLScissor(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5 + 30, 380, 185);
            GL11.glPushMatrix();
            if (this.lines.size() > 11) {
                GL11.glTranslatef((float)0.0f, (float)(-this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier)), (float)0.0f);
            }
            int i = 0;
            for (String line : this.lines) {
                this.func_73731_b(this.field_73886_k, line, this.field_73880_f / 2 - 200 + 10, this.field_73881_g / 2 - 125 + 35 + i * 12, 0xFFFFFF);
                ++i;
            }
            ItemStack itemStack = ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack();
            boolean dr = false;
            if (itemStack != null) {
                int itemY = this.lines.size() * 12 + 2;
                int tWidth = this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"objectives.collect"));
                int w = tWidth + 16 + 4;
                this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"objectives.collect"), this.field_73880_f / 2 - 10 - w / 2, this.field_73881_g / 2 - 125 + 35 + itemY + 4, 0xFFFFFF);
                this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
                this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
                int pX = this.field_73880_f / 2 - 10 - w / 2 + tWidth + 4;
                int pY = this.field_73881_g / 2 - 125 + 35 + itemY - (int)(this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
                dr = cursorX >= pX && cursorX <= pX + 18 && cursorY >= pY && cursorY <= pY + 18;
                GL11.glDisable((int)2896);
            }
            GL11.glPopMatrix();
            GUIUtils.endGLScissor();
            if (dr) {
                NationsGUIClientHooks.drawItemStackTooltip(itemStack, cursorX, cursorY);
                GL11.glDisable((int)2896);
            }
            this.scrollBarObj.draw(cursorX, cursorY);
        }
        if (this.displayEmotesGui) {
            float _mult = this.field_73882_e.field_71474_y.field_74357_r * 0.9f + 0.1f;
            int _opacity = (int)(255.0f * _mult);
            GuiChatTC.func_73734_a((int)(this.field_73880_f - 132), (int)(this.field_73881_g - 134), (int)(this.field_73880_f - 132 + 110), (int)(this.field_73881_g - 134 + 120), (int)(_opacity / 2 << 24));
        }
        if (ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty()) {
            int posYHelperAttacker;
            if (!ClientData.currentAssault.get("attackerHelpersCount").equals("0")) {
                int posXHelperAttacker = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a(ClientData.currentAssault.get("attackerFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.field_73881_g * 0.4) + 26;
                if (cursorX >= posXHelperAttacker && cursorX <= posXHelperAttacker + 19 && cursorY >= posYHelperAttacker && cursorY <= posYHelperAttacker + 3) {
                    this.drawHoveringText(Arrays.asList(ClientData.currentAssault.get("attackerHelpersName").split(",")), cursorX, cursorY, this.field_73886_k);
                }
            }
            if (!ClientData.currentAssault.get("defenderHelpersCount").equals("0")) {
                int posXHelperAttacker = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a(ClientData.currentAssault.get("defenderFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.field_73881_g * 0.4) + 26;
                if (cursorX >= posXHelperAttacker && cursorX <= posXHelperAttacker + 19 && cursorY >= posYHelperAttacker && cursorY <= posYHelperAttacker + 3) {
                    this.drawHoveringText(Arrays.asList(ClientData.currentAssault.get("defenderHelpersName").split(",")), cursorX, cursorY, this.field_73886_k);
                }
            }
        }
        boolean unicodeStore = this.field_73886_k.func_82883_a();
        if (TabbyChat.instance.generalSettings.tabbyChatEnable.getValue().booleanValue() && ClientProxy.clientConfig.enableUnicode) {
            this.field_73886_k.func_78264_a(true);
        }
        this.field_73880_f = this.sr.func_78326_a();
        this.field_73881_g = this.sr.func_78328_b();
        int inputHeight = 0;
        for (int i = 0; i < this.inputList.size(); ++i) {
            if (!this.inputList.get(i).func_73778_q()) continue;
            inputHeight += 12;
        }
        GuiChatTC.func_73734_a((int)2, (int)(this.field_73881_g - 2 - inputHeight), (int)(this.field_73880_f - 2), (int)(this.field_73881_g - 2), (int)Integer.MIN_VALUE);
        for (GuiTextField field : this.inputList) {
            if (!field.func_73778_q()) continue;
            field.func_73795_f();
            this.autocompleteTooltipEmote(this.field_73880_f, this.field_73881_g, field);
        }
        if (!this.field_73882_e.func_71356_B()) {
            this.drawChatTabs();
        }
        if (tc.enabled()) {
            this.scrollBar.drawScrollBar();
        }
        float scaleSetting = TabbyChat.gnc.getScaleSetting();
        GL11.glPushMatrix();
        float scaleOffset = (float)(this.sr.func_78328_b() - 28) * (1.0f - scaleSetting);
        GL11.glTranslatef((float)0.0f, (float)scaleOffset, (float)1.0f);
        GL11.glScalef((float)scaleSetting, (float)scaleSetting, (float)1.0f);
        for (GuiButton _button : this.field_73887_h) {
            if (_button instanceof EmoteButton || _button instanceof SimpleButton || _button instanceof Button || _button instanceof EmoteOpenButton) {
                GL11.glScalef((float)(1.0f / scaleSetting), (float)(1.0f / scaleSetting), (float)1.0f);
                GL11.glTranslatef((float)0.0f, (float)(-scaleOffset), (float)1.0f);
                _button.func_73737_a(this.field_73882_e, cursorX, cursorY);
                GL11.glTranslatef((float)0.0f, (float)scaleOffset, (float)1.0f);
                GL11.glScalef((float)scaleSetting, (float)scaleSetting, (float)1.0f);
                continue;
            }
            _button.func_73737_a(this.field_73882_e, cursorX, cursorY);
        }
        GL11.glPopMatrix();
        this.field_73886_k.func_78264_a(unicodeStore);
    }

    public void func_73894_a(String[] par1ArrayOfStr) {
        if (this.waitingOnPlayerNames) {
            this.foundPlayerNames.clear();
            String[] _copy = par1ArrayOfStr;
            int _len = par1ArrayOfStr.length;
            for (int i = 0; i < _len; ++i) {
                String name = _copy[i];
                if (name.length() <= 0) continue;
                this.foundPlayerNames.add(name);
            }
            if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.func_73895_u_();
            }
        }
    }

    public void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton instanceof ChatButton) {
            ChatButton _button = (ChatButton)par1GuiButton;
            if (Keyboard.isKeyDown((int)42) && GuiChatTC.tc.channelMap.get("Global") == _button.channel && (Minecraft.func_71410_x().field_71439_g.getDisplayName().equalsIgnoreCase("iBalix") || Minecraft.func_71410_x().field_71439_g.getDisplayName().equalsIgnoreCase("Mistersand"))) {
                this.field_73882_e.func_71373_a((GuiScreen)GuiChatTC.tc.generalSettings);
                return;
            }
            if (!tc.enabled()) {
                return;
            }
            if (Keyboard.isKeyDown((int)42) && GuiChatTC.tc.channelMap.get("Global") != _button.channel) {
                GuiChatTC.tc.channelMap.remove(_button.channel.title);
            } else {
                for (ChatChannel chan : GuiChatTC.tc.channelMap.values()) {
                    if (((Object)((Object)_button)).equals((Object)chan.tab)) continue;
                    chan.active = false;
                }
                if (!_button.channel.active) {
                    this.scrollBar.scrollBarMouseWheel();
                    _button.channel.active = true;
                    _button.channel.unread = false;
                    if (_button.channel.title.equals("Mon pays")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("FACTION")));
                    } else if (_button.channel.title.equals("ALL")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ALLY")));
                    } else if (_button.channel.title.equals("ENE")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ENEMY")));
                    } else if (_button.channel.title.equals("Global") && ClientProxy.serverType.equals("ng")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("PUBLIC")));
                    } else if (_button.channel.title.equals("ADMIN")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ADMIN")));
                    } else if (_button.channel.title.equals("MODO")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("MOD")));
                    } else if (_button.channel.title.equals("Journal")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("UA")));
                    } else if (_button.channel.title.equals("Mafia")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrMod")));
                    } else if (_button.channel.title.equals("Police")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("SrMod")));
                    } else if (_button.channel.title.equals("Guide")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrAdmin")));
                    } else if (_button.channel.title.equals("Avocat")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("Avocat")));
                    }
                }
                tc.resetDisplayedChat();
            }
        } else {
            List<Objective> objectives = ClientData.objectives;
            if (par1GuiButton.field_73741_f >= 10 && par1GuiButton.field_73741_f < 10 + objectives.size()) {
                ClientData.currentObjectiveIndex = par1GuiButton.field_73741_f - 10;
                this.displayText = true;
                this.generateTextLines();
            } else {
                switch (par1GuiButton.field_73741_f) {
                    case 0: {
                        this.displayText = true;
                        this.generateTextLines();
                        break;
                    }
                    case 3: {
                        Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
                        if (current != null && current.getId().split("-").length == 3) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
                        }
                        this.displayText = false;
                        break;
                    }
                    case 4: {
                        this.displayText = false;
                        break;
                    }
                    case 5: {
                        this.displayEmotesGui = !this.displayEmotesGui;
                        break;
                    }
                    case 6: {
                        if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
                            if (ClientData.currentJumpStartTime == -1L) {
                                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandMainGui());
                                break;
                            }
                            NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", -1L);
                            ClientData.currentJumpRecord = "";
                            ClientData.currentJumpStartTime = -1L;
                            break;
                        }
                        NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", -1L);
                        ClientData.currentJumpRecord = "";
                        ClientData.currentJumpStartTime = -1L;
                        break;
                    }
                    case 7: {
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandListGui((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
                        break;
                    }
                    case 8: {
                        this.displayEmotesGui = false;
                    }
                }
            }
            if (this.displayEmotesGui && this.mappingButtonIdEmote.containsKey(par1GuiButton.field_73741_f)) {
                this.field_73901_a.func_73782_a(this.field_73901_a.func_73781_b() + ":" + this.mappingButtonIdEmote.get(par1GuiButton.field_73741_f) + ":");
            }
        }
    }

    public void drawChatTabs() {
        Double offsetY;
        this.field_73887_h.clear();
        this.mappingButtonIdEmote.clear();
        tc.updateButtonLocations();
        for (ChatChannel _chan : GuiChatTC.tc.channelMap.values()) {
            this.field_73887_h.add(_chan.tab);
        }
        int y = 30;
        if (ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
            if (!this.displayText) {
                if (!ClientData.objectives.isEmpty()) {
                    int btnIndex = 10;
                    for (Objective objective : ClientData.objectives) {
                        this.field_73887_h.add(new Button(btnIndex, 4, y, 112, 29));
                        y += 34;
                        ++btnIndex;
                    }
                }
            } else {
                this.heightModifier = ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() != null ? 10 : 0;
                this.scrollBarObj = new ScrollBar(this.field_73880_f / 2 + 200 - 15, this.field_73881_g / 2 - 125 + 30, 185);
                if (ClientData.objectives.get(ClientData.currentObjectiveIndex).getItemStack() == null) {
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                } else {
                    this.field_73887_h.add(new SimpleButton(4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                    this.field_73887_h.add(new SimpleButton(3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a((String)"objectives.validate")));
                }
            }
        }
        this.field_73887_h.add(new EmoteOpenButton(5, this.field_73880_f - 34, this.field_73881_g - 14, 12, 12));
        int buttonIndex = 0;
        int emoteWidth = this.field_73880_f - 131 + buttonIndex % 5 * 22;
        int emoteHeight = this.field_73881_g - 134 + buttonIndex / 5 * 20;
        for (Map.Entry<String, String> pair : NationsGUI.EMOTES_SYMBOLS.entrySet()) {
            if (!NationsGUI.EMOTES_RESOURCES.containsKey(pair.getKey())) continue;
            this.field_73887_h.add(new EmoteButton(100 + buttonIndex, emoteWidth, emoteHeight, 22, 20, pair.getKey()));
            this.mappingButtonIdEmote.put(100 + buttonIndex, pair.getKey());
            emoteWidth = this.field_73880_f - 131 + ++buttonIndex % 5 * 22;
            emoteHeight = this.field_73881_g - 134 + buttonIndex / 5 * 20;
        }
        if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
            offsetY = (double)this.field_73881_g * 0.4 + 78.0;
            this.field_73887_h.add(new VoidButton(6, this.field_73880_f - 120 + 13, offsetY.intValue(), 94, 15));
            this.field_73887_h.add(new VoidButton(7, this.field_73880_f - 120 + 13, offsetY.intValue() + 20, 94, 15));
        } else if (!ClientData.currentJumpLocation.isEmpty()) {
            offsetY = (double)this.field_73881_g * 0.4 + 80.0;
            this.field_73887_h.add(new VoidButton(6, this.field_73880_f - 140, offsetY.intValue(), 140, 16));
        }
    }

    public int getFocusedFieldInd() {
        int _s = this.inputList.size();
        for (int i = 0; i < _s; ++i) {
            if (!this.inputList.get(i).func_73806_l() || !this.inputList.get(i).func_73778_q()) continue;
            return i;
        }
        return 0;
    }

    public void removeCharsAtCursor(int _del) {
        StringBuilder msg = new StringBuilder();
        int cPos = 0;
        boolean cFound = false;
        for (int i = this.inputList.size() - 1; i >= 0; --i) {
            msg.append(this.inputList.get(i).func_73781_b());
            if (this.inputList.get(i).func_73806_l()) {
                cPos += this.inputList.get(i).func_73799_h();
                cFound = true;
                continue;
            }
            if (cFound) continue;
            cPos += this.inputList.get(i).func_73781_b().length();
        }
        int other = cPos + _del;
        other = Math.min(msg.length() - 1, other);
        if ((other = Math.max(0, other)) < cPos) {
            msg.replace(other, cPos, "");
            this.setText(msg, other);
        } else if (other > cPos) {
            msg.replace(cPos, other, "");
            this.setText(msg, cPos);
        } else {
            return;
        }
    }

    public void insertCharsAtCursor(String _chars) {
        StringBuilder msg = new StringBuilder();
        int cPos = 0;
        boolean cFound = false;
        for (int i = this.inputList.size() - 1; i >= 0; --i) {
            msg.append(this.inputList.get(i).func_73781_b());
            if (this.inputList.get(i).func_73806_l()) {
                cPos += this.inputList.get(i).func_73799_h();
                cFound = true;
                continue;
            }
            if (cFound) continue;
            cPos += this.inputList.get(i).func_73781_b().length();
        }
        if (this.field_73886_k.func_78256_a(msg.toString()) + this.field_73886_k.func_78256_a(_chars) < (this.sr.func_78326_a() - 20) * this.inputList.size()) {
            msg.insert(cPos, _chars);
            this.setText(msg, cPos + _chars.length());
        }
    }

    public void setText(StringBuilder txt, int pos) {
        int strings;
        List<String> txtList = this.stringListByWidth(txt, this.sr.func_78326_a() - 20);
        for (int i = strings = Math.min(txtList.size() - 1, this.inputList.size() - 1); i >= 0; --i) {
            this.inputList.get(i).func_73782_a(txtList.get(strings - i));
            if (pos > txtList.get(strings - i).length()) {
                pos -= txtList.get(strings - i).length();
                this.inputList.get(i).func_73790_e(true);
                this.inputList.get(i).func_73796_b(false);
                continue;
            }
            if (pos >= 0) {
                this.inputList.get(i).func_73796_b(true);
                this.inputList.get(i).func_73790_e(true);
                this.inputList.get(i).func_73791_e(pos);
                pos = -1;
                continue;
            }
            this.inputList.get(i).func_73790_e(true);
            this.inputList.get(i).func_73796_b(false);
        }
        if (pos > 0) {
            this.field_73901_a.func_73803_e();
        }
        if (this.inputList.size() > txtList.size()) {
            for (int j = txtList.size(); j < this.inputList.size(); ++j) {
                this.inputList.get(j).func_73782_a("");
                this.inputList.get(j).func_73796_b(false);
                this.inputList.get(j).func_73790_e(false);
            }
        }
        if (!this.field_73901_a.func_73778_q()) {
            this.field_73901_a.func_73790_e(true);
            this.field_73901_a.func_73796_b(true);
        }
    }

    public List<String> stringListByWidth(StringBuilder _sb, int _w) {
        ArrayList<String> result = new ArrayList<String>(5);
        int _len = 0;
        StringBuilder bucket = new StringBuilder(_sb.length());
        for (int ind = 0; ind < _sb.length(); ++ind) {
            int _cw = this.field_73886_k.func_78263_a(_sb.charAt(ind));
            if (_len + _cw > _w) {
                result.add(bucket.toString());
                bucket = new StringBuilder(_sb.length());
                _len = 0;
            }
            _len += _cw;
            bucket.append(_sb.charAt(ind));
        }
        if (bucket.length() > 0) {
            result.add(bucket.toString());
        }
        return result;
    }

    public int getCurrentSends() {
        int _s;
        int lng = 0;
        for (int i = _s = this.inputList.size() - 1; i >= 0; --i) {
            lng += this.inputList.get(i).func_73781_b().length();
        }
        if (lng == 0) {
            return 0;
        }
        return (int)Math.ceil((float)lng / 100.0f);
    }

    private void generateTextLines() {
        String[] l;
        this.lines.clear();
        int lineWidth = 360;
        int spaceWidth = this.field_73886_k.func_78256_a(" ");
        Objective current = ClientData.objectives.get(ClientData.currentObjectiveIndex);
        for (String line : l = current.getText().split("\n")) {
            int spaceLeft = lineWidth;
            String[] words = line.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String word : words) {
                int wordWidth = this.field_73886_k.func_78256_a(word);
                if (wordWidth + spaceWidth > spaceLeft) {
                    this.lines.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    spaceLeft = lineWidth - wordWidth;
                } else {
                    spaceLeft -= wordWidth + spaceWidth;
                }
                stringBuilder.append(word);
                stringBuilder.append(' ');
            }
            this.lines.add(stringBuilder.toString());
        }
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
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
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
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
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void autocompleteTooltipEmote(int guiWidth, int guiHeight, GuiTextField textField) {
        Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
        Matcher m = pattern.matcher(textField.func_73781_b());
        if (m.find()) {
            String emoteToAutocomplete = m.group(1);
            ArrayList<String> emoteWhichMatch = new ArrayList<String>();
            for (Map.Entry<String, ResourceLocation> pair : NationsGUI.EMOTES_RESOURCES.entrySet()) {
                String emoteName = pair.getKey();
                pattern = Pattern.compile("^" + emoteToAutocomplete);
                m = pattern.matcher(emoteName);
                if (!m.find()) continue;
                emoteWhichMatch.add(":" + emoteName + ":");
            }
            if (emoteWhichMatch.size() > 0) {
                this.drawHoveringText(emoteWhichMatch, Minecraft.func_71410_x().field_71466_p.func_78256_a(textField.func_73781_b()), guiHeight - 14 - emoteWhichMatch.size() * 9, Minecraft.func_71410_x().field_71466_p);
            }
        }
    }

    public void autocompleteTextEmote(GuiTextField textField) {
        Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
        Matcher m = pattern.matcher(textField.func_73781_b());
        if (m.find()) {
            String emoteToAutocomplete = m.group(1);
            String emoteWhichMatch = null;
            for (Map.Entry<String, ResourceLocation> pair : NationsGUI.EMOTES_RESOURCES.entrySet()) {
                String emoteName = pair.getKey();
                pattern = Pattern.compile("^" + emoteToAutocomplete);
                m = pattern.matcher(emoteName);
                if (!m.find()) continue;
                emoteWhichMatch = ":" + emoteName + ":";
            }
            if (emoteWhichMatch != null) {
                textField.func_73782_a(textField.func_73781_b() + emoteWhichMatch.replace(":" + emoteToAutocomplete, "") + " ");
            }
        }
    }

    static {
        tc = TabbyChat.instance;
    }

    private class ScrollBar
    extends GuiScrollBar {
        public ScrollBar(float x, float y, int height) {
            super(x, y, height);
        }

        @Override
        protected void drawScroller() {
            ScrollBar.func_73734_a((int)((int)this.x), (int)((int)this.y), (int)((int)(this.x + 9.0f)), (int)((int)(this.y + (float)this.height)), (int)0x22FFFFFF);
            int yP = (int)(this.y + (float)(this.height - 20) * this.sliderValue);
            ScrollBar.func_73734_a((int)((int)this.x), (int)yP, (int)((int)(this.x + 9.0f)), (int)(yP + 20), (int)0x55FFFFFF);
        }
    }

    private class SimpleButton
    extends GuiButton {
        public SimpleButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
            super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            SimpleButton.func_73734_a((int)this.field_73746_c, (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b), (int)(this.field_82253_i ? 0x77FFFFFF : 0x55FFFFFF));
            GuiChatTC.this.field_73886_k.func_78276_b(this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - GuiChatTC.this.field_73886_k.func_78256_a(this.field_73744_e) / 2, this.field_73743_d + 4, 0xFFFFFF);
        }
    }

    private class EmoteButton
    extends GuiButton {
        private String emoteName;

        public EmoteButton(int id, int posX, int posY, int width, int height, String emoteName) {
            super(id, posX, posY, width, height, "");
            this.emoteName = emoteName;
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            if (GuiChatTC.this.displayEmotesGui) {
                this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
                float _mult = ((GuiChatTC)GuiChatTC.this).field_73882_e.field_71474_y.field_74357_r * 0.9f + 0.1f;
                if (this.field_82253_i) {
                    _mult *= 0.5f;
                }
                int _opacity = (int)(255.0f * _mult);
                EmoteButton.func_73734_a((int)this.field_73746_c, (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b), (int)(_opacity / 2 << 24));
                Double scale = 0.6;
                GL11.glPushMatrix();
                GL11.glScaled((double)scale, (double)scale, (double)scale);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                if (NationsGUI.EMOTES_RESOURCES.containsKey(this.emoteName)) {
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.EMOTES_RESOURCES.get(this.emoteName));
                    ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c * (1.0f / scale.floatValue()) + 8.0f, (float)this.field_73743_d * (1.0f / scale.floatValue()) + 7.0f, 0, 0, 18, 18, 18.0f, 18.0f, true);
                }
                GL11.glPopMatrix();
            }
        }
    }

    private class EmoteOpenButton
    extends GuiButton {
        public EmoteOpenButton(int id, int posX, int posY, int width, int height) {
            super(id, posX, posY, width, height, "\u263a");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            int l = 0xE0E0E0;
            if (this.field_82253_i) {
                l = 0xFFFFA0;
            }
            float _mult = ((GuiChatTC)GuiChatTC.this).field_73882_e.field_71474_y.field_74357_r * 0.9f + 0.1f;
            int _opacity = (int)(255.0f * _mult);
            EmoteOpenButton.func_73734_a((int)this.field_73746_c, (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b), (int)(_opacity / 2 << 24));
            this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, l);
        }
    }

    public class VoidButton
    extends GuiButton {
        public VoidButton(int id, int posX, int posY, int width, int height) {
            super(id, posX, posY, width, height, "");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        }
    }

    private class Button
    extends GuiButton {
        public Button(int id, int posX, int posY, int width, int height) {
            super(id, posX, posY, width, height, "");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
        }
    }
}

