/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONEffect;

public class EffectProperty
implements JSONProperty<JSONArmorSet> {
    private static Gson GSON = new Gson();

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
        return name.equals("effects");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        ArrayList<JSONEffect> effects = new ArrayList<JSONEffect>();
        JsonArray array = element.getAsJsonArray();
        for (JsonElement e : array) {
            effects.add((JSONEffect)GSON.fromJson(e, JSONEffect.class));
        }
        armorSet.getHelmet().effects = effects;
    }
}

