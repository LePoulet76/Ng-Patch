package net.ilexiconn.nationsgui.forge.server.json.registry;

import com.google.gson.JsonElement;

public interface JSONProperty<T extends Object> {

   boolean isApplicable(String var1, JsonElement var2, T var3);

   void setProperty(String var1, JsonElement var2, T var3);
}
