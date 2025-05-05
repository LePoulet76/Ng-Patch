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
   public static char REF_CHAR = 22;


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
      ArrayList list = new ArrayList();
      Matcher tagMatcher = TAG_EXTRACTOR.matcher(line);

      while(tagMatcher.find()) {
         String rawTag = tagMatcher.group(0);
         if(rawTag.charAt(0) == 42) {
            String[] parts = rawTag.split("\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if(parts[0].length() > 1) {
               String tagName = parts[0].substring(1);
               Class classe = (Class)registeredTags.get(tagName);
               if(classe != null) {
                  try {
                     AbstractChatTag e = (AbstractChatTag)classe.getConstructor(new Class[]{Map.class}).newInstance(new Object[]{parseParams(parts)});
                     list.add(e);
                     line = line.replace("[" + rawTag + "]", String.valueOf(REF_CHAR));
                  } catch (Exception var8) {
                     var8.printStackTrace();
                     line = line.replace("[" + rawTag + "]", "");
                  }
               }
            }
         }
      }

      return new Tuple(line, list);
   }

   private static Map<String, String> parseParams(String[] tagParts) {
      HashMap params = new HashMap();

      for(int j = 1; j < tagParts.length; ++j) {
         Matcher paramMatcher = PARAM_EXTRACTOR.matcher(tagParts[j]);
         if(paramMatcher.find()) {
            params.put(paramMatcher.group(1), paramMatcher.group(2));
         }
      }

      return params;
   }

   public static List<Tuple<String, ArrayList<AbstractChatTag>>> generateMultiLineWithTags(String message, int width) {
      FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
      Tuple tuple = parseChatLine(message);
      String str = (String)tuple.a;
      ArrayList tags = (ArrayList)tuple.b;
      ArrayList list = new ArrayList();
      int currentWidth = 0;
      int tagI = 0;
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList lineTags = new ArrayList();
      char lastColor = 32;
      boolean captureColor = false;
      int lineHeight = 1;

      int j;
      for(j = 0; j < str.length(); ++j) {
         char c = str.charAt(j);
         AbstractChatTag abstractChatTag = null;
         int charWidth;
         if(c == REF_CHAR) {
            abstractChatTag = (AbstractChatTag)tags.get(tagI);
            charWidth = abstractChatTag.getWidth();
            lineHeight = Math.max(lineHeight, abstractChatTag.getLineHeight());
            ++tagI;
         } else if(c == 167) {
            captureColor = true;
            charWidth = 0;
         } else if(captureColor) {
            lastColor = c;
            captureColor = false;
            charWidth = 0;
         } else {
            charWidth = fontRenderer.func_78263_a(c);
         }

         if(charWidth + currentWidth > width) {
            String[] parts = stringBuilder.toString().split(" ");
            String last = parts[parts.length - 1];
            if(stringBuilder.length() > 0 && stringBuilder.length() - last.length() - 1 >= 0 && (last.length() < 30 || last.contains("http://") || last.contains("https://"))) {
               stringBuilder.delete(stringBuilder.length() - last.length() - 1, stringBuilder.length());
            } else {
               last = "";
            }

            list.add(new Tuple(stringBuilder.toString(), lineTags));
            if(abstractChatTag != null) {
               for(int j1 = 1; j1 < lineHeight; ++j1) {
                  list.add(new Tuple("", new ArrayList()));
               }
            }

            stringBuilder = new StringBuilder();
            if(lastColor != 32) {
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
         if(abstractChatTag != null) {
            lineTags.add(abstractChatTag);
         }
      }

      if(stringBuilder.length() > 0) {
         list.add(new Tuple(stringBuilder.toString(), lineTags));

         for(j = 1; j < lineHeight; ++j) {
            list.add(new Tuple("", new ArrayList()));
         }
      }

      return list;
   }

}
