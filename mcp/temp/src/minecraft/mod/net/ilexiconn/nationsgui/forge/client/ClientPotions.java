package net.ilexiconn.nationsgui.forge.client;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

@SideOnly(Side.CLIENT)
public class ClientPotions {

   private static final Map<Integer, List<Potion>> POTIONS = new WeakHashMap();


   public static void addPotionToEntity(EntityLivingBase entity, Potion potion) {
      if(POTIONS.containsKey(Integer.valueOf(entity.field_70157_k))) {
         ((List)POTIONS.get(Integer.valueOf(entity.field_70157_k))).add(potion);
      } else {
         POTIONS.put(Integer.valueOf(entity.field_70157_k), Lists.newArrayList(new Potion[]{potion}));
      }

   }

   public static void removePotionFromEntity(EntityLivingBase entity, Potion potion) {
      if(POTIONS.containsKey(Integer.valueOf(entity.field_70157_k))) {
         ((List)POTIONS.get(Integer.valueOf(entity.field_70157_k))).remove(potion);
      }

   }

   public static List getPotionsForEntity(EntityLivingBase entity) {
      return entity == null?Collections.emptyList():getOrDefault(Integer.valueOf(entity.field_70157_k), Collections.emptyList());
   }

   private static List getOrDefault(Object key, List defaultValue) {
      List v;
      return (v = (List)POTIONS.get(key)) == null && !POTIONS.containsKey(key)?defaultValue:v;
   }

}
