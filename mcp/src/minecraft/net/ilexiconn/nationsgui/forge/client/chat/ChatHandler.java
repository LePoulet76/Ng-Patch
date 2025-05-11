/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package net.ilexiconn.nationsgui.forge.client.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;

public enum ChatHandler {
    INSTANCE;

    private JsonParser jsonParser = new JsonParser();
    private Map<String, IChatFallback> fallbackMap = new HashMap<String, IChatFallback>();

    public boolean executeFallback(String message) {
        JsonObject object = this.jsonParser.parse(message).getAsJsonObject();
        if (object == null || !object.has("text")) {
            return false;
        }
        message = object.get("text").getAsString();
        if (this.fallbackMap.containsKey(message)) {
            this.fallbackMap.get(message).call();
            return true;
        }
        return false;
    }

    public void registerCallback(IChatFallback fallback, String ... messages) {
        for (String message : messages) {
            this.fallbackMap.put(message, fallback);
        }
    }
}

