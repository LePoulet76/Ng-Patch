/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.itemskin.BodyPartSkin;
import org.json.simple.JSONObject;

public class HatSkin
extends BodyPartSkin {
    public HatSkin(JSONObject obj) {
        super(obj);
    }

    @Override
    protected String getDomain() {
        return "hats";
    }
}

