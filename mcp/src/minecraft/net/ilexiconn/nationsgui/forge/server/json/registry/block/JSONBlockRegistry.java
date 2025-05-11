/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONMaterial;
import net.minecraft.block.Block;

public class JSONBlockRegistry
extends JSONRegistry<JSONBlock> {
    @Override
    public JSONBlock constructNew(String name, JsonObject object) {
        if (!object.has("id")) {
            return null;
        }
        JSONBlock block = new JSONBlock(object.get("id").getAsInt(), JSONMaterial.parseMaterial(object.get("material").getAsString()));
        block.func_71864_b("nationsgui." + name);
        return block;
    }

    @Override
    public void register(String name, JSONBlock block) {
        GameRegistry.registerBlock((Block)block, (String)("nationsgui." + name));
    }

    @Override
    public Class<JSONBlock> getType() {
        return JSONBlock.class;
    }
}

