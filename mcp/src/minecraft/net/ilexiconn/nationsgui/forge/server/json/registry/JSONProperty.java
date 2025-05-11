/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.gson.JsonElement;

public interface JSONProperty<T> {
    public boolean isApplicable(String var1, JsonElement var2, T var3);

    public void setProperty(String var1, JsonElement var2, T var3);
}

