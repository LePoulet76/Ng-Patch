/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.JsonElement;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;

public class TranslucentProperty
implements JSONProperty<JSONBlock> {
    @Override
    public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
        return name.equals("translucent");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONBlock block) {
        block.isTranslucent = element.getAsBoolean();
    }
}

