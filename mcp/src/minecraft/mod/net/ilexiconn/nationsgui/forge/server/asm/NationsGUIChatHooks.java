package net.ilexiconn.nationsgui.forge.server.asm;

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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class NationsGUIChatHooks
{
    public static int mouseXChat = 0;
    public static int mouseYChat = 0;

    public static void autocompleteTooltipEmote(int guiWidth, int guiHeight, GuiTextField textField)
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
                drawHoveringText(emoteWhichMatch, Minecraft.getMinecraft().fontRenderer.getStringWidth(textField.getText()), guiHeight - 14 - emoteWhichMatch.size() * 9, Minecraft.getMinecraft().fontRenderer);
            }
        }
    }

    public static void autocompleteTextEmote(GuiTextField textField)
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

    public static void drawChatMessagePost(ChatLine chatLine, String formattedText, int x, int y, int alpha) throws IOException
    {
        short i = 320;
        byte j = 40;
        MathHelper.floor_float(Minecraft.getMinecraft().gameSettings.chatWidth * (float)(i - j) + (float)j);
        float chatScale = 1.0F;
        Float chatHeight = Float.valueOf(Minecraft.getMinecraft().gameSettings.chatHeightUnfocused);
        ArrayList tooltipsToDraw = new ArrayList();
        Double offsetX;
        int mouseChatYChanged;
        ArrayList tooltipInfos;

        if (formattedText.contains("\u00bb"))
        {
            String scale = getSenderName(formattedText);

            if (scale != null && !ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(scale, SkinType.BADGES).isEmpty())
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int it = 0;
                Double pair = Double.valueOf(0.5D);
                GL11.glPushMatrix();
                GL11.glScaled(pair.doubleValue(), pair.doubleValue(), pair.doubleValue());
                new ArrayList();

                for (Iterator k = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(scale, SkinType.BADGES).iterator(); k.hasNext(); ++it)
                {
                    AbstractSkin textBeforeEmote = (AbstractSkin)k.next();

                    if (it < 3)
                    {
                        offsetX = Double.valueOf((double)y * (1.0D / pair.doubleValue()) - 9.0D * (1.0D / pair.doubleValue()));
                        int chatOffsetY = Minecraft.getMinecraft().fontRenderer.getStringWidth(formattedText.split("  ")[0]) * 2 + it * 11 * 2 + 2;
                        textBeforeEmote.renderInGUI(chatOffsetY, offsetX.intValue(), 1.0F, 0.0F);
                        mouseChatYChanged = mouseYChat - 9;

                        if (NationsGUI.BADGES_TOOLTIPS.containsKey(textBeforeEmote.getId()) && mouseXChat > chatOffsetY / 2 && mouseXChat <= chatOffsetY / 2 + 9 && mouseChatYChanged > y * -1 - 9 && mouseChatYChanged <= -1 * y)
                        {
                            tooltipInfos = new ArrayList();
                            tooltipInfos.add(NationsGUI.BADGES_TOOLTIPS.get(textBeforeEmote.getId()));
                            tooltipInfos.add(Integer.valueOf(mouseXChat));
                            tooltipInfos.add(Integer.valueOf(-1 * mouseYChat + 15));
                            tooltipInfos.add(Minecraft.getMinecraft().fontRenderer);
                            tooltipsToDraw.add(tooltipInfos);
                        }
                    }
                }

                GL11.glPopMatrix();
            }
        }

        Double var21 = Double.valueOf(0.6D);
        GL11.glPushMatrix();
        GL11.glScaled(var21.doubleValue(), var21.doubleValue(), var21.doubleValue());
        Iterator var22 = NationsGUI.EMOTES_SYMBOLS.entrySet().iterator();

        while (var22.hasNext())
        {
            Entry var23 = (Entry)var22.next();

            if (formattedText.contains((String)var23.getValue()) && NationsGUI.EMOTES_RESOURCES.containsKey((String)var23.getKey()))
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.EMOTES_RESOURCES.get((String)var23.getKey()));
                String[] tooltipInfo = formattedText.split((String)var23.getValue());

                for (int var26 = 0; var26 < tooltipInfo.length - 1; ++var26)
                {
                    String var27 = "";

                    for (int var28 = 0; var28 <= var26; ++var28)
                    {
                        var27 = var27 + (String)var23.getValue() + tooltipInfo[var28];
                    }

                    offsetX = Double.valueOf((double)Minecraft.getMinecraft().fontRenderer.getStringWidth(var27) * (1.0D / var21.doubleValue()) - (double)Minecraft.getMinecraft().fontRenderer.getStringWidth((String)var23.getValue()) * (1.0D / var21.doubleValue()) - 3.0D);
                    Double var29 = Double.valueOf((double)y * (1.0D / var21.doubleValue()) - 9.0D * (1.0D / var21.doubleValue()) - 1.0D);
                    mouseChatYChanged = mouseYChat - 9;
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX.intValue(), (float)var29.intValue(), 0, 0, 18, 18, 18.0F, 18.0F, true);

                    if ((double)mouseXChat > offsetX.doubleValue() / (1.0D / var21.doubleValue()) && (double)mouseXChat <= offsetX.doubleValue() / (1.0D / var21.doubleValue()) + 9.0D && mouseChatYChanged > y * -1 - 9 && mouseChatYChanged <= -1 * y)
                    {
                        tooltipInfos = new ArrayList();
                        tooltipInfos.add(Collections.singletonList(":" + (String)var23.getKey() + ":"));
                        tooltipInfos.add(Integer.valueOf(mouseXChat));
                        tooltipInfos.add(Integer.valueOf(-1 * mouseYChat + 15));
                        tooltipInfos.add(Minecraft.getMinecraft().fontRenderer);
                        tooltipsToDraw.add(tooltipInfos);
                    }
                }
            }
        }

        Collections.reverse(tooltipsToDraw);
        GL11.glScaled(1.0D / var21.doubleValue() * 0.9D, 1.0D / var21.doubleValue() * 0.9D, 1.0D / var21.doubleValue() * 0.9D);
        Iterator var24 = tooltipsToDraw.iterator();

        while (var24.hasNext())
        {
            ArrayList var25 = (ArrayList)var24.next();
            drawHoveringText((List)var25.get(0), ((Integer)var25.get(1)).intValue(), ((Integer)var25.get(2)).intValue(), (FontRenderer)var25.get(3));
        }

        GL11.glPopMatrix();
    }

    public static String getSenderName(String line)
    {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9_\u00a7]*)[\\s]{1}\u00bb");
        Matcher m = pattern.matcher(line);
        return m.find() ? m.group(1).replaceAll("\u00a7[0-9a-z]{1}", "") : null;
    }

    public static int getCharWidth(char c)
    {
        ArrayList char2px = new ArrayList();
        ArrayList char3px = new ArrayList();
        ArrayList char4px = new ArrayList();
        ArrayList char5px = new ArrayList();
        ArrayList char7px = new ArrayList();
        Character[] list2px = new Character[] {'i', ',', ';', ':', '!', '|', '`'};
        Character[] list3px = new Character[] {'l', Character.valueOf('\''), Character.valueOf('\u00a4')};
        Character[] list4px = new Character[] {'t', '[', ']', 'I'};
        Character[] list5px = new Character[] {'f', 'k', Character.valueOf('\"'), '(', ')', '*', '{', '}', '<', '>'};
        Character[] list7px = new Character[] {'~', '@'};
        char2px.addAll(Arrays.asList(list2px));
        char3px.addAll(Arrays.asList(list3px));
        char4px.addAll(Arrays.asList(list4px));
        char5px.addAll(Arrays.asList(list5px));
        char7px.addAll(Arrays.asList(list7px));
        byte width = 6;

        if (char2px.contains(Character.valueOf(c)))
        {
            width = 2;
        }
        else if (char3px.contains(Character.valueOf(c)))
        {
            width = 3;
        }
        else if (char4px.contains(Character.valueOf(c)))
        {
            width = 4;
        }
        else if (char5px.contains(Character.valueOf(c)))
        {
            width = 5;
        }
        else if (char7px.contains(Character.valueOf(c)))
        {
            width = 7;
        }

        return width;
    }

    public static String filterChatLineString(String line)
    {
        int nbBadges = 0;
        String senderName = getSenderName(line);
        ArrayList displayedBadges = new ArrayList();

        if (senderName != null)
        {
            if (!ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).isEmpty())
            {
                Iterator pattern = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(senderName, SkinType.BADGES).iterator();

                while (pattern.hasNext())
                {
                    AbstractSkin m = (AbstractSkin)pattern.next();

                    if (!displayedBadges.contains(m.getId()))
                    {
                        ++nbBadges;
                    }
                }
            }

            if (GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.containsKey(senderName) && !displayedBadges.contains(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(senderName)) && NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(senderName)))
            {
                ++nbBadges;
            }
        }

        if (nbBadges == 1)
        {
            line = line.replace("##", "   ");
        }
        else if (nbBadges == 2)
        {
            line = line.replace("##", "      ");
        }
        else if (nbBadges >= 3)
        {
            line = line.replace("##", "          ");
        }
        else
        {
            line = line.replace("##", "");
        }

        Pattern var6 = Pattern.compile(":([a-z0-9]+):");
        Matcher var7 = var6.matcher(line);

        while (var7.find())
        {
            if (NationsGUI.EMOTES_SYMBOLS.containsKey(var7.group(1)))
            {
                line = line.replace(var7.group(0), "\u00a70" + (String)NationsGUI.EMOTES_SYMBOLS.get(var7.group(1)) + "\u00a7r ");
            }
        }

        return line;
    }

    public static PrintChatMessageEvent printChatMessage(String chatComponent, int chatLineId)
    {
        PrintChatMessageEvent event = new PrintChatMessageEvent(chatComponent, chatLineId);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static String onClientChat(String message)
    {
        ClientChatEvent event = new ClientChatEvent(message);

        if (MinecraftForge.EVENT_BUS.post(event))
        {
            message = null;
        }
        else
        {
            message = event.getMessage();
        }

        return message;
    }

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, 300.0D);
        tessellator.addVertex((double)par1, (double)par2, 300.0D);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, 300.0D);
        tessellator.addVertex((double)par3, (double)par4, 300.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            RenderItem itemRenderer = new RenderItem();
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

            itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

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

            itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
