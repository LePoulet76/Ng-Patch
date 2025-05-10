package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.JsonElement;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;

public class TranslucentProperty implements JSONProperty<JSONBlock>
{
    public boolean isApplicable(String name, JsonElement element, JSONBlock block)
    {
        return name.equals("translucent");
    }

    public void setProperty(String name, JsonElement element, JSONBlock block)
    {
        block.isTranslucent = element.getAsBoolean();
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
