package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONEffect;

public class EffectProperty implements JSONProperty<JSONArmorSet>
{
    private static Gson GSON = new Gson();

    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet)
    {
        return name.equals("effects");
    }

    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet)
    {
        ArrayList effects = new ArrayList();
        JsonArray array = element.getAsJsonArray();
        Iterator var6 = array.iterator();

        while (var6.hasNext())
        {
            JsonElement e = (JsonElement)var6.next();
            effects.add(GSON.fromJson(e, JSONEffect.class));
        }

        armorSet.getHelmet().effects = effects;
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
