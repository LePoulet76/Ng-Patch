/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.server.asm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.server.event.ClientChatEvent;
import net.ilexiconn.nationsgui.forge.server.event.PrintChatMessageEvent;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import org.lwjgl.opengl.GL11;

public class NationsGUIChatHooks {
    public static int mouseXChat = 0;
    public static int mouseYChat = 0;

    public static void autocompleteTooltipEmote(int guiWidth, int guiHeight, GuiTextField textField) {
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
                NationsGUIChatHooks.drawHoveringText(emoteWhichMatch, Minecraft.func_71410_x().field_71466_p.func_78256_a(textField.func_73781_b()), guiHeight - 14 - emoteWhichMatch.size() * 9, Minecraft.func_71410_x().field_71466_p);
            }
        }
    }

    public static void autocompleteTextEmote(GuiTextField textField) {
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

    public static void drawChatMessagePost(ChatLine chatLine, String formattedText, int x, int y, int alpha) throws IOException {
        ArrayList<Object> tooltipInfos;
        int mouseChatYChanged;
        String senderName;
        int i = 320;
        int j = 40;
        int chatWidth = MathHelper.func_76141_d((float)(Minecraft.func_71410_x().field_71474_y.field_96692_F * (float)(i - j) + (float)j));
        float chatScale = 1.0f;
        Float chatHeight = Float.valueOf(Minecraft.func_71410_x().field_71474_y.field_96693_G);
        ArrayList tooltipsToDraw = new ArrayList();
        if (formattedText.contains("\u00bb") && (senderName = NationsGUIChatHooks.getSenderName(formattedText)) != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            int badgeCounter = 0;
            Double scale = 0.5;
            GL11.glPushMatrix();
            GL11.glScaled((double)scale, (double)scale, (double)scale);
            ArrayList arrayList = new ArrayList();
            for (AbstractSkin badgeSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES)) {
                if (badgeCounter < 3) {
                    Double chatOffsetY = (double)y * (1.0 / scale) - 9.0 * (1.0 / scale);
                    int chatOffsetX = Minecraft.func_71410_x().field_71466_p.func_78256_a(formattedText.split("  ")[0]) * 2 + badgeCounter * 11 * 2 + 2;
                    badgeSkin.renderInGUI(chatOffsetX, chatOffsetY.intValue(), 1.0f, 0.0f);
                    mouseChatYChanged = mouseYChat - 9;
                    if (NationsGUI.BADGES_TOOLTIPS.containsKey(badgeSkin.getId()) && mouseXChat > chatOffsetX / 2 && mouseXChat <= chatOffsetX / 2 + 9 && mouseChatYChanged > y * -1 - 9 && mouseChatYChanged <= -1 * y) {
                        tooltipInfos = new ArrayList<Object>();
                        tooltipInfos.add(NationsGUI.BADGES_TOOLTIPS.get(badgeSkin.getId()));
                        tooltipInfos.add(mouseXChat);
                        tooltipInfos.add(-1 * mouseYChat + 15);
                        tooltipInfos.add(Minecraft.func_71410_x().field_71466_p);
                        tooltipsToDraw.add(tooltipInfos);
                    }
                }
                ++badgeCounter;
            }
            GL11.glPopMatrix();
        }
        Double scale = 0.6;
        GL11.glPushMatrix();
        GL11.glScaled((double)scale, (double)scale, (double)scale);
        for (Map.Entry<String, String> pair : NationsGUI.EMOTES_SYMBOLS.entrySet()) {
            if (!formattedText.contains(pair.getValue()) || !NationsGUI.EMOTES_RESOURCES.containsKey(pair.getKey())) continue;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.EMOTES_RESOURCES.get(pair.getKey()));
            String[] stringArray = formattedText.split(pair.getValue());
            for (int k = 0; k < stringArray.length - 1; ++k) {
                String textBeforeEmote = "";
                for (int a = 0; a <= k; ++a) {
                    textBeforeEmote = textBeforeEmote + pair.getValue() + stringArray[a];
                }
                Double offsetX = (double)Minecraft.func_71410_x().field_71466_p.func_78256_a(textBeforeEmote) * (1.0 / scale) - (double)Minecraft.func_71410_x().field_71466_p.func_78256_a(pair.getValue()) * (1.0 / scale) - 3.0;
                Double chatOffsetY = (double)y * (1.0 / scale) - 9.0 * (1.0 / scale) - 1.0;
                mouseChatYChanged = mouseYChat - 9;
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX.intValue(), chatOffsetY.intValue(), 0, 0, 18, 18, 18.0f, 18.0f, true);
                if (!((double)mouseXChat > offsetX / (1.0 / scale)) || !((double)mouseXChat <= offsetX / (1.0 / scale) + 9.0) || mouseChatYChanged <= y * -1 - 9 || mouseChatYChanged > -1 * y) continue;
                tooltipInfos = new ArrayList();
                tooltipInfos.add(Collections.singletonList(":" + pair.getKey() + ":"));
                tooltipInfos.add(mouseXChat);
                tooltipInfos.add(-1 * mouseYChat + 15);
                tooltipInfos.add(Minecraft.func_71410_x().field_71466_p);
                tooltipsToDraw.add(tooltipInfos);
            }
        }
        Collections.reverse(tooltipsToDraw);
        GL11.glScaled((double)(1.0 / scale * 0.9), (double)(1.0 / scale * 0.9), (double)(1.0 / scale * 0.9));
        for (ArrayList arrayList : tooltipsToDraw) {
            NationsGUIChatHooks.drawHoveringText((List)arrayList.get(0), (Integer)arrayList.get(1), (Integer)arrayList.get(2), (FontRenderer)arrayList.get(3));
        }
        GL11.glPopMatrix();
    }

    public static String getSenderName(String line) {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9_\u00a7]*)[\\s]{1}\u00bb");
        Matcher m = pattern.matcher(line);
        if (m.find()) {
            return m.group(1).replaceAll("\u00a7[0-9a-z]{1}", "");
        }
        return null;
    }

    public static int getCharWidth(char c) {
        ArrayList<Character> char2px = new ArrayList<Character>();
        ArrayList<Character> char3px = new ArrayList<Character>();
        ArrayList<Character> char4px = new ArrayList<Character>();
        ArrayList<Character> char5px = new ArrayList<Character>();
        ArrayList<Character> char7px = new ArrayList<Character>();
        Character[] list2px = new Character[]{Character.valueOf('i'), Character.valueOf(','), Character.valueOf(';'), Character.valueOf(':'), Character.valueOf('!'), Character.valueOf('|'), Character.valueOf('`')};
        Character[] list3px = new Character[]{Character.valueOf('l'), Character.valueOf('\''), Character.valueOf('\u00a4')};
        Character[] list4px = new Character[]{Character.valueOf('t'), Character.valueOf('['), Character.valueOf(']'), Character.valueOf('I')};
        Character[] list5px = new Character[]{Character.valueOf('f'), Character.valueOf('k'), Character.valueOf('\"'), Character.valueOf('('), Character.valueOf(')'), Character.valueOf('*'), Character.valueOf('{'), Character.valueOf('}'), Character.valueOf('<'), Character.valueOf('>')};
        Character[] list7px = new Character[]{Character.valueOf('~'), Character.valueOf('@')};
        char2px.addAll(Arrays.asList(list2px));
        char3px.addAll(Arrays.asList(list3px));
        char4px.addAll(Arrays.asList(list4px));
        char5px.addAll(Arrays.asList(list5px));
        char7px.addAll(Arrays.asList(list7px));
        int width = 6;
        if (char2px.contains(Character.valueOf(c))) {
            width = 2;
        } else if (char3px.contains(Character.valueOf(c))) {
            width = 3;
        } else if (char4px.contains(Character.valueOf(c))) {
            width = 4;
        } else if (char5px.contains(Character.valueOf(c))) {
            width = 5;
        } else if (char7px.contains(Character.valueOf(c))) {
            width = 7;
        }
        return width;
    }

    public static String filterChatLineString(String line) {
        int nbBadges = 0;
        String senderName = NationsGUIChatHooks.getSenderName(line);
        ArrayList displayedBadges = new ArrayList();
        if (senderName != null) {
            if (!ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty()) {
                for (AbstractSkin badgeSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES)) {
                    if (displayedBadges.contains(badgeSkin.getId())) continue;
                    ++nbBadges;
                }
            }
            if (GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.containsKey(senderName) && !displayedBadges.contains(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(senderName)) && NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(senderName))) {
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

    public static PrintChatMessageEvent printChatMessage(String chatComponent, int chatLineId) {
        PrintChatMessageEvent event = new PrintChatMessageEvent(chatComponent, chatLineId);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event;
    }

    public static String onClientChat(String message) {
        ClientChatEvent event = new ClientChatEvent(message);
        message = MinecraftForge.EVENT_BUS.post((Event)event) ? null : event.getMessage();
        return message;
    }

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float f = (float)(par5 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(par5 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(par5 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(par5 & 0xFF) / 255.0f;
        float f4 = (float)(par6 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(par6 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(par6 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(par6 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(f1, f2, f3, f);
        tessellator.func_78377_a((double)par3, (double)par2, 300.0);
        tessellator.func_78377_a((double)par1, (double)par2, 300.0);
        tessellator.func_78369_a(f5, f6, f7, f4);
        tessellator.func_78377_a((double)par1, (double)par4, 300.0);
        tessellator.func_78377_a((double)par3, (double)par4, 300.0);
        tessellator.func_78381_a();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public static void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
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
            itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            NationsGUIChatHooks.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            NationsGUIChatHooks.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            NationsGUIChatHooks.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            NationsGUIChatHooks.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }
}

