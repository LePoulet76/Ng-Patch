package acs.tabbychat;

import acs.tabbychat.GuiChatTC$Button;
import acs.tabbychat.GuiChatTC$EmoteButton;
import acs.tabbychat.GuiChatTC$EmoteOpenButton;
import acs.tabbychat.GuiChatTC$ScrollBar;
import acs.tabbychat.GuiChatTC$SimpleButton;
import acs.tabbychat.GuiChatTC$VoidButton;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickCountryData;
import net.ilexiconn.nationsgui.forge.client.chat.ChatClickProfilData;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
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
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet203AutoComplete;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiChatTC extends GuiChat
{
    public String historyBuffer = "";

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    public String defaultInputFieldText = "";

    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    public int sentHistoryCursor = -1;
    private boolean playerNamesFound = false;
    private boolean waitingOnPlayerNames = false;
    private int playerNameIndex = 0;
    private List foundPlayerNames = new ArrayList();
    private URI clickedURI = null;

    /** Chat entry field */
    public GuiTextField inputField;
    public List<GuiTextField> inputList = new ArrayList(3);
    public ChatScrollBar scrollBar;

    /** The button that was just pressed. */
    public GuiButton selectedButton = null;
    public int eventButton = 0;
    public long lastMouseEvent = 0L;
    public int field_92018_d = 0;
    public float zLevel = 0.0F;
    public ScaledResolution sr;
    public static GuiChatTC me;
    public static final TabbyChat tc = TabbyChat.instance;
    private int heightModifier = 0;
    private boolean displayText = false;
    public static final int BOX_WIDTH = 400;
    public static final int BOX_HEIGHT = 250;
    private GuiChatTC$ScrollBar scrollBarObj;
    private List<String> lines = new ArrayList();
    private RenderItem itemRenderer = new RenderItem();
    private HashMap<Integer, String> mappingButtonIdEmote = new HashMap();
    private HashMap<Integer, String> mappingButtonIdAnim = new HashMap();
    private boolean displayEmotesGui = false;

    public GuiChatTC()
    {
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRenderer;
        me = this;
        this.sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    }

    public GuiChatTC(String par1Str)
    {
        this.defaultInputFieldText = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        this.buttonList.clear();
        this.mappingButtonIdEmote.clear();
        this.inputList.clear();
        this.width = this.sr.getScaledWidth();
        this.height = this.sr.getScaledHeight();
        tc.checkServer();

        if (tc.enabled())
        {
            this.drawChatTabs();

            if (this.scrollBar == null)
            {
                this.scrollBar = new ChatScrollBar(this);
            }

            this.scrollBar.drawScrollBar();
        }
        else if (!Minecraft.getMinecraft().isSingleplayer())
        {
            tc.updateButtonLocations();
            this.buttonList.add(((ChatChannel)tc.channelMap.get("Global")).tab);
        }

        TabbyChat var10001 = tc;
        this.sentHistoryCursor = TabbyChat.gnc.getSentMessages().size();
        this.inputField = new GuiTextField(this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(500);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(true);
        this.inputList.add(0, this.inputField);

        if (tc.enabled())
        {
            int y;

            for (y = 1; y < 3; ++y)
            {
                GuiTextField placeholder = new GuiTextField(this.fontRenderer, 4, this.height - 12 * (y + 1), this.width, 12);
                placeholder.setMaxStringLength(500);
                placeholder.setEnableBackgroundDrawing(false);
                placeholder.setFocused(false);
                placeholder.setText("");
                placeholder.setCanLoseFocus(true);
                placeholder.setVisible(false);
                this.inputList.add(y, placeholder);
            }

            y = 30;
            int buttonIndex;

            if (ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            {
                if (!this.displayText)
                {
                    if (!ClientData.objectives.isEmpty())
                    {
                        buttonIndex = 10;

                        for (Iterator emoteWidth = ClientData.objectives.iterator(); emoteWidth.hasNext(); ++buttonIndex)
                        {
                            Objective emoteHeight = (Objective)emoteWidth.next();
                            this.buttonList.add(new GuiChatTC$Button(this, buttonIndex, 4, y, 112, 29));
                            y += 34;
                        }
                    }

                    this.scrollBarObj = null;
                }
                else
                {
                    this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null ? 10 : 0;
                    this.scrollBarObj = new GuiChatTC$ScrollBar(this, (float)(this.width / 2 + 200 - 15), (float)(this.height / 2 - 125 + 30), 185);

                    if (((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null)
                    {
                        this.buttonList.add(new GuiChatTC$SimpleButton(this, 3, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                    }
                    else
                    {
                        this.buttonList.add(new GuiChatTC$SimpleButton(this, 4, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                        this.buttonList.add(new GuiChatTC$SimpleButton(this, 3, this.width / 2 + 200 - 56 - 105, this.height / 2 + 125 - 25, 100, 15, I18n.getString("objectives.validate")));
                    }
                }
            }

            this.buttonList.add(new GuiChatTC$EmoteOpenButton(this, 5, this.width - 34, this.height - 14, 12, 12));
            buttonIndex = 0;
            int var8 = this.width - 131 + buttonIndex % 5 * 22;
            int var9 = this.height - 134 + buttonIndex / 5 * 20;
            Iterator it = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

            while (it.hasNext())
            {
                Entry offsetY = (Entry)it.next();

                if (NationsGUI.EMOTES_RESOURCES.containsKey((String)offsetY.getKey()))
                {
                    this.buttonList.add(new GuiChatTC$EmoteButton(this, 100 + buttonIndex, var8, var9, 22, 20, (String)offsetY.getKey()));
                    this.mappingButtonIdEmote.put(Integer.valueOf(100 + buttonIndex), (String)offsetY.getKey());
                    ++buttonIndex;
                    var8 = this.width - 131 + buttonIndex % 5 * 22;
                    var9 = this.height - 134 + buttonIndex / 5 * 20;
                }
            }

            Double var10;

            if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0)
            {
                var10 = Double.valueOf((double)this.height * 0.4D + 78.0D);
                this.buttonList.add(new GuiChatTC$VoidButton(this, 6, this.width - 120 + 13, var10.intValue(), 94, 15));
                this.buttonList.add(new GuiChatTC$VoidButton(this, 7, this.width - 120 + 13, var10.intValue() + 20, 94, 15));
            }
            else if (!ClientData.currentJumpLocation.isEmpty())
            {
                var10 = Double.valueOf((double)this.height * 0.4D + 80.0D);
                this.buttonList.add(new GuiChatTC$VoidButton(this, 6, this.width - 140, var10.intValue(), 140, 16));
            }
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    public void keyTyped(char _char, int _code)
    {
        this.waitingOnPlayerNames = false;

        if (_code == 15)
        {
            this.completePlayerName();
            this.autocompleteTextEmote(this.inputField);
        }
        else
        {
            this.playerNamesFound = false;
        }

        if (_code == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else
        {
            int gcp;
            int lng;

            if (_code == 28)
            {
                StringBuilder foc = new StringBuilder(1500);

                for (gcp = this.inputList.size() - 1; gcp >= 0; --gcp)
                {
                    foc.append(((GuiTextField)this.inputList.get(gcp)).getText());
                }

                if (foc.toString().length() > 0 && foc.toString().length() < 100 && !foc.toString().trim().equals(""))
                {
                    String var8 = foc.toString();

                    if (this.mc.handleClientCommand(var8))
                    {
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(var8);

                        for (lng = 1; lng < this.inputList.size(); ++lng)
                        {
                            ((GuiTextField)this.inputList.get(lng)).setText("");
                            ((GuiTextField)this.inputList.get(lng)).setVisible(false);
                        }

                        this.mc.displayGuiScreen((GuiScreen)null);
                        return;
                    }

                    if (TabbyChat.instance.getActive().size() > 0 && (this.inputField.getText().matches("/.*") || !((String)TabbyChat.instance.getActive().get(0)).equals("Global")) && this.inputField.getText().length() >= 80)
                    {
                        return;
                    }

                    if (this.inputField.getText().startsWith("/") && this.inputField.getText().length() >= 80)
                    {
                        return;
                    }

                    if (TabbyChat.instance.getActive().size() > 0)
                    {
                        if (ClientProxy.serverType.equals("build") && !foc.toString().matches("^/.*") && ((String)TabbyChat.instance.getActive().get(0)).matches("Ile [0-9]+"))
                        {
                            var8 = "/i msg [" + (String)TabbyChat.instance.getActive().get(0) + "]" + var8;
                        }
                        else if (!foc.toString().matches("^/.*") && !((String)TabbyChat.instance.getActive().get(0)).equals("Global") && !((String)TabbyChat.instance.getActive().get(0)).equals("Mon pays") && !((String)TabbyChat.instance.getActive().get(0)).equals("ALL") && !((String)TabbyChat.instance.getActive().get(0)).equals("ENE") && !((String)TabbyChat.instance.getActive().get(0)).equals("ADMIN") && !((String)TabbyChat.instance.getActive().get(0)).equals("MODO") && !((String)TabbyChat.instance.getActive().get(0)).equals("Police") && !((String)TabbyChat.instance.getActive().get(0)).equals("Mafia") && !((String)TabbyChat.instance.getActive().get(0)).equals("Journal") && !((String)TabbyChat.instance.getActive().get(0)).equals("Guide") && !((String)TabbyChat.instance.getActive().get(0)).equals("Avocat") && !((String)TabbyChat.instance.getActive().get(0)).equals("RP") && !((String)TabbyChat.instance.getActive().get(0)).equals("Logs"))
                        {
                            var8 = var8.substring(0, Math.min(80, var8.length()));
                            var8 = "/m " + (String)TabbyChat.instance.getActive().get(0) + " " + var8;
                        }
                    }

                    TabbyChatUtils.writeLargeChat(var8);

                    for (lng = 1; lng < this.inputList.size(); ++lng)
                    {
                        ((GuiTextField)this.inputList.get(lng)).setText("");
                        ((GuiTextField)this.inputList.get(lng)).setVisible(false);
                    }

                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else
            {
                int newPos;
                int var7;

                if (_code == 200)
                {
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        this.getSentHistory(-1);
                    }
                    else
                    {
                        var7 = this.getFocusedFieldInd();

                        if (var7 + 1 < this.inputList.size() && ((GuiTextField)this.inputList.get(var7 + 1)).getVisible())
                        {
                            gcp = ((GuiTextField)this.inputList.get(var7)).getCursorPosition();
                            lng = ((GuiTextField)this.inputList.get(var7 + 1)).getText().length();
                            newPos = Math.min(gcp, lng);
                            ((GuiTextField)this.inputList.get(var7)).setFocused(false);
                            ((GuiTextField)this.inputList.get(var7 + 1)).setFocused(true);
                            ((GuiTextField)this.inputList.get(var7 + 1)).setCursorPosition(newPos);
                        }
                        else
                        {
                            this.getSentHistory(-1);
                        }
                    }
                }
                else if (_code == 208)
                {
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        this.getSentHistory(1);
                    }
                    else
                    {
                        var7 = this.getFocusedFieldInd();

                        if (var7 - 1 >= 0 && ((GuiTextField)this.inputList.get(var7 - 1)).getVisible())
                        {
                            gcp = ((GuiTextField)this.inputList.get(var7)).getCursorPosition();
                            lng = ((GuiTextField)this.inputList.get(var7 - 1)).getText().length();
                            newPos = Math.min(gcp, lng);
                            ((GuiTextField)this.inputList.get(var7)).setFocused(false);
                            ((GuiTextField)this.inputList.get(var7 - 1)).setFocused(true);
                            ((GuiTextField)this.inputList.get(var7 - 1)).setCursorPosition(newPos);
                        }
                        else
                        {
                            this.getSentHistory(1);
                        }
                    }
                }
                else
                {
                    TabbyChat var10000;

                    if (_code == 201)
                    {
                        var10000 = tc;
                        TabbyChat.gnc.scroll(19);

                        if (tc.enabled())
                        {
                            this.scrollBar.scrollBarMouseWheel();
                        }
                    }
                    else if (_code == 209)
                    {
                        var10000 = tc;
                        TabbyChat.gnc.scroll(-19);

                        if (tc.enabled())
                        {
                            this.scrollBar.scrollBarMouseWheel();
                        }
                    }
                    else if (_code == 14)
                    {
                        if (this.inputField.isFocused() && this.inputField.getCursorPosition() > 0)
                        {
                            this.inputField.textboxKeyTyped(_char, _code);
                        }
                        else
                        {
                            this.removeCharsAtCursor(-1);
                        }
                    }
                    else if (_code == 211)
                    {
                        if (this.inputField.isFocused())
                        {
                            this.inputField.textboxKeyTyped(_char, _code);
                        }
                        else
                        {
                            this.removeCharsAtCursor(1);
                        }
                    }
                    else if (_code != 203 && _code != 205)
                    {
                        if (this.inputField.isFocused() && this.fontRenderer.getStringWidth(this.inputField.getText()) < this.sr.getScaledWidth() - 20)
                        {
                            if (TabbyChat.instance.getActive().size() > 0 && (this.inputField.getText().matches("/.*") || !((String)TabbyChat.instance.getActive().get(0)).equals("Global")) && this.inputField.getText().length() >= 80)
                            {
                                return;
                            }

                            if ((this.inputField.getText().startsWith("/") || this.inputField.getText().startsWith("/") || this.inputField.getText().startsWith("/") || this.inputField.getText().startsWith("/")) && this.inputField.getText().length() >= 80)
                            {
                                return;
                            }

                            if (this.inputField.getText().length() < 99)
                            {
                                this.inputField.textboxKeyTyped(_char, _code);
                            }
                        }
                        else
                        {
                            this.insertCharsAtCursor(Character.toString(_char));
                        }
                    }
                    else
                    {
                        ((GuiTextField)this.inputList.get(this.getFocusedFieldInd())).textboxKeyTyped(_char, _code);
                    }
                }
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        int wheelDelta = Mouse.getEventDWheel();

        if (wheelDelta != 0)
        {
            wheelDelta = Math.min(1, wheelDelta);
            wheelDelta = Math.max(-1, wheelDelta);

            if (!isShiftKeyDown())
            {
                wheelDelta *= 7;
            }

            TabbyChat var10000 = tc;
            TabbyChat.gnc.scroll(wheelDelta);

            if (tc.enabled())
            {
                this.scrollBar.scrollBarMouseWheel();
            }
        }
        else if (tc.enabled())
        {
            this.scrollBar.handleMouse();
        }

        if (this.mc.currentScreen.getClass() != GuiChat.class)
        {
            super.handleMouseInput();
        }
    }

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
    public void mouseMovedOrUp(int _x, int _y, int _button)
    {
        if (this.selectedButton != null && _button == 0)
        {
            this.selectedButton.mouseReleased(_x, _y);
            this.selectedButton = null;
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int _x, int _y, int _button)
    {
        TabbyChat var10000;

        if (_button == 0 && this.mc.gameSettings.chatLinks)
        {
            var10000 = tc;
            ChatClickData var8 = TabbyChat.gnc.func_73766_a(Mouse.getX(), Mouse.getY());

            if (var8 != null)
            {
                URI var10 = var8.getURI();

                if (var10 != null)
                {
                    if (this.mc.gameSettings.chatLinksPrompt)
                    {
                        this.clickedURI = var10;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var8.getClickedUrl(), 0, false));
                    }
                    else
                    {
                        this.func_73896_a(var10);
                    }

                    return;
                }
            }

            var10000 = tc;
            ChatClickCountryData var12 = TabbyChat.gnc.getClickedCountryData(Mouse.getX(), Mouse.getY());

            if (var12 != null)
            {
                String var14 = var12.getCountry();

                if (var14 != null)
                {
                    this.mc.displayGuiScreen(new FactionGUI(var12.getCountry()));
                    return;
                }
            }

            var10000 = tc;
            ChatClickProfilData var16 = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());

            if (var16 != null)
            {
                String var18 = var16.getProfil();

                if (var18 != null)
                {
                    FactionGUI.factionInfos = null;
                    this.mc.displayGuiScreen(new ProfilGui(var16.getProfil(), ""));
                    return;
                }
            }
        }
        else if (_button == 1)
        {
            var10000 = tc;
            ChatClickProfilData i = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());

            if (i != null)
            {
                String _guibutton = i.getProfil();

                if (_guibutton != null)
                {
                    if (!tc.channelMap.containsKey(_guibutton))
                    {
                        tc.channelMap.put(_guibutton, new ChatChannel(_guibutton));
                    }

                    ChatChannel chan;

                    for (Iterator field = tc.channelMap.values().iterator(); field.hasNext(); chan.active = false)
                    {
                        chan = (ChatChannel)field.next();
                    }

                    ((ChatChannel)tc.channelMap.get(_guibutton)).active = true;
                    tc.resetDisplayedChat();
                    return;
                }
            }
        }

        for (int var9 = 0; var9 < this.inputList.size(); ++var9)
        {
            if (_y >= this.height - 12 * (var9 + 1) && ((GuiTextField)this.inputList.get(var9)).getVisible())
            {
                ((GuiTextField)this.inputList.get(var9)).setFocused(true);
                Iterator var13 = this.inputList.iterator();

                while (var13.hasNext())
                {
                    GuiTextField var17 = (GuiTextField)var13.next();

                    if (var17 != this.inputList.get(var9))
                    {
                        var17.setFocused(false);
                    }
                }

                ((GuiTextField)this.inputList.get(var9)).mouseClicked(_x, _y, _button);
                break;
            }
        }

        if (_button == 0)
        {
            Iterator var11 = this.buttonList.iterator();

            while (var11.hasNext())
            {
                GuiButton var15 = (GuiButton)var11.next();

                if (var15.mousePressed(this.mc, _x, _y))
                {
                    this.selectedButton = var15;
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.actionPerformed(var15);
                }
            }
        }
    }

    public void confirmClicked(boolean zeroId, int worldNum)
    {
        if (worldNum == 0)
        {
            if (zeroId)
            {
                this.func_73896_a(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    public void func_73896_a(URI _uri)
    {
        try
        {
            Class t = Class.forName("java.awt.Desktop");
            Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            t.getMethod("browse", new Class[] {URI.class}).invoke(theDesktop, new Object[] {_uri});
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
        String textBuffer;

        if (this.playerNamesFound)
        {
            this.inputField.deleteFromCursor(this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.playerNameIndex >= this.foundPlayerNames.size())
            {
                this.playerNameIndex = 0;
            }
        }
        else
        {
            int _sb = this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.playerNameIndex = 0;
            String _iter = this.inputField.getText().substring(_sb).toLowerCase();
            textBuffer = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.func_73893_a(textBuffer, _iter);

            if (this.foundPlayerNames.isEmpty())
            {
                return;
            }

            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(_sb - this.inputField.getCursorPosition());
        }

        if (this.foundPlayerNames.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (Iterator var5 = this.foundPlayerNames.iterator(); var5.hasNext(); var4.append(textBuffer))
            {
                textBuffer = (String)var5.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(var4.toString(), 1);
        }

        if (this.foundPlayerNames.size() > this.playerNameIndex)
        {
            this.inputField.writeText((String)this.foundPlayerNames.get(this.playerNameIndex));
            ++this.playerNameIndex;
        }
    }

    public void func_73893_a(String nameStart, String buffer)
    {
        if (nameStart.length() >= 1)
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new Packet203AutoComplete(nameStart));
            this.waitingOnPlayerNames = true;
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    public void getSentHistory(int _dir)
    {
        int loc = this.sentHistoryCursor + _dir;
        TabbyChat var10000 = tc;
        int historyLength = TabbyChat.gnc.getSentMessages().size();
        loc = Math.max(0, loc);
        loc = Math.min(historyLength, loc);

        if (loc != this.sentHistoryCursor)
        {
            if (loc == historyLength)
            {
                this.sentHistoryCursor = historyLength;
                this.setText(new StringBuilder(""), 1);
            }
            else
            {
                if (this.sentHistoryCursor == historyLength)
                {
                    this.historyBuffer = this.inputField.getText();
                }

                StringBuilder var5 = new StringBuilder((String)TabbyChat.gnc.getSentMessages().get(loc));
                TabbyChat var10002 = tc;
                StringBuilder _sb = var5;
                this.setText(_sb, _sb.length());
                this.sentHistoryCursor = loc;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int cursorX, int cursorY, float pointless)
    {
        int inputHeight;
        Iterator scaleSetting;

        if (this.displayText && this.scrollBarObj != null)
        {
            Objective unicodeStore = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
            drawRect(this.width / 2 - 200, this.height / 2 - 125 + 25, this.width / 2 + 200, this.height / 2 + 125, -301989888);
            drawRect(this.width / 2 - 200, this.height / 2 - 125 + 5, this.width / 2 + 200, this.height / 2 - 125 + 25, -1728053248);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.width / 2 - 200 + 5, this.height / 2 - 125 + 8, 2, 20, 13, 13);
            this.drawString(this.fontRenderer, unicodeStore.getTitle(), this.width / 2 - 200 + 20, this.height / 2 - 125 + 11, 16777215);
            GUIUtils.startGLScissor(this.width / 2 - 200, this.height / 2 - 125 + 5 + 30, 380, 185);
            GL11.glPushMatrix();

            if (this.lines.size() > 11)
            {
                GL11.glTranslatef(0.0F, -this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier), 0.0F);
            }

            inputHeight = 0;

            for (scaleSetting = this.lines.iterator(); scaleSetting.hasNext(); ++inputHeight)
            {
                String scaleOffset = (String)scaleSetting.next();
                this.drawString(this.fontRenderer, scaleOffset, this.width / 2 - 200 + 10, this.height / 2 - 125 + 35 + inputHeight * 12, 16777215);
            }

            ItemStack var16 = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack();
            boolean var18 = false;

            if (var16 != null)
            {
                int itemY = this.lines.size() * 12 + 2;
                int _button = this.fontRenderer.getStringWidth(I18n.getString("objectives.collect"));
                int w = _button + 16 + 4;
                this.fontRenderer.drawString(I18n.getString("objectives.collect"), this.width / 2 - 10 - w / 2, this.height / 2 - 125 + 35 + itemY + 4, 16777215);
                this.itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var16, this.width / 2 - 10 - w / 2 + _button + 4, this.height / 2 - 125 + 35 + itemY);
                this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var16, this.width / 2 - 10 - w / 2 + _button + 4, this.height / 2 - 125 + 35 + itemY);
                int pX = this.width / 2 - 10 - w / 2 + _button + 4;
                int pY = this.height / 2 - 125 + 35 + itemY - (int)(this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
                var18 = cursorX >= pX && cursorX <= pX + 18 && cursorY >= pY && cursorY <= pY + 18;
                GL11.glDisable(GL11.GL_LIGHTING);
            }

            GL11.glPopMatrix();
            GUIUtils.endGLScissor();

            if (var18)
            {
                NationsGUIClientHooks.drawItemStackTooltip(var16, cursorX, cursorY);
                GL11.glDisable(GL11.GL_LIGHTING);
            }

            this.scrollBarObj.draw(cursorX, cursorY);
        }

        if (this.displayEmotesGui)
        {
            float var13 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            inputHeight = (int)(255.0F * var13);
            drawRect(this.width - 132, this.height - 134, this.width - 132 + 110, this.height - 134 + 120, inputHeight / 2 << 24);
        }

        if (ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty())
        {
            int var14;

            if (!((String)ClientData.currentAssault.get("attackerHelpersCount")).equals("0"))
            {
                var14 = this.width - 140 + 23 + this.fontRenderer.getStringWidth((String)ClientData.currentAssault.get("attackerFactionName")) - 1;
                inputHeight = (int)((double)this.height * 0.4D) + 26;

                if (cursorX >= var14 && cursorX <= var14 + 19 && cursorY >= inputHeight && cursorY <= inputHeight + 3)
                {
                    this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("attackerHelpersName")).split(",")), cursorX, cursorY, this.fontRenderer);
                }
            }

            if (!((String)ClientData.currentAssault.get("defenderHelpersCount")).equals("0"))
            {
                var14 = this.width - 140 + 23 + this.fontRenderer.getStringWidth((String)ClientData.currentAssault.get("defenderFactionName")) - 1;
                inputHeight = (int)((double)this.height * 0.4D) + 26;

                if (cursorX >= var14 && cursorX <= var14 + 19 && cursorY >= inputHeight && cursorY <= inputHeight + 3)
                {
                    this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("defenderHelpersName")).split(",")), cursorX, cursorY, this.fontRenderer);
                }
            }
        }

        boolean var15 = this.fontRenderer.getUnicodeFlag();

        if (TabbyChat.instance.generalSettings.tabbyChatEnable.getValue().booleanValue() && ClientProxy.clientConfig.enableUnicode)
        {
            this.fontRenderer.setUnicodeFlag(true);
        }

        this.width = this.sr.getScaledWidth();
        this.height = this.sr.getScaledHeight();
        inputHeight = 0;

        for (int var17 = 0; var17 < this.inputList.size(); ++var17)
        {
            if (((GuiTextField)this.inputList.get(var17)).getVisible())
            {
                inputHeight += 12;
            }
        }

        drawRect(2, this.height - 2 - inputHeight, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        scaleSetting = this.inputList.iterator();

        while (scaleSetting.hasNext())
        {
            GuiTextField var20 = (GuiTextField)scaleSetting.next();

            if (var20.getVisible())
            {
                var20.drawTextBox();
                this.autocompleteTooltipEmote(this.width, this.height, var20);
            }
        }

        if (!this.mc.isSingleplayer())
        {
            this.drawChatTabs();
        }

        if (tc.enabled())
        {
            this.scrollBar.drawScrollBar();
        }

        TabbyChat var10000 = tc;
        float var19 = TabbyChat.gnc.getScaleSetting();
        GL11.glPushMatrix();
        float var22 = (float)(this.sr.getScaledHeight() - 28) * (1.0F - var19);
        GL11.glTranslatef(0.0F, var22, 1.0F);
        GL11.glScalef(var19, var19, 1.0F);
        Iterator var21 = this.buttonList.iterator();

        while (var21.hasNext())
        {
            GuiButton var23 = (GuiButton)var21.next();

            if (!(var23 instanceof GuiChatTC$EmoteButton) && !(var23 instanceof GuiChatTC$SimpleButton) && !(var23 instanceof GuiChatTC$Button) && !(var23 instanceof GuiChatTC$EmoteOpenButton))
            {
                var23.drawButton(this.mc, cursorX, cursorY);
            }
            else
            {
                GL11.glScalef(1.0F / var19, 1.0F / var19, 1.0F);
                GL11.glTranslatef(0.0F, -var22, 1.0F);
                var23.drawButton(this.mc, cursorX, cursorY);
                GL11.glTranslatef(0.0F, var22, 1.0F);
                GL11.glScalef(var19, var19, 1.0F);
            }
        }

        GL11.glPopMatrix();
        this.fontRenderer.setUnicodeFlag(var15);
    }

    public void func_73894_a(String[] par1ArrayOfStr)
    {
        if (this.waitingOnPlayerNames)
        {
            this.foundPlayerNames.clear();
            String[] _copy = par1ArrayOfStr;
            int _len = par1ArrayOfStr.length;

            for (int i = 0; i < _len; ++i)
            {
                String name = _copy[i];

                if (name.length() > 0)
                {
                    this.foundPlayerNames.add(name);
                }
            }

            if (this.foundPlayerNames.size() > 0)
            {
                this.playerNamesFound = true;
                this.completePlayerName();
            }
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton instanceof ChatButton)
        {
            ChatButton objectives = (ChatButton)par1GuiButton;

            if (Keyboard.isKeyDown(42) && tc.channelMap.get("Global") == objectives.channel && (Minecraft.getMinecraft().thePlayer.getDisplayName().equalsIgnoreCase("iBalix") || Minecraft.getMinecraft().thePlayer.getDisplayName().equalsIgnoreCase("Mistersand")))
            {
                this.mc.displayGuiScreen(tc.generalSettings);
                return;
            }

            if (!tc.enabled())
            {
                return;
            }

            if (Keyboard.isKeyDown(42) && tc.channelMap.get("Global") != objectives.channel)
            {
                tc.channelMap.remove(objectives.channel.title);
            }
            else
            {
                Iterator current = tc.channelMap.values().iterator();

                while (current.hasNext())
                {
                    ChatChannel chan = (ChatChannel)current.next();

                    if (!objectives.equals(chan.tab))
                    {
                        chan.active = false;
                    }
                }

                if (!objectives.channel.active)
                {
                    this.scrollBar.scrollBarMouseWheel();
                    objectives.channel.active = true;
                    objectives.channel.unread = false;

                    if (objectives.channel.title.equals("Mon pays"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("FACTION")));
                    }
                    else if (objectives.channel.title.equals("ALL"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ALLY")));
                    }
                    else if (objectives.channel.title.equals("ENE"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ENEMY")));
                    }
                    else if (objectives.channel.title.equals("Global") && ClientProxy.serverType.equals("ng"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("PUBLIC")));
                    }
                    else if (objectives.channel.title.equals("ADMIN"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ADMIN")));
                    }
                    else if (objectives.channel.title.equals("MODO"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("MOD")));
                    }
                    else if (objectives.channel.title.equals("Journal"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("UA")));
                    }
                    else if (objectives.channel.title.equals("Mafia"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrMod")));
                    }
                    else if (objectives.channel.title.equals("Police"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("SrMod")));
                    }
                    else if (objectives.channel.title.equals("Guide"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrAdmin")));
                    }
                    else if (objectives.channel.title.equals("Avocat"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("Avocat")));
                    }
                }

                tc.resetDisplayedChat();
            }
        }
        else
        {
            List objectives1 = ClientData.objectives;

            if (par1GuiButton.id >= 10 && par1GuiButton.id < 10 + objectives1.size())
            {
                ClientData.currentObjectiveIndex = par1GuiButton.id - 10;
                this.displayText = true;
                this.generateTextLines();
            }
            else
            {
                switch (par1GuiButton.id)
                {
                    case 0:
                        this.displayText = true;
                        this.generateTextLines();

                    case 1:
                    case 2:
                    default:
                        break;

                    case 3:
                        Objective current1 = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);

                        if (current1 != null && current1.getId().split("-").length == 3)
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
                        }

                        this.displayText = false;
                        break;

                    case 4:
                        this.displayText = false;
                        break;

                    case 5:
                        this.displayEmotesGui = !this.displayEmotesGui;
                        break;

                    case 6:
                        if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0)
                        {
                            if (ClientData.currentJumpStartTime.longValue() == -1L)
                            {
                                Minecraft.getMinecraft().displayGuiScreen(new IslandMainGui());
                            }
                            else
                            {
                                NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", Long.valueOf(-1L));
                                ClientData.currentJumpRecord = "";
                                ClientData.currentJumpStartTime = Long.valueOf(-1L);
                            }
                        }
                        else
                        {
                            NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", Long.valueOf(-1L));
                            ClientData.currentJumpRecord = "";
                            ClientData.currentJumpStartTime = Long.valueOf(-1L);
                        }

                        break;

                    case 7:
                        Minecraft.getMinecraft().displayGuiScreen(new IslandListGui(Minecraft.getMinecraft().thePlayer));
                        break;

                    case 8:
                        this.displayEmotesGui = false;
                }
            }

            if (this.displayEmotesGui && this.mappingButtonIdEmote.containsKey(Integer.valueOf(par1GuiButton.id)))
            {
                this.inputField.setText(this.inputField.getText() + ":" + (String)this.mappingButtonIdEmote.get(Integer.valueOf(par1GuiButton.id)) + ":");
            }
        }
    }

    public void drawChatTabs()
    {
        this.buttonList.clear();
        this.mappingButtonIdEmote.clear();
        tc.updateButtonLocations();
        Iterator y = tc.channelMap.values().iterator();

        while (y.hasNext())
        {
            ChatChannel buttonIndex = (ChatChannel)y.next();
            this.buttonList.add(buttonIndex.tab);
        }

        int var7 = 30;
        int var8;

        if (ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
        {
            if (!this.displayText)
            {
                if (!ClientData.objectives.isEmpty())
                {
                    var8 = 10;

                    for (Iterator emoteWidth = ClientData.objectives.iterator(); emoteWidth.hasNext(); ++var8)
                    {
                        Objective emoteHeight = (Objective)emoteWidth.next();
                        this.buttonList.add(new GuiChatTC$Button(this, var8, 4, var7, 112, 29));
                        var7 += 34;
                    }
                }
            }
            else
            {
                this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null ? 10 : 0;
                this.scrollBarObj = new GuiChatTC$ScrollBar(this, (float)(this.width / 2 + 200 - 15), (float)(this.height / 2 - 125 + 30), 185);

                if (((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null)
                {
                    this.buttonList.add(new GuiChatTC$SimpleButton(this, 3, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                }
                else
                {
                    this.buttonList.add(new GuiChatTC$SimpleButton(this, 4, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                    this.buttonList.add(new GuiChatTC$SimpleButton(this, 3, this.width / 2 + 200 - 56 - 105, this.height / 2 + 125 - 25, 100, 15, I18n.getString("objectives.validate")));
                }
            }
        }

        this.buttonList.add(new GuiChatTC$EmoteOpenButton(this, 5, this.width - 34, this.height - 14, 12, 12));
        var8 = 0;
        int var9 = this.width - 131 + var8 % 5 * 22;
        int var10 = this.height - 134 + var8 / 5 * 20;
        Iterator it = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

        while (it.hasNext())
        {
            Entry offsetY = (Entry)it.next();

            if (NationsGUI.EMOTES_RESOURCES.containsKey((String)offsetY.getKey()))
            {
                this.buttonList.add(new GuiChatTC$EmoteButton(this, 100 + var8, var9, var10, 22, 20, (String)offsetY.getKey()));
                this.mappingButtonIdEmote.put(Integer.valueOf(100 + var8), (String)offsetY.getKey());
                ++var8;
                var9 = this.width - 131 + var8 % 5 * 22;
                var10 = this.height - 134 + var8 / 5 * 20;
            }
        }

        Double var11;

        if (ClientData.currentIsland != null && ClientData.currentIsland.size() > 0)
        {
            var11 = Double.valueOf((double)this.height * 0.4D + 78.0D);
            this.buttonList.add(new GuiChatTC$VoidButton(this, 6, this.width - 120 + 13, var11.intValue(), 94, 15));
            this.buttonList.add(new GuiChatTC$VoidButton(this, 7, this.width - 120 + 13, var11.intValue() + 20, 94, 15));
        }
        else if (!ClientData.currentJumpLocation.isEmpty())
        {
            var11 = Double.valueOf((double)this.height * 0.4D + 80.0D);
            this.buttonList.add(new GuiChatTC$VoidButton(this, 6, this.width - 140, var11.intValue(), 140, 16));
        }
    }

    public int getFocusedFieldInd()
    {
        int _s = this.inputList.size();

        for (int i = 0; i < _s; ++i)
        {
            if (((GuiTextField)this.inputList.get(i)).isFocused() && ((GuiTextField)this.inputList.get(i)).getVisible())
            {
                return i;
            }
        }

        return 0;
    }

    public void removeCharsAtCursor(int _del)
    {
        StringBuilder msg = new StringBuilder();
        int cPos = 0;
        boolean cFound = false;
        int other;

        for (other = this.inputList.size() - 1; other >= 0; --other)
        {
            msg.append(((GuiTextField)this.inputList.get(other)).getText());

            if (((GuiTextField)this.inputList.get(other)).isFocused())
            {
                cPos += ((GuiTextField)this.inputList.get(other)).getCursorPosition();
                cFound = true;
            }
            else if (!cFound)
            {
                cPos += ((GuiTextField)this.inputList.get(other)).getText().length();
            }
        }

        other = cPos + _del;
        other = Math.min(msg.length() - 1, other);
        other = Math.max(0, other);

        if (other < cPos)
        {
            msg.replace(other, cPos, "");
            this.setText(msg, other);
        }
        else
        {
            if (other <= cPos)
            {
                return;
            }

            msg.replace(cPos, other, "");
            this.setText(msg, cPos);
        }
    }

    public void insertCharsAtCursor(String _chars)
    {
        StringBuilder msg = new StringBuilder();
        int cPos = 0;
        boolean cFound = false;

        for (int i = this.inputList.size() - 1; i >= 0; --i)
        {
            msg.append(((GuiTextField)this.inputList.get(i)).getText());

            if (((GuiTextField)this.inputList.get(i)).isFocused())
            {
                cPos += ((GuiTextField)this.inputList.get(i)).getCursorPosition();
                cFound = true;
            }
            else if (!cFound)
            {
                cPos += ((GuiTextField)this.inputList.get(i)).getText().length();
            }
        }

        if (this.fontRenderer.getStringWidth(msg.toString()) + this.fontRenderer.getStringWidth(_chars) < (this.sr.getScaledWidth() - 20) * this.inputList.size())
        {
            msg.insert(cPos, _chars);
            this.setText(msg, cPos + _chars.length());
        }
    }

    public void setText(StringBuilder txt, int pos)
    {
        List txtList = this.stringListByWidth(txt, this.sr.getScaledWidth() - 20);
        int strings = Math.min(txtList.size() - 1, this.inputList.size() - 1);
        int j;

        for (j = strings; j >= 0; --j)
        {
            ((GuiTextField)this.inputList.get(j)).setText((String)txtList.get(strings - j));

            if (pos > ((String)txtList.get(strings - j)).length())
            {
                pos -= ((String)txtList.get(strings - j)).length();
                ((GuiTextField)this.inputList.get(j)).setVisible(true);
                ((GuiTextField)this.inputList.get(j)).setFocused(false);
            }
            else if (pos >= 0)
            {
                ((GuiTextField)this.inputList.get(j)).setFocused(true);
                ((GuiTextField)this.inputList.get(j)).setVisible(true);
                ((GuiTextField)this.inputList.get(j)).setCursorPosition(pos);
                pos = -1;
            }
            else
            {
                ((GuiTextField)this.inputList.get(j)).setVisible(true);
                ((GuiTextField)this.inputList.get(j)).setFocused(false);
            }
        }

        if (pos > 0)
        {
            this.inputField.setCursorPositionEnd();
        }

        if (this.inputList.size() > txtList.size())
        {
            for (j = txtList.size(); j < this.inputList.size(); ++j)
            {
                ((GuiTextField)this.inputList.get(j)).setText("");
                ((GuiTextField)this.inputList.get(j)).setFocused(false);
                ((GuiTextField)this.inputList.get(j)).setVisible(false);
            }
        }

        if (!this.inputField.getVisible())
        {
            this.inputField.setVisible(true);
            this.inputField.setFocused(true);
        }
    }

    public List<String> stringListByWidth(StringBuilder _sb, int _w)
    {
        ArrayList result = new ArrayList(5);
        int _len = 0;
        StringBuilder bucket = new StringBuilder(_sb.length());

        for (int ind = 0; ind < _sb.length(); ++ind)
        {
            int _cw = this.fontRenderer.getCharWidth(_sb.charAt(ind));

            if (_len + _cw > _w)
            {
                result.add(bucket.toString());
                bucket = new StringBuilder(_sb.length());
                _len = 0;
            }

            _len += _cw;
            bucket.append(_sb.charAt(ind));
        }

        if (bucket.length() > 0)
        {
            result.add(bucket.toString());
        }

        return result;
    }

    public int getCurrentSends()
    {
        int lng = 0;
        int _s = this.inputList.size() - 1;

        for (int i = _s; i >= 0; --i)
        {
            lng += ((GuiTextField)this.inputList.get(i)).getText().length();
        }

        return lng == 0 ? 0 : (int)Math.ceil((double)((float)lng / 100.0F));
    }

    private void generateTextLines()
    {
        this.lines.clear();
        short lineWidth = 360;
        int spaceWidth = this.fontRenderer.getStringWidth(" ");
        Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
        String[] l = current.getText().split("\n");
        String[] var5 = l;
        int var6 = l.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            String line = var5[var7];
            int spaceLeft = lineWidth;
            String[] words = line.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            String[] var12 = words;
            int var13 = words.length;

            for (int var14 = 0; var14 < var13; ++var14)
            {
                String word = var12[var14];
                int wordWidth = this.fontRenderer.getStringWidth(word);

                if (wordWidth + spaceWidth > spaceLeft)
                {
                    this.lines.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    spaceLeft = lineWidth - wordWidth;
                }
                else
                {
                    spaceLeft -= wordWidth + spaceWidth;
                }

                stringBuilder.append(word);
                stringBuilder.append(' ');
            }

            this.lines.add(stringBuilder.toString());
        }
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void autocompleteTooltipEmote(int guiWidth, int guiHeight, GuiTextField textField)
    {
        Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
        Matcher m = pattern.matcher(textField.getText());

        if (m.find())
        {
            String emoteToAutocomplete = m.group(1);
            ArrayList emoteWhichMatch = new ArrayList();
            Iterator it = NationsGUI.EMOTES_RESOURCES.entrySet().iterator();

            while (it.hasNext())
            {
                Entry pair = (Entry)it.next();
                String emoteName = (String)pair.getKey();
                pattern = Pattern.compile("^" + emoteToAutocomplete);
                m = pattern.matcher(emoteName);

                if (m.find())
                {
                    emoteWhichMatch.add(":" + emoteName + ":");
                }
            }

            if (emoteWhichMatch.size() > 0)
            {
                this.drawHoveringText(emoteWhichMatch, Minecraft.getMinecraft().fontRenderer.getStringWidth(textField.getText()), guiHeight - 14 - emoteWhichMatch.size() * 9, Minecraft.getMinecraft().fontRenderer);
            }
        }
    }

    public void autocompleteTextEmote(GuiTextField textField)
    {
        Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
        Matcher m = pattern.matcher(textField.getText());

        if (m.find())
        {
            String emoteToAutocomplete = m.group(1);
            String emoteWhichMatch = null;
            Iterator it = NationsGUI.EMOTES_RESOURCES.entrySet().iterator();

            while (it.hasNext())
            {
                Entry pair = (Entry)it.next();
                String emoteName = (String)pair.getKey();
                pattern = Pattern.compile("^" + emoteToAutocomplete);
                m = pattern.matcher(emoteName);

                if (m.find())
                {
                    emoteWhichMatch = ":" + emoteName + ":";
                }
            }

            if (emoteWhichMatch != null)
            {
                textField.setText(textField.getText() + emoteWhichMatch.replace(":" + emoteToAutocomplete, "") + " ");
            }
        }
    }

    static Minecraft access$000(GuiChatTC x0)
    {
        return x0.mc;
    }

    static boolean access$100(GuiChatTC x0)
    {
        return x0.displayEmotesGui;
    }

    static Minecraft access$200(GuiChatTC x0)
    {
        return x0.mc;
    }

    static FontRenderer access$300(GuiChatTC x0)
    {
        return x0.fontRenderer;
    }

    static FontRenderer access$400(GuiChatTC x0)
    {
        return x0.fontRenderer;
    }
}
