/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  cpw.mods.fml.common.registry.LanguageRegistry
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;

public class LanguageProperty
implements JSONProperty<JSONArmorSet> {
    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
        return element.isJsonObject();
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        JsonObject object = element.getAsJsonObject();
        if (object.has("lang")) {
            for (Map.Entry entry : object.get("lang").getAsJsonObject().entrySet()) {
                if (name.equals("helmet")) {
                    LanguageRegistry.instance().addNameForObject((Object)armorSet.getHelmet(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                    continue;
                }
                if (name.equals("chestplate")) {
                    LanguageRegistry.instance().addNameForObject((Object)armorSet.getChestplate(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                    continue;
                }
                if (name.equals("leggings")) {
                    LanguageRegistry.instance().addNameForObject((Object)armorSet.getLeggings(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                    continue;
                }
                if (!name.equals("boots")) continue;
                LanguageRegistry.instance().addNameForObject((Object)armorSet.getBoots(), (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }
        }
    }
}

