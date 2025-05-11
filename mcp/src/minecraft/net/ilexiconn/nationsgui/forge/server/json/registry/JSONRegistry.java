/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;

public abstract class JSONRegistry<T> {
    private List<JSONProperty<T>> properties = new ArrayList<JSONProperty<T>>();
    private List<T> registry = new ArrayList<T>();

    public abstract T constructNew(String var1, JsonObject var2);

    public abstract void register(String var1, T var2);

    public abstract Class<T> getType();

    public void addProperty(JSONProperty<T> property) {
        this.properties.add(property);
    }

    public List<JSONProperty<T>> getProperties() {
        return this.properties;
    }

    public List<T> getRegistry() {
        return this.registry;
    }
}

