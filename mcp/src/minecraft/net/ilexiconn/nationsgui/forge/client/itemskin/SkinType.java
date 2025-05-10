package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;

public enum SkinType
{
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
    private final Class <? extends AbstractSkin > aClass;
    private final List<AbstractSkin> skinList = new ArrayList();
    private static final Map<AbstractSkin, Boolean> skin3DMap = new HashMap();
    private final boolean is3D;

    private SkinType(Class aClass, boolean is3D)
    {
        this.aClass = aClass;
        this.is3D = is3D;
    }

    public static boolean is3DSkin(AbstractSkin abstractSkin)
    {
        return ((Boolean)skin3DMap.get(abstractSkin)).booleanValue();
    }

    public AbstractSkin[] getSkins()
    {
        return (AbstractSkin[])this.skinList.toArray(new AbstractSkin[0]);
    }

    public AbstractSkin createSkin(JSONObject jsonObject) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractSkin abstractSkin = (AbstractSkin)this.aClass.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{jsonObject});
        this.skinList.add(abstractSkin);
        skin3DMap.put(abstractSkin, Boolean.valueOf(this.is3D));
        return abstractSkin;
    }
}
