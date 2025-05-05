package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SkinManager {

   private final Map<String, List<AbstractSkin>> mapPlayerSkin = new HashMap();
   private final Map<String, AbstractSkin> idMap = new HashMap();
   public HashMap<SkinType, HashMap<String, List<AbstractSkin>>> cachedPlayersSkin = new HashMap();


   public static void parseAndConvert(Object o) {
      if(o instanceof JSONArray) {
         JSONArray object = (JSONArray)o;

         for(int i = 0; i < object.size(); ++i) {
            parseAndConvert(object.get(i));
         }
      } else if(o instanceof JSONObject) {
         JSONObject var5 = (JSONObject)o;
         Iterator var6 = var5.keySet().iterator();

         while(var6.hasNext()) {
            Object key = var6.next();
            Object value = var5.get(key);
            if(value instanceof Long) {
               var5.put(key, Double.valueOf(((Long)value).doubleValue()));
            } else if(value instanceof JSONArray || value instanceof JSONObject) {
               parseAndConvert(value);
            }
         }
      }

   }

   public void loadSkins() throws IOException, ParseException {
      JSONParser jsonParser = new JSONParser();
      JSONArray manifest = (JSONArray)jsonParser.parse((Reader)(new FileReader("assets/indexes/skins.json")));
      parseAndConvert(manifest);
      Iterator var3 = manifest.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         JSONObject object = (JSONObject)o;
         String id = (String)object.get("id");

         try {
            if(this.idMap.containsKey(id)) {
               ((AbstractSkin)this.idMap.get(id)).loadTransforms(object);
            } else {
               SkinType e = SkinType.valueOf(((String)object.get("type")).toUpperCase());
               AbstractSkin skin = e.createSkin(object);
               this.idMap.put(id, skin);
            }
         } catch (IllegalArgumentException var9) {
            Minecraft.func_71410_x().func_98033_al().func_98236_b("ItemSkin Skipped - Unknown Type - ID : " + id);
         } catch (IllegalAccessException var10) {
            Minecraft.func_71410_x().func_98033_al().func_98235_b("ItemSkin Skipped - Critical error - ID : " + id, var10);
         }
      }

   }

   public AbstractSkin getSkinFromID(String id) {
      return (AbstractSkin)this.idMap.get(id);
   }

   public boolean playerHasSkin(String player, AbstractSkin abstractSkin) {
      return AbstractItemSkin.forcedRenderSkin != null?abstractSkin == AbstractSkin.forcedRenderSkin:(SkinType.is3DSkin(abstractSkin) && !ClientProxy.clientConfig.render3DSkins?false:(!this.mapPlayerSkin.containsKey(player)?false:((List)this.mapPlayerSkin.get(player)).contains(abstractSkin)));
   }

   public boolean playerHasSkin(String player, String skinID) {
      return this.playerHasSkin(player, this.getSkinFromID(skinID));
   }

   public List<AbstractSkin> getPlayerSkins(String player) {
      return (List)this.mapPlayerSkin.get(player);
   }

   public void setPlayerSkins(String playerName, List<String> skins) {
      ArrayList abstractSkins = new ArrayList();
      Iterator var4 = skins.iterator();

      while(var4.hasNext()) {
         String skinID = (String)var4.next();
         AbstractSkin skin = this.getSkinFromID(skinID);
         if(skin != null) {
            abstractSkins.add(skin);
         }
      }

      this.mapPlayerSkin.put(playerName, abstractSkins);
      this.clearPlayerCachedSkins(playerName, (SkinType)null);
   }

   public AbstractSkin getUniquePlayerActiveSkins(String playerName, SkinType ... skinTypes) {
      SkinType[] var3 = skinTypes;
      int var4 = skinTypes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SkinType skinType = var3[var5];
         List playerActiveSkins = this.getPlayerActiveSkins(playerName, skinType);
         if(!playerActiveSkins.isEmpty()) {
            return (AbstractSkin)playerActiveSkins.get(0);
         }
      }

      return null;
   }

   public List<AbstractSkin> getPlayerActiveSkins(String playerName, SkinType skinType) {
      AbstractSkin[] var4;
      int var5;
      int var6;
      AbstractSkin skin;
      if(playerName.equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI && ((CosmeticCategoryGUI)Minecraft.func_71410_x().field_71462_r).overridePreviewSkin != null) {
         AbstractSkin skins = ClientProxy.SKIN_MANAGER.getSkinFromID(((CosmeticCategoryGUI)Minecraft.func_71410_x().field_71462_r).overridePreviewSkin);
         var4 = skinType.getSkins();
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            skin = var4[var6];
            if(skin.equals(skins)) {
               return Arrays.asList(new AbstractSkin[]{skins});
            }
         }
      }

      if(!this.cachedPlayersSkin.containsKey(skinType)) {
         this.cachedPlayersSkin.put(skinType, new HashMap());
      }

      if(!((HashMap)this.cachedPlayersSkin.get(skinType)).containsKey(playerName)) {
         ArrayList var8 = new ArrayList();
         var4 = skinType.getSkins();
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            skin = var4[var6];
            if(ClientProxy.SKIN_MANAGER.playerHasSkin(playerName, skin)) {
               var8.add(skin);
            }
         }

         ((HashMap)this.cachedPlayersSkin.get(skinType)).put(playerName, var8);
      }

      return (List)((HashMap)this.cachedPlayersSkin.get(skinType)).get(playerName);
   }

   public void clearPlayerCachedSkins(String playerName, SkinType skinType) {
      if(skinType != null && this.cachedPlayersSkin.containsKey(skinType)) {
         ((HashMap)this.cachedPlayersSkin.get(skinType)).remove(playerName);
      } else if(skinType == null) {
         Iterator var3 = this.cachedPlayersSkin.entrySet().iterator();

         while(var3.hasNext()) {
            Entry entry = (Entry)var3.next();
            ((HashMap)entry.getValue()).remove(playerName);
         }
      }

   }
}
