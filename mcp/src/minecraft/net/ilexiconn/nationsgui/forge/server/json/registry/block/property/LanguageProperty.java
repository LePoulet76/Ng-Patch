/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  cpw.mods.fml.common.registry.LanguageRegistry
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.JsonElement;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;

public class LanguageProperty
implements JSONProperty<JSONBlock> {
    @Override
    public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
        return name.equals("lang");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONBlock block) {
        for (Map.Entry entry : element.getAsJsonObject().entrySet()) {
            LanguageRegistry.instance().addNameForObject((Object)block, (String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
        }
    }
}

