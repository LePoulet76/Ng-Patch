package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.JsonElement;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;

public class LanguageProperty implements JSONProperty<JSONBlock>
{
    public boolean isApplicable(String name, JsonElement element, JSONBlock block)
    {
        return name.equals("lang");
    }

    public void setProperty(String name, JsonElement element, JSONBlock block)
    {
        Iterator var4 = element.getAsJsonObject().entrySet().iterator();

        while (var4.hasNext())
        {
            Entry entry = (Entry)var4.next();
            LanguageRegistry.instance().addNameForObject(block, (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
        }
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
