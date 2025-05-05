package acs.tabbychat;

import acs.tabbychat.ChatChannel;
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
import java.util.Map.Entry;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class GuiNewChatTC extends GuiNewChat {

   public final Minecraft field_73772_a;
   public ScaledResolution sr;
   public int chatWidth;
   public int chatHeight;
   protected List<String> field_73770_b;
   protected List<ChatLine> backupLines;
   protected List<TaggableChatLine> field_73771_c;
   private int scrollOffset;
   private boolean chatScrolled;
   public static final GuiNewChatTC me = new GuiNewChatTC();
   public static int mouseXChat = 0;
   public static int mouseYChat = 0;
   private int xOffset;
   private int yOrigin;
   private List<Object[]> tooltipsToDisplay;
   private List<Object[]> tooltipsBadgeToDisplay;
   public static final int LINE_SPAWN_ANIMATION_TIME = 100;
   private long spawnAnimationTime;


   public GuiNewChatTC() {
      this(Minecraft.func_71410_x());
   }

   public GuiNewChatTC(Minecraft par1Minecraft) {
      super(par1Minecraft);
      this.chatWidth = 320;
      this.chatHeight = 0;
      this.field_73770_b = new ArrayList();
      this.backupLines = new ArrayList();
      this.field_73771_c = new ArrayList();
      this.scrollOffset = 0;
      this.chatScrolled = false;
      this.xOffset = 0;
      this.yOrigin = 0;
      this.tooltipsToDisplay = new ArrayList();
      this.tooltipsBadgeToDisplay = new ArrayList();
      this.spawnAnimationTime = 0L;
      this.field_73772_a = par1Minecraft;
      this.sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
   }

   public void func_73762_a(int currentTick) {
      boolean unicodeStore = this.field_73772_a.field_71466_p.func_82883_a();
      boolean lineCounter = false;
      int visLineCounter = 0;
      if(TabbyChat.instance.generalSettings.tabbyChatEnable.getValue().booleanValue() && ClientProxy.clientConfig.enableUnicode) {
         this.field_73772_a.field_71466_p.func_78264_a(true);
      }

      if(this.field_73772_a.field_71474_y.field_74343_n != 2) {
         this.sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
         this.chatHeight = 0;
         this.chatWidth = 320;
         boolean maxDisplayedLines = false;
         boolean chatOpen = false;
         int validLinesDisplayed = 0;
         int numLinesTotal = this.field_73771_c.size();
         float chatOpacity = this.field_73772_a.field_71474_y.field_74357_r * 0.9F + 0.1F;
         float chatScaling = Math.max(0.1F, this.func_96131_h());
         int timeStampOffset = 0;
         int fadeTicks = 200;
         if(numLinesTotal > 0) {
            chatOpen = this.func_73760_d();
         }

         float animationOffset;
         float lineAge;
         int var28;
         if(TabbyChat.instance.enabled()) {
            if(TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
               if(chatOpen) {
                  animationOffset = TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0F;
               } else {
                  animationOffset = TabbyChat.instance.advancedSettings.chatBoxUnfocHeight.getValue().floatValue() / 100.0F;
               }

               var28 = (int)Math.floor((double)((float)(this.sr.func_78328_b() - 51) * animationOffset / 9.0F));
            } else {
               var28 = this.func_96127_i();
               this.chatWidth = MathHelper.func_76123_f((float)this.func_96126_f() / chatScaling);
            }

            if(ClientProxy.clientConfig.enableTimestamp) {
               timeStampOffset = this.field_73772_a.field_71466_p.func_78256_a(((TimeStampEnum)TabbyChat.instance.generalSettings.timeStampStyle.getValue()).maxTime);
            }

            if(TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
               int var29 = this.sr.func_78326_a() - 14 - timeStampOffset;
               lineAge = TabbyChat.instance.advancedSettings.chatBoxWidth.getValue().floatValue() / 100.0F;
               this.chatWidth = MathHelper.func_76123_f(lineAge * (float)var29 / chatScaling);
            }

            fadeTicks = TabbyChat.instance.advancedSettings.chatFadeTicks.getValue().intValue();
         } else {
            var28 = this.func_96127_i();
            this.chatWidth = MathHelper.func_76123_f((float)this.func_96126_f() / chatScaling);
         }

         animationOffset = 0.0F;
         if(this.isAnimating()) {
            lineAge = Math.min((float)(System.currentTimeMillis() - this.spawnAnimationTime) / 100.0F, 1.0F);
            animationOffset = 9.0F + -9.0F * lineAge;
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(2.0F, 20.0F, 0.0F);
         GL11.glScalef(chatScaling, chatScaling, 1.0F);
         int _size = this.field_73771_c.size();

         int badge;
         for(int var27 = 0; var27 + this.scrollOffset < _size && var27 < var28; ++var27) {
            this.chatHeight = var27 * 9;
            TaggableChatLine fontHeight = (TaggableChatLine)this.field_73771_c.get(var27 + this.scrollOffset);
            if(fontHeight != null) {
               int var30 = currentTick - fontHeight.func_74540_b();
               if(var30 < fadeTicks || chatOpen) {
                  double objects = (double)currentTick / (double)fadeTicks;
                  objects = 10.0D * (1.0D - objects);
                  objects = Math.min(0.0D, objects);
                  objects = Math.max(1.0D, objects);
                  objects *= objects;
                  int currentOpacity = DialogOverride.displaysDialog()?0:(int)(255.0D * objects);
                  if(chatOpen) {
                     currentOpacity = 255;
                  }

                  currentOpacity = (int)((float)currentOpacity * chatOpacity);
                  ++validLinesDisplayed;
                  if(currentOpacity > 3) {
                     ++visLineCounter;
                     byte displayedBadges = 3;
                     this.yOrigin = -var27 * 9;
                     GL11.glPushMatrix();
                     if(var27 == 0 && this.isAnimating()) {
                        GL11.glTranslated(0.0D, (double)animationOffset, 0.0D);
                     } else if(ClientProxy.clientConfig.enableChatBackground) {
                        func_73734_a(displayedBadges, this.yOrigin - 9, displayedBadges + this.chatWidth + 4 + timeStampOffset, this.yOrigin, currentOpacity / 2 << 24);
                     }

                     GL11.glEnable(3042);
                     String scrollMax = fontHeight.func_74538_a();
                     if(!this.field_73772_a.field_71474_y.field_74344_o) {
                        scrollMax = StringUtils.func_76338_a(scrollMax);
                     }

                     scrollMax = this.cleanChatLine(scrollMax);
                     this.xOffset = 0;
                     badge = 0;
                     GL11.glPushMatrix();
                     GL11.glTranslated((double)displayedBadges, (double)(this.yOrigin - 8), 0.0D);
                     StringBuilder scrollBarColor = new StringBuilder();

                     for(int i = 0; i < scrollMax.length(); ++i) {
                        char c = scrollMax.charAt(i);
                        if(c == ChatTagManager.REF_CHAR) {
                           if(scrollBarColor.length() > 0) {
                              this.field_73772_a.field_71466_p.func_78261_a(scrollBarColor.toString(), this.xOffset, 0, 16777215 + (currentOpacity << 24));
                              this.xOffset += this.field_73772_a.field_71466_p.func_78256_a(scrollBarColor.toString());
                              scrollBarColor = new StringBuilder();
                           }

                           if(((ChatChannel)TabbyChat.instance.channelMap.get("Global")).active && fontHeight.getTags().size() > badge) {
                              GL11.glPushMatrix();
                              GL11.glTranslated((double)this.xOffset, 0.0D, 0.0D);
                              GL11.glColor3f(1.0F, 1.0F, 1.0F);
                              AbstractChatTag abstractChatTag = (AbstractChatTag)fontHeight.getTags().get(badge);
                              abstractChatTag.render(mouseXChat - this.xOffset, -(mouseYChat + this.yOrigin - 8));
                              GL11.glPopMatrix();
                              this.xOffset += abstractChatTag.getWidth();
                              ++badge;
                           }
                        } else {
                           scrollBarColor.append(c);
                        }
                     }

                     if(scrollBarColor.length() > 0) {
                        this.field_73772_a.field_71466_p.func_78261_a(scrollBarColor.toString(), this.xOffset, 0, 16777215 + (currentOpacity << 24));
                     }

                     GL11.glPopMatrix();
                     GL11.glPopMatrix();
                  }
               }
            }
         }

         int badgeCounter;
         if(chatOpen && !TabbyChat.instance.enabled()) {
            int var31 = this.field_73772_a.field_71466_p.field_78288_b;
            GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
            int var33 = numLinesTotal * var31 + numLinesTotal;
            badgeCounter = validLinesDisplayed * var31 + validLinesDisplayed;
            if(var33 != badgeCounter) {
               int var35 = this.scrollOffset * badgeCounter / numLinesTotal;
               int var37 = badgeCounter * badgeCounter / var33;
               badge = var35 > 0?170:96;
               int var40 = this.chatScrolled?13382451:3355562;
               func_73734_a(0, -var35, 2, -var35 - var37, var40 + (badge << 24));
               func_73734_a(2, -var35, 1, -var35 - var37, 13421772 + (badge << 24));
            }
         }

         Iterator var32 = this.tooltipsToDisplay.iterator();

         Object[] var34;
         while(var32.hasNext()) {
            var34 = (Object[])var32.next();
            this.drawHoveringText((List)var34[0], ((Integer)var34[1]).intValue(), ((Integer)var34[2]).intValue(), Minecraft.func_71410_x().field_71466_p);
         }

         var32 = this.tooltipsBadgeToDisplay.iterator();

         while(var32.hasNext()) {
            var34 = (Object[])var32.next();
            badgeCounter = 0;
            ArrayList var36 = new ArrayList();
            if(((List)var34[0]).size() > 0) {
               Gui.func_73734_a(((Integer)var34[1]).intValue(), ((Integer)var34[2]).intValue() - 13, ((Integer)var34[1]).intValue() + ((List)var34[0]).size() * 9 + 7, ((Integer)var34[2]).intValue(), -434562791);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

               for(Iterator var38 = ((List)var34[0]).iterator(); var38.hasNext(); ++badgeCounter) {
                  Object var39 = var38.next();
                  if(var39 != null && !((String)var39).isEmpty() && !var36.contains(var39) && badgeCounter < 3 && NationsGUI.BADGES_RESOURCES.containsKey(var39)) {
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(var39));
                     ModernGui.drawScaledCustomSizeModalRect((float)(((Integer)var34[1]).intValue() + 3 + badgeCounter * 10), (float)(((Integer)var34[2]).intValue() - 13 + 2), 0.0F, 0.0F, 18, 18, 9, 9, 18.0F, 18.0F, false);
                     var36.add((String)var39);
                  }
               }
            }
         }

         GL11.glPopMatrix();
      }

      if(TabbyChat.instance.enabled() && !this.func_73760_d() && TabbyChat.instance.generalSettings.unreadFlashing.getValue().booleanValue()) {
         TabbyChat.instance.pollForUnread(this, -visLineCounter * 9, currentTick);
      }

      this.tooltipsToDisplay.clear();
      this.tooltipsBadgeToDisplay.clear();
      this.field_73772_a.field_71466_p.func_78264_a(unicodeStore);
   }

   public String cleanChatLine(String rawChatLine) {
      String result = rawChatLine.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*All[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
      result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*Annonce[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
      result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*RP[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
      result = result.replaceAll("^[\u00a7a-z0-9]*\\[[\u00a7a-z0-9]*Logs[\u00a7a-z0-9]*\\][\u00a7a-z0-9]*[ ]*", "");
      result = result.replaceAll("\\[Ile [0-9]+\\]", "");
      Pattern p = Pattern.compile("((\u00a7.{1})?[^\u00a7]*(\\s|^))" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "((\\s|$))(?!.*\u00bb)", 32);
      Matcher m = p.matcher(result);
      if(m.find()) {
         if(m.groupCount() == 5 && m.group(1) != null && m.group(2) != null && m.group(3) != null && m.group(4) != null && m.group(5) != null) {
            result = m.replaceAll(Matcher.quoteReplacement(m.group(1)) + "\u00a74@" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "\u00a7r" + Matcher.quoteReplacement(m.group(2)) + Matcher.quoteReplacement(m.group(3)));
         } else if(m.groupCount() == 3 && m.group(1) != null && m.group(2) != null && m.group(3) != null) {
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
      ArrayList multiLineChat = new ArrayList();
      if(id != 0) {
         this.func_73759_c(id);
      }

      int maxWidth = MathHelper.func_76141_d((float)this.func_96126_f() / Math.max(0.1F, this.func_96131_h()));
      if(TabbyChat.instance.enabled()) {
         TabbyChat.instance.checkServer();
         if(TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
            maxWidth = this.chatWidth;
         }
      }

      for(Iterator lineIter = ChatTagManager.generateMultiLineWithTags(_msg, Math.max(50, maxWidth)).iterator(); lineIter.hasNext(); isLineOne = false) {
         Tuple maxChats = (Tuple)lineIter.next();
         String chatLineSize = (String)maxChats.a;
         if(chatOpen && this.scrollOffset > 0) {
            this.chatScrolled = true;
            this.func_73758_b(1);
         }

         if(!isLineOne) {
            chatLineSize = " " + chatLineSize;
         }

         TaggableChatLine cmdLineSize = new TaggableChatLine(tick, chatLineSize, (ArrayList)maxChats.b, id);
         multiLineChat.add(cmdLineSize);
      }

      int var13;
      int var14;
      if(TabbyChat.instance.enabled()) {
         var13 = TabbyChat.instance.processChat(multiLineChat);
      } else {
         var13 = multiLineChat.size();

         for(var14 = 0; var14 < var13; ++var14) {
            this.field_73771_c.add(0, (TaggableChatLine)multiLineChat.get(var14));
            if(!backupFlag) {
               this.backupLines.add(0, multiLineChat.get(var14));
            }
         }
      }

      var13 = TabbyChat.instance.enabled()?Integer.parseInt(TabbyChat.instance.advancedSettings.chatScrollHistory.getValue()):100;
      var14 = this.field_73771_c.size();
      int var15 = this.backupLines.size();
      if(var14 >= var13 + 5) {
         this.field_73771_c.subList(var14 - 11, var14 - 1).clear();
      }

      if(!backupFlag && var15 >= var13 + 5) {
         this.backupLines.subList(var15 - 11, var15 - 1).clear();
      }

   }

   public void func_96132_b() {
      this.field_73771_c.clear();
      this.func_73764_c();

      for(int i = this.backupLines.size() - 1; i >= 0; --i) {
         ChatLine _cl = (ChatLine)this.backupLines.get(i);
         this.func_96129_a(_cl.func_74538_a(), _cl.func_74539_c(), _cl.func_74540_b(), true);
      }

   }

   public List func_73756_b() {
      return this.field_73770_b;
   }

   public void func_73767_b(String _msg) {
      if(this.field_73770_b.isEmpty() || !((String)this.field_73770_b.get(this.field_73770_b.size() - 1)).equals(_msg)) {
         this.field_73770_b.add(_msg);
      }

   }

   public void func_73764_c() {
      this.scrollOffset = 0;
      this.chatScrolled = false;
   }

   public void func_73758_b(int _lines) {
      int maxLineDisplay;
      if(TabbyChat.instance.enabled() && TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
         float numLines;
         if(this.func_73760_d()) {
            numLines = TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0F;
         } else {
            numLines = TabbyChat.instance.advancedSettings.chatBoxUnfocHeight.getValue().floatValue() / 100.0F;
         }

         maxLineDisplay = (int)Math.floor((double)((float)(this.sr.func_78328_b() - 51) * numLines / 9.0F));
      } else {
         maxLineDisplay = this.func_96127_i();
      }

      this.scrollOffset += _lines;
      int numLines1 = this.field_73771_c.size();
      this.scrollOffset = Math.min(this.scrollOffset, numLines1 - maxLineDisplay);
      if(this.scrollOffset <= 0) {
         this.scrollOffset = 0;
         this.chatScrolled = false;
      }

   }

   public ChatClickCountryData getClickedCountryData(int clickX, int clickY) {
      if(!this.func_73760_d()) {
         return null;
      } else {
         ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
         int scaleFactor = _sr.func_78325_e();
         float scaleSetting = Math.max(0.1F, this.func_96131_h());
         int clickXRel = clickX / scaleFactor - 3;
         int clickYRel = clickY / scaleFactor - 28;
         clickXRel = MathHelper.func_76141_d((float)clickXRel / scaleSetting);
         clickYRel = MathHelper.func_76141_d((float)clickYRel / scaleSetting);
         if(clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if(clickXRel <= MathHelper.func_76141_d((float)this.chatWidth / scaleSetting) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
               int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
               return lineIndex < displayedLines + this.scrollOffset && this.field_73771_c.get(lineIndex) != null?new ChatClickCountryData(this.field_73772_a.field_71466_p, (ChatLine)this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex):null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public ChatClickProfilData getClickedProfilData(int clickX, int clickY) {
      if(!this.func_73760_d()) {
         return null;
      } else {
         ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
         int scaleFactor = _sr.func_78325_e();
         float scaleSetting = Math.max(0.1F, this.func_96131_h());
         int clickXRel = clickX / scaleFactor - 3;
         int clickYRel = clickY / scaleFactor - 28;
         clickXRel = MathHelper.func_76141_d((float)clickXRel / scaleSetting);
         clickYRel = MathHelper.func_76141_d((float)clickYRel / scaleSetting);
         if(clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if(clickXRel <= MathHelper.func_76141_d((float)this.chatWidth / scaleSetting) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
               int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
               return lineIndex < displayedLines + this.scrollOffset && this.field_73771_c.get(lineIndex) != null?new ChatClickProfilData(this.field_73772_a.field_71466_p, (ChatLine)this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex):null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public ChatClickData func_73766_a(int clickX, int clickY) {
      if(!this.func_73760_d()) {
         return null;
      } else {
         ScaledResolution _sr = new ScaledResolution(this.field_73772_a.field_71474_y, this.field_73772_a.field_71443_c, this.field_73772_a.field_71440_d);
         int scaleFactor = _sr.func_78325_e();
         float scaleSetting = Math.max(0.1F, this.func_96131_h());
         int clickXRel = clickX / scaleFactor - 2;
         int clickYRel = clickY / scaleFactor - 28;
         clickXRel = MathHelper.func_76141_d((float)clickXRel / scaleSetting);
         clickYRel = MathHelper.func_76141_d((float)clickYRel / scaleSetting);
         if(clickXRel >= 0 && clickYRel >= 0) {
            int displayedLines = Math.min(this.getHeightSetting() / 9, this.field_73771_c.size());
            if(clickXRel <= MathHelper.func_76141_d((float)this.chatWidth / scaleSetting) && clickYRel < this.field_73772_a.field_71466_p.field_78288_b * displayedLines + displayedLines) {
               int lineIndex = clickYRel / this.field_73772_a.field_71466_p.field_78288_b + this.scrollOffset;
               if(lineIndex < displayedLines + this.scrollOffset && this.field_73771_c.size() > lineIndex && this.field_73771_c.get(lineIndex) != null) {
                  int offSetY = 0;

                  for(Iterator var11 = this.field_73771_c.iterator(); var11.hasNext(); offSetY += 9) {
                     ChatLine chatLine = (ChatLine)var11.next();
                     if(chatLine instanceof TaggableChatLine) {
                        TaggableChatLine taggableChatLine = (TaggableChatLine)chatLine;
                        String message = taggableChatLine.func_74538_a();
                        StringBuilder stringBuilder = new StringBuilder();
                        int xOffset = 0;
                        int j = 0;

                        for(int i = 0; i < message.length(); ++i) {
                           char c = message.charAt(i);
                           if(c == ChatTagManager.REF_CHAR) {
                              if(stringBuilder.length() > 0) {
                                 xOffset += this.field_73772_a.field_71466_p.func_78256_a(stringBuilder.toString());
                                 stringBuilder = new StringBuilder();
                              }

                              if(taggableChatLine.getTags().size() > j) {
                                 AbstractChatTag chatTag = (AbstractChatTag)taggableChatLine.getTags().get(j);
                                 chatTag.onClick(clickXRel - xOffset, -(clickYRel + this.field_73772_a.field_71466_p.field_78288_b * this.scrollOffset - offSetY - 8));
                                 xOffset += chatTag.getWidth();
                                 ++j;
                              }
                           } else {
                              stringBuilder.append(c);
                           }
                        }
                     }
                  }

                  return new ChatClickData(this.field_73772_a.field_71466_p, (ChatLine)this.field_73771_c.get(lineIndex), clickXRel, clickYRel - (lineIndex - this.scrollOffset) * this.field_73772_a.field_71466_p.field_78288_b + lineIndex);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public void func_73757_a(String par1Str, Object ... par2ArrayOfObj) {
      this.func_73765_a((new StringTranslate()).func_74803_a(par1Str, par2ArrayOfObj));
   }

   public boolean func_73760_d() {
      return this.field_73772_a.field_71462_r instanceof GuiChat || this.field_73772_a.field_71462_r instanceof GuiChatTC;
   }

   public void func_73759_c(int _id) {
      Iterator _iter = this.field_73771_c.iterator();

      ChatLine _cl;
      while(_iter.hasNext()) {
         _cl = (ChatLine)_iter.next();
         if(_cl.func_74539_c() == _id) {
            _iter.remove();
            return;
         }
      }

      _iter = this.backupLines.iterator();

      while(_iter.hasNext()) {
         _cl = (ChatLine)_iter.next();
         if(_cl.func_74539_c() == _id) {
            _iter.remove();
            return;
         }
      }

   }

   public int getHeightSetting() {
      if(TabbyChat.instance.enabled() && TabbyChat.instance.advancedSettings.customChatBoxSize.getValue().booleanValue()) {
         float scaleFactor = TabbyChat.instance.advancedSettings.chatBoxFocHeight.getValue().floatValue() / 100.0F;
         return (int)Math.floor((double)((float)(this.sr.func_78328_b() - 51) * scaleFactor));
      } else {
         return func_96130_b(this.field_73772_a.field_71474_y.field_96694_H);
      }
   }

   public int getWidthSetting() {
      return this.chatWidth;
   }

   public float getScaleSetting() {
      return Math.max(0.1F, this.func_96131_h());
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
      for(int i = 0; i < _add.size(); ++i) {
         this.field_73771_c.set(_pos + i, new TaggableChatLine((ChatLine)_add.get(i)));
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
      if(_new != null && _new.size() > 0) {
         int _c = 0;
         int _n = 0;
         boolean dt = false;
         int max = _new.size();

         while(_n < max && _c < _current.size()) {
            int var7 = ((ChatLine)_new.get(_n)).func_74540_b() - ((TaggableChatLine)_current.get(_c)).func_74540_b();
            if(var7 > 0) {
               _current.add(_c, new TaggableChatLine((ChatLine)_new.get(_n)));
               ++_n;
            } else if(var7 == 0) {
               if(((TaggableChatLine)_current.get(_c)).equals(_new.get(_n))) {
                  ++_c;
                  ++_n;
               } else {
                  ++_c;
               }
            } else {
               ++_c;
            }
         }

         while(_n < max) {
            _current.add(_current.size(), new TaggableChatLine((ChatLine)_new.get(_n)));
            ++_n;
         }

      }
   }

   public static String filterChatLineString(String line) {
      int nbBadges = 0;
      String senderName = getSenderName(line);
      ArrayList displayedBadges = new ArrayList();
      if(senderName != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty()) {
         Iterator pattern = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).iterator();

         while(pattern.hasNext()) {
            AbstractSkin m = (AbstractSkin)pattern.next();
            if(!displayedBadges.contains(m.getId())) {
               ++nbBadges;
            }
         }
      }

      if(nbBadges == 1) {
         line = line.replace("##", "   ");
      } else if(nbBadges == 2) {
         line = line.replace("##", "      ");
      } else if(nbBadges >= 3) {
         line = line.replace("##", "          ");
      } else {
         line = line.replace("##", "");
      }

      Pattern var6 = Pattern.compile(":([a-z0-9]+):");
      Matcher var7 = var6.matcher(line);

      while(var7.find()) {
         if(NationsGUI.EMOTES_SYMBOLS.containsKey(var7.group(1))) {
            line = line.replace(var7.group(0), "\u00a70" + (String)NationsGUI.EMOTES_SYMBOLS.get(var7.group(1)) + "\u00a7r ");
         }
      }

      return line;
   }

   public static boolean containsRestrictedBadge(String text) {
      Iterator var1 = Arrays.asList(new String[]{"fondateur", "co-fonda", "respadmin", "admin", "supermodo", "moderateur"}).iterator();

      String badge;
      do {
         if(!var1.hasNext()) {
            return false;
         }

         badge = (String)var1.next();
      } while(!text.contains(badge));

      return true;
   }

   public static String implode(String separator, String ... data) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < data.length - 1; ++i) {
         if(!data[i].matches(" *")) {
            sb.append(data[i]);
            sb.append(separator);
         }
      }

      sb.append(data[data.length - 1].trim());
      return sb.toString();
   }

   public static String getSenderName(String line) {
      Pattern pattern = Pattern.compile("([a-zA-Z0-9_\u00a7]*)[\\s]{1}\u00bb");
      Matcher m = pattern.matcher(line);
      return m.find()?m.group(1).replaceAll("\u00a7[0-9a-z]{1}", ""):null;
   }

   public void addTooltipToDisplay(List par1List, int par2, int par3) {
      this.tooltipsToDisplay.add(new Object[]{par1List, Integer.valueOf(par2 + this.xOffset), Integer.valueOf(par3 + this.yOrigin - 9)});
   }

   public void addTooltipBadgeToDisplay(List par1List, int par2, int par3) {
      this.tooltipsBadgeToDisplay.add(new Object[]{par1List, Integer.valueOf(par2 + this.xOffset), Integer.valueOf(par3 + this.yOrigin - 9)});
   }

   public void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(par1List != null && !par1List.isEmpty()) {
         new RenderItem();
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

         int var16 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, 0.0D, 300.0D);
         int l1 = -267386864;
         this.func_73733_a(var16 - 3, j1 - 4, var16 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var16 - 3, j1 + k1 + 3, var16 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var16 - 3, j1 - 3, var16 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var16 - 4, j1 - 3, var16 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var16 + k + 3, j1 - 3, var16 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var16 - 3, j1 - 3 + 1, var16 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var16 + k + 2, j1 - 3 + 1, var16 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var16 - 3, j1 - 3, var16 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var16 - 3, j1 + k1 + 2, var16 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var16, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         GL11.glPopMatrix();
         GL11.glDisable(2896);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public String drawChatMessagePost(ChatLine chatLine, String formattedText, int x, int y, int alpha) throws IOException {
      short i = 320;
      byte j = 40;
      MathHelper.func_76141_d(Minecraft.func_71410_x().field_71474_y.field_96692_F * (float)(i - j) + (float)j);
      float chatScale = Math.max(0.1F, Minecraft.func_71410_x().field_71474_y.field_96691_E);
      Float chatHeight = Float.valueOf(Minecraft.func_71410_x().field_71474_y.field_96693_G);
      ArrayList tooltipsToDraw = new ArrayList();
      ArrayList tooltipInfo;
      Double chatOffsetY;
      int mouseChatYChanged;
      if(formattedText.contains("\u00bb")) {
         String scale = getSenderName(formattedText);
         if(scale != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(scale, SkinType.BADGES).isEmpty()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int it = 0;
            Double pair = Double.valueOf(0.5D);
            GL11.glPushMatrix();
            GL11.glScaled(pair.doubleValue(), pair.doubleValue(), pair.doubleValue());
            tooltipInfo = new ArrayList();

            for(Iterator k = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(scale, SkinType.BADGES).iterator(); k.hasNext(); ++it) {
               AbstractSkin textBeforeEmote = (AbstractSkin)k.next();
               String offsetX = textBeforeEmote.getId();
               if(!tooltipInfo.contains(offsetX) && it < 3) {
                  chatOffsetY = Double.valueOf((double)y * (1.0D / pair.doubleValue()) - 9.0D * (1.0D / pair.doubleValue()));
                  mouseChatYChanged = Minecraft.func_71410_x().field_71466_p.func_78256_a(formattedText.split("  ")[0]) * 2 + it * 11 * 2 + 10;
                  textBeforeEmote.renderInGUI(mouseChatYChanged, chatOffsetY.intValue(), 1.0F, 0.0F);
                  int tooltipInfos = mouseYChat - 9;
                  if(NationsGUI.BADGES_TOOLTIPS.containsKey(offsetX) && mouseXChat > mouseChatYChanged / 2 && mouseXChat <= mouseChatYChanged / 2 + 9 && tooltipInfos > y * -1 - 9 && tooltipInfos <= -1 * y) {
                     ArrayList tooltipInfos1 = new ArrayList();
                     tooltipInfos1.add(NationsGUI.BADGES_TOOLTIPS.get(offsetX));
                     tooltipInfos1.add(Integer.valueOf(mouseXChat));
                     tooltipInfos1.add(Integer.valueOf(-1 * mouseYChat + 15));
                     tooltipInfos1.add(Minecraft.func_71410_x().field_71466_p);
                     tooltipsToDraw.add(tooltipInfos1);
                  }

                  tooltipInfo.add(offsetX);
               }
            }

            GL11.glPopMatrix();
         }
      }

      Double var23 = Double.valueOf(0.6D);
      GL11.glPushMatrix();
      GL11.glScaled(var23.doubleValue(), var23.doubleValue(), var23.doubleValue());
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Iterator var24 = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

      while(var24.hasNext()) {
         Entry var25 = (Entry)var24.next();
         if(formattedText.contains((String)var25.getValue()) && NationsGUI.EMOTES_RESOURCES.containsKey((String)var25.getKey())) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.EMOTES_RESOURCES.get((String)var25.getKey()));
            String[] var27 = formattedText.split((String)var25.getValue());

            for(int var28 = 0; var28 < var27.length - 1; ++var28) {
               String var29 = "";

               for(int var30 = 0; var30 <= var28; ++var30) {
                  var29 = var29 + (String)var25.getValue() + var27[var30];
               }

               Double var31 = Double.valueOf((double)Minecraft.func_71410_x().field_71466_p.func_78256_a(var29) * (1.0D / var23.doubleValue()) - (double)Minecraft.func_71410_x().field_71466_p.func_78256_a((String)var25.getValue()) * (1.0D / var23.doubleValue()) - 0.0D);
               chatOffsetY = Double.valueOf((double)y * (1.0D / var23.doubleValue()) - 9.0D * (1.0D / var23.doubleValue()) - 1.0D);
               mouseChatYChanged = mouseYChat - 9;
               ModernGui.drawModalRectWithCustomSizedTexture((float)var31.intValue(), (float)chatOffsetY.intValue(), 0, 0, 18, 18, 18.0F, 18.0F, true);
               if((double)mouseXChat > var31.doubleValue() / (1.0D / var23.doubleValue()) && (double)mouseXChat <= var31.doubleValue() / (1.0D / var23.doubleValue()) + 9.0D && mouseChatYChanged > y * -1 - 9 && mouseChatYChanged <= -1 * y) {
                  ArrayList var32 = new ArrayList();
                  var32.add(Collections.singletonList(":" + (String)var25.getKey() + ":"));
                  var32.add(Integer.valueOf(mouseXChat));
                  var32.add(Integer.valueOf(-1 * mouseYChat + 15));
                  var32.add(Minecraft.func_71410_x().field_71466_p);
                  tooltipsToDraw.add(var32);
               }
            }

            formattedText = implode(" ", var27);
         }
      }

      Collections.reverse(tooltipsToDraw);
      GL11.glScaled(1.0D / var23.doubleValue() * 0.9D, 1.0D / var23.doubleValue() * 0.9D, 1.0D / var23.doubleValue() * 0.9D);
      Iterator var26 = tooltipsToDraw.iterator();

      while(var26.hasNext()) {
         tooltipInfo = (ArrayList)var26.next();
         this.drawHoveringText((List)tooltipInfo.get(0), ((Integer)tooltipInfo.get(1)).intValue(), ((Integer)tooltipInfo.get(2)).intValue(), (FontRenderer)tooltipInfo.get(3));
      }

      GL11.glPopMatrix();
      return formattedText;
   }

}
