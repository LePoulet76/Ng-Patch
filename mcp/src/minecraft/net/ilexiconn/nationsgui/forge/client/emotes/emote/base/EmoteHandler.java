/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;

public final class EmoteHandler {
    public static Map<String, Class<? extends EmoteBase>> emoteMap = new TreeMap<String, Class<? extends EmoteBase>>();
    private static WeakHashMap<EntityPlayer, EmoteBase> playerEmotes = new WeakHashMap();
    private static List<EntityPlayer> updatedPlayers = new ArrayList<EntityPlayer>();
    public static Map<String, ResourceLocation> cosmEmotesIcon = new HashMap<String, ResourceLocation>();
    public static Map<String, ResourceLocation> cosmEmotesTexture = new HashMap<String, ResourceLocation>();

    public static void putEmote(EntityPlayer player, String emoteName) {
        if (emoteMap.containsKey(emoteName)) {
            EmoteHandler.putEmote(player, emoteMap.get(emoteName));
        } else {
            player.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cThat emote doesn't exist. Try /emote list."));
        }
    }

    public static void putEmote(EntityPlayer player, Class<? extends EmoteBase> clazz) {
        ModelBiped model = EmoteHandler.getPlayerModel();
        ModelBiped armorModel = EmoteHandler.getPlayerArmorModel();
        ModelBiped armorLegModel = EmoteHandler.getPlayerArmorLegModel();
        if (model.field_78116_c.field_78796_g < 0.0f) {
            model.field_78116_c.field_78796_g = (float)Math.PI * 2 - model.field_78116_c.field_78796_g;
        }
        model.field_78122_k.field_78807_k = true;
        try {
            playerEmotes.put(player, clazz.getConstructor(EntityPlayer.class, ModelBiped.class, ModelBiped.class, ModelBiped.class).newInstance(player, model, armorModel, armorLegModel));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateEmotes(Entity e) {
        EntityPlayer player;
        if (e instanceof EntityPlayer && playerEmotes.containsKey(player = (EntityPlayer)e)) {
            EmoteBase emote = playerEmotes.get(player);
            if (emote.isDone()) {
                playerEmotes.remove(player);
                EmoteHandler.resetModel(EmoteHandler.getPlayerModel());
                EmoteHandler.resetModel(EmoteHandler.getPlayerArmorModel());
                EmoteHandler.resetModel(EmoteHandler.getPlayerArmorLegModel());
            } else {
                emote.update(!updatedPlayers.contains(player));
            }
            updatedPlayers.add(player);
        }
    }

    public static void clearPlayerList() {
        updatedPlayers.clear();
    }

    private static ModelBiped getPlayerModel() {
        RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)render, (String[])new String[]{"modelBipedMain", "f", "field_77109_a"});
    }

    private static ModelBiped getPlayerArmorModel() {
        RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)render, (String[])new String[]{"modelArmorChestplate", "g", "field_77108_b"});
    }

    private static ModelBiped getPlayerArmorLegModel() {
        RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)render, (String[])new String[]{"modelArmor", "h", "field_77111_i"});
    }

    private static void resetModel(ModelBiped model) {
        if (model != null) {
            EmoteHandler.resetPart(model.field_78116_c);
            EmoteHandler.resetPart(model.field_78114_d);
            EmoteHandler.resetPart(model.field_78115_e);
            EmoteHandler.resetPart(model.field_78113_g);
            EmoteHandler.resetPart(model.field_78112_f);
            EmoteHandler.resetPart(model.field_78124_i);
            EmoteHandler.resetPart(model.field_78123_h);
            EmoteHandler.resetPart(model.field_78122_k);
        }
    }

    private static void resetPart(ModelRenderer renderer) {
        if (renderer != null) {
            renderer.field_82907_q = 0.0f;
            renderer.field_82908_p = 0.0f;
            renderer.field_82906_o = 0.0f;
            renderer.field_78808_h = 0.0f;
            renderer.field_78807_k = false;
        }
    }
}

