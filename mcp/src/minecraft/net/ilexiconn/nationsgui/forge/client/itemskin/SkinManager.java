/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<String, List<AbstractSkin>> mapPlayerSkin = new HashMap<String, List<AbstractSkin>>();
    private final Map<String, AbstractSkin> idMap = new HashMap<String, AbstractSkin>();
    public HashMap<SkinType, HashMap<String, List<AbstractSkin>>> cachedPlayersSkin = new HashMap();

    public static void parseAndConvert(Object o) {
        block4: {
            block3: {
                if (!(o instanceof JSONArray)) break block3;
                JSONArray array = (JSONArray)o;
                for (int i = 0; i < array.size(); ++i) {
                    SkinManager.parseAndConvert(array.get(i));
                }
                break block4;
            }
            if (!(o instanceof JSONObject)) break block4;
            JSONObject object = (JSONObject)o;
            for (Object key : object.keySet()) {
                Object value = object.get(key);
                if (value instanceof Long) {
                    object.put(key, ((Long)value).doubleValue());
                    continue;
                }
                if (!(value instanceof JSONArray) && !(value instanceof JSONObject)) continue;
                SkinManager.parseAndConvert(value);
            }
        }
    }

    public void loadSkins() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray manifest = (JSONArray)jsonParser.parse(new FileReader("assets/indexes/skins.json"));
        SkinManager.parseAndConvert(manifest);
        for (Object o : manifest) {
            JSONObject object = (JSONObject)o;
            String id = (String)object.get("id");
            try {
                if (this.idMap.containsKey(id)) {
                    this.idMap.get(id).loadTransforms(object);
                    continue;
                }
                SkinType skinType = SkinType.valueOf(((String)object.get("type")).toUpperCase());
                AbstractSkin skin = skinType.createSkin(object);
                this.idMap.put(id, skin);
            }
            catch (IllegalArgumentException e) {
                Minecraft.func_71410_x().func_98033_al().func_98236_b("ItemSkin Skipped - Unknown Type - ID : " + id);
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                Minecraft.func_71410_x().func_98033_al().func_98235_b("ItemSkin Skipped - Critical error - ID : " + id, (Throwable)e);
            }
        }
    }

    public AbstractSkin getSkinFromID(String id) {
        return this.idMap.get(id);
    }

    public boolean playerHasSkin(String player, AbstractSkin abstractSkin) {
        if (AbstractItemSkin.forcedRenderSkin != null) {
            return abstractSkin == AbstractSkin.forcedRenderSkin;
        }
        if (SkinType.is3DSkin(abstractSkin) && !ClientProxy.clientConfig.render3DSkins) {
            return false;
        }
        if (!this.mapPlayerSkin.containsKey(player)) {
            return false;
        }
        return this.mapPlayerSkin.get(player).contains(abstractSkin);
    }

    public boolean playerHasSkin(String player, String skinID) {
        return this.playerHasSkin(player, this.getSkinFromID(skinID));
    }

    public List<AbstractSkin> getPlayerSkins(String player) {
        return this.mapPlayerSkin.get(player);
    }

    public void setPlayerSkins(String playerName, List<String> skins) {
        ArrayList<AbstractSkin> abstractSkins = new ArrayList<AbstractSkin>();
        for (String skinID : skins) {
            AbstractSkin skin = this.getSkinFromID(skinID);
            if (skin == null) continue;
            abstractSkins.add(skin);
        }
        this.mapPlayerSkin.put(playerName, abstractSkins);
        this.clearPlayerCachedSkins(playerName, null);
    }

    public AbstractSkin getUniquePlayerActiveSkins(String playerName, SkinType ... skinTypes) {
        for (SkinType skinType : skinTypes) {
            List<AbstractSkin> playerActiveSkins = this.getPlayerActiveSkins(playerName, skinType);
            if (playerActiveSkins.isEmpty()) continue;
            return playerActiveSkins.get(0);
        }
        return null;
    }

    public List<AbstractSkin> getPlayerActiveSkins(String playerName, SkinType skinType) {
        if (playerName.equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI && ((CosmeticCategoryGUI)Minecraft.func_71410_x().field_71462_r).overridePreviewSkin != null) {
            AbstractSkin abstractSkin = ClientProxy.SKIN_MANAGER.getSkinFromID(((CosmeticCategoryGUI)Minecraft.func_71410_x().field_71462_r).overridePreviewSkin);
            for (AbstractSkin skin : skinType.getSkins()) {
                if (!skin.equals(abstractSkin)) continue;
                return Arrays.asList(abstractSkin);
            }
        }
        if (!this.cachedPlayersSkin.containsKey((Object)skinType)) {
            this.cachedPlayersSkin.put(skinType, new HashMap());
        }
        if (!this.cachedPlayersSkin.get((Object)skinType).containsKey(playerName)) {
            ArrayList<AbstractSkin> skins = new ArrayList<AbstractSkin>();
            for (AbstractSkin skin : skinType.getSkins()) {
                if (!ClientProxy.SKIN_MANAGER.playerHasSkin(playerName, skin)) continue;
                skins.add(skin);
            }
            this.cachedPlayersSkin.get((Object)skinType).put(playerName, skins);
        }
        return this.cachedPlayersSkin.get((Object)skinType).get(playerName);
    }

    public void clearPlayerCachedSkins(String playerName, SkinType skinType) {
        if (skinType != null && this.cachedPlayersSkin.containsKey((Object)skinType)) {
            this.cachedPlayersSkin.get((Object)skinType).remove(playerName);
        } else if (skinType == null) {
            for (Map.Entry<SkinType, HashMap<String, List<AbstractSkin>>> entry : this.cachedPlayersSkin.entrySet()) {
                entry.getValue().remove(playerName);
            }
        }
    }
}

