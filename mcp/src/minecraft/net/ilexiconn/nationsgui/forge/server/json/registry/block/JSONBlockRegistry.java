package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;

public class JSONBlockRegistry extends JSONRegistry<JSONBlock>
{
    public JSONBlock constructNew(String name, JsonObject object)
    {
        if (!object.has("id"))
        {
            return null;
        }
        else
        {
            JSONBlock block = new JSONBlock(object.get("id").getAsInt(), JSONMaterial.parseMaterial(object.get("material").getAsString()));
            block.setUnlocalizedName("nationsgui." + name);
            return block;
        }
    }

    public void register(String name, JSONBlock block)
    {
        GameRegistry.registerBlock(block, "nationsgui." + name);
    }

    public Class<JSONBlock> getType()
    {
        return JSONBlock.class;
    }

    public void register(String var1, Object var2)
    {
        this.register(var1, (JSONBlock)var2);
    }

    public Object constructNew(String var1, JsonObject var2)
    {
        return this.constructNew(var1, var2);
    }
}
