package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

public abstract class AbstractItemSkin extends AbstractSkin
{
    private final int itemID;

    protected AbstractItemSkin(JSONObject object)
    {
        super(object);
        this.itemID = Integer.parseInt((String)object.get("itemID"));
    }

    public int getItemID()
    {
        return this.itemID;
    }

    public static List<AbstractItemSkin> getSkinsOfItem(SkinType type, int itemID)
    {
        ArrayList list = new ArrayList();
        AbstractSkin[] var3 = type.getSkins();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            AbstractSkin abstractSkin = var3[var5];
            AbstractItemSkin abstractItemSkin = (AbstractItemSkin)abstractSkin;

            if (itemID == abstractItemSkin.itemID)
            {
                list.add(abstractItemSkin);
            }
        }

        return list;
    }
}
