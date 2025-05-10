package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONDrop;

public class DropProperty implements JSONProperty<JSONBlock>
{
    private static Gson GSON = new Gson();

    public boolean isApplicable(String name, JsonElement element, JSONBlock block)
    {
        return name.equals("drop");
    }

    public void setProperty(String name, JsonElement element, JSONBlock block)
    {
        ArrayList drops = new ArrayList();
        JsonArray array = element.getAsJsonArray();
        Iterator var6 = array.iterator();

        while (var6.hasNext())
        {
            JsonElement entry = (JsonElement)var6.next();
            drops.add(GSON.fromJson(entry, JSONDrop.class));
        }

        block.drops = drops;
    }

    public void setProperty(String var1, JsonElement var2, Object var3)
    {
        this.setProperty(var1, var2, (JSONBlock)var3);
    }

    public boolean isApplicable(String var1, JsonElement var2, Object var3)
    {
        return this.isApplicable(var1, var2, (JSONBlock)var3);
    }
}
