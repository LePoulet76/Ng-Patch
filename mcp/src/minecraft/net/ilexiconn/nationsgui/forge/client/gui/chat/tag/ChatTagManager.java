/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.BadgeChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.BadgesChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.BannerChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.CountryChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.CountryFlagChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.EmoteChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.HologramTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ImageTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.PlaceholderTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.PlayerChatTag;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ChatTagManager {
    private static final HashMap<String, Class<? extends AbstractChatTag>> registeredTags = new HashMap();
    private static final Pattern TAG_EXTRACTOR = Pattern.compile("(?<=\\[)(\\*.*?\")(?=\\])");
    private static final Pattern PARAM_EXTRACTOR = Pattern.compile("([a-zA-Z0-9]+)=\\\"(.*?)(?<!\\\\\\\\)\\\"");
    public static char REF_CHAR = (char)22;

    public static void registerTags() {
        registeredTags.put("banner", BannerChatTag.class);
        registeredTags.put("placeholder", PlaceholderTag.class);
        registeredTags.put("image", ImageTag.class);
        registeredTags.put("emote", EmoteChatTag.class);
        registeredTags.put("badge", BadgeChatTag.class);
        registeredTags.put("countryflag", CountryFlagChatTag.class);
        registeredTags.put("badges", BadgesChatTag.class);
        registeredTags.put("country", CountryChatTag.class);
        registeredTags.put("player", PlayerChatTag.class);
        registeredTags.put("hologram", HologramTag.class);
    }

    public static Tuple<String, ArrayList<AbstractChatTag>> parseChatLine(String line) {
        ArrayList<AbstractChatTag> list = new ArrayList<AbstractChatTag>();
        Matcher tagMatcher = TAG_EXTRACTOR.matcher(line);
        while (tagMatcher.find()) {
            String tagName;
            Class<? extends AbstractChatTag> classe;
            String[] parts;
            String rawTag = tagMatcher.group(0);
            if (rawTag.charAt(0) != '*' || (parts = rawTag.split("\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))[0].length() <= 1 || (classe = registeredTags.get(tagName = parts[0].substring(1))) == null) continue;
            try {
                AbstractChatTag abstractChatTag = classe.getConstructor(Map.class).newInstance(ChatTagManager.parseParams(parts));
                list.add(abstractChatTag);
                line = line.replace("[" + rawTag + "]", String.valueOf(REF_CHAR));
            }
            catch (Exception e) {
                e.printStackTrace();
                line = line.replace("[" + rawTag + "]", "");
            }
        }
        return new Tuple<String, ArrayList<AbstractChatTag>>(line, list);
    }

    private static Map<String, String> parseParams(String[] tagParts) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (int j = 1; j < tagParts.length; ++j) {
            Matcher paramMatcher = PARAM_EXTRACTOR.matcher(tagParts[j]);
            if (!paramMatcher.find()) continue;
            params.put(paramMatcher.group(1), paramMatcher.group(2));
        }
        return params;
    }

    public static List<Tuple<String, ArrayList<AbstractChatTag>>> generateMultiLineWithTags(String message, int width) {
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        Tuple<String, ArrayList<AbstractChatTag>> tuple = ChatTagManager.parseChatLine(message);
        String str = (String)tuple.a;
        ArrayList tags = (ArrayList)tuple.b;
        ArrayList<Tuple<String, ArrayList<AbstractChatTag>>> list = new ArrayList<Tuple<String, ArrayList<AbstractChatTag>>>();
        int currentWidth = 0;
        int tagI = 0;
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<AbstractChatTag> lineTags = new ArrayList<AbstractChatTag>();
        char lastColor = ' ';
        boolean captureColor = false;
        int lineHeight = 1;
        for (int i = 0; i < str.length(); ++i) {
            int charWidth;
            char c = str.charAt(i);
            AbstractChatTag abstractChatTag = null;
            if (c == REF_CHAR) {
                abstractChatTag = (AbstractChatTag)((Object)tags.get(tagI));
                charWidth = abstractChatTag.getWidth();
                lineHeight = Math.max(lineHeight, abstractChatTag.getLineHeight());
                ++tagI;
            } else if (c == '\u00a7') {
                captureColor = true;
                charWidth = 0;
            } else if (captureColor) {
                lastColor = c;
                captureColor = false;
                charWidth = 0;
            } else {
                charWidth = fontRenderer.func_78263_a(c);
            }
            if (charWidth + currentWidth > width) {
                String[] parts = stringBuilder.toString().split(" ");
                String last = parts[parts.length - 1];
                if (stringBuilder.length() > 0 && stringBuilder.length() - last.length() - 1 >= 0 && (last.length() < 30 || last.contains("http://") || last.contains("https://"))) {
                    stringBuilder.delete(stringBuilder.length() - last.length() - 1, stringBuilder.length());
                } else {
                    last = "";
                }
                list.add(new Tuple(stringBuilder.toString(), lineTags));
                if (abstractChatTag != null) {
                    for (int j = 1; j < lineHeight; ++j) {
                        list.add(new Tuple("", new ArrayList()));
                    }
                }
                stringBuilder = new StringBuilder();
                if (lastColor != ' ') {
                    stringBuilder.append('\u00a7');
                    stringBuilder.append(lastColor);
                }
                stringBuilder.append(last);
                lineTags = new ArrayList();
                currentWidth = 0;
                lineHeight = 1;
            }
            currentWidth += charWidth;
            stringBuilder.append(c);
            if (abstractChatTag == null) continue;
            lineTags.add(abstractChatTag);
        }
        if (stringBuilder.length() > 0) {
            list.add(new Tuple(stringBuilder.toString(), lineTags));
            for (int j = 1; j < lineHeight; ++j) {
                list.add(new Tuple("", new ArrayList()));
            }
        }
        return list;
    }
}

