package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;

public class LanguageProperty implements JSONProperty<JSONArmorSet>
{
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet)
    {
        return element.isJsonObject();
    }

    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet)
    {
        JsonObject object = element.getAsJsonObject();

        if (object.has("lang"))
        {
            Iterator var5 = object.get("lang").getAsJsonObject().entrySet().iterator();

            while (var5.hasNext())
            {
                Entry entry = (Entry)var5.next();

                if (name.equals("helmet"))
                {
                    LanguageRegistry.instance().addNameForObject(armorSet.getHelmet(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
                else if (name.equals("chestplate"))
                {
                    LanguageRegistry.instance().addNameForObject(armorSet.getChestplate(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
                else if (name.equals("leggings"))
                {
                    LanguageRegistry.instance().addNameForObject(armorSet.getLeggings(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
                else if (name.equals("boots"))
                {
                    LanguageRegistry.instance().addNameForObject(armorSet.getBoots(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
            }
        }
    }

    public void setProperty(String var1, JsonElement var2, Object var3)
    {
        this.setProperty(var1, var2, (JSONArmorSet)var3);
    }

    public boolean isApplicable(String var1, JsonElement var2, Object var3)
    {
        return this.isApplicable(var1, var2, (JSONArmorSet)var3);
    }
}
