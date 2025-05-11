/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONDrop;

public class DropProperty
implements JSONProperty<JSONBlock> {
    private static Gson GSON = new Gson();

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
        return name.equals("drop");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONBlock block) {
        ArrayList<JSONDrop> drops = new ArrayList<JSONDrop>();
        JsonArray array = element.getAsJsonArray();
        for (JsonElement entry : array) {
            drops.add((JSONDrop)GSON.fromJson(entry, JSONDrop.class));
        }
        block.drops = drops;
    }
}

