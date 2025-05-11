/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import org.json.simple.JSONObject;

public abstract class AbstractItemSkin
extends AbstractSkin {
    private final int itemID;

    protected AbstractItemSkin(JSONObject object) {
        super(object);
        this.itemID = Integer.parseInt((String)object.get("itemID"));
    }

    public int getItemID() {
        return this.itemID;
    }

    public static List<AbstractItemSkin> getSkinsOfItem(SkinType type, int itemID) {
        ArrayList<AbstractItemSkin> list = new ArrayList<AbstractItemSkin>();
        for (AbstractSkin abstractSkin : type.getSkins()) {
            AbstractItemSkin abstractItemSkin = (AbstractItemSkin)abstractSkin;
            if (itemID != abstractItemSkin.itemID) continue;
            list.add(abstractItemSkin);
        }
        return list;
    }
}

