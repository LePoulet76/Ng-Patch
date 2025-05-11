/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONParticle;

public class ParticleProperty
implements JSONProperty<JSONBlock> {
    private static Gson GSON = new Gson();

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
        return name.equals("particle");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONBlock block) {
        JsonObject object = element.getAsJsonObject();
        for (Map.Entry entry : object.entrySet()) {
            ArrayList<Object> particles = new ArrayList<Object>();
            JsonArray array = ((JsonElement)entry.getValue()).getAsJsonArray();
            for (JsonElement e : array) {
                particles.add(GSON.fromJson(e, JSONParticle.class));
            }
            block.particles.put((String)entry.getKey(), (List<JSONParticle>)particles);
        }
    }
}

