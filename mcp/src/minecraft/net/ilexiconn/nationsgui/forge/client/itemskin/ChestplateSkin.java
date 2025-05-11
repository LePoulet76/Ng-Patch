/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.itemskin.BodyPartSkin;
import org.json.simple.JSONObject;

public class ChestplateSkin
extends BodyPartSkin {
    public ChestplateSkin(JSONObject object) {
        super(object);
    }

    @Override
    protected String getDomain() {
        return "chestplates";
    }
}

