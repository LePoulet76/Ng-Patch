package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public abstract class JSONRegistry<T extends Object>
{
    private List<JSONProperty<T>> properties = new ArrayList();
    private List<T> registry = new ArrayList();

    public abstract T constructNew(String var1, JsonObject var2);

    public abstract void register(String var1, T var2);

    public abstract Class<T> getType();

    public void addProperty(JSONProperty<T> property)
    {
        this.properties.add(property);
    }

    public List<JSONProperty<T>> getProperties()
    {
        return this.properties;
    }

    public List<T> getRegistry()
    {
        return this.registry;
    }
}
