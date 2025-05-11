/*
 * Decompiled with CFR 0.152.
 */
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
    ITEM_SIMPLE(ItemSkinSimple.class, false),
    ITEM_MODEL(ItemSkinModel.class, true),
    BOW(ItemSkinBow.class, false),
    FLANSMOD_GUN(FlansGunSkin.class, false),
    ENTITY(EntitySkin.class, false),
    HAT(HatSkin.class, true),
    CHESTPLATE(ChestplateSkin.class, true),
    CAPES(CapeSkin.class, false),
    EMOTES(EmoteSkin.class, false),
    BADGES(BadgeSkin.class, false),
    BUDDIES(BuddySkin.class, false),
    ARMOR(ArmorSkin.class, true),
    HANDS(HandSkin.class, true),
    ARMOR_SIMPLE(ArmorSimpleSkin.class, false);

    private final Class<? extends AbstractSkin> aClass;
    private final List<AbstractSkin> skinList = new ArrayList<AbstractSkin>();
    private static final Map<AbstractSkin, Boolean> skin3DMap;
    private final boolean is3D;

    private SkinType(Class<? extends AbstractSkin> aClass, boolean is3D) {
        this.aClass = aClass;
        this.is3D = is3D;
    }

    public static boolean is3DSkin(AbstractSkin abstractSkin) {
        return skin3DMap.get(abstractSkin);
    }

    public AbstractSkin[] getSkins() {
        return this.skinList.toArray(new AbstractSkin[0]);
    }

    public AbstractSkin createSkin(JSONObject jsonObject) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractSkin abstractSkin = this.aClass.getConstructor(JSONObject.class).newInstance(jsonObject);
        this.skinList.add(abstractSkin);
        skin3DMap.put(abstractSkin, this.is3D);
        return abstractSkin;
    }

    static {
        skin3DMap = new HashMap<AbstractSkin, Boolean>();
    }
}

