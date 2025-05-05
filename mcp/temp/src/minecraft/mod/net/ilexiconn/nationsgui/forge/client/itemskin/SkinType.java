package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ArmorSimpleSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ArmorSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BadgeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BuddySkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.CapeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ChestplateSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.EmoteSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.EntitySkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.FlansGunSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.HandSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinBow;
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinModel;
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinSimple;
import org.json.simple.JSONObject;

public enum SkinType {

   ITEM_SIMPLE("ITEM_SIMPLE", 0, ItemSkinSimple.class, false),
   ITEM_MODEL("ITEM_MODEL", 1, ItemSkinModel.class, true),
   BOW("BOW", 2, ItemSkinBow.class, false),
   FLANSMOD_GUN("FLANSMOD_GUN", 3, FlansGunSkin.class, false),
   ENTITY("ENTITY", 4, EntitySkin.class, false),
   HAT("HAT", 5, HatSkin.class, true),
   CHESTPLATE("CHESTPLATE", 6, ChestplateSkin.class, true),
   CAPES("CAPES", 7, CapeSkin.class, false),
   EMOTES("EMOTES", 8, EmoteSkin.class, false),
   BADGES("BADGES", 9, BadgeSkin.class, false),
   BUDDIES("BUDDIES", 10, BuddySkin.class, false),
   ARMOR("ARMOR", 11, ArmorSkin.class, true),
   HANDS("HANDS", 12, HandSkin.class, true),
   ARMOR_SIMPLE("ARMOR_SIMPLE", 13, ArmorSimpleSkin.class, false);
   private final Class<? extends AbstractSkin> aClass;
   private final List<AbstractSkin> skinList = new ArrayList();
   private static final Map<AbstractSkin, Boolean> skin3DMap = new HashMap();
   private final boolean is3D;
   // $FF: synthetic field
   private static final SkinType[] $VALUES = new SkinType[]{ITEM_SIMPLE, ITEM_MODEL, BOW, FLANSMOD_GUN, ENTITY, HAT, CHESTPLATE, CAPES, EMOTES, BADGES, BUDDIES, ARMOR, HANDS, ARMOR_SIMPLE};


   private SkinType(String var1, int var2, Class aClass, boolean is3D) {
      this.aClass = aClass;
      this.is3D = is3D;
   }

   public static boolean is3DSkin(AbstractSkin abstractSkin) {
      return ((Boolean)skin3DMap.get(abstractSkin)).booleanValue();
   }

   public AbstractSkin[] getSkins() {
      return (AbstractSkin[])this.skinList.toArray(new AbstractSkin[0]);
   }

   public AbstractSkin createSkin(JSONObject jsonObject) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
      AbstractSkin abstractSkin = (AbstractSkin)this.aClass.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{jsonObject});
      this.skinList.add(abstractSkin);
      skin3DMap.put(abstractSkin, Boolean.valueOf(this.is3D));
      return abstractSkin;
   }

}
