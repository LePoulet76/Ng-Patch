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

   public static Map<String, Class<? extends EmoteBase>> emoteMap = new TreeMap();
   private static WeakHashMap<EntityPlayer, EmoteBase> playerEmotes = new WeakHashMap();
   private static List<EntityPlayer> updatedPlayers = new ArrayList();
   public static Map<String, ResourceLocation> cosmEmotesIcon = new HashMap();
   public static Map<String, ResourceLocation> cosmEmotesTexture = new HashMap();


   public static void putEmote(EntityPlayer player, String emoteName) {
      if(emoteMap.containsKey(emoteName)) {
         putEmote(player, (Class)emoteMap.get(emoteName));
      } else {
         player.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cThat emote doesn\'t exist. Try /emote list."));
      }

   }

   public static void putEmote(EntityPlayer player, Class<? extends EmoteBase> clazz) {
      ModelBiped model = getPlayerModel();
      ModelBiped armorModel = getPlayerArmorModel();
      ModelBiped armorLegModel = getPlayerArmorLegModel();
      if(model.field_78116_c.field_78796_g < 0.0F) {
         model.field_78116_c.field_78796_g = 6.2831855F - model.field_78116_c.field_78796_g;
      }

      model.field_78122_k.field_78807_k = true;

      try {
         playerEmotes.put(player, clazz.getConstructor(new Class[]{EntityPlayer.class, ModelBiped.class, ModelBiped.class, ModelBiped.class}).newInstance(new Object[]{player, model, armorModel, armorLegModel}));
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static void updateEmotes(Entity e) {
      if(e instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)e;
         if(playerEmotes.containsKey(player)) {
            EmoteBase emote = (EmoteBase)playerEmotes.get(player);
            if(emote.isDone()) {
               playerEmotes.remove(player);
               resetModel(getPlayerModel());
               resetModel(getPlayerArmorModel());
               resetModel(getPlayerArmorLegModel());
            } else {
               emote.update(!updatedPlayers.contains(player));
            }

            updatedPlayers.add(player);
         }
      }

   }

   public static void clearPlayerList() {
      updatedPlayers.clear();
   }

   private static ModelBiped getPlayerModel() {
      RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
      return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[]{"modelBipedMain", "f", "field_77109_a"});
   }

   private static ModelBiped getPlayerArmorModel() {
      RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
      return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[]{"modelArmorChestplate", "g", "field_77108_b"});
   }

   private static ModelBiped getPlayerArmorLegModel() {
      RenderPlayer render = (RenderPlayer)RenderManager.field_78727_a.field_78729_o.get(EntityPlayer.class);
      return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[]{"modelArmor", "h", "field_77111_i"});
   }

   private static void resetModel(ModelBiped model) {
      if(model != null) {
         resetPart(model.field_78116_c);
         resetPart(model.field_78114_d);
         resetPart(model.field_78115_e);
         resetPart(model.field_78113_g);
         resetPart(model.field_78112_f);
         resetPart(model.field_78124_i);
         resetPart(model.field_78123_h);
         resetPart(model.field_78122_k);
      }

   }

   private static void resetPart(ModelRenderer renderer) {
      if(renderer != null) {
         renderer.field_78808_h = renderer.field_82906_o = renderer.field_82908_p = renderer.field_82907_q = 0.0F;
         renderer.field_78807_k = false;
      }

   }

}
