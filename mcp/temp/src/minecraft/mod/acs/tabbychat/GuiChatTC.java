package acs.tabbychat;

import acs.tabbychat.ChatButton;
import acs.tabbychat.ChatChannel;
import acs.tabbychat.ChatScrollBar;
import acs.tabbychat.GuiChatTC$Button;
import acs.tabbychat.GuiChatTC$EmoteButton;
import acs.tabbychat.GuiChatTC$EmoteOpenButton;
import acs.tabbychat.GuiChatTC$ScrollBar;
import acs.tabbychat.GuiChatTC$SimpleButton;
import acs.tabbychat.GuiChatTC$VoidButton;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
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

public class GuiChatTC extends GuiChat {

   public String historyBuffer = "";
   public String field_73900_q = "";
   public int field_73899_c = -1;
   private boolean playerNamesFound = false;
   private boolean waitingOnPlayerNames = false;
   private int playerNameIndex = 0;
   private List foundPlayerNames = new ArrayList();
   private URI clickedURI = null;
   public GuiTextField field_73901_a;
   public List<GuiTextField> inputList = new ArrayList(3);
   public ChatScrollBar scrollBar;
   public GuiButton field_73883_a = null;
   public int field_85042_b = 0;
   public long field_85043_c = 0L;
   public int field_92018_d = 0;
   public float field_73735_i = 0.0F;
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
      Keyboard.enableRepeatEvents(true);
      this.sr = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
      this.field_73887_h.clear();
      this.mappingButtonIdEmote.clear();
      this.inputList.clear();
      this.field_73880_f = this.sr.func_78326_a();
      this.field_73881_g = this.sr.func_78328_b();
      tc.checkServer();
      if(tc.enabled()) {
         this.drawChatTabs();
         if(this.scrollBar == null) {
            this.scrollBar = new ChatScrollBar(this);
         }

         this.scrollBar.drawScrollBar();
      } else if(!Minecraft.func_71410_x().func_71356_B()) {
         tc.updateButtonLocations();
         this.field_73887_h.add(((ChatChannel)tc.channelMap.get("Global")).tab);
      }

      TabbyChat var10001 = tc;
      this.field_73899_c = TabbyChat.gnc.func_73756_b().size();
      this.field_73901_a = new GuiTextField(this.field_73886_k, 4, this.field_73881_g - 12, this.field_73880_f - 4, 12);
      this.field_73901_a.func_73804_f(500);
      this.field_73901_a.func_73786_a(false);
      this.field_73901_a.func_73796_b(true);
      this.field_73901_a.func_73782_a(this.field_73900_q);
      this.field_73901_a.func_73805_d(true);
      this.inputList.add(0, this.field_73901_a);
      if(tc.enabled()) {
         int y;
         for(y = 1; y < 3; ++y) {
            GuiTextField placeholder = new GuiTextField(this.field_73886_k, 4, this.field_73881_g - 12 * (y + 1), this.field_73880_f, 12);
            placeholder.func_73804_f(500);
            placeholder.func_73786_a(false);
            placeholder.func_73796_b(false);
            placeholder.func_73782_a("");
            placeholder.func_73805_d(true);
            placeholder.func_73790_e(false);
            this.inputList.add(y, placeholder);
         }

         y = 30;
         int buttonIndex;
         if(ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
            if(!this.displayText) {
               if(!ClientData.objectives.isEmpty()) {
                  buttonIndex = 10;

                  for(Iterator emoteWidth = ClientData.objectives.iterator(); emoteWidth.hasNext(); ++buttonIndex) {
                     Objective emoteHeight = (Objective)emoteWidth.next();
                     this.field_73887_h.add(new GuiChatTC$Button(this, buttonIndex, 4, y, 112, 29));
                     y += 34;
                  }
               }

               this.scrollBarObj = null;
            } else {
               this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null?10:0;
               this.scrollBarObj = new GuiChatTC$ScrollBar(this, (float)(this.field_73880_f / 2 + 200 - 15), (float)(this.field_73881_g / 2 - 125 + 30), 185);
               if(((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null) {
                  this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
               } else {
                  this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
                  this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a("objectives.validate")));
               }
            }
         }

         this.field_73887_h.add(new GuiChatTC$EmoteOpenButton(this, 5, this.field_73880_f - 34, this.field_73881_g - 14, 12, 12));
         buttonIndex = 0;
         int var8 = this.field_73880_f - 131 + buttonIndex % 5 * 22;
         int var9 = this.field_73881_g - 134 + buttonIndex / 5 * 20;
         Iterator it = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

         while(it.hasNext()) {
            Entry offsetY = (Entry)it.next();
            if(NationsGUI.EMOTES_RESOURCES.containsKey((String)offsetY.getKey())) {
               this.field_73887_h.add(new GuiChatTC$EmoteButton(this, 100 + buttonIndex, var8, var9, 22, 20, (String)offsetY.getKey()));
               this.mappingButtonIdEmote.put(Integer.valueOf(100 + buttonIndex), (String)offsetY.getKey());
               ++buttonIndex;
               var8 = this.field_73880_f - 131 + buttonIndex % 5 * 22;
               var9 = this.field_73881_g - 134 + buttonIndex / 5 * 20;
            }
         }

         Double var10;
         if(ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
            var10 = Double.valueOf((double)this.field_73881_g * 0.4D + 78.0D);
            this.field_73887_h.add(new GuiChatTC$VoidButton(this, 6, this.field_73880_f - 120 + 13, var10.intValue(), 94, 15));
            this.field_73887_h.add(new GuiChatTC$VoidButton(this, 7, this.field_73880_f - 120 + 13, var10.intValue() + 20, 94, 15));
         } else if(!ClientData.currentJumpLocation.isEmpty()) {
            var10 = Double.valueOf((double)this.field_73881_g * 0.4D + 80.0D);
            this.field_73887_h.add(new GuiChatTC$VoidButton(this, 6, this.field_73880_f - 140, var10.intValue(), 140, 16));
         }

      }
   }

   public void func_73876_c() {
      this.field_73901_a.func_73780_a();
   }

   public void func_73869_a(char _char, int _code) {
      this.waitingOnPlayerNames = false;
      if(_code == 15) {
         this.func_73895_u_();
         this.autocompleteTextEmote(this.field_73901_a);
      } else {
         this.playerNamesFound = false;
      }

      if(_code == 1) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
      } else {
         int gcp;
         int lng;
         if(_code == 28) {
            StringBuilder foc = new StringBuilder(1500);

            for(gcp = this.inputList.size() - 1; gcp >= 0; --gcp) {
               foc.append(((GuiTextField)this.inputList.get(gcp)).func_73781_b());
            }

            if(foc.toString().length() > 0 && foc.toString().length() < 100 && !foc.toString().trim().equals("")) {
               String var8 = foc.toString();
               if(this.field_73882_e.func_71409_c(var8)) {
                  Minecraft.func_71410_x().field_71456_v.func_73827_b().func_73767_b(var8);

                  for(lng = 1; lng < this.inputList.size(); ++lng) {
                     ((GuiTextField)this.inputList.get(lng)).func_73782_a("");
                     ((GuiTextField)this.inputList.get(lng)).func_73790_e(false);
                  }

                  this.field_73882_e.func_71373_a((GuiScreen)null);
                  return;
               }

               if(TabbyChat.instance.getActive().size() > 0 && (this.field_73901_a.func_73781_b().matches("/.*") || !((String)TabbyChat.instance.getActive().get(0)).equals("Global")) && this.field_73901_a.func_73781_b().length() >= 80) {
                  return;
               }

               if(this.field_73901_a.func_73781_b().startsWith("/") && this.field_73901_a.func_73781_b().length() >= 80) {
                  return;
               }

               if(TabbyChat.instance.getActive().size() > 0) {
                  if(ClientProxy.serverType.equals("build") && !foc.toString().matches("^/.*") && ((String)TabbyChat.instance.getActive().get(0)).matches("Ile [0-9]+")) {
                     var8 = "/i msg [" + (String)TabbyChat.instance.getActive().get(0) + "]" + var8;
                  } else if(!foc.toString().matches("^/.*") && !((String)TabbyChat.instance.getActive().get(0)).equals("Global") && !((String)TabbyChat.instance.getActive().get(0)).equals("Mon pays") && !((String)TabbyChat.instance.getActive().get(0)).equals("ALL") && !((String)TabbyChat.instance.getActive().get(0)).equals("ENE") && !((String)TabbyChat.instance.getActive().get(0)).equals("ADMIN") && !((String)TabbyChat.instance.getActive().get(0)).equals("MODO") && !((String)TabbyChat.instance.getActive().get(0)).equals("Police") && !((String)TabbyChat.instance.getActive().get(0)).equals("Mafia") && !((String)TabbyChat.instance.getActive().get(0)).equals("Journal") && !((String)TabbyChat.instance.getActive().get(0)).equals("Guide") && !((String)TabbyChat.instance.getActive().get(0)).equals("Avocat") && !((String)TabbyChat.instance.getActive().get(0)).equals("RP") && !((String)TabbyChat.instance.getActive().get(0)).equals("Logs")) {
                     var8 = var8.substring(0, Math.min(80, var8.length()));
                     var8 = "/m " + (String)TabbyChat.instance.getActive().get(0) + " " + var8;
                  }
               }

               TabbyChatUtils.writeLargeChat(var8);

               for(lng = 1; lng < this.inputList.size(); ++lng) {
                  ((GuiTextField)this.inputList.get(lng)).func_73782_a("");
                  ((GuiTextField)this.inputList.get(lng)).func_73790_e(false);
               }

               this.field_73882_e.func_71373_a((GuiScreen)null);
            }
         } else {
            int newPos;
            int var7;
            if(_code == 200) {
               if(GuiScreen.func_73861_o()) {
                  this.func_73892_a(-1);
               } else {
                  var7 = this.getFocusedFieldInd();
                  if(var7 + 1 < this.inputList.size() && ((GuiTextField)this.inputList.get(var7 + 1)).func_73778_q()) {
                     gcp = ((GuiTextField)this.inputList.get(var7)).func_73799_h();
                     lng = ((GuiTextField)this.inputList.get(var7 + 1)).func_73781_b().length();
                     newPos = Math.min(gcp, lng);
                     ((GuiTextField)this.inputList.get(var7)).func_73796_b(false);
                     ((GuiTextField)this.inputList.get(var7 + 1)).func_73796_b(true);
                     ((GuiTextField)this.inputList.get(var7 + 1)).func_73791_e(newPos);
                  } else {
                     this.func_73892_a(-1);
                  }
               }
            } else if(_code == 208) {
               if(GuiScreen.func_73861_o()) {
                  this.func_73892_a(1);
               } else {
                  var7 = this.getFocusedFieldInd();
                  if(var7 - 1 >= 0 && ((GuiTextField)this.inputList.get(var7 - 1)).func_73778_q()) {
                     gcp = ((GuiTextField)this.inputList.get(var7)).func_73799_h();
                     lng = ((GuiTextField)this.inputList.get(var7 - 1)).func_73781_b().length();
                     newPos = Math.min(gcp, lng);
                     ((GuiTextField)this.inputList.get(var7)).func_73796_b(false);
                     ((GuiTextField)this.inputList.get(var7 - 1)).func_73796_b(true);
                     ((GuiTextField)this.inputList.get(var7 - 1)).func_73791_e(newPos);
                  } else {
                     this.func_73892_a(1);
                  }
               }
            } else {
               TabbyChat var10000;
               if(_code == 201) {
                  var10000 = tc;
                  TabbyChat.gnc.func_73758_b(19);
                  if(tc.enabled()) {
                     this.scrollBar.scrollBarMouseWheel();
                  }
               } else if(_code == 209) {
                  var10000 = tc;
                  TabbyChat.gnc.func_73758_b(-19);
                  if(tc.enabled()) {
                     this.scrollBar.scrollBarMouseWheel();
                  }
               } else if(_code == 14) {
                  if(this.field_73901_a.func_73806_l() && this.field_73901_a.func_73799_h() > 0) {
                     this.field_73901_a.func_73802_a(_char, _code);
                  } else {
                     this.removeCharsAtCursor(-1);
                  }
               } else if(_code == 211) {
                  if(this.field_73901_a.func_73806_l()) {
                     this.field_73901_a.func_73802_a(_char, _code);
                  } else {
                     this.removeCharsAtCursor(1);
                  }
               } else if(_code != 203 && _code != 205) {
                  if(this.field_73901_a.func_73806_l() && this.field_73886_k.func_78256_a(this.field_73901_a.func_73781_b()) < this.sr.func_78326_a() - 20) {
                     if(TabbyChat.instance.getActive().size() > 0 && (this.field_73901_a.func_73781_b().matches("/.*") || !((String)TabbyChat.instance.getActive().get(0)).equals("Global")) && this.field_73901_a.func_73781_b().length() >= 80) {
                        return;
                     }

                     if((this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/") || this.field_73901_a.func_73781_b().startsWith("/")) && this.field_73901_a.func_73781_b().length() >= 80) {
                        return;
                     }

                     if(this.field_73901_a.func_73781_b().length() < 99) {
                        this.field_73901_a.func_73802_a(_char, _code);
                     }
                  } else {
                     this.insertCharsAtCursor(Character.toString(_char));
                  }
               } else {
                  ((GuiTextField)this.inputList.get(this.getFocusedFieldInd())).func_73802_a(_char, _code);
               }
            }
         }
      }

   }

   public void func_73867_d() {
      int wheelDelta = Mouse.getEventDWheel();
      if(wheelDelta != 0) {
         wheelDelta = Math.min(1, wheelDelta);
         wheelDelta = Math.max(-1, wheelDelta);
         if(!func_73877_p()) {
            wheelDelta *= 7;
         }

         TabbyChat var10000 = tc;
         TabbyChat.gnc.func_73758_b(wheelDelta);
         if(tc.enabled()) {
            this.scrollBar.scrollBarMouseWheel();
         }
      } else if(tc.enabled()) {
         this.scrollBar.handleMouse();
      }

      if(this.field_73882_e.field_71462_r.getClass() != GuiChat.class) {
         super.func_73867_d();
      }

   }

   public void func_73879_b(int _x, int _y, int _button) {
      if(this.field_73883_a != null && _button == 0) {
         this.field_73883_a.func_73740_a(_x, _y);
         this.field_73883_a = null;
      }

   }

   public void func_73864_a(int _x, int _y, int _button) {
      TabbyChat var10000;
      if(_button == 0 && this.field_73882_e.field_71474_y.field_74359_p) {
         var10000 = tc;
         ChatClickData var8 = TabbyChat.gnc.func_73766_a(Mouse.getX(), Mouse.getY());
         if(var8 != null) {
            URI var10 = var8.func_78308_g();
            if(var10 != null) {
               if(this.field_73882_e.field_71474_y.field_74358_q) {
                  this.clickedURI = var10;
                  this.field_73882_e.func_71373_a(new GuiConfirmOpenLink(this, var8.func_78309_f(), 0, false));
               } else {
                  this.func_73896_a(var10);
               }

               return;
            }
         }

         var10000 = tc;
         ChatClickCountryData var12 = TabbyChat.gnc.getClickedCountryData(Mouse.getX(), Mouse.getY());
         if(var12 != null) {
            String var14 = var12.getCountry();
            if(var14 != null) {
               this.field_73882_e.func_71373_a(new FactionGUI(var12.getCountry()));
               return;
            }
         }

         var10000 = tc;
         ChatClickProfilData var16 = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());
         if(var16 != null) {
            String var18 = var16.getProfil();
            if(var18 != null) {
               FactionGUI.factionInfos = null;
               this.field_73882_e.func_71373_a(new ProfilGui(var16.getProfil(), ""));
               return;
            }
         }
      } else if(_button == 1) {
         var10000 = tc;
         ChatClickProfilData i = TabbyChat.gnc.getClickedProfilData(Mouse.getX(), Mouse.getY());
         if(i != null) {
            String _guibutton = i.getProfil();
            if(_guibutton != null) {
               if(!tc.channelMap.containsKey(_guibutton)) {
                  tc.channelMap.put(_guibutton, new ChatChannel(_guibutton));
               }

               ChatChannel chan;
               for(Iterator field = tc.channelMap.values().iterator(); field.hasNext(); chan.active = false) {
                  chan = (ChatChannel)field.next();
               }

               ((ChatChannel)tc.channelMap.get(_guibutton)).active = true;
               tc.resetDisplayedChat();
               return;
            }
         }
      }

      for(int var9 = 0; var9 < this.inputList.size(); ++var9) {
         if(_y >= this.field_73881_g - 12 * (var9 + 1) && ((GuiTextField)this.inputList.get(var9)).func_73778_q()) {
            ((GuiTextField)this.inputList.get(var9)).func_73796_b(true);
            Iterator var13 = this.inputList.iterator();

            while(var13.hasNext()) {
               GuiTextField var17 = (GuiTextField)var13.next();
               if(var17 != this.inputList.get(var9)) {
                  var17.func_73796_b(false);
               }
            }

            ((GuiTextField)this.inputList.get(var9)).func_73793_a(_x, _y, _button);
            break;
         }
      }

      if(_button == 0) {
         Iterator var11 = this.field_73887_h.iterator();

         while(var11.hasNext()) {
            GuiButton var15 = (GuiButton)var11.next();
            if(var15.func_73736_c(this.field_73882_e, _x, _y)) {
               this.field_73883_a = var15;
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               this.func_73875_a(var15);
            }
         }
      }

   }

   public void func_73878_a(boolean zeroId, int worldNum) {
      if(worldNum == 0) {
         if(zeroId) {
            this.func_73896_a(this.clickedURI);
         }

         this.clickedURI = null;
         this.field_73882_e.func_71373_a(this);
      }

   }

   public void func_73896_a(URI _uri) {
      try {
         Class t = Class.forName("java.awt.Desktop");
         Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         t.getMethod("browse", new Class[]{URI.class}).invoke(theDesktop, new Object[]{_uri});
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public void func_73895_u_() {
      String textBuffer;
      if(this.playerNamesFound) {
         this.field_73901_a.func_73777_b(this.field_73901_a.func_73798_a(-1, this.field_73901_a.func_73799_h(), false) - this.field_73901_a.func_73799_h());
         if(this.playerNameIndex >= this.foundPlayerNames.size()) {
            this.playerNameIndex = 0;
         }
      } else {
         int _sb = this.field_73901_a.func_73798_a(-1, this.field_73901_a.func_73799_h(), false);
         this.foundPlayerNames.clear();
         this.playerNameIndex = 0;
         String _iter = this.field_73901_a.func_73781_b().substring(_sb).toLowerCase();
         textBuffer = this.field_73901_a.func_73781_b().substring(0, this.field_73901_a.func_73799_h());
         this.func_73893_a(textBuffer, _iter);
         if(this.foundPlayerNames.isEmpty()) {
            return;
         }

         this.playerNamesFound = true;
         this.field_73901_a.func_73777_b(_sb - this.field_73901_a.func_73799_h());
      }

      if(this.foundPlayerNames.size() > 1) {
         StringBuilder var4 = new StringBuilder();

         for(Iterator var5 = this.foundPlayerNames.iterator(); var5.hasNext(); var4.append(textBuffer)) {
            textBuffer = (String)var5.next();
            if(var4.length() > 0) {
               var4.append(", ");
            }
         }

         this.field_73882_e.field_71456_v.func_73827_b().func_73763_a(var4.toString(), 1);
      }

      if(this.foundPlayerNames.size() > this.playerNameIndex) {
         this.field_73901_a.func_73792_b((String)this.foundPlayerNames.get(this.playerNameIndex));
         ++this.playerNameIndex;
      }

   }

   public void func_73893_a(String nameStart, String buffer) {
      if(nameStart.length() >= 1) {
         this.field_73882_e.field_71439_g.field_71174_a.func_72552_c(new Packet203AutoComplete(nameStart));
         this.waitingOnPlayerNames = true;
      }

   }

   public void func_73892_a(int _dir) {
      int loc = this.field_73899_c + _dir;
      TabbyChat var10000 = tc;
      int historyLength = TabbyChat.gnc.func_73756_b().size();
      loc = Math.max(0, loc);
      loc = Math.min(historyLength, loc);
      if(loc != this.field_73899_c) {
         if(loc == historyLength) {
            this.field_73899_c = historyLength;
            this.setText(new StringBuilder(""), 1);
         } else {
            if(this.field_73899_c == historyLength) {
               this.historyBuffer = this.field_73901_a.func_73781_b();
            }

            StringBuilder var5 = new StringBuilder((String)TabbyChat.gnc.func_73756_b().get(loc));
            TabbyChat var10002 = tc;
            StringBuilder _sb = var5;
            this.setText(_sb, _sb.length());
            this.field_73899_c = loc;
         }

      }
   }

   public void func_73863_a(int cursorX, int cursorY, float pointless) {
      int inputHeight;
      Iterator scaleSetting;
      if(this.displayText && this.scrollBarObj != null) {
         Objective unicodeStore = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
         func_73734_a(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 25, this.field_73880_f / 2 + 200, this.field_73881_g / 2 + 125, -301989888);
         func_73734_a(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5, this.field_73880_f / 2 + 200, this.field_73881_g / 2 - 125 + 25, -1728053248);
         ClientEventHandler.STYLE.bindTexture("hud2");
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_73880_f / 2 - 200 + 5, this.field_73881_g / 2 - 125 + 8, 2, 20, 13, 13);
         this.func_73731_b(this.field_73886_k, unicodeStore.getTitle(), this.field_73880_f / 2 - 200 + 20, this.field_73881_g / 2 - 125 + 11, 16777215);
         GUIUtils.startGLScissor(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5 + 30, 380, 185);
         GL11.glPushMatrix();
         if(this.lines.size() > 11) {
            GL11.glTranslatef(0.0F, -this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier), 0.0F);
         }

         inputHeight = 0;

         for(scaleSetting = this.lines.iterator(); scaleSetting.hasNext(); ++inputHeight) {
            String scaleOffset = (String)scaleSetting.next();
            this.func_73731_b(this.field_73886_k, scaleOffset, this.field_73880_f / 2 - 200 + 10, this.field_73881_g / 2 - 125 + 35 + inputHeight * 12, 16777215);
         }

         ItemStack var16 = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack();
         boolean var18 = false;
         if(var16 != null) {
            int itemY = this.lines.size() * 12 + 2;
            int _button = this.field_73886_k.func_78256_a(I18n.func_135053_a("objectives.collect"));
            int w = _button + 16 + 4;
            this.field_73886_k.func_78276_b(I18n.func_135053_a("objectives.collect"), this.field_73880_f / 2 - 10 - w / 2, this.field_73881_g / 2 - 125 + 35 + itemY + 4, 16777215);
            this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), var16, this.field_73880_f / 2 - 10 - w / 2 + _button + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
            this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), var16, this.field_73880_f / 2 - 10 - w / 2 + _button + 4, this.field_73881_g / 2 - 125 + 35 + itemY);
            int pX = this.field_73880_f / 2 - 10 - w / 2 + _button + 4;
            int pY = this.field_73881_g / 2 - 125 + 35 + itemY - (int)(this.scrollBarObj.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
            var18 = cursorX >= pX && cursorX <= pX + 18 && cursorY >= pY && cursorY <= pY + 18;
            GL11.glDisable(2896);
         }

         GL11.glPopMatrix();
         GUIUtils.endGLScissor();
         if(var18) {
            NationsGUIClientHooks.drawItemStackTooltip(var16, cursorX, cursorY);
            GL11.glDisable(2896);
         }

         this.scrollBarObj.draw(cursorX, cursorY);
      }

      if(this.displayEmotesGui) {
         float var13 = this.field_73882_e.field_71474_y.field_74357_r * 0.9F + 0.1F;
         inputHeight = (int)(255.0F * var13);
         func_73734_a(this.field_73880_f - 132, this.field_73881_g - 134, this.field_73880_f - 132 + 110, this.field_73881_g - 134 + 120, inputHeight / 2 << 24);
      }

      if(ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty()) {
         int var14;
         if(!((String)ClientData.currentAssault.get("attackerHelpersCount")).equals("0")) {
            var14 = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a((String)ClientData.currentAssault.get("attackerFactionName")) - 1;
            inputHeight = (int)((double)this.field_73881_g * 0.4D) + 26;
            if(cursorX >= var14 && cursorX <= var14 + 19 && cursorY >= inputHeight && cursorY <= inputHeight + 3) {
               this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("attackerHelpersName")).split(",")), cursorX, cursorY, this.field_73886_k);
            }
         }

         if(!((String)ClientData.currentAssault.get("defenderHelpersCount")).equals("0")) {
            var14 = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a((String)ClientData.currentAssault.get("defenderFactionName")) - 1;
            inputHeight = (int)((double)this.field_73881_g * 0.4D) + 26;
            if(cursorX >= var14 && cursorX <= var14 + 19 && cursorY >= inputHeight && cursorY <= inputHeight + 3) {
               this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("defenderHelpersName")).split(",")), cursorX, cursorY, this.field_73886_k);
            }
         }
      }

      boolean var15 = this.field_73886_k.func_82883_a();
      if(TabbyChat.instance.generalSettings.tabbyChatEnable.getValue().booleanValue() && ClientProxy.clientConfig.enableUnicode) {
         this.field_73886_k.func_78264_a(true);
      }

      this.field_73880_f = this.sr.func_78326_a();
      this.field_73881_g = this.sr.func_78328_b();
      inputHeight = 0;

      for(int var17 = 0; var17 < this.inputList.size(); ++var17) {
         if(((GuiTextField)this.inputList.get(var17)).func_73778_q()) {
            inputHeight += 12;
         }
      }

      func_73734_a(2, this.field_73881_g - 2 - inputHeight, this.field_73880_f - 2, this.field_73881_g - 2, Integer.MIN_VALUE);
      scaleSetting = this.inputList.iterator();

      while(scaleSetting.hasNext()) {
         GuiTextField var20 = (GuiTextField)scaleSetting.next();
         if(var20.func_73778_q()) {
            var20.func_73795_f();
            this.autocompleteTooltipEmote(this.field_73880_f, this.field_73881_g, var20);
         }
      }

      if(!this.field_73882_e.func_71356_B()) {
         this.drawChatTabs();
      }

      if(tc.enabled()) {
         this.scrollBar.drawScrollBar();
      }

      TabbyChat var10000 = tc;
      float var19 = TabbyChat.gnc.getScaleSetting();
      GL11.glPushMatrix();
      float var22 = (float)(this.sr.func_78328_b() - 28) * (1.0F - var19);
      GL11.glTranslatef(0.0F, var22, 1.0F);
      GL11.glScalef(var19, var19, 1.0F);
      Iterator var21 = this.field_73887_h.iterator();

      while(var21.hasNext()) {
         GuiButton var23 = (GuiButton)var21.next();
         if(!(var23 instanceof GuiChatTC$EmoteButton) && !(var23 instanceof GuiChatTC$SimpleButton) && !(var23 instanceof GuiChatTC$Button) && !(var23 instanceof GuiChatTC$EmoteOpenButton)) {
            var23.func_73737_a(this.field_73882_e, cursorX, cursorY);
         } else {
            GL11.glScalef(1.0F / var19, 1.0F / var19, 1.0F);
            GL11.glTranslatef(0.0F, -var22, 1.0F);
            var23.func_73737_a(this.field_73882_e, cursorX, cursorY);
            GL11.glTranslatef(0.0F, var22, 1.0F);
            GL11.glScalef(var19, var19, 1.0F);
         }
      }

      GL11.glPopMatrix();
      this.field_73886_k.func_78264_a(var15);
   }

   public void func_73894_a(String[] par1ArrayOfStr) {
      if(this.waitingOnPlayerNames) {
         this.foundPlayerNames.clear();
         String[] _copy = par1ArrayOfStr;
         int _len = par1ArrayOfStr.length;

         for(int i = 0; i < _len; ++i) {
            String name = _copy[i];
            if(name.length() > 0) {
               this.foundPlayerNames.add(name);
            }
         }

         if(this.foundPlayerNames.size() > 0) {
            this.playerNamesFound = true;
            this.func_73895_u_();
         }
      }

   }

   public void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton instanceof ChatButton) {
         ChatButton objectives = (ChatButton)par1GuiButton;
         if(Keyboard.isKeyDown(42) && tc.channelMap.get("Global") == objectives.channel && (Minecraft.func_71410_x().field_71439_g.getDisplayName().equalsIgnoreCase("iBalix") || Minecraft.func_71410_x().field_71439_g.getDisplayName().equalsIgnoreCase("Mistersand"))) {
            this.field_73882_e.func_71373_a(tc.generalSettings);
            return;
         }

         if(!tc.enabled()) {
            return;
         }

         if(Keyboard.isKeyDown(42) && tc.channelMap.get("Global") != objectives.channel) {
            tc.channelMap.remove(objectives.channel.title);
         } else {
            Iterator current = tc.channelMap.values().iterator();

            while(current.hasNext()) {
               ChatChannel chan = (ChatChannel)current.next();
               if(!objectives.equals(chan.tab)) {
                  chan.active = false;
               }
            }

            if(!objectives.channel.active) {
               this.scrollBar.scrollBarMouseWheel();
               objectives.channel.active = true;
               objectives.channel.unread = false;
               if(objectives.channel.title.equals("Mon pays")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("FACTION")));
               } else if(objectives.channel.title.equals("ALL")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ALLY")));
               } else if(objectives.channel.title.equals("ENE")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ENEMY")));
               } else if(objectives.channel.title.equals("Global") && ClientProxy.serverType.equals("ng")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("PUBLIC")));
               } else if(objectives.channel.title.equals("ADMIN")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("ADMIN")));
               } else if(objectives.channel.title.equals("MODO")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("MOD")));
               } else if(objectives.channel.title.equals("Journal")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("UA")));
               } else if(objectives.channel.title.equals("Mafia")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrMod")));
               } else if(objectives.channel.title.equals("Police")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("SrMod")));
               } else if(objectives.channel.title.equals("Guide")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("JrAdmin")));
               } else if(objectives.channel.title.equals("Avocat")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChatTabSetChatPacket("Avocat")));
               }
            }

            tc.resetDisplayedChat();
         }
      } else {
         List objectives1 = ClientData.objectives;
         if(par1GuiButton.field_73741_f >= 10 && par1GuiButton.field_73741_f < 10 + objectives1.size()) {
            ClientData.currentObjectiveIndex = par1GuiButton.field_73741_f - 10;
            this.displayText = true;
            this.generateTextLines();
         } else {
            switch(par1GuiButton.field_73741_f) {
            case 0:
               this.displayText = true;
               this.generateTextLines();
            case 1:
            case 2:
            default:
               break;
            case 3:
               Objective current1 = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
               if(current1 != null && current1.getId().split("-").length == 3) {
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
               if(ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
                  if(ClientData.currentJumpStartTime.longValue() == -1L) {
                     Minecraft.func_71410_x().func_71373_a(new IslandMainGui());
                  } else {
                     NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", Long.valueOf(-1L));
                     ClientData.currentJumpRecord = "";
                     ClientData.currentJumpStartTime = Long.valueOf(-1L);
                  }
               } else {
                  NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", Long.valueOf(-1L));
                  ClientData.currentJumpRecord = "";
                  ClientData.currentJumpStartTime = Long.valueOf(-1L);
               }
               break;
            case 7:
               Minecraft.func_71410_x().func_71373_a(new IslandListGui(Minecraft.func_71410_x().field_71439_g));
               break;
            case 8:
               this.displayEmotesGui = false;
            }
         }

         if(this.displayEmotesGui && this.mappingButtonIdEmote.containsKey(Integer.valueOf(par1GuiButton.field_73741_f))) {
            this.field_73901_a.func_73782_a(this.field_73901_a.func_73781_b() + ":" + (String)this.mappingButtonIdEmote.get(Integer.valueOf(par1GuiButton.field_73741_f)) + ":");
         }
      }

   }

   public void drawChatTabs() {
      this.field_73887_h.clear();
      this.mappingButtonIdEmote.clear();
      tc.updateButtonLocations();
      Iterator y = tc.channelMap.values().iterator();

      while(y.hasNext()) {
         ChatChannel buttonIndex = (ChatChannel)y.next();
         this.field_73887_h.add(buttonIndex.tab);
      }

      int var7 = 30;
      int var8;
      if(ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
         if(!this.displayText) {
            if(!ClientData.objectives.isEmpty()) {
               var8 = 10;

               for(Iterator emoteWidth = ClientData.objectives.iterator(); emoteWidth.hasNext(); ++var8) {
                  Objective emoteHeight = (Objective)emoteWidth.next();
                  this.field_73887_h.add(new GuiChatTC$Button(this, var8, 4, var7, 112, 29));
                  var7 += 34;
               }
            }
         } else {
            this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null?10:0;
            this.scrollBarObj = new GuiChatTC$ScrollBar(this, (float)(this.field_73880_f / 2 + 200 - 15), (float)(this.field_73881_g / 2 - 125 + 30), 185);
            if(((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null) {
               this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
            } else {
               this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
               this.field_73887_h.add(new GuiChatTC$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a("objectives.validate")));
            }
         }
      }

      this.field_73887_h.add(new GuiChatTC$EmoteOpenButton(this, 5, this.field_73880_f - 34, this.field_73881_g - 14, 12, 12));
      var8 = 0;
      int var9 = this.field_73880_f - 131 + var8 % 5 * 22;
      int var10 = this.field_73881_g - 134 + var8 / 5 * 20;
      Iterator it = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

      while(it.hasNext()) {
         Entry offsetY = (Entry)it.next();
         if(NationsGUI.EMOTES_RESOURCES.containsKey((String)offsetY.getKey())) {
            this.field_73887_h.add(new GuiChatTC$EmoteButton(this, 100 + var8, var9, var10, 22, 20, (String)offsetY.getKey()));
            this.mappingButtonIdEmote.put(Integer.valueOf(100 + var8), (String)offsetY.getKey());
            ++var8;
            var9 = this.field_73880_f - 131 + var8 % 5 * 22;
            var10 = this.field_73881_g - 134 + var8 / 5 * 20;
         }
      }

      Double var11;
      if(ClientData.currentIsland != null && ClientData.currentIsland.size() > 0) {
         var11 = Double.valueOf((double)this.field_73881_g * 0.4D + 78.0D);
         this.field_73887_h.add(new GuiChatTC$VoidButton(this, 6, this.field_73880_f - 120 + 13, var11.intValue(), 94, 15));
         this.field_73887_h.add(new GuiChatTC$VoidButton(this, 7, this.field_73880_f - 120 + 13, var11.intValue() + 20, 94, 15));
      } else if(!ClientData.currentJumpLocation.isEmpty()) {
         var11 = Double.valueOf((double)this.field_73881_g * 0.4D + 80.0D);
         this.field_73887_h.add(new GuiChatTC$VoidButton(this, 6, this.field_73880_f - 140, var11.intValue(), 140, 16));
      }

   }

   public int getFocusedFieldInd() {
      int _s = this.inputList.size();

      for(int i = 0; i < _s; ++i) {
         if(((GuiTextField)this.inputList.get(i)).func_73806_l() && ((GuiTextField)this.inputList.get(i)).func_73778_q()) {
            return i;
         }
      }

      return 0;
   }

   public void removeCharsAtCursor(int _del) {
      StringBuilder msg = new StringBuilder();
      int cPos = 0;
      boolean cFound = false;

      int other;
      for(other = this.inputList.size() - 1; other >= 0; --other) {
         msg.append(((GuiTextField)this.inputList.get(other)).func_73781_b());
         if(((GuiTextField)this.inputList.get(other)).func_73806_l()) {
            cPos += ((GuiTextField)this.inputList.get(other)).func_73799_h();
            cFound = true;
         } else if(!cFound) {
            cPos += ((GuiTextField)this.inputList.get(other)).func_73781_b().length();
         }
      }

      other = cPos + _del;
      other = Math.min(msg.length() - 1, other);
      other = Math.max(0, other);
      if(other < cPos) {
         msg.replace(other, cPos, "");
         this.setText(msg, other);
      } else {
         if(other <= cPos) {
            return;
         }

         msg.replace(cPos, other, "");
         this.setText(msg, cPos);
      }

   }

   public void insertCharsAtCursor(String _chars) {
      StringBuilder msg = new StringBuilder();
      int cPos = 0;
      boolean cFound = false;

      for(int i = this.inputList.size() - 1; i >= 0; --i) {
         msg.append(((GuiTextField)this.inputList.get(i)).func_73781_b());
         if(((GuiTextField)this.inputList.get(i)).func_73806_l()) {
            cPos += ((GuiTextField)this.inputList.get(i)).func_73799_h();
            cFound = true;
         } else if(!cFound) {
            cPos += ((GuiTextField)this.inputList.get(i)).func_73781_b().length();
         }
      }

      if(this.field_73886_k.func_78256_a(msg.toString()) + this.field_73886_k.func_78256_a(_chars) < (this.sr.func_78326_a() - 20) * this.inputList.size()) {
         msg.insert(cPos, _chars);
         this.setText(msg, cPos + _chars.length());
      }

   }

   public void setText(StringBuilder txt, int pos) {
      List txtList = this.stringListByWidth(txt, this.sr.func_78326_a() - 20);
      int strings = Math.min(txtList.size() - 1, this.inputList.size() - 1);

      int j;
      for(j = strings; j >= 0; --j) {
         ((GuiTextField)this.inputList.get(j)).func_73782_a((String)txtList.get(strings - j));
         if(pos > ((String)txtList.get(strings - j)).length()) {
            pos -= ((String)txtList.get(strings - j)).length();
            ((GuiTextField)this.inputList.get(j)).func_73790_e(true);
            ((GuiTextField)this.inputList.get(j)).func_73796_b(false);
         } else if(pos >= 0) {
            ((GuiTextField)this.inputList.get(j)).func_73796_b(true);
            ((GuiTextField)this.inputList.get(j)).func_73790_e(true);
            ((GuiTextField)this.inputList.get(j)).func_73791_e(pos);
            pos = -1;
         } else {
            ((GuiTextField)this.inputList.get(j)).func_73790_e(true);
            ((GuiTextField)this.inputList.get(j)).func_73796_b(false);
         }
      }

      if(pos > 0) {
         this.field_73901_a.func_73803_e();
      }

      if(this.inputList.size() > txtList.size()) {
         for(j = txtList.size(); j < this.inputList.size(); ++j) {
            ((GuiTextField)this.inputList.get(j)).func_73782_a("");
            ((GuiTextField)this.inputList.get(j)).func_73796_b(false);
            ((GuiTextField)this.inputList.get(j)).func_73790_e(false);
         }
      }

      if(!this.field_73901_a.func_73778_q()) {
         this.field_73901_a.func_73790_e(true);
         this.field_73901_a.func_73796_b(true);
      }

   }

   public List<String> stringListByWidth(StringBuilder _sb, int _w) {
      ArrayList result = new ArrayList(5);
      int _len = 0;
      StringBuilder bucket = new StringBuilder(_sb.length());

      for(int ind = 0; ind < _sb.length(); ++ind) {
         int _cw = this.field_73886_k.func_78263_a(_sb.charAt(ind));
         if(_len + _cw > _w) {
            result.add(bucket.toString());
            bucket = new StringBuilder(_sb.length());
            _len = 0;
         }

         _len += _cw;
         bucket.append(_sb.charAt(ind));
      }

      if(bucket.length() > 0) {
         result.add(bucket.toString());
      }

      return result;
   }

   public int getCurrentSends() {
      int lng = 0;
      int _s = this.inputList.size() - 1;

      for(int i = _s; i >= 0; --i) {
         lng += ((GuiTextField)this.inputList.get(i)).func_73781_b().length();
      }

      return lng == 0?0:(int)Math.ceil((double)((float)lng / 100.0F));
   }

   private void generateTextLines() {
      this.lines.clear();
      short lineWidth = 360;
      int spaceWidth = this.field_73886_k.func_78256_a(" ");
      Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
      String[] l = current.getText().split("\n");
      String[] var5 = l;
      int var6 = l.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String line = var5[var7];
         int spaceLeft = lineWidth;
         String[] words = line.split(" ");
         StringBuilder stringBuilder = new StringBuilder();
         String[] var12 = words;
         int var13 = words.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            String word = var12[var14];
            int wordWidth = this.field_73886_k.func_78256_a(word);
            if(wordWidth + spaceWidth > spaceLeft) {
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
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void autocompleteTooltipEmote(int guiWidth, int guiHeight, GuiTextField textField) {
      Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
      Matcher m = pattern.matcher(textField.func_73781_b());
      if(m.find()) {
         String emoteToAutocomplete = m.group(1);
         ArrayList emoteWhichMatch = new ArrayList();
         Iterator it = NationsGUI.EMOTES_RESOURCES.entrySet().iterator();

         while(it.hasNext()) {
            Entry pair = (Entry)it.next();
            String emoteName = (String)pair.getKey();
            pattern = Pattern.compile("^" + emoteToAutocomplete);
            m = pattern.matcher(emoteName);
            if(m.find()) {
               emoteWhichMatch.add(":" + emoteName + ":");
            }
         }

         if(emoteWhichMatch.size() > 0) {
            this.drawHoveringText(emoteWhichMatch, Minecraft.func_71410_x().field_71466_p.func_78256_a(textField.func_73781_b()), guiHeight - 14 - emoteWhichMatch.size() * 9, Minecraft.func_71410_x().field_71466_p);
         }
      }

   }

   public void autocompleteTextEmote(GuiTextField textField) {
      Pattern pattern = Pattern.compile(":([a-z0-9]+)$");
      Matcher m = pattern.matcher(textField.func_73781_b());
      if(m.find()) {
         String emoteToAutocomplete = m.group(1);
         String emoteWhichMatch = null;
         Iterator it = NationsGUI.EMOTES_RESOURCES.entrySet().iterator();

         while(it.hasNext()) {
            Entry pair = (Entry)it.next();
            String emoteName = (String)pair.getKey();
            pattern = Pattern.compile("^" + emoteToAutocomplete);
            m = pattern.matcher(emoteName);
            if(m.find()) {
               emoteWhichMatch = ":" + emoteName + ":";
            }
         }

         if(emoteWhichMatch != null) {
            textField.func_73782_a(textField.func_73781_b() + emoteWhichMatch.replace(":" + emoteToAutocomplete, "") + " ");
         }
      }

   }

   // $FF: synthetic method
   static Minecraft access$000(GuiChatTC x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static boolean access$100(GuiChatTC x0) {
      return x0.displayEmotesGui;
   }

   // $FF: synthetic method
   static Minecraft access$200(GuiChatTC x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static FontRenderer access$300(GuiChatTC x0) {
      return x0.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer access$400(GuiChatTC x0) {
      return x0.field_73886_k;
   }

}
