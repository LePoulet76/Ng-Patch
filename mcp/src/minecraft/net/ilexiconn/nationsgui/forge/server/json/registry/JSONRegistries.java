/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlockRegistry;

public class JSONRegistries {
    private static List<JSONRegistry<?>> REGISTRIES = new ArrayList();
    private static Gson GSON = new Gson();

    public static <T> JSONRegistry<T> getRegistry(Class<T> type) {
        for (JSONRegistry<?> registry : REGISTRIES) {
            if (registry.getType() != type) continue;
            return registry;
        }
        return null;
    }

    public static <T> void parseJSON(JSONRegistry<T> registry, String url) throws IOException {
        InputStream stream = new URL(url).openStream();
        JSONRegistries.parseJSON(registry, stream);
        stream.close();
    }

    public static <T> void parseJSON(JSONRegistry<T> registry, InputStream stream) {
        Type type = new TypeToken<Map<String, JsonElement>>(){}.getType();
        Map map = (Map)GSON.fromJson((Reader)new InputStreamReader(stream), type);
        HashMap<T, String> names = new HashMap<T, String>();
        for (Map.Entry entry : map.entrySet()) {
            JsonObject json;
            String name = (String)entry.getKey();
            T object = registry.constructNew(name, json = ((JsonElement)entry.getValue()).getAsJsonObject());
            if (object == null) continue;
            for (Map.Entry values : json.entrySet()) {
                for (JSONProperty<T> property : registry.getProperties()) {
                    if (!property.isApplicable((String)values.getKey(), (JsonElement)values.getValue(), object)) continue;
                    property.setProperty((String)values.getKey(), (JsonElement)values.getValue(), object);
                }
            }
            names.put(object, name);
            registry.getRegistry().add(object);
        }
        for (Map.Entry object : registry.getRegistry()) {
            registry.register((String)names.get(object), object);
        }
    }

    static {
        REGISTRIES.add(new JSONBlockRegistry());
        REGISTRIES.add(new JSONArmorRegistry());
    }
}

